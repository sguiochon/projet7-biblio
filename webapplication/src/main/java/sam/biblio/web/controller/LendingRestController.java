package sam.biblio.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import sam.biblio.web.dto.LendCopyDTO;
import sam.biblio.web.service.LendCopyService;

import java.security.Principal;

@RestController
public class LendingRestController {

    @Autowired
    LendCopyService lendCopyService;

    @PostMapping(value = "/lend")
    public String lendCopy(Principal principal, @RequestBody LendCopyDTO lendCopyDTO) {
        System.out.println(">>>>> demande de reservation d'un exemplaire: " + lendCopyDTO.toString());

        lendCopyService.lendCopy(lendCopyDTO.getDocumentId(), principal.getName());

        return "";
    }

}
