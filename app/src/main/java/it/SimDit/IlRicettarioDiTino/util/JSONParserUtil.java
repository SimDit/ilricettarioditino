package it.SimDit.IlRicettarioDiTino.util;

import android.app.Application;
import android.content.Context;
import android.util.JsonReader;
import android.util.JsonToken;


import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import it.SimDit.IlRicettarioDiTino.model.Recipes;
import it.SimDit.IlRicettarioDiTino.model.RecipesMeals;

/**
 * Util class to show different ways to parse a JSON file.
 */
public class JSONParserUtil {

    private static final String TAG = JSONParserUtil.class.getSimpleName();

    private final Context context;

    private final String mealsParameter="meals";
    private final String strMealParameter="strMeal";
    private final String strMealThumbParameter="strMealThumb";
    private final String idMealParameter="idMeal";
    private final String strAreaParameter="strArea";
    private final String strCountryParameter="strCountry";

    public JSONParserUtil(Application application) {
        this.context = application.getApplicationContext();
    }

    /**
     * Returns a list of News from a JSON file parsed using JsonReader class.
     * Doc can be read here: <a href="https://developer.android.com/reference/android/util/JsonReader">...</a>
     * @param fileName The JSON file to be parsed.
     * @return The NewsApiResponse object associated with the JSON file content.
     * @throws IOException
     */
    public RecipesMeals parseJSONFileWithJsonReader(String fileName) throws IOException {
        RecipesMeals recipesMeals = new RecipesMeals(new ArrayList<>());
        List<Recipes> recipesList = new ArrayList<>();

        try (InputStream inputStream = context.getAssets().open(fileName);
             JsonReader jsonReader = new JsonReader(
                     new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            jsonReader.beginObject();

            while (jsonReader.hasNext()) {
                String rootJSONParam = jsonReader.nextName();
                if (rootJSONParam.equals(mealsParameter)) {
                    if (jsonReader.peek() == JsonToken.NULL) {
                        jsonReader.nextNull();
                        continue;
                    }
                    jsonReader.beginArray();
                    while (jsonReader.hasNext()) {
                        if (jsonReader.peek() == JsonToken.NULL) {
                            jsonReader.nextNull();
                            continue;
                        }
                        jsonReader.beginObject();
                        Recipes recipes = new Recipes();
                        while (jsonReader.hasNext()) {
                            String mealsJSONParam = jsonReader.nextName();
                            if (jsonReader.peek() == JsonToken.NULL) {
                                jsonReader.nextNull();
                            } else if (mealsJSONParam.equals(strMealParameter)) {
                                recipes.setStrMeal(jsonReader.nextString());
                            } else if (mealsJSONParam.equals(strMealThumbParameter)) {
                                recipes.setStrMealThumb(jsonReader.nextString());
                            } else if (mealsJSONParam.equals(idMealParameter)) {
                                recipes.setIdMeal(jsonReader.nextString());
                            } else if (mealsJSONParam.equals(strAreaParameter)) {
                                recipes.setStrArea(jsonReader.nextString());
                            } else if (mealsJSONParam.equals(strCountryParameter)) {
                                recipes.setStrCountry(jsonReader.nextString());
                            } else {
                                jsonReader.skipValue();
                            }
                        }
                        jsonReader.endObject();
                        recipesList.add(recipes);
                    }
                    jsonReader.endArray();
                } else {
                    jsonReader.skipValue();
                }
            }
            jsonReader.endObject();
        }

        recipesMeals.setRecipesList(recipesList);
        return recipesMeals;
    }



    /**
     * Returns a list of News from a JSON file parsed using JSONObject and JSONReader classes.
     * Doc of JSONObject: <a href="https://developer.android.com/reference/org/json/JSONObject">...</a>
     * Doc of JSONArray: <a href="https://developer.android.com/reference/org/json/JSONArray">...</a>
     * @param fileName The JSON file to be parsed.
     * @return The NewsApiResponse object associated with the JSON file content.
     * @throws IOException
     * @throws JSONException
     */
    /*
    public NewsApiResponse parseJSONFileWithJSONObjectArray(String fileName)
            throws IOException, JSONException {

        InputStream inputStream = context.getAssets().open(fileName);
        String content = IOUtils.toString(inputStream, StandardCharsets.UTF_8);

        JSONObject rootJSONObject = new JSONObject(content);

        NewsApiResponse newsApiResponse = new NewsApiResponse();
        newsApiResponse.setStatus(rootJSONObject.getString(statusParameter));
        newsApiResponse.setTotalResults(rootJSONObject.getInt(totalResultsParameter));

        JSONArray articlesJSONArray = rootJSONObject.getJSONArray(articlesParameter);

        List<News> newsList = null;
        int articlesCount = articlesJSONArray.length();

        if (articlesCount > 0) {
            newsList = new ArrayList<>();
            News news;
            for (int i = 0; i < articlesCount; i++) {
                JSONObject articleJSONObject = articlesJSONArray.getJSONObject(i);
                JSONObject sourceJSONObject = articleJSONObject.getJSONObject(sourceParameter);
                news = new News();
                news.setAuthor(articleJSONObject.getString(authorParameter));
                news.setTitle(articleJSONObject.getString(titleParameter));
                news.setDescription(articleJSONObject.getString(descriptionParameter));
                news.setUrl(articleJSONObject.getString(urlParameter));
                news.setUrlToImage(articleJSONObject.getString(urlToImageParameter));
                news.setDate(articleJSONObject.getString(publishedAtParameter));
                news.setContent(articleJSONObject.getString(contentParameter));
                news.setSource(new NewsSource(sourceJSONObject.getString(nameParameter)));
                newsList.add(news);
            }
        }
        newsApiResponse.setArticles(newsList);

        return newsApiResponse;
    }
*/
    /**
     * Returns a list of News from a JSON file parsed using Gson.
     * Doc can be read here: <a href="https://github.com/google/gson">...</a>
     * @param fileName The JSON file to be parsed.
     * @return The NewsApiResponse object associated with the JSON file content.
     * @throws IOException
     */
    /*
    public NewsApiResponse parseJSONFileWithGSon(String fileName) throws IOException {
        InputStream inputStream = context.getAssets().open(fileName);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        return new Gson().fromJson(bufferedReader, NewsApiResponse.class);
    }*/
}
