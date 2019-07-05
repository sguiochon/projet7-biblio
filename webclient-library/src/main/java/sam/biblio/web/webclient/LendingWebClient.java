package sam.biblio.web.webclient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import sam.biblio.dto.PageInfo;
import sam.biblio.dto.library.Lending;
import sam.biblio.dto.security.User;

@Component
public class LendingWebClient extends CommonWebClient {
    protected LendingWebClient(@Value("${api.biblio.endpoint}") String endpoint,
                               @Value("${api.biblio.resource.path.lendings}") String resourcePath,
                               @Value("${api.biblio.basic-authentication.id}") String username,
                               @Value("${api.biblio.basic-authentication.password}") String password){
        super(endpoint, resourcePath, username, password);
    }

    public PagedResources<Lending> getEntities(PageInfo page){
        ResponseEntity<PagedResources<Lending>> response = buildRestTemplate().exchange( buildParams(API_URL, page),
                HttpMethod.GET,
                new HttpEntity(createHeaders(username, password)),
                new ParameterizedTypeReference<PagedResources<Lending>>() {
                });
        return response.getBody();
    }
}
