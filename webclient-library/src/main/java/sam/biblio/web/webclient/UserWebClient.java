package sam.biblio.web.webclient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;
import sam.biblio.dto.PageInfo;
import sam.biblio.dto.security.User;

@Component
public class UserWebClient extends CommonWebClient{

    public UserWebClient(@Value("${api.biblio.endpoint}") String api_biblio_endpoint,
                         @Value("${api.biblio.resource.path.users}") String api_biblio_resource_path,
                         @Value("${api.biblio.basic-authentication.id}") String username,
                         @Value("${api.biblio.basic-authentication.password}") String password){
        super(api_biblio_endpoint, api_biblio_resource_path, username, password);
    }

    public PagedResources<User> findAll(PageInfo page){
        ResponseEntity<PagedResources<User>> response = buildRestTemplate().exchange( buildParams(API_URL, page),
                HttpMethod.GET,
                new HttpEntity(createHeaders(username, password)),
                new ParameterizedTypeReference<PagedResources<User>>() {
                });
        return response.getBody();
    }

}
