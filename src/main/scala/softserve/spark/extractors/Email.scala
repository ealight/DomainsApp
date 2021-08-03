package softserve.spark.extractors

object Email {
  def unapply(arg: String): Option[String] = {
    val getHostFromMail = (url: String) => url
      .splitAt(url.indexOf("@") + 1)._2
      .replace("?", "")

    if (arg.contains("@"))
      Some(getHostFromMail(arg))
    else None
  }
}
