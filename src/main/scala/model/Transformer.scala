package model

import scala.collection.mutable.ListBuffer

object Transformer {

  def transform(me: Seq[Array[String]], ts: Int): Seq[Seq[String]] = {

    val rawHeader = me.head
    val dateIndex = rawHeader.length - ts - 1

    val res = scala.collection.mutable.HashMap.empty[List[(String, String)], ListBuffer[(String, String)]]
    val rows = me.tail.map(row => {

      val xs = for {
        i <- rawHeader.length - ts until rawHeader.length
        properties = extractProperties(rawHeader.take(dateIndex), row)
        extras = extractExtras(rawHeader(i))
        date = row(dateIndex)
        value = row(i)
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

    buildHeader(res.iterator.next) :: buildData(res)
  }

  private def extractExtras(valueColumn: String) = {
    val pairs = valueColumn.split("\\|")
    pairs.map(t => {
      val pair = t.split("\\:")
      pair(0) -> pair(1)
    })
  }

  def extractProperties(rawHeader: Array[String], row: Array[String]) = {
    for (i <- rawHeader.indices) yield rawHeader(i) -> row(i)
  }

  def buildHeader(row: (List[(String, String)], ListBuffer[(String, String)])) = {
    val left = row._1.map('"' + _._1 + '"')
    val right = row._2.map(_._1)
    left ++ right
  }

  def buildData(res: scala.collection.mutable.HashMap[List[(String, String)], ListBuffer[(String, String)]]) = {
    res.map {
      case (k, v) => {
        val left = k.map('"' + _._2 + '"')
        val right = v.map(n => if (n._2.toDouble != 0.0d) n._2 else "")
        left ++ right
      }
    }.toList
  }

}