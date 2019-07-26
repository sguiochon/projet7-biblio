package sam.biblio.web.webclient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import sam.biblio.dto.PageInfo;
import sam.biblio.dto.library.Document;

import java.net.URISyntaxException;

@Component
public class DocumentWebClient extends CommonWebClient {
    protected DocumentWebClient(@Value("${api.biblio.endpoint}") String endpoint,
                                @Value("${api.biblio.resource.path.documents}") String resourcePath,
                                @Value("${api.biblio.basic-authentication.id}") String username,
                                @Value("${api.biblio.basic-authentication.password}") String password) throws URISyntaxException {
        super(endpoint, resourcePath, username, password);
    }

    public PagedResources<Resource<Document>> findAll(PageInfo page) throws URISyntaxException {
        ResponseEntity<PagedResources<Resource<Document>>> response = buildRestTemplate().exchange(setUrl(apiEndPoint + resourcePath).addParam(page).buildURL(),
                HttpMethod.GET,
                new HttpEntity(createHeaders(username, password)),
                new ParameterizedTypeReference<PagedResources<Resource<Document>>>() {
                });
        return response.getBody();
    }

    public Resource<Document> findByResourceUrl(String resourceUrl) throws URISyntaxException {
        ResponseEntity<Resource<Document>> response = buildRestTemplate().exchange(setUrl(resourceUrl).buildURL(),
                HttpMethod.GET,
                new HttpEntity(createHeaders(username, password)),
                new ParameterizedTypeReference<Resource<Document>>() {
                });
        return response.getBody();
    }

}
