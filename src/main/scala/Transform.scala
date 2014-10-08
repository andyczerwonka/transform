import au.com.bytecode.opencsv.CSVReader
import java.io.FileReader
import scala.collection.JavaConverters._

object Transform extends App {
  
  val reader = new CSVReader(new FileReader(args(0)))
  val me = reader.readAll().asScala.toSeq
  reader.close()

  var csv = model.Met.transform(me)
  csv.foreach(l => println(l.mkString(",")))

}