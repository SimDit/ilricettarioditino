package it.SimDit.IlRicettarioDiTino.model;

import java.util.List;

public class RecipesMeals {

    private List<Recipes> recipesList;

    public RecipesMeals(List<Recipes> recipesList) {
        this.recipesList = recipesList;
    }
    public List<Recipes> getRecipesList() {
        return recipesList;
    }
    public void setRecipesList(List<Recipes> recipesList) {
        this.recipesList = recipesList;
    }
    @Override
    public String toString() {
        return "RecipesMeals{" +
                "recipesList=" + recipesList +
                '}';
    }
}
