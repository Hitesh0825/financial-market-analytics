| Case | Expected handling |
|---|---|
| missing id/symbol, nonpositive price/volume, invalid timestamp | routed to invalid Parquet output |
| malformed JSON | null parsed fields, routed invalid |
| duplicate event_id | dropped within watermark/state retention |
| out-of-order event within 2m watermark | aggregated by event time |
| older than watermark | may be discarded by Spark |
| volume > 2x preceding average | VOLUME_SPIKE alert |
