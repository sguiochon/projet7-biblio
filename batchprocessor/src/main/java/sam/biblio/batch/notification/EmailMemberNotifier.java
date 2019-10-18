package sam.biblio.batch.notification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import sam.biblio.model.library.Lending;
import sam.biblio.model.library.Member;

@Component
@Profile("prod")
public class EmailMemberNotifier implements MemberNotifier {

    private static final Logger log = LoggerFactory.getLogger(EmailMemberNotifier.class);

    private final JavaMailSender mailSender;
    private final Environment env;

    @Autowired
    public EmailMemberNotifier(JavaMailSender javaMailSender, Environment env) {
        this.mailSender = javaMailSender;
        this.env = env;
    }

    @Override
    public void accept(Member member) {
        final SimpleMailMessage email = constructEmailMessage(member);
        log.debug("Envoi d'email: {}", email);
        mailSender.send(email);
    }

    private final SimpleMailMessage constructEmailMessage(final Member member) {
        final String recipientAddress = member.getUser().getEmail();
        final String subject = "Rappel de retour de prêt";

        StringBuilder message = new StringBuilder("Cher lecteur,\r\n La date de retour d'ouvrage(s) emprunté(s) a été dépassée.\r\n");
        message.append("Ouvrage(s) concerné(s):\r\n");

        for (Lending l : member.getLendings()){
            message.append("\t").append(l.getCopy().getDocument().getTitle()).append(", date de retour prévue: ").append(l.getEnd());
        }

        final SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText(message.toString());
        email.setFrom(env.getProperty("email.from"));
        return email;
    }


}
