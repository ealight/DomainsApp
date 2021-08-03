package softserve.spark.extractors

object Domain {
  def unapply(arg: String): Option[String] = {
    if (arg.matches("[*.*]"))
      Some(arg)
    else None
  }
}
