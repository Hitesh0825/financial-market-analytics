# LAB 28 — Real-Time Financial Market Analytics

An educational, simulated market-data pipeline: a Scala Kafka producer sends keyed JSON events; Spark Structured Streaming validates, de-duplicates, computes event-time windows and alerts; durable outputs are written to HDFS-compatible storage and HBase. Hive exposes analytical Parquet data.

> This is a simulation, not a trading platform. Never connect it to financial accounts or live order flow.

## Architecture
`Scala producer -> Kafka (market-events, 3 partitions, key=symbol) -> Spark -> HDFS/Parquet + HBase alerts -> Hive`.

Run `docker compose up -d kafka hdfs-namenode hdfs-datanode hive-metastore hive-server hbase` (Docker Desktop must be running), then `./scripts/run-all.sh`. Kafka uses one broker in development; `docker-compose.advanced.yml` supplies advanced configuration.

## Configuration and operation
Copy `.env.example` to `.env`. Primary operations: `./scripts/health-check.sh`, `./kafka/create-topics.sh`, `./scripts/run-producer.sh`, `./scripts/run-spark.sh`. See `docs/final-report.md` for validation, limits, and reproducible procedures.
