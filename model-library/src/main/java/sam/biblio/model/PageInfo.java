package sam.biblio.model;

import org.springframework.hateoas.PagedResources;

public class PageInfo {
    private long number;
    private long totalPages;
    private long size;
    private long totalElements;

    public PageInfo(long size){
        this.size = size;
        this.number = -1;
        this.totalPages = 1;
    }

    public PageInfo(long number, long totalPages, long size, long totalElements) {
        this.number = number;
        this.totalPages = totalPages;
        this.size = size;
        this.totalElements = totalElements;
    }

    public static PageInfo fromPagedResourceMetadata(PagedResources.PageMetadata metadata) {
        return new PageInfo(metadata.getNumber(), metadata.getTotalPages(), metadata.getSize(), metadata.getTotalElements());
    }

    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
    }

    public long getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(long totalPages) {
        this.totalPages = totalPages;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public boolean hasNextPage() {
        return (number + 1 < totalPages);
    }

    public PageInfo nextPage() {
        this.number++;
        return this;
    }

}
