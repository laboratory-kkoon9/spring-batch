package com.kkoon9.springbatch.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@EnableBatchProcessing
@SpringBootApplication
public class FlowJob {
    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Bean
    public Tasklet loadStockFile() {
        return (stepContribution, chunkContext) -> {
            System.out.println("The stock file has been loaded");
            return RepeatStatus.FINISHED;
        };
    }

    @Bean
    public Tasklet loadCustomerFile() {
        return (stepContribution, chunkContext) -> {
            System.out.println("The customer file has been loaded");
            return RepeatStatus.FINISHED;
        };
    }

    @Bean
    public Tasklet updateStart() {
        return (stepContribution, chunkContext) -> {
            System.out.println("The start has been updated");
            return RepeatStatus.FINISHED;
        };
    }

    @Bean
    public Tasklet runBatchTasklet() {
        return (stepContribution, chunkContext) -> {
            System.out.println("The batch has been run");
            return RepeatStatus.FINISHED;
        };
    }

    @Bean
    public Flow preProcessingFlow() {
        return new FlowBuilder<Flow>("preProcessingFlow").start(loadFileStep())
                .next(loadCustomerStep())
                .next(updateStartStep())
                .build();
    }

    @Bean
    public Step loadFileStep() {
        return this.stepBuilderFactory.get("loadFileStep")
                .tasklet(loadStockFile())
                .build();
    }

    @Bean
    public Step loadCustomerStep() {
        return this.stepBuilderFactory.get("loadCustomerStep")
                .tasklet(loadCustomerFile())
                .build();
    }

    @Bean
    public Step updateStartStep() {
        return this.stepBuilderFactory.get("updateStartStep")
                .tasklet(updateStart())
                .build();
    }

    @Bean
    public Job conditionalStepLogicJob() {
        return this.jobBuilderFactory.get("conditionalStepLogicJob")
                .start(preProcessingFlow())
                .next(runBatch())
                .end()
                .build();
    }

    private Step runBatch() {
        return this.stepBuilderFactory.get("runBatch")
                .tasklet(runBatchTasklet())
                .build();
    }

    public static void main(String[] args) {
        SpringApplication.run(FlowJob.class, args);
    }
}
