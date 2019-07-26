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
import sam.biblio.dto.library.Copy;

import java.net.URISyntaxException;

@Component
public class CopyWebClient extends CommonWebClient {

    protected CopyWebClient(@Value("${api.biblio.endpoint}") String endpoint,
                            @Value("${api.biblio.resource.path.copies}") String resourcePath,
                            @Value("${api.biblio.basic-authentication.id}") String username,
                            @Value("${api.biblio.basic-authentication.password}") String password) throws URISyntaxException {
        super(endpoint, resourcePath, username, password);
    }

    public PagedResources<Resource<Copy>> findAll(PageInfo page) throws URISyntaxException {
        ResponseEntity<PagedResources<Resource<Copy>>> response = buildRestTemplate().exchange( setUrl(apiEndPoint + resourcePath).addParam(page).buildURL(),
                HttpMethod.GET,
                new HttpEntity(createHeaders(username, password)),
                new ParameterizedTypeReference<PagedResources<Resource<Copy>>>() {
                });
        return response.getBody();
    }

    public Resource<Copy> findByResourceUrl(String resourceUrl) throws URISyntaxException {
        ResponseEntity<Resource<Copy>> response = buildRestTemplate().exchange(setUrl(resourceUrl).buildURL(),
                HttpMethod.GET,
                new HttpEntity(createHeaders(username, password)),
                new ParameterizedTypeReference<Resource<Copy>>() {
                });
        return response.getBody();
    }

}
