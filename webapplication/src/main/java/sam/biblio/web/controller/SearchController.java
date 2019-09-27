package sam.biblio.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sam.biblio.dto.library.Document;
import sam.biblio.web.config.ApplicationConfig;
import sam.biblio.web.dto.NavDTO;
import sam.biblio.web.dto.RechercheDocumentDTO;
import sam.biblio.web.service.SearchDocumentService;

import java.util.List;
import java.util.stream.Collectors;

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

        PagedResources<Resource<Document>> documents = searchDocumentService.searchDocument(applicationConfig.tableSize, currentPage, criteria);

        NavDTO nav = null;
        if (documents.getMetadata().getTotalPages() != 0)
            nav = navHelper.buildNavInfo(currentPage, documents.getMetadata().getTotalPages());

        List<Document> collected = documents.getContent().stream().map((s)->{return s.getContent();}).collect(Collectors.toList());

        model.addAttribute("nav", nav);
        model.addAttribute("recherche", recherche);
        model.addAttribute("documents", collected);

        documents.getContent().forEach((i)->{
            System.out.println(i.getContent().getTitle());});

        return "documentsPage";
    }


}
