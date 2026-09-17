#!/usr/bin/env bash
set -euo pipefail
docker compose exec hbase hbase shell -n <<'EOF' | tee evidence/hbase-scan.log
scan 'market_alerts', {LIMIT => 20}
EOF
