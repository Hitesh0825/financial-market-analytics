#!/usr/bin/env bash
set -euo pipefail
(cd producer && sbt "run --bootstrap ${KAFKA_BOOTSTRAP_SERVERS:-localhost:9092} --topic ${KAFKA_TOPIC:-market-events} --rate ${EVENT_RATE:-10} --count ${EVENT_COUNT:-0} --invalid-rate ${INVALID_EVENT_RATE:-0.02} --duplicate-rate ${DUPLICATE_EVENT_RATE:-0.02} --late-rate ${LATE_EVENT_RATE:-0.02}")
