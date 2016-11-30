name := "food-reviews"

version := "1.0"

scalaVersion := "2.11.8"

libraryDependencies ++=  Seq(
  "org.apache.spark" % "spark-core_2.11" % "2.0.2",
  "org.apache.spark" % "spark-sql_2.11" % "2.0.2",
  "com.databricks" % "spark-csv_2.11" % "1.5.0"
)
