package sam.biblio.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.net.URISyntaxException;

@Controller
public class HomePageController {

    @RequestMapping(value = "/", method={RequestMethod.GET, RequestMethod.POST})
    public String viewHomePage() throws URISyntaxException {
        System.out.println(">>>>>  GET /");


        return "homepage";
    }

}
