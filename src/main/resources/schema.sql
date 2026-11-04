CREATE TABLE IF NOT EXISTS commodity_prices (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    symbol      VARCHAR(20)   NOT NULL,
    trade_date  DATE          NOT NULL,
    price       DECIMAL(18,4) NOT NULL,
    currency    VARCHAR(3)    NOT NULL,
    vendor      VARCHAR(50),
    loaded_at   TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
