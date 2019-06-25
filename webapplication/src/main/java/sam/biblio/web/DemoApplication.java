package sam.biblio.web;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.hal.Jackson2HalModule;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import sam.biblio.web.model.security.User;


import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {


    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {
        System.out.println("Bon, je me lance...");
/*
        WebClient webClient = WebClient.create("http://localhost:9990");
        Mono<User> userFlux = webClient.get()
                .uri("/users/1")
                .retrieve()
                .bodyToMono(User.class);

        AtomicReference<User> user = new AtomicReference<>();
        //userFlux.
        userFlux.subscribe(innerUser -> {
            System.out.println(innerUser);
            user.set(innerUser);
            System.out.println("/////// " + user.get());
        });

        System.out.println(">>>>>>User " + user.get());
*/

        //WebClient.UriSpec<WebClient.RequestBodySpec> request1 = webClient.method(HttpMethod.POST);

        //WebClient.RequestBodySpec uri1 = webClient.method(HttpMethod.GET).uri("/users");

        //Users users = uri1.exchange().block().bodyToMono(Users.class).block();

//        String output = uri1.body(BodyInserters.fromObject(new String())).exchange().block().bodyToMono(String.class).block();
//        System.out.println("Resultat: " + output);


        //System.out.println("Resultat: " + users);


        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.registerModule(new Jackson2HalModule());

        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();

        messageConverter.setSupportedMediaTypes(MediaType.parseMediaTypes("application/hal+json" ));
        messageConverter.setObjectMapper(objectMapper);


        RestTemplate restTemplate = new RestTemplate(Arrays.asList(messageConverter));
        String fooResourceUrl = "http://localhost:9990/users";
//        ResponseEntity<User> response = restTemplate.getForEntity(fooResourceUrl + "/1", User.class);
//        System.out.println("((((((((((((( " + response.getBody());


        //Users users = restTemplate.getForObject(fooResourceUrl, Users.class);

       ResponseEntity< PagedResources<User> > response2 = restTemplate.exchange(fooResourceUrl,
               HttpMethod.GET,
               HttpEntity.EMPTY,
               new ParameterizedTypeReference< PagedResources<User> >(){});


       for (User r : response2.getBody().getContent()){
           System.out.println("Prenom : " + r.getFirstName());
           System.out.println("Id: " + r.getId());
       }

        System.out.println("(((((((((((( " + response2.getBody().toString());

    }

}
