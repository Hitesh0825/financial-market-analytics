#!/usr/bin/env bash
set -euo pipefail
docker compose exec kafka kafka-consumer-groups.sh --bootstrap-server kafka:9092 --describe --group market-lab | tee evidence/offsets.log
