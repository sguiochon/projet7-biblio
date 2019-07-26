package sam.biblio.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import sam.biblio.dto.library.Lending;
import sam.biblio.dto.library.Member;
import sam.biblio.dto.security.User;
import sam.biblio.web.webclient.LendingWebClient;
import sam.biblio.web.webclient.MemberWebClient;
import sam.biblio.web.webclient.UserWebClient;

import java.util.stream.Collectors;

@SpringBootApplication
@PropertySource("classpath:application.properties")
public class DemoApplication implements CommandLineRunner {

    @Autowired
    UserWebClient userWebClient;

    @Autowired
    LendingWebClient lendingWebClient;

    @Autowired
    MemberWebClient memberWebClient;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {
        System.out.println("Bon, je me lance...");

        PagedResources<Resource<User>> users = userWebClient.findAll(null);

        System.out.println("Number: " + users.getMetadata().getNumber() );
        System.out.println("Total pages: " + users.getMetadata().getTotalPages());
        System.out.println("Total elements: " + users.getMetadata().getTotalElements());
        System.out.println("Size: " + users.getMetadata().getSize());

        for (Resource<User> r : users.getContent()) {
            System.out.println(r.toString());
            System.out.println("Id: " + r.getId() + ", Prenom: " + r.getContent().getFirstName() + ", Nom: " + r.getContent().getLastName());
        }
        System.out.println("**********************************");



/*
        PagedResources<Lending> lendings = lendingWebClient.findAll(null);
        for (Lending lending : lendings.getContent()){

            System.out.println("Id: " + lending.getId() + ", start: " + lending.getStart() + ", links: " + lending.getLinks().stream().map(Link::toString).collect(Collectors.joining(",")));

            Link memberLink = lending.getLink("member");
            System.out.println("Member link: " + memberLink.getHref());

            Resource<Member> member = memberWebClient.findByResourceUrl(memberLink.getHref());

            Link userLink = member.getLink("user");
            System.out.println("User link: " + userLink.getHref());

            Resource<User> user = userWebClient.findByResourceUrl(userLink.getHref());
            System.out.println("User: " + user.getContent().getFirstName() + " " + user.getContent().getLastName());
        }*/

        PagedResources<Resource<Lending>> lendings = lendingWebClient.findAll(null);
        for (Resource<Lending> lending : lendings.getContent()){

            System.out.println("PRET -- Id: " + lending.getId() + ", start: " + lending.getContent().getStart() + ", links: " + lending.getLinks().stream().map(Link::toString).collect(Collectors.joining(",")));

            Link memberLink = lending.getLink("member");
            System.out.println("Member link: " + memberLink.getHref());

            Resource<Member> member = memberWebClient.findByResourceUrl(memberLink.getHref());

            Link userLink = member.getLink("user");
            System.out.println("User link: " + userLink.getHref());

            Resource<User> user = userWebClient.findByResourceUrl(userLink.getHref());
            System.out.println("User: " + user.getContent().getFirstName() + " " + user.getContent().getLastName());
        }

    }

}
