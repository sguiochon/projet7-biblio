package sam.biblio.batch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.Resource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import sam.biblio.batch.listener.JobCompletionNotificationListener;
import sam.biblio.batch.processor.LendingItemProcessor;
import sam.biblio.batch.reader.LendingWebClientItemReader;
import sam.biblio.batch.writer.MemberItemWriter;
import sam.biblio.dto.library.Lending;
import sam.biblio.dto.library.Member;

@Configuration
@EnableScheduling
public class BatchConfig {

    private final JobBuilderFactory jobBuilderFactory;

    private final StepBuilderFactory stepBuilderFactory;

    private final JobLauncher jobLauncher;

    private final LendingWebClientItemReader reader;

    private final LendingItemProcessor processor;

    private final MemberItemWriter writer;

    @Autowired
    public BatchConfig(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory, JobLauncher jobLauncher, LendingWebClientItemReader reader, LendingItemProcessor processor, MemberItemWriter writer) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.jobLauncher = jobLauncher;
        this.reader = reader;
        this.processor = processor;
        this.writer = writer;
    }

    @Scheduled(fixedDelay = 5000)
    public void run() throws Exception {
        reader.init();
        processor.init();
        jobLauncher.run(
                importUserJob(),
                new JobParametersBuilder().addLong("uniqueness", System.nanoTime()).toJobParameters()
        );
    }

    @Bean
    public Job importUserJob() {//JobCompletionNotificationListener listener, Step step1) {
        return jobBuilderFactory.get("sendMailToMembers")
                .incrementer(new RunIdIncrementer())
                .listener(new JobCompletionNotificationListener())
                .flow(step1())
                .end()
                .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .<Resource<Lending>, Member>chunk(10)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }


}

