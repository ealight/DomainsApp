package softserve.spark

import org.apache.spark.sql.functions.desc
import org.apache.spark.sql.{DataFrame, Dataset, SparkSession}
import softserve.spark.extractors.{Domain, Email, Link}

object Application {

  def main(args: Array[String]): Unit = {
    val session = SparkSession.builder
      .master("local[1]")
      .appName("DomainsApp")
      .getOrCreate

    val filePath = "C:\\Users\\dburk\\SparkProject\\part-00000"

    val file = session.read.textFile(filePath)

    val clearDomains = extractDomain(file, session)

    val fieldDomainName = "domain"
    val outputPath = "data"
    val maxElements = 10000

    val df = clearDomains.toDF(fieldDomainName)
    val groupedByDomainsCount = groupAndCount(fieldDomainName, maxElements, df)

    groupedByDomainsCount.toJavaRDD.saveAsTextFile(outputPath)
  }

  private def groupAndCount(fieldDomainName: String, maxElements: Int, df: DataFrame) = {
    val fieldCountName = "count"

    df
      .groupBy(fieldDomainName)
      .count()
      .orderBy(desc(fieldCountName))
      .limit(maxElements)
  }

  private def extractDomain(file: Dataset[String], spark: SparkSession) = {
    import spark.implicits.newStringEncoder

    file
      .filter(_.nonEmpty)
      .map {
        case Link(line) => Link.getHostFromUrl(line)
        case Email(line) => Email.getHostFromMail(line)
        case Domain(domain) => domain
        case _ => ""
      }
      .filter(_.nonEmpty)
  }
}
