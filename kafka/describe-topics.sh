#!/usr/bin/env bash
set -euo pipefail
docker compose exec kafka kafka-topics.sh --bootstrap-server kafka:9092 --describe --topic market-events | tee evidence/kafka-topic-describe.log
