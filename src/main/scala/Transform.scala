object Transform extends App {
  
  val source = scala.io.Source.fromFile(args(0))
  val lines = source.getLines.toList
  source.close()

  val columns = Integer.parseInt(args(1))
  model.Transformer.transform(lines, columns)

}