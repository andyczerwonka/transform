package model

object Transformer {
  
  def transform(me: List[String], ts: Int): List[String] = {
    val header = me.head.split(",")
    header foreach println
    me
  }

}