package it.SimDit.IlRicettarioDiTino.model;



public class Recipes{
    // Main info
    private String idMeal;
    private String strMeal;
    private String strMealThumb;
    private String strCountry;
    private String strArea;
    //To handle the heart icon
    private boolean favorite;


    public Recipes(){}

   public Recipes(String idMeal, String strMeal, String strMealThumb, String strCategory, String strArea) {
        this.idMeal = idMeal;
        this.strMeal = strMeal;
        this.strMealThumb = strMealThumb;
        this.strCountry = strCategory;
        this.strArea = strArea;
    }

    public String getIdMeal() {
        return idMeal;
    }

    public void setIdMeal(String idMeal) {
        this.idMeal = idMeal;
    }

    public String getStrMeal() {
        return strMeal;
    }

    public void setStrMeal(String strMeal) {
        this.strMeal = strMeal;
    }

    public String getStrMealThumb() {
        return strMealThumb;
    }

    public void setStrMealThumb(String strMealThumb) {
        this.strMealThumb = strMealThumb;
    }

    public String getStrCountry() {
        return strCountry;
    }

    public void setStrCountry(String strCountry) {
        this.strCountry = strCountry;
    }

    public String getStrArea() {
        return strArea;
    }

    public void setStrArea(String strArea) {
        this.strArea = strArea;
    }

    public boolean getFavorite(){
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

}


















