package softserve.spark.extractors

object Email {
  def getHostFromMail(url: String): String = {
    url
      .splitAt(url.indexOf("@") + 1)._2
      .replace("?", "")
  }

  def unapply(arg: String): Option[String] = {
    if (arg.contains("@"))
      Some(arg)
    else None
  }
}
