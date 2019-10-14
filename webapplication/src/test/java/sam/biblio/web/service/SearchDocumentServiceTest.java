package sam.biblio.web.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import sam.biblio.model.PageInfo;
import sam.biblio.model.library.Document;
import sam.biblio.web.webclient.DocumentWebClient;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class SearchDocumentServiceTest {

    @Autowired
    private DocumentWebClient documentWebClient;


    public SearchDocumentServiceTest() {

    }

    @Test
    public void testSearchDocuments() {
        // Arrange
        String criteria = "l";
        PageInfo page = new PageInfo(10L);

        // Act
        PagedResources<Resource<Document>> result = documentWebClient.findByTextString(page, criteria);


        //Assert
        for (Resource<Document> resourceDocument : result.getContent()){

            System.out.println(resourceDocument.toString());

            Document document = resourceDocument.getContent();
            assertNotNull("Id du document non null", document.getId());

        }

    }

}
