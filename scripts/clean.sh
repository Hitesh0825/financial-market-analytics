#!/usr/bin/env bash
set -euo pipefail
docker compose down -v
rm -rf producer/target spark/target
