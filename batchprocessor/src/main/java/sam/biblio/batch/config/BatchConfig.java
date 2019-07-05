package sam.biblio.batch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sam.biblio.batch.listener.JobCompletionNotificationListener;
import sam.biblio.batch.processor.LendingItemProcessor;
import sam.biblio.batch.reader.LendingWebClientItemReader;
import sam.biblio.batch.writer.MemberItemWriter;
import sam.biblio.dto.library.Lending;
import sam.biblio.dto.library.Member;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;


    @Autowired
    LendingWebClientItemReader reader;

    @Bean
    public ItemReader<Lending> reader() {
        return reader;
    }

    @Bean
    public ItemProcessor processor() {
        return new LendingItemProcessor();
    }

    @Bean
    public ItemWriter writer() {
        return new MemberItemWriter();
    }

    @Bean
    public Job importUserJob(JobCompletionNotificationListener listener, Step step1) {
        return jobBuilderFactory.get("sendMailToMembers")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(step1)
                .end()
                .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .<Lending, Member>chunk(10)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }

}

