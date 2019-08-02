package sam.biblio.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import sam.biblio.dto.security.User;
import sam.biblio.web.webclient.UserWebClient;

import java.net.URISyntaxException;

@Controller
public class HomePageController {


    @Autowired
    UserWebClient userWebClient;


    @RequestMapping(value = "/", method={RequestMethod.GET, RequestMethod.POST})
    public String viewHomePage() throws URISyntaxException {
        System.out.println(">>>>>  GET /");
        //Resource<User> user = userWebClient.findByEmail("coucou@hell.com");

        return "homepage";
    }

}
