package com.market
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._
object MarketAnalytics {
  def main(args:Array[String]):Unit = {
    val spark=SparkSession.builder.appName("market-analytics").getOrCreate()
    val kafka=sys.env.getOrElse("KAFKA_BOOTSTRAP_SERVERS","kafka:9092")
    val schema=new StructType().add("event_id",StringType).add("symbol",StringType).add("price",DoubleType).add("volume",LongType).add("timestamp",StringType)
    val source=spark.readStream.format("kafka").option("kafka.bootstrap.servers",kafka).option("subscribe","market-events").option("startingOffsets","earliest").load().selectExpr("CAST(value AS STRING) json")
    val parsed=source.select(from_json(col("json"),schema).as("e"),col("json")).select("e.*","json").withColumn("event_time",to_timestamp(col("timestamp"),"yyyy-MM-dd HH:mm:ss"))
    val valid=parsed.filter(EventValidator.valid).withWatermark("event_time","2 minutes").dropDuplicates("event_id")
    parsed.filter(not(EventValidator.valid)).writeStream.format("parquet").option("path","hdfs:///financial-market/invalid").option("checkpointLocation","hdfs:///checkpoints/market-analytics/invalid").start()
    valid.writeStream.format("parquet").option("path","hdfs:///financial-market/processed").option("checkpointLocation","hdfs:///checkpoints/market-analytics/processed").start()
    val agg=valid.groupBy(window(col("event_time"),"5 minutes","1 minute"),col("symbol")).agg(avg("price").as("average_price"),max("price").as("maximum_price"),min("price").as("minimum_price"),sum("volume").as("total_volume")).select(col("symbol"),col("window.start").as("window_start"),col("window.end").as("window_end"),col("average_price"),col("maximum_price"),col("minimum_price"),col("total_volume"))
    agg.writeStream.format("parquet").option("path","hdfs:///financial-market/analytics").option("checkpointLocation","hdfs:///checkpoints/market-analytics/analytics").outputMode("append").start().awaitTermination()
  }
}
