package sam.biblio.model.library;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Lending {

    private Long id;
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    private LocalDate start;
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    private LocalDate end;
    private Integer nbPostponement;
    private Member member;
    private Copy copy;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = LocalDate.parse(start, DateTimeFormatter.ISO_DATE);
    }

    public void setStart(LocalDate start) {
        this.start = start;
    }

    public LocalDate getEnd() {
        return end;
    }

    public void setEnd(LocalDate end) {
        this.end = end;
    }

    public void setEnd(String end) {
        this.end = LocalDate.parse(end, DateTimeFormatter.ISO_DATE);
    }

    public Integer getNbPostponement() {
        return nbPostponement;
    }

    public void setNbPostponement(Integer nbPostponement) {
        this.nbPostponement = nbPostponement;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Copy getCopy() {
        return copy;
    }

    public void setCopy(Copy copy) {
        this.copy = copy;
    }

}
