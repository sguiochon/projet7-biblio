package sam.biblio.batch.writer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import sam.biblio.model.library.Member;

@Component
@Profile("dev")
public class LogMemberNotifier extends AbstractMemberNotifier {

    public static Logger LOG = LoggerFactory.getLogger(LogMemberNotifier.class);

    @Override
    public void accept(Member member) {
        LOG.info("Member: {} {} ",  member.getUser().getFirstName(), member.getUser().getLastName());

        member.getLendings().stream().forEach((l)->LOG.info("Title: {}, End : {}", l.getCopy().getDocument().getTitle(), l.getEnd()));

    }
}
