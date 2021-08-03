package softserve.spark.extractors

import java.net.URL

object Link {
  def unapply(arg: String): Option[String] = {
    val protocols: List[String] = List("http", "ftp")

    val getHostFromUrl = (url: String) => {
      val extractedUrl = url.splitAt (url.indexOf (protocols) )._2
      new URL (extractedUrl).getHost
    }

    if (arg.startsWith(protocols))
      Some(getHostFromUrl(arg))
    else None
  }
}
