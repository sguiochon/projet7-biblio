package sam.biblio.web.dto;

import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

public class PageDTO<T> {

    PageMetadata pageMetadata;
    private List<T> content;

    public PageDTO() {
        this.pageMetadata = new PageMetadata();
    }

    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> records) {
        this.content = records;
    }

    public PageMetadata getPageMetadata() {
        return pageMetadata;
    }

    public void setPageMetadata(PageMetadata pageMetadata) {
        this.pageMetadata = pageMetadata;
    }

    public void addContent(T t) {
        if (content == null)
            content = new ArrayList<>();
        content.add(t);
    }

    public static class PageMetadata {
        private long taille;
        private long totalElements;
        private long totalPages;
        private long numero;

        PageMetadata() {
        }

        PageMetadata(long taille, long numero, long totalElements, long totalPages) {
            Assert.isTrue(taille > -1L, "Size must not be negative!");
            Assert.isTrue(numero > -1L, "Number must not be negative!");
            Assert.isTrue(totalElements > -1L, "Total elements must not be negative!");
            Assert.isTrue(totalPages > -1L, "Total pages must not be negative!");
            this.taille = taille;
            this.numero = numero;
            this.totalElements = totalElements;
            this.totalPages = totalPages;
        }

        public PageMetadata(long taille, long numero, long totalElements) {
            this(taille, numero, totalElements, taille == 0L ? 0L : (long) Math.ceil((double) totalElements / (double) taille));
        }

        public long getTaille() {
            return taille;
        }

        public void setTaille(long taille) {
            this.taille = taille;
        }

        public long getTotalElements() {
            return totalElements;
        }

        public void setTotalElements(long totalElements) {
            this.totalElements = totalElements;
        }

        public long getTotalPages() {
            return totalPages;
        }

        public void setTotalPages(long totalPages) {
            this.totalPages = totalPages;
        }

        public long getNumero() {
            return numero;
        }

        public void setNumero(long numero) {
            this.numero = numero;
        }

        public String toString() {
            return String.format("Metadata { number: %d, total pages: %d, total elements: %d, size: %d }", this.numero, this.totalPages, this.totalElements, this.taille);
        }

    }

}
