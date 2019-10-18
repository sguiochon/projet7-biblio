package sam.biblio.batch.steps;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sam.biblio.batch.notification.MemberNotifier;
import sam.biblio.model.library.Member;

import java.util.Map;

@Component
public class StepNotifyMember implements Tasklet, StepExecutionListener {

    private final Logger log = LoggerFactory.getLogger(StepNotifyMember.class);

    private Map<String, Member> internalMap;

    @Autowired
    MemberNotifier memberNotifier;

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        internalMap.values().forEach(memberNotifier);
        return RepeatStatus.FINISHED;
    }

    @Override
    public void beforeStep(StepExecution stepExecution) {
        ExecutionContext executionContext = stepExecution.getJobExecution().getExecutionContext();
        this.internalMap = (Map<String, Member>) executionContext.get("members");
        log.debug("Lines Processor initialized.");
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        log.debug("Notifier ended. ");
        return ExitStatus.COMPLETED;
    }
}
