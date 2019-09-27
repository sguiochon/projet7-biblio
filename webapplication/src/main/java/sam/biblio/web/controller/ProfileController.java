package sam.biblio.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import sam.biblio.dto.library.Member;
import sam.biblio.dto.security.User;
import sam.biblio.web.webclient.MemberWebClient;
import sam.biblio.web.webclient.UserWebClient;

import java.net.URISyntaxException;
import java.security.Principal;

@Controller
public class ProfileController {

    @Autowired
    UserWebClient userWebClient;

    @Autowired
    MemberWebClient memberWebClient;

    @RequestMapping(value = "/profile", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView viewProfil(Principal principal) throws URISyntaxException {

        Resource<User> user = userWebClient.findByEmail(principal.getName());

        Resource<Member> member = memberWebClient.findByUserEmail(principal.getName());

        member.getContent().setUser(user.getContent());

        ModelAndView modelAndView = new ModelAndView("profilePage");
        modelAndView.addObject("member", member.getContent());
        return modelAndView;
    }
}
