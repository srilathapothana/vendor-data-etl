package com.srilatha.etl;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileParseException;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class BatchConfig {

    /** Extract: read the vendor CSV file. */
    @Bean
    public FlatFileItemReader<RawPrice> reader(@Value("${etl.input}") Resource input) {
        return new FlatFileItemReaderBuilder<RawPrice>()
                .name("priceReader")
                .resource(input)
                .linesToSkip(1)
                .delimited()
                .names("symbol", "tradeDate", "price", "currency", "vendor")
                .targetType(RawPrice.class)
                .build();
    }

    /** Load: insert cleaned records into the commodity_prices table. */
    @Bean
    public JdbcBatchItemWriter<PriceRecord> writer(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<PriceRecord>()
                .dataSource(dataSource)
                .sql("INSERT INTO commodity_prices (symbol, trade_date, price, currency, vendor) "
                        + "VALUES (:symbol, :tradeDate, :price, :currency, :vendor)")
                .beanMapped()
                .build();
    }

    @Bean
    public Step loadPricesStep(JobRepository jobRepository, PlatformTransactionManager tx,
                               FlatFileItemReader<RawPrice> reader, PriceProcessor processor,
                               JdbcBatchItemWriter<PriceRecord> writer) {
        return new StepBuilder("loadPrices", jobRepository)
                .<RawPrice, PriceRecord>chunk(5, tx)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .faultTolerant()
                .skip(FlatFileParseException.class)
                .skipLimit(10)
                .build();
    }

    @Bean
    public Job importPricesJob(JobRepository jobRepository, Step loadPricesStep, JobSummaryListener listener) {
        return new JobBuilder("importPrices", jobRepository)
                .listener(listener)
                .start(loadPricesStep)
                .build();
    }
}
