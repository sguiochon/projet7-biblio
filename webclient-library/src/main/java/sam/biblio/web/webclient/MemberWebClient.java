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
import sam.biblio.model.library.Member;

@Component
public class MemberWebClient extends CommonWebClient {

    private String findByUserEmailURLFragment;
    private String findByUserEmailParam;

    protected MemberWebClient(@Value("${api.biblio.endpoint}") String endpoint,
                              @Value("${api.biblio.resource.path.members}") String resourcePath,
                              @Value("${api.biblio.basic-authentication.id}") String username,
                              @Value("${api.biblio.basic-authentication.password}") String password,
                              @Value("${api.biblio.resource.path.members.searchByUserEmail}") String findByUserEmailURLFragment,
                              @Value("${api.biblio.resource.path.members.searchByUserEmail.param}") String findByUserEmailParam,
                              @Value("${httpclient.connectTimeout:10000}") int connectTimeout,
                              @Value("${httpclient.readTimeout:10000}") int readTimeout) {
        super(endpoint, resourcePath, username, password, connectTimeout, readTimeout);
        this.findByUserEmailURLFragment = findByUserEmailURLFragment;
        this.findByUserEmailParam = findByUserEmailParam;
    }

    public PagedResources<Resource<Member>> findAll(PageInfo page) {
        ResponseEntity<PagedResources<Resource<Member>>> response = restTemplate.exchange(setUrl(apiEndPoint + resourcePath).addParam(page).buildURL(),
                HttpMethod.GET,
                new HttpEntity(createHeaders(username, password)),
                new ParameterizedTypeReference<PagedResources<Resource<Member>>>() {
                });
        return response.getBody();
    }

    public Resource<Member> findByResourceUrl(String resourceUrl) {
        ResponseEntity<Resource<Member>> response = restTemplate.exchange(setUrl(resourceUrl).buildURL(),
                HttpMethod.GET,
                new HttpEntity(createHeaders(username, password)),
                new ParameterizedTypeReference<Resource<Member>>() {
                });
        return response.getBody();
    }

    public Resource<Member> findByUserEmail(String userEmail) {
        ResponseEntity<Resource<Member>> response = restTemplate.exchange(setUrl(apiEndPoint + findByUserEmailURLFragment).addParam(findByUserEmailParam, userEmail).buildURL(),
                HttpMethod.GET,
                new HttpEntity(createHeaders(username, password)),
                new ParameterizedTypeReference<Resource<Member>>() {
                });
        return response.getBody();
    }
}
