package sam.biblio.web.webclient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import sam.biblio.model.PageInfo;
import sam.biblio.model.library.Lending;

import java.time.LocalDate;

@Component
public class LendingWebClient extends CommonWebClient {



    private String findByEndBeforeURLFragment;
    private String findByEndBeforeParamDate;

    protected LendingWebClient(@Value("${api.biblio.endpoint}") String endpoint,
                               @Value("${api.biblio.resource.path.lendings}") String resourcePath,
                               @Value("${api.biblio.basic-authentication.id}") String username,
                               @Value("${api.biblio.basic-authentication.password}") String password,
                               @Value("${api.biblio.resource.path.lendings.searchByEndDateBefore}") String findByEndBeforeURLFragment,
                               @Value("${api.biblio.resource.path.lendings.searchByEndDateBefore.dateParam}") String findByEndBeforeParamDate,
                               @Value("${httpclient.connectTimeout:10000}") int connectTimeout,
                               @Value("${httpclient.readTimeout:10000}") int readTimeout) {
        super(endpoint, resourcePath, username, password, connectTimeout, readTimeout);
        this.findByEndBeforeURLFragment = findByEndBeforeURLFragment;
        this.findByEndBeforeParamDate = findByEndBeforeParamDate;
    }

    public PagedResources<Resource<Lending>> findByEndDateBefore(PageInfo page, LocalDate limitDate) {
        ResponseEntity<PagedResources<Resource<Lending>>> response = restTemplate.exchange(setUrl(apiEndPoint + findByEndBeforeURLFragment).addParam(page).addParam(findByEndBeforeParamDate, limitDate.toString()).buildURL(),
                HttpMethod.GET,
                new HttpEntity(createHeaders(username, password)),
                new ParameterizedTypeReference<PagedResources<Resource<Lending>>>() {
                });
        return response.getBody();
    }

    public PagedResources<Resource<Lending>> findAll(PageInfo page) {
        ResponseEntity<PagedResources<Resource<Lending>>> response = restTemplate.exchange(setUrl(apiEndPoint + resourcePath).addParam(page).buildURL(),
                HttpMethod.GET,
                new HttpEntity(createHeaders(username, password)),
                new ParameterizedTypeReference<PagedResources<Resource<Lending>>>() {
                });
        return response.getBody();
    }

    public Resource<Lending> findByResourceUrl(String resourceUrl) {
        ResponseEntity<Resource<Lending>> response = restTemplate.exchange(setUrl(resourceUrl).buildURL(),
                HttpMethod.GET,
                new HttpEntity(createHeaders(username, password)),
                new ParameterizedTypeReference<Resource<Lending>>() {
                });
        return response.getBody();
    }

}
