# Real-Time Financial Market Analytics

A simulated real-time financial-market analytics project built around Apache Kafka, Scala, and Spark Structured Streaming.

The project models market events such as stock prices and trading volumes, then processes them for validation, deduplication, windowed analytics, price/volume changes, and volume-spike alerts.

> Educational simulation only. This project is not a trading platform and must not be connected to real financial accounts, exchanges, or live order systems.

## Features

- Kafka topic: `market-events`
- Three Kafka partitions
- Kafka message key: `symbol`
- Strongly typed Scala market-event model
- Simulated price, volume, duplicate, invalid, and late events
- Event validation and invalid-record routing
- Duplicate handling using `event_id`
- Five-minute event-time analytics window
- One-minute sliding interval
- Average, maximum, minimum price, and total volume
- Price-change and volume-change design
- Configurable volume-spike detection
- Spark watermarking and checkpoints
- HDFS/Parquet, Hive, and HBase integration definitions
- Test fixtures, performance scripts, documentation, and presentation material

## Architecture

```text
Scala Market Event Producer
          |
          v
Kafka: market-events
3 partitions, key = symbol
          |
          v
Spark Structured Streaming
          |
          +--> Validation / Invalid Records
          +--> Deduplication by event_id
          +--> Event-time Windows
          +--> Volume Spike Alerts
          |
          +--> HDFS / Parquet Analytics
          +--> Hive Analytical Queries
          +--> HBase Alert Lookup
Event Schema
{
  "event_id": "EVT-000001",
  "symbol": "ABC",
  "price": 1250.50,
  "volume": 15000,
  "timestamp": "2026-09-17 10:35:00"
}
Field	Description
event_id	Unique logical event identifier
symbol	Financial instrument symbol; also the Kafka key
price	Simulated market price
volume	Simulated trade volume
timestamp	Event-time timestamp in yyyy-MM-dd HH:mm:ss format


Current Runtime Status
Kafka development mode has been verified successfully:
- Apache Kafka broker is running in Docker.
- Topic market-events was created.
- Topic has 3 partitions.
- Replication factor is 1.
- All partitions have a healthy leader and ISR.
HDFS, Hive, HBase, and Spark source/configuration are included, but their runtime deployment requires additional compatible container configuration and a supported Java/Spark environment.
Prerequisites
- Windows with Docker Desktop running
- PowerShell
- Internet access for Docker image downloads
- Java 17 or Java 21 for Scala/sbt/Spark builds
- Optional: Git Bash or WSL for included .sh scripts
Start Kafka
From the project directory:
docker compose up -d
docker compose ps
Expected result:
kafka ... Up ... 0.0.0.0:9092->9092/tcp
Create the Kafka Topic
docker compose exec --workdir /opt/kafka/bin kafka ./kafka-topics.sh --bootstrap-server localhost:9092 --create --if-not-exists --topic market-events --partitions 3 --replication-factor 1
Verify the topic:
docker compose exec --workdir /opt/kafka/bin kafka ./kafka-topics.sh --bootstrap-server localhost:9092 --describe --topic market-events
The output should show:
PartitionCount: 3
ReplicationFactor: 1
Produce a Test Event
'ABC|{"event_id":"EVT-000001","symbol":"ABC","price":1250.50,"volume":15000,"timestamp":"2026-09-17 10:35:00"}' | docker compose exec -T --workdir /opt/kafka/bin kafka ./kafka-console-producer.sh --bootstrap-server localhost:9092 --topic market-events --property parse.key=true --property "key.separator=|"
Consume Test Events
docker compose exec --workdir /opt/kafka/bin kafka ./kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic market-events --from-beginning --max-messages 10 --property print.key=true
Why symbol Is the Kafka Key
Using symbol as the message key ensures that events for the same instrument are consistently routed to the same Kafka partition.
This provides:
- Ordering for each symbol
- Parallel processing across different symbols
- Better scalability as consumer instances increase
- A natural partition strategy for market-event processing
Spark Analytics Design
Spark Structured Streaming is designed to perform:
- JSON parsing with from_json
- Validation of required fields
- Event-time conversion
- Watermarking with a two-minute delay
- Duplicate removal using event_id
- Five-minute windows sliding every minute
- Average price
- Maximum price
- Minimum price
- Total trading volume
- Price and volume change calculations
- Volume-spike alerts when volume exceeds twice the recent average
Validation Rules
A record is invalid when any of the following applies:
- event_id is missing or empty
- symbol is missing or empty
- price <= 0
- volume <= 0
- timestamp cannot be parsed
- JSON is malformed
Invalid records must be isolated from the valid analytics stream and must not stop processing.
Kafka Partitioning
Development configuration:
Topic: market-events
Partitions: 3
Replication factor: 1
Advanced production-style configuration is documented separately:
Brokers: 3
Partitions: 3
Replication factor: 3
min.insync.replicas: 2
Project Structure
producer/       Scala Kafka producer
spark/          Spark Structured Streaming application
kafka/          Kafka administration scripts
hdfs/           HDFS setup and verification scripts
hive/           Hive table definitions and SQL queries
hbase/          HBase schema and verification scripts
tests/          Valid, invalid, duplicate, late, and spike fixtures
performance/    Low-load and high-load test scripts
docs/           Technical documentation and final report
presentation/   Presentation and demo script
Stop Kafka
docker compose down
Known Limitations
- The current Docker Compose runtime starts Kafka only.
- Scala/sbt and Spark require Java 17 or Java 21; Java 25 is not supported by the currently installed sbt setup.
- HDFS, Hive, and HBase require a separately validated full-stack Compose configuration.
- The project does not claim end-to-end exactly-once delivery.
- Spark duplicate handling is bounded by watermark/state retention.
Security
- No real financial credentials are stored or required.
- Use .env.example as a configuration template.
- Do not commit .env files or secret values.
- Use simulated market events only.
License
MIT License.
```
