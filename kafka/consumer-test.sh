#!/usr/bin/env bash
set -euo pipefail
docker compose exec kafka kafka-console-consumer.sh --bootstrap-server kafka:9092 --topic market-events --from-beginning --max-messages 10
