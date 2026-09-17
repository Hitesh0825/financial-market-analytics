package com.market
import java.time.{LocalDateTime, ZoneOffset}
import java.time.format.DateTimeFormatter
import scala.util.Random
object MarketEventGenerator {
  private val symbols = Vector("ABC", "XYZ", "MNO", "PQR", "DEF")
  private val f = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
  def next(id: Long, invalid: Double, late: Double): MarketEvent = {
    val bad = Random.nextDouble < invalid
    val ts = if (Random.nextDouble < late) LocalDateTime.now(ZoneOffset.UTC).minusMinutes(3) else LocalDateTime.now(ZoneOffset.UTC)
    MarketEvent(if (bad) "" else f"EVT-$id%06d", if (bad) "" else symbols(Random.nextInt(symbols.size)), if (bad) -1 else 80 + Random.nextDouble * 120, if (bad) 0 else 100 + Random.nextInt(50000), ts.format(f))
  }
}
