#!/usr/bin/env bash
set -u
for s in kafka hdfs-namenode hive-server hbase; do
  if docker compose ps --status running --services | grep -qx "$s"; then echo "OK $s"; else echo "DOWN $s"; fi
done
