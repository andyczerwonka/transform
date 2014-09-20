package model

import org.joda.time.{ Days, LocalDate }
import org.joda.time.format.DateTimeFormat

object Met {

  type MeasureExport = Seq[Array[String]]

  sealed trait Periodicity
  case object Daily extends Periodicity
  case object Monthly extends Periodicity
  case object Yearly extends Periodicity
  val periodicities = Seq(Daily, Monthly, Yearly)

  def transform(me: MeasureExport, ts: Int): Seq[Seq[String]] = {

    def perdiodicity(from: LocalDate)(to: LocalDate) = Days.daysBetween(from, to).getDays() match {
      case 1 => Daily
      case x if x > 1 && x < 31 => Monthly
      case _ => Yearly
    }

    val firstTwoDates = {
      val ymd = DateTimeFormat.forPattern("yyyy-MM-dd")
      val dateIndex = me.head.indexWhere(_ == "Date")
      me.tail.take(2).map(_(dateIndex)) match {
        case Seq(d1, d2) => (ymd.parseLocalDate(d1), ymd.parseLocalDate(d2))
      }
    }

    val p = perdiodicity(firstTwoDates._1)(_: LocalDate)
    val sot = p(firstTwoDates._2)

    ???
  }

}