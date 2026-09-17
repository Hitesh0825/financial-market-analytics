package com.market
import org.apache.spark.sql.DataFrame
/** Deployment seam: use the HBase connector and row key symbol#timestamp#event_id. */
object HBaseWriter { def writeAlerts(alerts: DataFrame): Unit = alerts.write.format("console").option("truncate",false).save() }
