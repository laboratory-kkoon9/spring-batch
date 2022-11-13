package com.kkoon9.springbatch.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@EnableBatchProcessing
@SpringBootApplication
public class ConditionalJob {
    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Bean
    public Tasklet passTasklet() {
        return ((stepContribution, chunkContext) -> {
            return RepeatStatus.FINISHED;
            // throw new RuntimeException("THis is a failure");
        });
    }

    @Bean
    public Tasklet successTasklet() {
        return (stepContribution, chunkContext) -> {
            System.out.println("Success!");
            return RepeatStatus.FINISHED;
        };
    }

    @Bean
    public Tasklet failTasklet() {
        return (stepContribution, chunkContext) -> {
            System.out.println("Failure!");
            return RepeatStatus.FINISHED;
        };
    }

    @Bean
    public Job job() {
        return this.jobBuilderFactory.get("conditionalJob")
                .start(firstStep())
                .next(decider())
                .from(decider())
                .on("FAILED").to(failStep())
                .from(decider())
                .on("*").to(successStep())
                .end()
                .build();
    }

    private JobExecutionDecider decider() {
        return new RandomDecider();
    }

    @Bean
    public Step firstStep() {
        return this.stepBuilderFactory.get("firstStep")
                .tasklet(passTasklet())
                .build();
    }

    @Bean
    public Step successStep() {
        return this.stepBuilderFactory.get("successStep")
                .tasklet(successTasklet())
                .build();
    }

    @Bean
    public Step failStep() {
        return this.stepBuilderFactory.get("failStep")
                .tasklet(failTasklet())
                .build();
    }

    public static void main(String[] args) {
        SpringApplication.run(ConditionalJob.class, args);
    }
}
