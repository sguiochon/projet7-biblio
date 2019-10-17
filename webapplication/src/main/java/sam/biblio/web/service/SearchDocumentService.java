package sam.biblio.web.service;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import sam.biblio.model.PageInfo;
import sam.biblio.model.library.Copy;
import sam.biblio.model.library.Document;
import sam.biblio.web.dto.DocumentDTO;
import sam.biblio.web.dto.PageDTO;
import sam.biblio.web.webclient.CopyWebClient;
import sam.biblio.web.webclient.DocumentWebClient;
import sam.biblio.web.webclient.LendingWebClient;


@Service
public class SearchDocumentService {

    private final DocumentWebClient documentWebClient;

    private final CopyWebClient copyWebClient;

    private final LendingWebClient lendingWebClient;

    private final DozerBeanMapper mapper;

    @Autowired
    public SearchDocumentService(DocumentWebClient documentWebClient, CopyWebClient copyWebClient, LendingWebClient lendingWebClient, DozerBeanMapper mapper) {
        this.documentWebClient = documentWebClient;
        this.copyWebClient = copyWebClient;
        this.lendingWebClient = lendingWebClient;
        this.mapper = mapper;
    }

    public PageDTO<DocumentDTO> searchDocument(int pageSize, int pageNumber, String criteria) {

        PageInfo pageIn = new PageInfo(pageSize);
        pageIn.setNumber(pageNumber);

        PagedResources<Resource<Document>> result = documentWebClient.findByTextString(pageIn, criteria);
        PageDTO<DocumentDTO> pageDTO = new PageDTO<>();
        mapper.map(result.getMetadata(), pageDTO.getPageMetadata());

        for (Resource<Document> documentResource : result.getContent()) {
            DocumentDTO documentDTO = new DocumentDTO();
            mapper.map(documentResource.getContent(), documentDTO);
            pageDTO.addContent(documentDTO);

            Link copiesLink = documentResource.getLink("copies");
            PagedResources<Resource<Copy>> copyResources = copyWebClient.findByResourcesUrl(copiesLink.getHref());

            // Evaluating the number of free copies of the document:
            for (Resource<Copy> copyResource : copyResources.getContent()) {
                Link lendingLink = copyResource.getLink("lending");
                try {
                    lendingWebClient.findByResourceUrl(lendingLink.getHref());
                    // This copy is lent. We are only considering free copy...
                } catch (HttpClientErrorException e) {
                    // No resource found. Which means there is no Lending associated with the Copy
                    documentDTO.incrementExemplairesLibres();
                }
            }
        }
        return pageDTO;
    }
}
