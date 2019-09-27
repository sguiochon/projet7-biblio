package sam.biblio.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.stereotype.Service;
import sam.biblio.dto.PageInfo;
import sam.biblio.dto.library.Document;
import sam.biblio.web.webclient.DocumentWebClient;


@Service
public class SearchDocumentService {

    @Autowired
    DocumentWebClient documentWebClient;

    public PagedResources<Resource<Document>> searchDocument(int pageSize, int pageNumber, String criteria) {

        PageInfo pageIn = new PageInfo(pageSize);
        pageIn.setNumber(pageNumber);

        System.out.println("Page size: " + pageIn.getSize()+ ", Page nb: " + pageIn.getNumber()+ ", Criteria: " + criteria);

        return documentWebClient.findByTextString(pageIn, criteria);

    }


}
