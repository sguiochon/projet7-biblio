package sam.biblio.web.webclient;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.hateoas.hal.Jackson2HalModule;
import org.springframework.http.*;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import sam.biblio.dto.PageInfo;
import org.apache.http.client.utils.URIBuilder;

import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.Arrays;


public class CommonWebClient {

    String apiEndPoint;
    String resourcePath;
    String username;
    String password;
    URIBuilder uriBuilder;

    protected CommonWebClient(String apiEndpoint, String resourcePath, String username, String password) throws URISyntaxException {
        this.apiEndPoint = apiEndpoint;
        this.resourcePath = resourcePath;
        this.username = username;
        this.password = password;
    }

    /**
     * Builds a RestTemplate instance that fixes a bug in SpringBoot Web Client: http header Accept is not
     * valid for Spring Data REST API and must be forced to 'application/hal+json'.
     * @return
     */
    RestTemplate buildRestTemplate(){
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.registerModule(new Jackson2HalModule());

        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();

        messageConverter.setSupportedMediaTypes(MediaType.parseMediaTypes("application/hal+json"));
        messageConverter.setObjectMapper(objectMapper);

        return new RestTemplate(Arrays.asList(messageConverter));
    }

    HttpHeaders createHeaders(String username, String password){
        return new HttpHeaders() {{
            String auth = username + ":" + password;
            byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")) );
            String authHeader = "Basic " + new String( encodedAuth );
            set( "Authorization", authHeader );
        }};
    }

    CommonWebClient addParam(PageInfo pageInfo){
        if (pageInfo!=null){
            uriBuilder.addParameter("page", String.valueOf(pageInfo.getNumber()));
            uriBuilder.addParameter("size", String.valueOf(pageInfo.getSize()));
        }
        return this;
    }

    CommonWebClient addParam(String name, String value){
        uriBuilder.addParameter(name, value);
        return this;
    }

    CommonWebClient setUrl(String url) throws URISyntaxException {
        uriBuilder = new URIBuilder(url);
        return this;
    }

    String buildURL() throws URISyntaxException {
        String urlString = uriBuilder.build().toString();
        uriBuilder.clearParameters();
        return urlString;
    }


/*
    String buildParams(String url, PageInfo pageInfo){
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
*/


}
