#!/usr/bin/env bash
set -euo pipefail
./scripts/start.sh
./scripts/run-spark.sh &
./scripts/run-producer.sh
