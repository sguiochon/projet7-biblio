package sam.biblio.batch.processor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import sam.biblio.dto.library.Lending;
import sam.biblio.dto.library.Member;
import sam.biblio.web.webclient.LendingWebClient;
import sam.biblio.web.webclient.MemberWebClient;

public class LendingItemProcessor implements ItemProcessor<Lending, Member> {

    @Autowired
    MemberWebClient memberWebClient;

    @Override
    public Member process(Lending lending) throws Exception {
        System.out.println("Lending: id=" + lending.getId()+ ", start=" + lending.getStart() + ", end=" + lending.getEnd());
        return new Member();
    }
}
