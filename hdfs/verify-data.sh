#!/usr/bin/env bash
set -euo pipefail
docker compose exec hdfs-namenode hdfs dfs -ls -R /financial-market | tee evidence/hdfs-listing.log
