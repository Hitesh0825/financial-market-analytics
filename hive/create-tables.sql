CREATE EXTERNAL TABLE IF NOT EXISTS market_analytics (
 symbol STRING, window_start TIMESTAMP, window_end TIMESTAMP, average_price DOUBLE,
 maximum_price DOUBLE, minimum_price DOUBLE, total_volume BIGINT, price_change DOUBLE
) STORED AS PARQUET LOCATION '/financial-market/analytics';
