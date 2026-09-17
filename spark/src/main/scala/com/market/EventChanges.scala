package com.market
import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.expressions.Window
import org.apache.spark.sql.functions._
/** Per-micro-batch change calculation. A production deployment persists prior values
  * in HBase/state to bridge batches; the first event has null changes. */
object EventChanges {
  def add(df: DataFrame): DataFrame = {
    val w = Window.partitionBy("symbol").orderBy("event_time")
    df.withColumn("previous_price", lag("price", 1).over(w))
      .withColumn("previous_volume", lag("volume", 1).over(w))
      .withColumn("price_change", col("price") - col("previous_price"))
      .withColumn("price_change_percent", when(col("previous_price") =!= 0, col("price_change") / col("previous_price") * 100))
      .withColumn("volume_change", col("volume") - col("previous_volume"))
      .withColumn("volume_change_percent", when(col("previous_volume") =!= 0, col("volume_change") / col("previous_volume") * 100))
  }
}
