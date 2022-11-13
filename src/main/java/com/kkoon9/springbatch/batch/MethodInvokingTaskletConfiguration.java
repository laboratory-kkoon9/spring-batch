package com.kkoon9.springbatch.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.step.tasklet.CallableTaskletAdapter;
import org.springframework.batch.core.step.tasklet.MethodInvokingTaskletAdapter;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.Callable;

@EnableBatchProcessing
@SpringBootApplication
public class MethodInvokingTaskletConfiguration {
    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job job() {
        return this.jobBuilderFactory.get("methodInvokingJob")
                .start(methodInvokingJob())
                .build();
    }

    private Step methodInvokingJob() {
        return this.stepBuilderFactory.get("methodInvokingJob")
                .tasklet(methodInvokingTasklet())
                .build();
    }

    private MethodInvokingTaskletAdapter methodInvokingTasklet() {
        MethodInvokingTaskletAdapter methodInvokingTaskletAdapter = new MethodInvokingTaskletAdapter();

        methodInvokingTaskletAdapter.setTargetObject(service());
        methodInvokingTaskletAdapter.setTargetMethod("serviceMethod");

        return methodInvokingTaskletAdapter;
    }

    @Bean
    public CustomerService service() {
        return new CustomerService();
    }

    public static void main(String[] args) {
        SpringApplication.run(MethodInvokingTaskletConfiguration.class, args);
    }
}
