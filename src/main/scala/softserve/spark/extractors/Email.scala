package softserve.spark.extractors

object Email {
  def unapply(arg: String): Option[String] = {
    if (arg.contains("@"))
      arg.split("@").lastOption
    else
      None
  }
}
