#!/usr/bin/env bash
set -euo pipefail
docker compose exec hbase hbase shell -n <<'EOF'
create 'market_alerts', 'alert'
EOF
