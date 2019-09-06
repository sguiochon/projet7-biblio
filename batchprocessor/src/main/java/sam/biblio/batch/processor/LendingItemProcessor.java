package sam.biblio.batch.processor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.stereotype.Component;
import sam.biblio.dto.library.Copy;
import sam.biblio.dto.library.Document;
import sam.biblio.dto.library.Lending;
import sam.biblio.dto.library.Member;
import sam.biblio.dto.security.User;
import sam.biblio.web.webclient.*;

import java.util.HashMap;
import java.util.Map;

@Component
public class LendingItemProcessor implements ItemProcessor<Resource<Lending>, Member> {

    @Autowired
    MemberWebClient memberWebClient;

    @Autowired
    UserWebClient userWebClient;

    @Autowired
    CopyWebClient copyWebClient;

    @Autowired
    DocumentWebClient documentWebClient;

    private Map<String, Member> internalMap;

    public void init(){
        internalMap = new HashMap();
    }

    @Override
    public Member process(Resource<Lending> lendingResource) throws Exception {

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

        Member storedMember = internalMap.get(member.getLink("self").getHref());
        if (storedMember==null){
            storedMember = member.getContent();
            storedMember.setUser(userResource.getContent());
            storedMember.addLending(lendingResource.getContent());
            internalMap.put(member.getLink("self").getHref(), storedMember);
            return storedMember;
        }
        else{
            storedMember.addLending(lendingResource.getContent());
            return null;
        }

    }
}
