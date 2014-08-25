package model

object Transformer {

  val df = new java.text.SimpleDateFormat("yyyy-MM-dd")

  def transform(me: List[Array[String]], ts: Int): List[Array[String]] = {

    me.foreach(row => {
      println("")
      row.foreach(c => {
        print(c)
        print(", ")
      })
    })

    me
  }

}