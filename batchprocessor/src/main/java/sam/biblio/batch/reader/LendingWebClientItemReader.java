package sam.biblio.batch.reader;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.PagedResources;
import org.springframework.stereotype.Component;
import sam.biblio.dto.PageInfo;
import sam.biblio.dto.library.Lending;
import sam.biblio.web.webclient.LendingWebClient;

import java.util.ArrayList;
import java.util.List;

@Component
public class LendingWebClientItemReader implements ItemReader<Lending> {

    @Autowired
    LendingWebClient lendingWebClient;

    private PageInfo pageInfo = new PageInfo(4);
    private List<Lending> internalList = new ArrayList();

    @Override
    public Lending read() throws Exception {
        System.out.println("appel read()");
        if (internalList.isEmpty())
            loadPagedResources();

        if (internalList == null)
            return null;
        else
            return internalList.remove(0);
    }


    private void loadPagedResources() {
        PagedResources<Lending> resources = null;
        if (pageInfo.hasNextPage()) {
            pageInfo = pageInfo.nextPage();
            resources = lendingWebClient.getEntities(pageInfo);
            pageInfo = PageInfo.fromPagedResourceMetadata(resources.getMetadata());
        }

        System.out.println("Chargement de la page " + pageInfo.getNumber() + " sur " + pageInfo.getTotalPages());
        System.out.println("Total elements: " + pageInfo.getTotalElements());
        System.out.println("has next page?: " + pageInfo.hasNextPage());

        if (resources != null) {
            internalList = new ArrayList<Lending>(resources.getContent());
        } else {
            internalList = null; // Means there is no more data to retrieve
        }
    }

}
