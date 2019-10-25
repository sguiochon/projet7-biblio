package sam.biblio.web.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.stereotype.Service;
import sam.biblio.model.library.Document;
import sam.biblio.model.library.Lending;
import sam.biblio.web.webclient.CopyWebClient;
import sam.biblio.web.webclient.DocumentWebClient;
import sam.biblio.web.webclient.LendingWebClient;

@Service
public class LendCopyService {

    private static final Logger log = LoggerFactory.getLogger(LendCopyService.class);

    @Autowired
    private DocumentWebClient documentWebClient;

    @Autowired
    private LendingWebClient lendingWebClient;

    @Autowired
    private CopyWebClient copyWebClient;

    //Todo: finaliser le service...

    public Lending lendCopy(Long documentID, String userLogin) {
        log.debug("Demande de reservation du document {} par {}", documentID, userLogin);

        // Vérification que le document existe
        Resource<Document> resourceDocument = documentWebClient.findById(documentID);
        if (resourceDocument.getContent()==null)
            log.info("Tentative de prêt d'un document inexistant: {}", documentID);
        else
            log.debug("Prêt - le document existe!");
        // Recherche d'un exemplaire disponible

        // Création d'un prêt

        // Affectation de l'exemplaire au prêt

        // Affectation de l'utilisateur au prêt

        return null;
    }

}
