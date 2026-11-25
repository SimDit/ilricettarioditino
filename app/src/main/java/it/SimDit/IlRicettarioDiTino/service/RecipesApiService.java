package it.SimDit.IlRicettarioDiTino.service;

import static it.SimDit.IlRicettarioDiTino.util.Constants.FILTER_PHP_ENDPOINT;
import static it.SimDit.IlRicettarioDiTino.util.Constants.FILTER_PHP_INGREDIENT_PARAMETER;

import it.SimDit.IlRicettarioDiTino.model.RecipesMeals;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

/**
 * Interface for Service to get recipes from the Web Service.
 */

public interface RecipesApiService {
    @GET(FILTER_PHP_ENDPOINT)
    Call<RecipesMeals> getRecipes(
            @Query(FILTER_PHP_INGREDIENT_PARAMETER) String ingredient,
            @Header("Autorization") String apiKey
    );

}
