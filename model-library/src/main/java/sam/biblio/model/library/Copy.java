package sam.biblio.model.library;

public class Copy {
    private Long id;
    private CopyStatus status;
    private Document document;
    private Lending lending;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CopyStatus getStatus() {
        return status;
    }

    public void setStatus(CopyStatus status) {
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
