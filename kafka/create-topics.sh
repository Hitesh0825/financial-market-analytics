#!/usr/bin/env bash
set -euo pipefail
docker compose exec kafka kafka-topics.sh --bootstrap-server kafka:9092 --create --if-not-exists --topic market-events --partitions 3 --replication-factor 1
docker compose exec kafka kafka-topics.sh --bootstrap-server kafka:9092 --describe --topic market-events | tee evidence/kafka-topic-describe.log
