#!/usr/bin/env bash
set -euo pipefail
EVENT_RATE=10 EVENT_COUNT=600 ./scripts/run-producer.sh | tee evidence/low-load.log
