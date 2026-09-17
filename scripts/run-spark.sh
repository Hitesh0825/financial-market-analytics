#!/usr/bin/env bash
set -euo pipefail
(cd spark && sbt package)
spark-submit --class com.market.MarketAnalytics spark/target/scala-2.12/*.jar
