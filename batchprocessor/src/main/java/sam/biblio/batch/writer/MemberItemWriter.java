package sam.biblio.batch.writer;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import sam.biblio.dto.library.Member;

import java.util.List;

public class MemberItemWriter implements ItemWriter<Member> {

    @Autowired
    AbstractMemberNotifier memberNotifier;

    @Override
    public void write(List<? extends Member> list) throws Exception {
        list.forEach(memberNotifier);
    }
}
