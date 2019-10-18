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
import sam.biblio.model.security.User;

import java.net.URISyntaxException;

@Component
public class UserWebClient extends CommonWebClient{

    private String findByEmailURLFragment;
    private String findByEmailParamEmail;

    public UserWebClient(@Value("${api.biblio.endpoint}") String endpoint,
                         @Value("${api.biblio.resource.path.users}") String path,
                         @Value("${api.biblio.basic-authentication.id}") String username,
                         @Value("${api.biblio.basic-authentication.password}") String password,
                         @Value ("${api.biblio.resource.path.users.searchByEmail}") String findByEmailURLFragment,
                         @Value ("${api.biblio.resource.path.users.searchByEmail.emailParam}") String findByEmailParamEmail,
                         @Value("${httpclient.connectTimeout:10000}") int connectTimeout,
                         @Value("${httpclient.readTimeout:10000}") int readTimeout) {
        super(endpoint, path, username, password, connectTimeout, readTimeout);
        this.findByEmailURLFragment = findByEmailURLFragment;
        this.findByEmailParamEmail = findByEmailParamEmail;
    }

    public PagedResources<Resource<User>> findAll(PageInfo page){
        ResponseEntity<PagedResources<Resource<User>>> response = restTemplate.exchange( setUrl(apiEndPoint + resourcePath).addParam(page).buildURL(),
                HttpMethod.GET,
                new HttpEntity(createHeaders(username, password)),
                new ParameterizedTypeReference<PagedResources<Resource<User>>>() {
                });
        return response.getBody();
    }

    public Resource<User> findByResourceUrl(String resourceUrl) {
        ResponseEntity<Resource<User>> response = restTemplate.exchange(setUrl(resourceUrl).buildURL(),
                HttpMethod.GET,
                new HttpEntity(createHeaders(username, password)),
                new ParameterizedTypeReference<Resource<User>>() {
                });
        return response.getBody();
    }

    public Resource<User> findByEmail(String email){
        ResponseEntity<Resource<User>> response = restTemplate.exchange( setUrl(apiEndPoint + findByEmailURLFragment).addParam(findByEmailParamEmail, email).buildURL(),
                HttpMethod.GET,
                new HttpEntity(createHeaders(username, password)),
                new ParameterizedTypeReference<Resource<User>>() {
                });
        return response.getBody();
    }
}
