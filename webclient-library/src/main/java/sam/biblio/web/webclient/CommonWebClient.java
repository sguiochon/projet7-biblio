package sam.biblio.web.webclient;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.hal.Jackson2HalModule;
import org.springframework.http.*;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import sam.biblio.dto.PageInfo;
import sam.biblio.dto.security.User;

import java.nio.charset.Charset;
import java.util.Arrays;


public class CommonWebClient<T> {

    protected String API_URL;
    protected String username;
    protected String password;

    protected CommonWebClient(String api_endpoint, String resource_path, String username, String password) {
        this.API_URL = api_endpoint + resource_path;
        this.username = username;
        this.password = password;
    }

    /**
     * Builds a RestTemplate instance that fixes a bug in SpringBoot Web Client: http header Accept is not
     * valid for Spring Data REST API and must be forced to 'application/hal+json'.
     * @return
     */
    protected RestTemplate buildRestTemplate(){
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.registerModule(new Jackson2HalModule());

        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();

        messageConverter.setSupportedMediaTypes(MediaType.parseMediaTypes("application/hal+json"));
        messageConverter.setObjectMapper(objectMapper);

        return new RestTemplate(Arrays.asList(messageConverter));
    }

    protected HttpHeaders createHeaders(String username, String password){
        return new HttpHeaders() {{
            String auth = username + ":" + password;
            byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")) );
            String authHeader = "Basic " + new String( encodedAuth );
            set( "Authorization", authHeader );
        }};
    }

    protected String buildParams(String url, PageInfo pageInfo){
        StringBuilder str = new StringBuilder(url);
        if (pageInfo==null){
            str.append("?page=0");
        }
        else{
            str.append("?page=").append(pageInfo.getNumber());
            str.append("&size=").append(pageInfo.getSize());
        }
        return str.toString();
    }

    public PagedResources<T> getEntities(PageInfo page){
        ResponseEntity<PagedResources<T>> response = buildRestTemplate().exchange( buildParams(API_URL, page),
                HttpMethod.GET,
                new HttpEntity(createHeaders(username, password)),
                new ParameterizedTypeReference<PagedResources<T>>() {
                });
        return response.getBody();
    }


}
