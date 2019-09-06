package sam.biblio.web.webclient;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.hateoas.hal.Jackson2HalModule;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import sam.biblio.dto.PageInfo;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Collections;


public class CommonWebClient {

    String apiEndPoint;
    String resourcePath;
    String username;
    String password;
    private StringBuilder stringBuilder;

    protected CommonWebClient(String apiEndpoint, String resourcePath, String username, String password) {
        this.apiEndPoint = apiEndpoint;
        this.resourcePath = resourcePath;
        this.username = username;
        this.password = password;
    }

    /**
     * Builds a RestTemplate instance that fixes a bug in SpringBoot Web Client: http header Accept is not
     * valid for Spring Data REST API and must be forced to 'application/hal+json'.
     *
     */
    RestTemplate buildRestTemplate() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.registerModule(new Jackson2HalModule());

        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();

        messageConverter.setSupportedMediaTypes(MediaType.parseMediaTypes(Arrays.asList("*/*", "application/hal+json;charset=utf-8", "application/json;charset=utf-8")));
        messageConverter.setObjectMapper(objectMapper);

        return new RestTemplate(Collections.singletonList(messageConverter));
    }

    HttpHeaders createHeaders(String username, String password) {
        return new HttpHeaders() {{
            String auth = username + ":" + password;
            byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
            String authHeader = "Basic " + new String(encodedAuth);
            set("Authorization", authHeader);
        }};
    }

    CommonWebClient addParam(PageInfo pageInfo) {
        if (pageInfo != null) {
            this.addParam("page", String.valueOf(pageInfo.getNumber()));
            this.addParam("size", String.valueOf(pageInfo.getSize()));
        }
        return this;
    }

    CommonWebClient addParam(String name, String value) {
        if (!stringBuilder.toString().contains("?"))
            stringBuilder.append("?");
        if (!stringBuilder.toString().endsWith("?"))
            stringBuilder.append("&");
        stringBuilder.append(name).append("=").append(value);
        return this;
    }

    CommonWebClient setUrl(String url) {
        stringBuilder = new StringBuilder(url);
        return this;
    }

    String buildURL() {
        String urlString = stringBuilder.toString();
        stringBuilder = null;
        return urlString;
    }

}
