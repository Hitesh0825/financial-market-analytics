# 1. LAB 28 — Real-Time Financial Market Analytics
# 2. Business Problem
Simulated market data needs rapid summaries.
# 3. Objectives
Validate, aggregate, persist and alert.
# 4. Architecture
Scala -> Kafka -> Spark -> HDFS/Hive and HBase.
# 5. Event Schema
ID, symbol, price, volume, timestamp.
# 6. Kafka
market-events and three partitions.
# 7. Partitions
Symbol ordering and parallelism.
# 8. Groups and Offsets
Assignments and recovery.
# 9. Spark
Streaming JSON validation.
# 10. Windows
5-minute sliding analytics.
# 11. HDFS/Hive
Parquet and SQL.
# 12. HBase
Real-time alerts.
# 13. Errors
Bad records are isolated.
# 14. Performance/Fault tolerance
Tests are NOT EXECUTED here.
# 15. Conclusion
Runnable blueprint subject to Docker availability.
