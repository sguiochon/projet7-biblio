package sam.biblio.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sam.biblio.web.config.ApplicationConfig;
import sam.biblio.web.dto.DocumentDTO;
import sam.biblio.web.dto.NavDTO;
import sam.biblio.web.dto.PageDTO;
import sam.biblio.web.dto.RechercheDocumentDTO;
import sam.biblio.web.service.SearchDocumentService;

import java.util.List;

@Controller
public class SearchController {

    @Autowired
    SearchDocumentService searchDocumentService;
    @Autowired
    private ApplicationConfig applicationConfig;
    @Autowired
    private TableNavigationHelper navHelper;

    @RequestMapping("/search")
    public String findBook(@RequestParam(name = "criteria") String criteria, @RequestParam(name = "p", required = false) String pageNb, Model model) {

        RechercheDocumentDTO recherche = new RechercheDocumentDTO();
        recherche.setCriteria(criteria);
        recherche.setPageNb(pageNb);

        int currentPage = pageNb == null ? 0 : Integer.parseInt(pageNb);

        PageDTO<DocumentDTO> page = searchDocumentService.searchDocument(applicationConfig.tableSize, currentPage, criteria);

        NavDTO nav = null;
        if (page.getPageMetadata().getTotalPages() != 0)
            nav = navHelper.buildNavInfo(currentPage, page.getPageMetadata().getTotalPages());

        List<DocumentDTO> collected = page.getContent();

        model.addAttribute("nav", nav);
        model.addAttribute("recherche", recherche);
        model.addAttribute("documents", collected);

        return "documentsPage";
    }


}
