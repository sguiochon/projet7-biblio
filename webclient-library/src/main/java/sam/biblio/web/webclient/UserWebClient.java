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
import sam.biblio.dto.security.User;

import java.net.URISyntaxException;

@Component
public class UserWebClient extends CommonWebClient{

    public UserWebClient(@Value("${api.biblio.endpoint}") String api_biblio_endpoint,
                         @Value("${api.biblio.resource.path.users}") String api_biblio_resource_path,
                         @Value("${api.biblio.basic-authentication.id}") String username,
                         @Value("${api.biblio.basic-authentication.password}") String password) throws URISyntaxException {
        super(api_biblio_endpoint, api_biblio_resource_path, username, password);
    }

    public PagedResources<Resource<User>> findAll(PageInfo page) throws URISyntaxException {
        ResponseEntity<PagedResources<Resource<User>>> response = buildRestTemplate().exchange( setUrl(apiEndPoint + resourcePath).addParam(page).buildURL(),
                HttpMethod.GET,
                new HttpEntity(createHeaders(username, password)),
                new ParameterizedTypeReference<PagedResources<Resource<User>>>() {
                });
        return response.getBody();
    }

    public Resource<User> findByResourceUrl(String resourceUrl) throws URISyntaxException {
        ResponseEntity<Resource<User>> response = buildRestTemplate().exchange(setUrl(resourceUrl).buildURL(),
                HttpMethod.GET,
                new HttpEntity(createHeaders(username, password)),
                new ParameterizedTypeReference<Resource<User>>() {
                });
        return response.getBody();
    }
}
