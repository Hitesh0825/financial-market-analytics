# Final Technical Report — LAB 28
## Abstract
A simulated Scala/Kafka/Spark market analytics pipeline is supplied with Compose operational definitions.
## Design
Events contain event_id, symbol, price, volume and timestamp. Kafka uses three symbol-keyed partitions. Spark validates, de-duplicates with a two-minute watermark, computes 5m/1m sliding average/max/min/volume outputs, and writes Parquet/checkpoints. HBase and Hive schemas are included.
## Guarantees
Producer idempotence reduces retry duplicates. Spark deduplication is state/window bounded, so this is not end-to-end exactly-once.
## Actual results and limitations
Docker CLI is installed but daemon access was denied; native Spark/Hadoop/Hive/HBase are absent; sbt failed under Java 25. Runtime service, performance and recovery experiments are NOT EXECUTED. This report does not claim them as completed.
