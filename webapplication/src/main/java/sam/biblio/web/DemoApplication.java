package sam.biblio.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.hateoas.PagedResources;
import sam.biblio.dto.security.User;
import sam.biblio.web.webclient.UserWebClient;

@SpringBootApplication
@PropertySource("classpath:application.properties")
public class DemoApplication implements CommandLineRunner {

    @Autowired
    UserWebClient userWebClient;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {
        System.out.println("Bon, je me lance...");


        //WebClient.UriSpec<WebClient.RequestBodySpec> request1 = webClient.method(HttpMethod.POST);

        //WebClient.RequestBodySpec uri1 = webClient.method(HttpMethod.GET).uri("/users");

        //Users users = uri1.exchange().block().bodyToMono(Users.class).block();

//        String output = uri1.body(BodyInserters.fromObject(new String())).exchange().block().bodyToMono(String.class).block();
//        System.out.println("Resultat: " + output);


        //System.out.println("Resultat: " + users);

        PagedResources<User> users = userWebClient.findAll(null);

        System.out.println("Number: " + users.getMetadata().getNumber() );
        System.out.println("Total pages: " + users.getMetadata().getTotalPages());
        System.out.println("Total elements: " + users.getMetadata().getTotalElements());
        System.out.println("Size: " + users.getMetadata().getSize());

        for (User r : users.getContent()) {
            System.out.println("Id: " + r.getId() + ", Prenom: " + r.getFirstName() + ", Nom: " + r.getLastName());
        }


        /*
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.registerModule(new Jackson2HalModule());

        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();

        messageConverter.setSupportedMediaTypes(MediaType.parseMediaTypes("application/hal+json"));
        messageConverter.setObjectMapper(objectMapper);

        RestTemplate restTemplate = new RestTemplate(Arrays.asList(messageConverter));
        String apiResourceUrl = "http://localhost:9990/users";

        ResponseEntity<PagedResources<User>> response2 = restTemplate.exchange(apiResourceUrl,
                HttpMethod.GET,
                HttpEntity.EMPTY,
                new ParameterizedTypeReference<PagedResources<User>>() {
                });


        for (User r : response2.getBody().getContent()) {
            System.out.println("Prenom : " + r.getFirstName());
            System.out.println("Id: " + r.getId());
        }

        System.out.println("(((((((((((( " + response2.getBody().toString());
*/
    }

}
