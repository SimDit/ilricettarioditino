package it.SimDit.IlRicettarioDiTino.util;
/**
 * Utility class to save constants used by the app.
 */
public class Constants {
    // Constants for SharedPreferences
    public static final String SHARED_PREFERENCES_FILE_NAME = "it.SimDit.IlRicettarioDiTino.preferences";
    public static final String SHARED_PREFERENCES_FAVORITE_RECIPES = "favorite_recipes";

    // Constants for EncryptedSharedPreferences
    public static final String ENCRYPTED_SHARED_PREFERENCES_FILE_NAME = "it.SimDit.IlRicettarioDiTino.encrypted_preferences";
    public static final String EMAIL_ADDRESS = "email_address";
    public static final String PASSWORD = "password";

    // Constants for encrypted files
    public static final String ENCRYPTED_DATA_FILE_NAME = "it.SimDit.IlRicettarioDiTino.encrypted_file.txt";

    // Constants for files contained in assets folder
    public static final String RECIPES_API_TEST_JSON_FILE="recipes.json";
    public static final String INGREDIENTS_API_TEST_JSON_FILE="ingredients.json";

    // Constants for TheMealDBApi ()
    public static final String RECIPES_API_BASE_URL = "https://www.themealdb.com/api/json/v1/";
    public static final String FILTER_PHP_ENDPOINT = "filter.php";
    public static final String FILTER_PHP_INGREDIENT_PARAMETER = "i";

    // Constants for refresh rate of recipes
    public static final String LAST_UPDATE = "last_update";
    public static final long FRESH_TIMEOUT = 24 * 60 * 60 * 1000; // 24 hours in milliseconds


}
