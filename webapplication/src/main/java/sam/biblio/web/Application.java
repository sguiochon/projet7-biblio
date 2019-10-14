package sam.biblio.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import sam.biblio.model.PageInfo;
import sam.biblio.model.library.Document;
import sam.biblio.model.security.User;
import sam.biblio.web.webclient.DocumentWebClient;
import sam.biblio.web.webclient.LendingWebClient;
import sam.biblio.web.webclient.MemberWebClient;
import sam.biblio.web.webclient.UserWebClient;

@SpringBootApplication
@PropertySource("classpath:application.properties")
public class Application implements CommandLineRunner {

    @Autowired
    UserWebClient userWebClient;

    @Autowired
    LendingWebClient lendingWebClient;

    @Autowired
    MemberWebClient memberWebClient;

    @Autowired
    DocumentWebClient documentWebClient;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }


    @Override
    public void run(String... args) throws Exception {

        System.out.println("_________________________________________");

        PagedResources<Resource<User>> users = userWebClient.findAll(null);
        Resource<User> user = userWebClient.findByEmail("inferno@hell.com");
        System.out.println(user.toString());

        System.out.println("_________________________________________");

        String criteria = "l";
        PageInfo page = new PageInfo(10L);
        PagedResources<Resource<Document>> result = documentWebClient.findByTextString(page, criteria);
        for (Resource<Document> resourceDocument : result.getContent()){
            System.out.println(resourceDocument.toString());
        }
    }


}
