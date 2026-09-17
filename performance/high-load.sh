#!/usr/bin/env bash
set -euo pipefail
EVENT_RATE=1000 EVENT_COUNT=60000 ./scripts/run-producer.sh | tee evidence/high-load.log
