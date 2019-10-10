package sam.biblio.web.webclient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import sam.biblio.model.PageInfo;
import sam.biblio.model.library.Document;

@Component
public class DocumentWebClient extends CommonWebClient {

    private String searchByTextURLFragment;
    private String searchByTextParamAuthor;
    private String searchByTextParamTitle;

    protected DocumentWebClient(@Value("${api.biblio.endpoint}") String endpoint,
                                @Value("${api.biblio.resource.path.documents}") String resourcePath,
                                @Value("${api.biblio.basic-authentication.id}") String username,
                                @Value("${api.biblio.basic-authentication.password}") String password,
                                @Value("${api.biblio.resource.path.documents.searchByText}") String searchByTextURLFragment,
                                @Value("${api.biblio.resource.path.documents.searchByText.paramAuthor}") String searchByTextParamAuthor,
                                @Value("${api.biblio.resource.path.documents.searchByText.paramTitle}")String searchByTextParamTitle,
                                @Value("${httpclient.connectTimeout:10000}") int connectTimeout,
                                @Value("${httpclient.readTimeout:10000}") int readTimeout) {
        super(endpoint, resourcePath, username, password, connectTimeout, readTimeout);
        this.searchByTextURLFragment = searchByTextURLFragment;
        this.searchByTextParamAuthor = searchByTextParamAuthor;
        this.searchByTextParamTitle = searchByTextParamTitle;
    }

    public PagedResources<Resource<Document>> findAll(PageInfo page){
        ResponseEntity<PagedResources<Resource<Document>>> response = buildRestTemplate().exchange(setUrl(apiEndPoint + resourcePath).addParam(page).buildURL(),
                HttpMethod.GET,
                new HttpEntity(createHeaders(username, password)),
                new ParameterizedTypeReference<PagedResources<Resource<Document>>>() {
                });
        return response.getBody();
    }

    public Resource<Document> findByResourceUrl(String resourceUrl) {
        ResponseEntity<Resource<Document>> response = buildRestTemplate().exchange(setUrl(resourceUrl).buildURL(),
                HttpMethod.GET,
                new HttpEntity(createHeaders(username, password)),
                new ParameterizedTypeReference<Resource<Document>>() {
                });
        return response.getBody();
    }

    public PagedResources<Resource<Document>> findByTextString(PageInfo page, String criteria){
        ResponseEntity<PagedResources<Resource<Document>>> response = buildRestTemplate().exchange(setUrl(apiEndPoint + searchByTextURLFragment).addParam(page).addParam(searchByTextParamAuthor, criteria).addParam(searchByTextParamTitle, criteria).buildURL(),
                HttpMethod.GET,
                new HttpEntity(createHeaders(username, password)),
                new ParameterizedTypeReference<PagedResources<Resource<Document>>>() {
                });
        return response.getBody();
    }

}
