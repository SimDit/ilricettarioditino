package it.SimDit.IlRicettarioDiTino.model;

public class Ingredients {
    private String idIngredient;
    private String strIngredient;
    private String strDescription;
    private String strThumb;
    private String strType;

    // Constructors
    public Ingredients() {
    }

    public Ingredients(String idIngredient, String strIngredient, String strDescription, String strThumb, String strType) {
        this.idIngredient = idIngredient;
        this.strIngredient = strIngredient;
        this.strDescription = strDescription;
        this.strThumb = strThumb;
        this.strType = strType;
    }

    // Getters and Setters
    public String getIdIngredient() {
        return idIngredient;
    }

    public void setIdIngredient(String idIngredient) {
        this.idIngredient = idIngredient;
    }

    public String getStrIngredient() {
        return strIngredient;
    }

    public void setStrIngredient(String strIngredient) {
        this.strIngredient = strIngredient;
    }

    public String getStrDescription() {
        return strDescription;
    }

    public void setStrDescription(String strDescription) {
        this.strDescription = strDescription;
    }

    public String getStrThumb() {
        return strThumb;
    }

    public void setStrThumb(String strThumb) {
        this.strThumb = strThumb;
    }

    public String getStrType() {
        return strType;
    }

    public void setStrType(String strType) {
        this.strType = strType;
    }
}
