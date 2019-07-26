package sam.biblio.batch.reader;

import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.stereotype.Component;
import sam.biblio.dto.PageInfo;
import sam.biblio.dto.library.Lending;
import sam.biblio.web.webclient.LendingWebClient;

import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class LendingWebClientItemReader implements ItemReader<Resource<Lending>> {


    private LendingWebClient lendingWebClient;
    private PageInfo pageInfo;
    private List<Resource<Lending>> internalList;
    private LocalDate limitDate;

    @Autowired
    public LendingWebClientItemReader(LendingWebClient lendingWebClient,
                                      @Value("${batch.findByEndDateBefore.forcedValue}") String forcedLimitDate,
                                      @Value("${api.biblio.page.size}") Integer pageSize) {
        this.lendingWebClient = lendingWebClient;
        this.limitDate = forcedLimitDate == null? LocalDate.now() : LocalDate.parse(forcedLimitDate);
        this.internalList = new ArrayList();
        this.pageInfo = new PageInfo(pageSize);
    }

    @Override
    public Resource<Lending> read() throws Exception {
        if (internalList.isEmpty())
            loadPagedResources();

        if (internalList == null)
            return null;
        else
            return internalList.remove(0);
    }


    private void loadPagedResources() throws URISyntaxException {

        System.out.println("##### >>>>> Forced limit date: " + limitDate);

        PagedResources<Resource<Lending>> resources = null;
        if (pageInfo.hasNextPage()) {
            pageInfo = pageInfo.nextPage();
            resources = lendingWebClient.findByEndDateBefore(pageInfo, limitDate);
            pageInfo = PageInfo.fromPagedResourceMetadata(resources.getMetadata());
        }

        System.out.println("Chargement de la page " + pageInfo.getNumber() + " sur " + pageInfo.getTotalPages());
        System.out.println("Total elements: " + pageInfo.getTotalElements());
        System.out.println("has next page?: " + pageInfo.hasNextPage());

        if (resources != null) {
            internalList = new ArrayList<Resource<Lending>>(resources.getContent());
        } else {
            internalList = null; // Means there is no more data to retrieve
        }
    }

}
