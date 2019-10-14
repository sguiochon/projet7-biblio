package sam.biblio.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomePageController {

    @RequestMapping(value = "/", method = {RequestMethod.GET, RequestMethod.POST})
    public String viewHomePage() {
        return "homePage";
    }

}
