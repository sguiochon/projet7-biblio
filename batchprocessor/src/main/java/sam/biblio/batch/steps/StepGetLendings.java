package sam.biblio.batch.steps;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.stereotype.Component;
import sam.biblio.model.PageInfo;
import sam.biblio.model.library.Copy;
import sam.biblio.model.library.Document;
import sam.biblio.model.library.Lending;
import sam.biblio.model.library.Member;
import sam.biblio.model.security.User;
import sam.biblio.web.webclient.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Component
public class StepGetLendings implements Tasklet, StepExecutionListener {

    private final Logger log = LoggerFactory.getLogger(StepGetLendings.class);

    private PageInfo pageInfo;
    private Map<String, Member> internalMap;
    private LocalDate limitDate;
    private int pageSize;

    private LendingWebClient lendingWebClient;
    @Autowired
    MemberWebClient memberWebClient;

    @Autowired
    UserWebClient userWebClient;

    @Autowired
    CopyWebClient copyWebClient;

    @Autowired
    DocumentWebClient documentWebClient;

    @Autowired
    public StepGetLendings(LendingWebClient lendingWebClient,
                                      @Value("${batch.findByEndDateBefore.forcedValue}") String forcedLimitDate,
                                      @Value("${api.biblio.page.size}") Integer pageSize) {
        this.lendingWebClient = lendingWebClient;
        this.limitDate = forcedLimitDate == null? LocalDate.now() : LocalDate.parse(forcedLimitDate);
        this.pageSize = pageSize;
    }

    @Override
    public void beforeStep(StepExecution stepExecution) {
        this.internalMap = new HashMap();
        this.pageInfo = new PageInfo(pageSize);
        log.debug("Lines Reader initialized.");
    }


    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        do{
            pageInfo = pageInfo.nextPage();
            PagedResources<Resource<Lending>> lendingResources = lendingWebClient.findByEndDateBefore(pageInfo, limitDate);
            for (Resource<Lending> resourceLending : lendingResources.getContent()){
                aggregateMembers(resourceLending);
            }
            pageInfo = PageInfo.fromPagedResourceMetadata(lendingResources.getMetadata());
        }
        while(pageInfo.hasNextPage());
        return RepeatStatus.FINISHED;
    }

    private void aggregateMembers(Resource<Lending> lendingResource) {
        Link memberLink = lendingResource.getLink("member");
        Resource<Member> member = memberWebClient.findByResourceUrl(memberLink.getHref());

        Link userLink = member.getLink("user");
        Resource<User> userResource = userWebClient.findByResourceUrl(userLink.getHref());

        Link copyLink = lendingResource.getLink("copy");
        Resource<Copy> copyResource = copyWebClient.findByResourceUrl(copyLink.getHref());

        Link documentLink = copyResource.getLink("document");
        Resource<Document> documentResource = documentWebClient.findByResourceUrl(documentLink.getHref());

        copyResource.getContent().setDocument(documentResource.getContent());
        lendingResource.getContent().setCopy(copyResource.getContent());

        log.debug("Processing lending #{} made by user {}", lendingResource.getContent().getId(), userResource.getContent().getEmail());

        Member storedMember = internalMap.get(member.getLink("self").getHref());
        if (storedMember==null){
            storedMember = member.getContent();
            storedMember.setUser(userResource.getContent());
            storedMember.addLending(lendingResource.getContent());
            internalMap.put(member.getLink("self").getHref(), storedMember);
        }
        else{
            storedMember.addLending(lendingResource.getContent());
        }
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        stepExecution.getJobExecution().getExecutionContext().put("members", internalMap);
        log.debug("Lendings Reader ended. Found {} members", internalMap.size());
        return ExitStatus.COMPLETED;
    }

}
