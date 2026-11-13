package it.SimDit.IlRicettarioDiTino.ui.main;

import static it.SimDit.IlRicettarioDiTino.util.Constants.RECIPES_API_TEST_JSON_FILE;

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
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import it.SimDit.IlRicettarioDiTino.R;
import it.SimDit.IlRicettarioDiTino.adapter.RecipesRecyclerViewAdapter;
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
        ingredientInputTextView = view.findViewById(R.id.ingredientInput);
        ingredientResultsRecyclerView = view.findViewById(R.id.ingredientResults);
        ingredientInputLayout = view.findViewById(R.id.ingredientInputLayout);
        ingredientInputLayout.setOnClickListener(v -> {
            ingredientResultsRecyclerView.setVisibility(View.VISIBLE);

        });
        ingredientInputTextView.setOnClickListener(v ->
                ingredientResultsRecyclerView.setVisibility(View.VISIBLE)
        );





        RecyclerView recyclerViewRecipes = view.findViewById(R.id.recipeResults);
        RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(requireContext(),
                        LinearLayoutManager.VERTICAL, false);

        List<Recipes> recipesList=getRecipesListWithJsonReader();
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

    private List<Recipes> getRecipesListWithJsonReader() {
        JSONParserUtil jsonParserUtil = new JSONParserUtil(requireActivity().getApplication());
        try{
            return jsonParserUtil.parseJSONFileWithJsonReader(RECIPES_API_TEST_JSON_FILE).getRecipesList();
        }catch(IOException e){
            e.printStackTrace();
        }
        return null;
    }
}