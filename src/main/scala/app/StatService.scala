package app

import org.apache.spark.sql.functions._
import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}
import org.apache.spark.sql.{DataFrame, SQLContext}
import org.apache.spark.{SparkConf, SparkContext}

class StatService(filename: String) {

  private val conf = new SparkConf()
    .setAppName("food-reviews")
    .setMaster("local[*]")
  private val sc = new SparkContext(conf)
  private val sqlContext = new SQLContext(sc)
  private val customSchema = StructType(Array(
    StructField("Id", IntegerType, nullable = false),
    StructField("ProductId", StringType, nullable = false),
    StructField("UserId", StringType, nullable = false),
    StructField("ProfileName", StringType, nullable = false),
    StructField("HelpfulnessNumerator", IntegerType, nullable = false),
    StructField("HelpfulnessDenominator", IntegerType, nullable = false),
    StructField("Score", IntegerType, nullable = false),
    StructField("Time", IntegerType, nullable = false),
    StructField("Summary", StringType, nullable = false),
    StructField("Text", StringType, nullable = false)))

  private val df = sqlContext.read
    .format("com.databricks.spark.csv")
    .option("header", "true")
    .schema(customSchema)
    .load(filename)

  def findMostActiveUsers(num: Int): List[String] =
    mostFrequentByColumn(df, "ProfileName", num)

  private def mostFrequentByColumn(df: DataFrame, columnName: String, amount: Int): List[String] =
    df.groupBy(columnName).count()
      .sort(desc("count"))
      .limit(amount)
      .select(columnName)
      .collect()
      .map(r => r.getString(0))
      .toList

  def findMostCommentedFoodItems(num: Int): List[String] =
    mostFrequentByColumn(df, "ProductId", num)

  def findMostUserWords(num: Int): List[String] = {
    val columnName = "words"
    val words = df.select("Text")
      .explode("Text", columnName)((line: String) => line.split("(?:\\s+|(?!')\\W+)+"))
    mostFrequentByColumn(words, columnName, num)
  }

  def stop(): Unit =
    sc.stop()
}
