package sam.biblio.web.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sam.biblio.model.library.Lending;
import sam.biblio.web.webclient.DocumentWebClient;

@Service
public class LendCopyService {

    private final static Log LOG = LogFactory.getLog(LendCopyService.class);

    @Autowired
    private DocumentWebClient documentWebClient;

    //Todo: finaliser le service...

    public Lending lendCopy(Long documentID, String userLogin){

        return null;
    }

}
