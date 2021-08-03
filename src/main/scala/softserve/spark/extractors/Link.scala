package softserve.spark.extractors

import java.net.URL

object Link {
  def unapply(arg: String): Option[String] = {
    val protocols: List[String] = List("http://", "https://", "ftp://")

    protocols
      .find(pr => arg.startsWith(pr))
      .flatMap(_ => Some(new URL(arg).getHost))
  }
}
