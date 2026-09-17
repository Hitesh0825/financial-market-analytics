#!/usr/bin/env bash
set -euo pipefail
docker compose up -d
./kafka/create-topics.sh
./hdfs/create-directories.sh
