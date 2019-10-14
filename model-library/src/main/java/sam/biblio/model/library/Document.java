package sam.biblio.model.library;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Document {//extends ResourceSupport {
    @JsonProperty("documentId")
    private Long id;
    private String title;
    private String author;
    private String description;
    private String image;
    private List<Copy> copies;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Copy> getCopies() {
        return copies;
    }

    public void setCopies(List<Copy> copies) {
        this.copies = copies;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Document [id=").append(id)
                .append(", title=").append(title)
                .append(", author=").append(author)
                .append(", description=").append(description)
                .append(", image=").append(image)
                .append(", copies=").append(copies).append("]");
        return builder.toString();
    }
}
