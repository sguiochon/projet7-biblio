package sam.biblio.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LendCopyDTO {
    @JsonProperty("id")
    Long documentId;

    public Long getDocumentId() {
        return documentId;
    }

    public void setDocumentId(Long documentId) {
        this.documentId = documentId;
    }
}
