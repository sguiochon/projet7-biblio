package sam.biblio.web.service;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.stereotype.Service;
import sam.biblio.model.PageInfo;
import sam.biblio.model.library.Document;
import sam.biblio.web.dto.DocumentDTO;
import sam.biblio.web.dto.PageDTO;
import sam.biblio.web.webclient.DocumentWebClient;


@Service
public class SearchDocumentService {

    @Autowired
    DocumentWebClient documentWebClient;

    @Autowired
    private DozerBeanMapper mapper;

    public PageDTO<DocumentDTO> searchDocument(int pageSize, int pageNumber, String criteria) {

        PageInfo pageIn = new PageInfo(pageSize);
        pageIn.setNumber(pageNumber);

        System.out.println("Page size: " + pageIn.getSize() + ", Page nb: " + pageIn.getNumber() + ", Criteria: " + criteria);

        PagedResources<Resource<Document>> result =  documentWebClient.findByTextString(pageIn, criteria);

        PageDTO pageDTO = new PageDTO<DocumentDTO>();

        mapper.map(result.getMetadata(), pageDTO.getPageMetadata());
        result.getContent().forEach(r->{
            DocumentDTO documentDTO = new DocumentDTO();
            mapper.map(r.getContent(), documentDTO);
            pageDTO.addContent(documentDTO);
        });

        return pageDTO;

    }


}
