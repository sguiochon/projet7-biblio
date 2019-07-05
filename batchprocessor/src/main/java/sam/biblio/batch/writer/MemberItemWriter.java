package sam.biblio.batch.writer;

import org.springframework.batch.item.ItemWriter;
import sam.biblio.dto.library.Member;

import java.util.List;

public class MemberItemWriter implements ItemWriter<Member> {
    @Override
    public void write(List<? extends Member> list) throws Exception {
        System.out.println("fin traitement. List size: " + list.size());
    }
}
