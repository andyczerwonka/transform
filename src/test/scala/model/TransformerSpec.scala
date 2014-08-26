package model

import org.junit.runner.RunWith
import org.specs2.mutable.Specification
import org.specs2.runner.JUnitRunner
import scala.io.Source
import au.com.bytecode.opencsv.CSVReader
import java.io.FileReader
import scala.collection.JavaConverters._
import java.io.BufferedReader
import java.io.InputStreamReader

@RunWith(classOf[JUnitRunner])
class TransformerSpec extends Specification {

  "The transformer" should {

    val fileResource = getClass.getResource("/me.txt")
    val in = new BufferedReader(new InputStreamReader(fileResource.openStream()));
    val reader = new CSVReader(in)
    val me = reader.readAll().asScala.toSeq
    reader.close()

    "display file" in {
      val data = Transformer.transform(me.take(10), 2)
      data.length mustEqual 3
    }

  }

}