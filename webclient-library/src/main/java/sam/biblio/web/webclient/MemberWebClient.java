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
import sam.biblio.dto.library.Member;

@Component
public class MemberWebClient extends CommonWebClient {

    protected MemberWebClient(@Value("${api.biblio.endpoint}") String endpoint,
                              @Value("${api.biblio.resource.path.members}") String resourcePath,
                              @Value("${api.biblio.basic-authentication.id}") String username,
                              @Value("${api.biblio.basic-authentication.password}") String password) {
        super(endpoint, resourcePath, username, password);
    }

    public PagedResources<Member> findAll(PageInfo page) {
        ResponseEntity<PagedResources<Member>> response = buildRestTemplate().exchange(buildParams(API_URL, page),
                HttpMethod.GET,
                new HttpEntity(createHeaders(username, password)),
                new ParameterizedTypeReference<PagedResources<Member>>() {
                });
        return response.getBody();
    }

    public Resource<Member> findByResourceUrl(String resourceUrl) {
        ResponseEntity<Resource<Member>> response = buildRestTemplate().exchange(resourceUrl,
                HttpMethod.GET,
                new HttpEntity(createHeaders(username, password)),
                new ParameterizedTypeReference<Resource<Member>>() {
                });
        return response.getBody();
    }
}
