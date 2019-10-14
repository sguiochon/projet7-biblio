package sam.biblio.model.library;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Copy {
    @JsonProperty("copyId")
    private Long id;
    private CopyStatusEnum status;
    private Document document;
    private Lending lending;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CopyStatusEnum getStatus() {
        return status;
    }

    public void setStatus(CopyStatusEnum status) {
        this.status = status;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public Lending getLending() {
        return lending;
    }

    public void setLending(Lending lending) {
        this.lending = lending;
    }

}
