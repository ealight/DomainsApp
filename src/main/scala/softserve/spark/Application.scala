package softserve.spark

import org.apache.spark.sql.Dataset
import softserve.spark.extractors.{Domain, Email, Link}

object Application {

  import session.implicits._

  def main(args: Array[String]): Unit = {
    val filePath = "C:\\Users\\dburk\\SparkProject\\part-00000"

    val file = session.read.textFile(filePath)

    val clearDomains = extractDomain(file)

    val maxElements = 10000

    val countDomains = clearDomains.rdd
      .groupBy(domain => domain)
      .map {
        case (domain, groupedDomains) => (domain, groupedDomains.count(_ == domain))
      }
      .sortBy(tuple => tuple._2, ascending = false)

    val limitDomains = session.sparkContext.parallelize(countDomains.take(maxElements))

    limitDomains.saveAsTextFile("data")
  }

  private def extractDomain(file: Dataset[String]) = {
    file
      .filter(_.nonEmpty)
      .map {
        case Link(link) => link
        case Email(email) => email
        case Domain(domain) => domain
        case _ => ""
      }
      .filter(_.nonEmpty)
  }

  /*  private def groupAndCount(fieldDomainName: String, maxElements: Int, df: DataFrame) = {
    val fieldCountName = "count"

    df
      .groupBy(fieldDomainName)
      .count()
      .orderBy(desc(fieldCountName))
      .limit(maxElements)
  }*/
}
