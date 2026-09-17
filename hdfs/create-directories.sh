#!/usr/bin/env bash
set -euo pipefail
docker compose exec hdfs-namenode hdfs dfs -mkdir -p /financial-market/{raw,processed,invalid,analytics} /checkpoints/market-analytics
