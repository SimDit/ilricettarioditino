package it.SimDit.IlRicettarioDiTino.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class IngredientsMeals {
    @SerializedName("meals")
    private List<Ingredients> ingredients;

    public IngredientsMeals() {
    }

    public IngredientsMeals(List<Ingredients> ingredients) {
        this.ingredients = ingredients;
    }

    public List<Ingredients> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredients> ingredients) {
        this.ingredients = ingredients;
    }
}

