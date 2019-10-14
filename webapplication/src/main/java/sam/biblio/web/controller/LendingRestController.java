package sam.biblio.web.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import sam.biblio.web.dto.LendCopyDTO;

import java.security.Principal;

@RestController
public class LendingRestController {

    @PostMapping(value="/lend")
    public String lendCopy(Principal principal, @RequestBody LendCopyDTO lendCopyDTO){
        System.out.println(">>>>> demande de reservation d'un exemplaire: " + lendCopyDTO.toString());
        return "";
    }

}
