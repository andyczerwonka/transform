package model

import scala.collection.mutable.ListBuffer

object Transformer {

  val df = new java.text.SimpleDateFormat("yyyy-MM-dd")

  def transform(me: Seq[Array[String]], ts: Int): Seq[Array[String]] = {

    val rawHeader = me.head
    val dateIndex = rawHeader.length - ts - 1

    val res = scala.collection.mutable.HashMap.empty[List[(String, String)], ListBuffer[(String, String)]]
    val rows = me.tail.map(row => {

      val xs = for {
        i <- rawHeader.length - ts until rawHeader.length
        val properties = extractProperties(rawHeader, row)
        val extras = extractExtras(rawHeader(i))
        val date = row(dateIndex)
        val value = row(i)
      } yield (properties ++ extras).toList -> (date, value)

      xs.foreach {
        case (k, v) => {
          res.get(k) match {
            case Some(buffer) => buffer += v
            case None => res += k -> ListBuffer(v)
          }
        }
      }

    })

    res.foreach(println)
    me
  }

  private def extractExtras(valueColumn: String) = {
    val pairs = valueColumn.split("\\|")
    pairs.map(t => {
      val pair = t.split("\\:")
      pair(0) -> pair(1)
    })
  }

  def extractProperties(rawHeader: Array[String], row: Array[String]) = {
    for (i <- 0 until 2) yield rawHeader(i) -> row(i)
  }

}