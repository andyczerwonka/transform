import au.com.bytecode.opencsv.CSVReader
import java.io.FileReader
import scala.collection.JavaConverters._

object Transform extends App {
  
  val source = scala.io.Source.fromFile(args(0))
  val lines = source.getLines.toList
  source.close()
  
  new FileReader(args(0))
  
  val reader = new CSVReader(new FileReader(args(0)))
  val me = reader.readAll().asScala.toList
  reader.close()

  val count = Integer.parseInt(args(1))
  model.Transformer.transform(me, count)

}