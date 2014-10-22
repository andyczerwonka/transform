import au.com.bytecode.opencsv.CSVReader
import java.io.FileReader
import scala.collection.JavaConverters._
import org.joda.time.format.DateTimeFormat

object Transform extends App {

  // read the file
  val reader = new CSVReader(new FileReader(args(0)))
  val me = reader.readAll().asScala.toSeq
  reader.close()

  // spit out the header
  val dateIndex = me.head.indexWhere(_ == "Date")
  val (from, to) = {
    val ymd = DateTimeFormat.forPattern("yyyy-MM-dd")
    val dates = me.tail.take(2).map(_(dateIndex)).map(ymd.parseLocalDate(_))
    (dates(0), dates(1))
  }
  val rawHeader = me.head
  val periods = args(1).toInt
  val header = model.Met.createHeader(rawHeader, from, to, periods)
  println(header.mkString(","))

  // transform in groups by the first grouping element (project)
  me.tail.groupBy(_(0)).values.foreach(group => {
    val transposed = model.Met.transform(rawHeader, group)
    transposed.foreach(row => println(row.mkString(",")))
  })

}