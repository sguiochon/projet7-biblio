package sam.biblio.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import sam.biblio.dto.security.User;
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

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }


    @Override
    public void run(String... args) throws Exception {

        PagedResources<Resource<User>> users = userWebClient.findAll(null);

        Resource<User> user = userWebClient.findByEmail("inferno@hell.com");
        System.out.println(user.toString());
    }


}
