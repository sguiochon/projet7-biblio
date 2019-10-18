package sam.biblio.batch.notification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import sam.biblio.model.library.Member;

@Component
@Profile("dev")
public class LogMemberNotifier implements MemberNotifier {

    private static final Logger log = LoggerFactory.getLogger(LogMemberNotifier.class);

    @Override
    public void accept(Member member) {
        log.info("Member: {} {} ",  member.getUser().getFirstName(), member.getUser().getLastName());
        member.getLendings().forEach(l-> log.info("Pret #{}, Exemplaire #{}, Titre: {}, fin : {}", l.getId(), l.getCopy().getId(), l.getCopy().getDocument().getTitle(), l.getEnd()));
    }
}
