package softserve.spark.extractors

import java.net.URL

object Link {
  val protocols: List[String] = List("http", "ftp")

  def getHostFromUrl(url: String): String = {
    val extractedUrl = url.splitAt (url.indexOf (protocols) )._2
    new URL (extractedUrl).getHost
  }

  def unapply(arg: String): Option[String] = {
    if (arg.startsWith(protocols))
      Some(arg)
    else None
  }
}
