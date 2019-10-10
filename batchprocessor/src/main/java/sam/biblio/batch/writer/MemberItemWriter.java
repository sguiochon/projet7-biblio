package sam.biblio.batch.writer;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sam.biblio.model.library.Member;

import java.util.List;

@Component
public class MemberItemWriter implements ItemWriter<Member> {

    @Autowired
    AbstractMemberNotifier memberNotifier;

    @Override
    public void write(List<? extends Member> list) throws Exception {
        list.forEach(memberNotifier);
    }
}
