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
class MetSpec extends Specification {

  "The measure export transformer" should {

    val fileResource = getClass.getResource("/pob2.txt")
    val in = new BufferedReader(new InputStreamReader(fileResource.openStream()));
    val reader = new CSVReader(in)
    val me = reader.readAll().asScala.toSeq
    reader.close()

    "extract the header" in {
      val attributes = Met.extractAttributes(me.head)
      attributes._1.length === 2
    }

  }

}