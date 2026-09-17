SELECT symbol, AVG(average_price) FROM market_analytics GROUP BY symbol;
SELECT MAX(maximum_price), MIN(minimum_price), SUM(total_volume) FROM market_analytics;
SELECT symbol, MAX(total_volume) AS highest_volume FROM market_analytics GROUP BY symbol;
