package com.market
import org.apache.spark.sql.Column
import org.apache.spark.sql.functions._
object EventValidator {
  def valid: Column = col("event_id").isNotNull && length(trim(col("event_id")))>0 &&
    col("symbol").isNotNull && length(trim(col("symbol")))>0 && col("price")>0 &&
    col("volume")>0 && col("event_time").isNotNull
}
