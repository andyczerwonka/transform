package model

import org.joda.time.{ Days, LocalDate }
import org.joda.time.format.DateTimeFormat

object Met {

  type MeasureExport = Seq[Array[String]]
  type Header = Array[String]

  sealed trait Periodicity
  case object Daily extends Periodicity
  case object Monthly extends Periodicity
  case object Yearly extends Periodicity

  def transform(me: MeasureExport): Seq[Seq[String]] = {

    def perdiodicity(from: LocalDate, to: LocalDate) = Days.daysBetween(from, to).getDays() match {
      case 1 => Daily
      case x if x > 1 && x < 31 => Monthly
      case _ => Yearly
    }

    val from, to = {
      val ymd = DateTimeFormat.forPattern("yyyy-MM-dd")
      val dateIndex = me.head.indexWhere(_ == "Date")
      val dates = me.tail.take(2).map(_(dateIndex)).map(ymd.parseLocalDate(_))
      dates(0)
      dates(1)
    }
    val p = perdiodicity(from, to)

    val attributes = extractAttributes(me.head)
    println(attributes)
    Nil
  }

  def extractAttributes(header: Header) = {
    val left = header.takeWhile(_ != "Date")
    val columns = header.splitAt(header.indexOf("Date"))._2.tail
    val right = columns.flatMap(parseColumn(_)).toSet
    (left, right)
  }

  def parseColumn(encoded: String) = {
    val pairs = encoded.split("\\|")
    pairs.map(_.split("\\:")(0))
  }

}