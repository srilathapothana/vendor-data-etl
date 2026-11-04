# Vendor Data ETL (Spring Batch)

A small ETL pipeline built with Java, Spring Boot and Spring Batch.

**Extract** - reads commodity price rows from a vendor CSV file (`src/main/resources/data/prices.csv`).
**Transform** - validates and cleans each row: trims and upper-cases symbols and currency, parses dates and prices,
and filters out rows with a missing symbol, bad date, invalid or non-positive price.
**Load** - writes valid rows into the `commodity_prices` table in batches of 5 (chunk-oriented processing).

When the job finishes, a listener logs how many rows were read, filtered, written and skipped.

## Tech
Java 17, Spring Boot 3, Spring Batch, JDBC, H2 (in-memory), Maven

## Run
```bash
mvn spring-boot:run
```
The job runs on startup. Check the console for the summary line, e.g. `read=10, filtered=4, written=6, skipped=0`.

## Use a different database
Change `spring.datasource.*` in `application.properties` and add the matching JDBC driver (MySQL, Oracle, etc.).
