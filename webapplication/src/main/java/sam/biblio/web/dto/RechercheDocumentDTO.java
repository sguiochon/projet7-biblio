package sam.biblio.web.dto;

public class RechercheDocumentDTO extends RechercheDTO {

    private String criteria;

    public String getCriteria() {
        return criteria;
    }

    public void setCriteria(String criteria) {
        this.criteria = criteria;
    }
}
