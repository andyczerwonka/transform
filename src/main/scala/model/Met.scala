package model

import org.joda.time.{ Days, LocalDate }
import org.joda.time.format.DateTimeFormat
import scala.collection.mutable.ListBuffer

object Met {

  type MeasureExport = Seq[Array[String]]
  type Header = Array[String]

  sealed trait Periodicity {
    def generate(from: LocalDate, count: Int): Seq[String]
  }

  case object Daily extends Periodicity {
    override def generate(from: LocalDate, count: Int) = {
      for (i <- 0 until count) yield from.plusDays(i).toString("yyyy-MM-dd")
    }
  }

  case object Monthly extends Periodicity {
    override def generate(from: LocalDate, count: Int) = {
      for (i <- 0 until count) yield from.plusMonths(i).toString("yyyy-MM-dd")
    }
  }

  case object Yearly extends Periodicity {
    override def generate(from: LocalDate, count: Int) = {
      for (i <- 0 until count) yield from.plusYears(i).toString("yyyy-MM-dd")
    }
  }

  def transform(me: MeasureExport): Seq[Seq[String]] = {

    val dateIndex = me.head.indexWhere(_ == "Date")
    val (fixedKeys, encodedKeys) = extractAttributes(me.head)
    val encodedColumns = me.head.splitAt(me.head.indexOf("Date"))._2.tail
    val encocdedHeaders = encodedColumns.map(parseEncodedColumn(_))

    val expanded = me.tail.flatMap(mer => {
      val (fixed, withDate) = mer.splitAt(dateIndex)
      withDate.tail.zipWithIndex.map {
        case (value, i) => {
          val rowKey = fixed ++ encodedKeys.map(encocdedHeaders(i).getOrElse(_, ""))
          rowKey.map('"' + _.trim + '"').toIndexedSeq -> value
        }
      }
    })

    val res = scala.collection.mutable.HashMap.empty[IndexedSeq[String], ListBuffer[String]]
    expanded.foreach {
      case (k, v) => {
        res.get(k) match {
          case Some(buffer) => buffer += v
          case None => res += k -> ListBuffer(v)
        }
      }
    }

    val filtered = res.filter {
      case (k, v) => v.exists(_ != "0.0")
    }

    val (from, to) = {
      val ymd = DateTimeFormat.forPattern("yyyy-MM-dd")
      val dates = me.tail.take(2).map(_(dateIndex)).map(ymd.parseLocalDate(_))
      (dates(0), dates(1))
    }
    val dates = perdiodicity(from, to).generate(from, filtered.head._2.size)
    val header = fixedKeys ++ encodedKeys ++ dates
    val data = filtered.map {
      case (attributes, values) => attributes.toSeq ++ values.toSeq
    }

    header.toSeq +: data.toSeq

  }

  def perdiodicity(from: LocalDate, to: LocalDate) = Days.daysBetween(from, to).getDays() match {
    case 1 => Daily
    case x if x > 1 && x <= 31 => Monthly
    case _ => Yearly
  }

  def extractAttributes(header: Header) = {
    val left = header.takeWhile(_ != "Date")
    val columns = header.splitAt(header.indexOf("Date"))._2.tail
    val right = columns.flatMap(parseEncodedColumn(_).keys).toSet.toArray
    (left, right)
  }

  def parseEncodedColumn(encoded: String) = {
    val pairs = encoded.split("\\|")
    pairs.map(_.split("\\:") match { case Array(k, v) => k -> v }).toMap
  }

}