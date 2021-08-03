package softserve

import org.apache.spark.sql.SparkSession

package object spark {
  val session: SparkSession = SparkSession.builder
    .master("local[1]")
    .appName("DomainsApp")
    .getOrCreate

}
