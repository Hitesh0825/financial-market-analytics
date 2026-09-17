package com.market
import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.expressions.Window
import org.apache.spark.sql.functions._
object VolumeSpikeDetector {
  def alerts(df: DataFrame, threshold: Double): DataFrame = {
    val w=Window.partitionBy("symbol").orderBy("event_time").rowsBetween(-20,-1)
    df.withColumn("average_volume",avg("volume").over(w)).withColumn("spike_ratio",col("volume")/col("average_volume"))
      .filter(col("average_volume").isNotNull && col("spike_ratio")>threshold)
      .select(col("event_id"),col("symbol"),col("price"),col("volume"),col("average_volume"),col("spike_ratio"),lit("VOLUME_SPIKE").as("alert_type"),col("event_time").as("timestamp"))
  }
}
