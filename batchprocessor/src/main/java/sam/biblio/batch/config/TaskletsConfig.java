package sam.biblio.batch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import sam.biblio.batch.steps.StepGetLendings;
import sam.biblio.batch.steps.StepNotifyMember;

@Configuration
@EnableScheduling
public class TaskletsConfig {

    private final JobBuilderFactory jobs;

    private final StepBuilderFactory steps;

    private final JobLauncher jobLauncher;

    private final StepGetLendings stepGetLendings;

    private final StepNotifyMember stepNotifyMember;

    @Autowired
    public TaskletsConfig(JobBuilderFactory jobs, StepBuilderFactory steps, JobLauncher jobLauncher, StepGetLendings stepGetLendings, StepNotifyMember stepNotifyMember) {
        this.jobs = jobs;
        this.steps = steps;
        this.jobLauncher = jobLauncher;
        this.stepGetLendings = stepGetLendings;
        this.stepNotifyMember = stepNotifyMember;
    }

    @Bean
    protected Step readLendings() {
        return steps.get("processLendings").tasklet(stepGetLendings).build();
    }

    @Bean
    protected Step notifyMembers() {
        return steps.get("notifyMembers").tasklet(stepNotifyMember).build();
    }

    @Bean
    public Job job() {
        return jobs
                .get("taskletsJob")
                .start(readLendings())
                .next(notifyMembers())
                .build();
    }


    @Scheduled(cron = "${application.cron.pattern}")
    public void run() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
        jobLauncher.run(
                job(),
                new JobParametersBuilder().addLong("uniqueness", System.nanoTime()).toJobParameters()
        );
    }
}
