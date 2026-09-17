.PHONY: up down topic health producer spark
up: ; docker compose up -d
down: ; docker compose down
topic: ; ./kafka/create-topics.sh
health: ; ./scripts/health-check.sh
producer: ; ./scripts/run-producer.sh
spark: ; ./scripts/run-spark.sh
