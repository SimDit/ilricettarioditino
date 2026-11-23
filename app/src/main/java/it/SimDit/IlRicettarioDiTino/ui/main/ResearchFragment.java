package it.SimDit.IlRicettarioDiTino.ui.main;

import static it.SimDit.IlRicettarioDiTino.util.Constants.INGREDIENTS_API_TEST_JSON_FILE;
import static it.SimDit.IlRicettarioDiTino.util.Constants.RECIPES_API_TEST_JSON_FILE;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import it.SimDit.IlRicettarioDiTino.R;
import it.SimDit.IlRicettarioDiTino.adapter.IngredientsRecyclerViewAdapter;
import it.SimDit.IlRicettarioDiTino.adapter.RecipesRecyclerViewAdapter;
import it.SimDit.IlRicettarioDiTino.model.Ingredients;
import it.SimDit.IlRicettarioDiTino.model.Recipes;
import it.SimDit.IlRicettarioDiTino.util.JSONParserUtil;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ResearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ResearchFragment extends Fragment {
    private TextInputLayout ingredientInputLayout;
    private RecyclerView ingredientResultsRecyclerView;
    private AutoCompleteTextView ingredientInputTextView;
    private static final String TAG = "ResearchFragment";

    public ResearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ResearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ResearchFragment newInstance(String param1, String param2) {
        ResearchFragment fragment = new ResearchFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_research, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ChipGroup selectedIngredients = view.findViewById(R.id.selectedIngredients);
        TextInputLayout ingredientInputLayout = view.findViewById(R.id.ingredientInputLayout);
        ;
        AutoCompleteTextView ingredientInputTextView = view.findViewById(R.id.ingredientInput);
        ;
        RecyclerView ingredientResultsRecyclerView = view.findViewById(R.id.ingredientResults);
        RecyclerView.LayoutManager layoutManagerIngredients =
                new LinearLayoutManager(requireContext(),
                        LinearLayoutManager.VERTICAL, false);
        List<Ingredients> ingredientsList = getIngredientsListWithGson();
        IngredientsRecyclerViewAdapter ingredientsRecyclerViewAdapter =
                new IngredientsRecyclerViewAdapter(ingredientsList,
                        new IngredientsRecyclerViewAdapter.OnItemClickListener() {

                            @Override
                            public void onIngredientItemClick(Ingredients ingredient) {

                                // Recuperiamo il nome dell'ingrediente
                                String ingredientName = ingredient.getStrIngredient();

                                // Verifichiamo che non sia già stato selezionato
                                for (int i = 0; i < selectedIngredients.getChildCount(); i++) {

                                    Chip existingChip = (Chip) selectedIngredients.getChildAt(i);

                                    if (existingChip.getText().toString().equalsIgnoreCase(ingredientName)) {
                                        Toast.makeText(requireContext(),
                                                "Ingrediente già selezionato",
                                                Toast.LENGTH_SHORT).show();

                                        return;
                                    }
                                }

                                // Creiamo un nuovo Chip
                                Chip ingredientChip = new Chip(requireContext());

                                ingredientChip.setText(ingredientName);
                                ingredientChip.setCloseIconVisible(true);

                                // Permettiamo di rimuovere il Chip con la X
                                ingredientChip.setOnCloseIconClickListener(v ->
                                        selectedIngredients.removeView(ingredientChip)
                                );

                                // Aggiungiamo il Chip al ChipGroup
                                selectedIngredients.addView(ingredientChip);

                                // Nascondiamo la RecyclerView
                                //ingredientResultsRecyclerView.setVisibility(View.GONE);

                                // Svuotiamo il campo di ricerca
                                ingredientInputTextView.setText("", false);

                                // Ripristiniamo la lista completa degli ingredienti
                                //ingredientsRecyclerViewAdapter.resetFilter();
                            }
                        });
        ingredientResultsRecyclerView.setLayoutManager(layoutManagerIngredients);
        ingredientResultsRecyclerView.setAdapter(ingredientsRecyclerViewAdapter);

        // Mostra la RecyclerView quando si tocca il campo di ricerca sia su icona, sia su layout
        // che sul testo.L'ultimo mostra la lista pure se cambia il focus aprendo da 0 la barra di
        // ricerca degli ingredienti dato che in principio il primo tocco sul testo lo interpretava
        //come cambio focus e spostava solo l'int"Cerca un ingrediente" in alto a sinistra senza fare altro
        View.OnClickListener showIngredients = v ->
                ingredientResultsRecyclerView.setVisibility(View.VISIBLE);

        ingredientInputLayout.setOnClickListener(showIngredients);
        ingredientInputTextView.setOnClickListener(showIngredients);
        ingredientInputLayout.setStartIconOnClickListener(showIngredients);
        ingredientInputTextView.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                ingredientResultsRecyclerView.setVisibility(View.VISIBLE);
            }
        });


        // Nasconde la RecyclerView e la tastiera quando si tocca fuori
        view.setOnTouchListener((v, event) -> {

            if (event.getAction() == android.view.MotionEvent.ACTION_DOWN) {

                float x = event.getX();
                float y = event.getY();

                android.graphics.Rect inputRect = new android.graphics.Rect();
                android.graphics.Rect resultsRect = new android.graphics.Rect();

                ingredientInputLayout.getHitRect(inputRect);
                ingredientResultsRecyclerView.getHitRect(resultsRect);

                if (!inputRect.contains((int) x, (int) y)
                        && !resultsRect.contains((int) x, (int) y)) {

                    // Nasconde la RecyclerView
                    ingredientResultsRecyclerView.setVisibility(View.GONE);

                    // Chiude la tastiera
                    InputMethodManager imm = (InputMethodManager)
                            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE);

                    imm.hideSoftInputFromWindow(
                            ingredientInputTextView.getWindowToken(), 0
                    );

                    // Rimuove il focus dal campo di ricerca
                    ingredientInputTextView.clearFocus();
                }
            }

            return false;
        });


        RecyclerView recyclerViewRecipes = view.findViewById(R.id.recipeResults);
        RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(requireContext(),
                        LinearLayoutManager.VERTICAL, false);

        List<Recipes> recipesList = getRecipesListWithGson();
        RecipesRecyclerViewAdapter recipesRecyclerViewAdapter = new RecipesRecyclerViewAdapter(recipesList,
                new RecipesRecyclerViewAdapter.OnItemClickListener() {
                    @Override
                    public void onRecipeItemClick(Recipes recipe) {
                /*Bundle args = new Bundle();
                args.putString(RecipesDetailFragment.ARG_MEAL_ID, recipe.getIdMeal());
                NavHostFragment.findNavController(ResearchFragment.this)
                        .navigate(R.id.recipesDetailFragment, args);
                 */
                        String message = recipe.getFavorite()
                                ? getString(R.string.recipe_added_to_favorites)
                                : getString(R.string.recipe_removed_from_favorites);

                        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFavoriteButtonClick(Recipes recipe) {

                        String message = recipe.getFavorite()
                                ? getString(R.string.recipe_added_to_favorites)
                                : getString(R.string.recipe_removed_from_favorites);

                        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
                    }
                });


        recyclerViewRecipes.setLayoutManager(layoutManager);
        recyclerViewRecipes.setAdapter(recipesRecyclerViewAdapter);


    }


    private List<Recipes> getRecipesListWithGson() {
        JSONParserUtil jsonParserUtil = new JSONParserUtil(requireActivity().getApplication());
        try {
            return jsonParserUtil.recipesParseJSONFileWithGSon(RECIPES_API_TEST_JSON_FILE).getRecipesList();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<Ingredients> getIngredientsListWithGson() {
        JSONParserUtil jsonParserUtil = new JSONParserUtil(requireActivity().getApplication());
        try {
            return jsonParserUtil.ingredientsParseJSONFileWithGSon(INGREDIENTS_API_TEST_JSON_FILE).getIngredients();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}