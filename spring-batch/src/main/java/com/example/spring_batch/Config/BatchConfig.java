package com.example.spring_batch.Config;

import com.example.spring_batch.Processor.WeatherItemProcessor;
import com.example.spring_batch.Reader.WeatherItemReader;
import com.example.spring_batch.Writer.WeatherItemWriter;
import com.example.spring_batch.entity.Weather;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableBatchProcessing
public class BatchConfig {
    private final WeatherItemReader reader;
    private final WeatherItemProcessor processor;
    private final WeatherItemWriter writer;

    public BatchConfig(WeatherItemReader reader, WeatherItemProcessor processor, WeatherItemWriter writer) {
        this.reader = reader;
        this.processor = processor;
        this.writer = writer;
    }

    @Bean
    public Step weatherStep(JobRepository jobRepository, PlatformTransactionManager transactionManager){
        return new StepBuilder("weatherStep",jobRepository)
                .<Weather,Weather>chunk(10000,transactionManager)
                .reader(reader.reader())
                .processor(processor)
                .writer(writer)
                .transactionManager(transactionManager)
                .build();
    }

    @Bean
    public Job weatherJob(JobRepository jobRepository, PlatformTransactionManager transactionManager){
        return new JobBuilder("weatherJob",jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(weatherStep(jobRepository,transactionManager))
                .build();
    }
}
