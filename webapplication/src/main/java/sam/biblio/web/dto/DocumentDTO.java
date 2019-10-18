package sam.biblio.web.dto;

import org.dozer.Mapping;

public class DocumentDTO {

    private Long id;
    @Mapping("title")
    private String titre;
    @Mapping("author")
    private String auteur;
    private String description;
    private String image;
    private Integer nbExemplairesLibres = 0;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getNbExemplairesLibres() {
        return nbExemplairesLibres;
    }

    public void setNbExemplairesLibres(Integer nbExemplairesLibres) {
        this.nbExemplairesLibres = nbExemplairesLibres;
    }

    public void incrementExemplairesLibres() {
        nbExemplairesLibres++;
    }
}
