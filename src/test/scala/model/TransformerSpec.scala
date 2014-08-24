package model

import org.junit.runner.RunWith
import org.specs2.mutable.Specification
import org.specs2.runner.JUnitRunner
import scala.io.Source

@RunWith(classOf[JUnitRunner])
class TransformerSpec extends Specification {

  "The transformer" should {
    
    val content = Source.fromURL(getClass.getResource("/me.txt")).getLines().toList

    "display file" in {
      Transformer.transform(content, 2)
      1 mustEqual 1
    }

  }

}