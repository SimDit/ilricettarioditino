package it.SimDit.IlRicettarioDiTino.ui.main;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashSet;

import it.SimDit.IlRicettarioDiTino.R;
import it.SimDit.IlRicettarioDiTino.adapter.RecipesRecyclerViewAdapter;
import it.SimDit.IlRicettarioDiTino.model.Recipes;
import it.SimDit.IlRicettarioDiTino.util.JSONParserUtil;
import it.SimDit.IlRicettarioDiTino.util.SharedPreferencesUtil;

import java.io.IOException;

import static it.SimDit.IlRicettarioDiTino.util.Constants.RECIPES_API_TEST_JSON_FILE;
import static it.SimDit.IlRicettarioDiTino.util.Constants.SHARED_PREFERENCES_FILE_NAME;
import static it.SimDit.IlRicettarioDiTino.util.Constants.SHARED_PREFERENCES_FAVORITE_RECIPES;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FavoritesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavoritesFragment extends Fragment {
    private SharedPreferencesUtil sharedPreferencesUtil;
    private Set<String> favoriteRecipesIds;
    private List<Recipes> favoriteRecipesList;
    private RecipesRecyclerViewAdapter recipesRecyclerViewAdapter;
    private RecyclerView favoriteRecipesRecyclerView;
    private View favoritesEmptyState;


    public FavoritesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FavoritesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FavoritesFragment newInstance(String param1, String param2) {
        FavoritesFragment fragment = new FavoritesFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferencesUtil = new SharedPreferencesUtil(
                requireActivity().getApplication()
        );

        Set<String> savedFavoriteRecipesIds =
                sharedPreferencesUtil.readStringSetData(
                        SHARED_PREFERENCES_FILE_NAME,
                        SHARED_PREFERENCES_FAVORITE_RECIPES
                );

        if (savedFavoriteRecipesIds != null) {
            favoriteRecipesIds = new HashSet<>(savedFavoriteRecipesIds);
        } else {
            favoriteRecipesIds = new HashSet<>();
        }

        favoriteRecipesList = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorites, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        favoriteRecipesRecyclerView = view.findViewById(R.id.favoriteRecipesRecyclerView);
        favoritesEmptyState = view.findViewById(R.id.favoritesEmptyState);

        RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(requireContext(),
                        LinearLayoutManager.VERTICAL, false);

        List<Recipes> recipesList = getRecipesListWithGson();

        if (recipesList != null) {
            for (Recipes recipe : recipesList) {
                if (favoriteRecipesIds.contains(recipe.getIdMeal())) {
                    recipe.setFavorite(true);
                    favoriteRecipesList.add(recipe);
                }
            }
        }

        recipesRecyclerViewAdapter = new RecipesRecyclerViewAdapter(
                favoriteRecipesList,
                new RecipesRecyclerViewAdapter.OnItemClickListener() {

                    @Override
                    public void onRecipeItemClick(Recipes recipe) {

                    }

                    @Override
                    public void onFavoriteButtonClick(Recipes recipe) {

                        favoriteRecipesIds.remove(recipe.getIdMeal());

                        sharedPreferencesUtil.writeStringSetData(
                                SHARED_PREFERENCES_FILE_NAME,
                                SHARED_PREFERENCES_FAVORITE_RECIPES,
                                new HashSet<>(favoriteRecipesIds)
                        );

                        favoriteRecipesList.remove(recipe);
                        recipesRecyclerViewAdapter.notifyDataSetChanged();
                        updateEmptyState();
                    }
                }
        );

        favoriteRecipesRecyclerView.setLayoutManager(layoutManager);
        favoriteRecipesRecyclerView.setAdapter(recipesRecyclerViewAdapter);
        updateEmptyState();
    }

    private void updateEmptyState() {
        boolean hasFavorites = !favoriteRecipesList.isEmpty();
        favoriteRecipesRecyclerView.setVisibility(hasFavorites ? View.VISIBLE : View.GONE);
        favoritesEmptyState.setVisibility(hasFavorites ? View.GONE : View.VISIBLE);
    }

    private List<Recipes> getRecipesListWithGson() {

        JSONParserUtil jsonParserUtil =
                new JSONParserUtil(requireActivity().getApplication());

        try {
            return jsonParserUtil
                    .recipesParseJSONFileWithGSon(RECIPES_API_TEST_JSON_FILE)
                    .getRecipesList();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
