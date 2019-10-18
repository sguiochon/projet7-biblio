package sam.biblio.web.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sam.biblio.model.library.Lending;
import sam.biblio.web.webclient.DocumentWebClient;

@Service
public class LendCopyService {

    private static final Logger log = LoggerFactory.getLogger(LendCopyService.class);

    @Autowired
    private DocumentWebClient documentWebClient;

    //Todo: finaliser le service...

    public Lending lendCopy(Long documentID, String userLogin) {
        log.debug("salut {} {}", documentID, userLogin);
        return null;
    }

}
