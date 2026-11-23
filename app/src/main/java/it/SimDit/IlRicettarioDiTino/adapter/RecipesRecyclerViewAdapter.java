package it.SimDit.IlRicettarioDiTino.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import it.SimDit.IlRicettarioDiTino.R;
import it.SimDit.IlRicettarioDiTino.model.Recipes;

/**
 * Custom adapter that extends RecyclerView.Adapter to show an ArrayList of Recipes
 * with a RecyclerView.
 */
public class RecipesRecyclerViewAdapter extends
        RecyclerView.Adapter<RecipesRecyclerViewAdapter.RecipesViewHolder> {

    /**
     * Interface to associate a click listener with
     * a RecyclerView item.
     */
    //Useful to handle all the logic about the Item Click or Button Click outside the adapter to notify the activity that use the adapter get the news when button is clicked
    public interface OnItemClickListener {
        void onRecipeItemClick(Recipes recipe);

        void onFavoriteButtonClick(Recipes recipe);
    }

    private final List<Recipes> recipesList;
    private final OnItemClickListener onItemClickListener;

    public RecipesRecyclerViewAdapter(List<Recipes> recipesList, OnItemClickListener onItemClickListener) {
        this.recipesList = recipesList;
        this.onItemClickListener = onItemClickListener;
    }

    //You can do the inflate ot the layout cell
    @NonNull
    @Override
    public RecipesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.recipe_list_item, parent, false);

        return new RecipesViewHolder(view);
    }

    //Binding data call
    @Override
    public void onBindViewHolder(@NonNull RecipesViewHolder holder, int position) {
        holder.bind(recipesList.get(position));
    }

    @Override
    public int getItemCount() {
        if (recipesList != null) {
            return recipesList.size();
        }
        return 0;
    }

    /**
     * Custom ViewHolder to bind data to the RecyclerView items.
     */
    public class RecipesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private final TextView recipeTitle;
        private final TextView recipeCountry;
        private final ImageView imageFood;
        private final ImageButton favoriteButton;
        //Constructor usefull to get all the reference from the recipe_list_item
        public RecipesViewHolder(@NonNull View itemView) {
            super(itemView);
            recipeTitle = itemView.findViewById(R.id.recipe_title);
            recipeCountry = itemView.findViewById(R.id.recipe_country);
            imageFood = itemView.findViewById(R.id.recipe_image);
            favoriteButton = itemView.findViewById(R.id.button_add_to_favorites);
            //Listener to the favorite button
            favoriteButton.setOnClickListener(this);
            //Listener to the cell item click
            itemView.setOnClickListener(this);

        }
        //Useful for binding data in the ViewHolder
        public void bind(Recipes recipe) {
            recipeTitle.setText(recipe.getStrMeal());
            recipeCountry.setText(recipe.getStrCountry());
            Glide.with(itemView)
                    .load(recipe.getStrMealThumb())
                    .into(imageFood);

            favoriteButton.setSelected(recipe.getFavorite());
            favoriteButton.setImageResource(
                    recipe.getFavorite()
                            ? R.drawable.heart_like_solid_svgrepo_com
                            : R.drawable.ic_favorite_border
            );
        }

        @Override
        public void onClick(View v) {
            int position = getBindingAdapterPosition();

            if (position == RecyclerView.NO_POSITION) {
                return;
            }
            Recipes recipe = recipesList.get(position);
            if (v.getId() == R.id.button_add_to_favorites) {

                // Aggiorno lo stato del preferito nel Model
                boolean isFavorite = !recipe.getFavorite();
                recipe.setFavorite(isFavorite);

                // Aggiorno l'aspetto del pulsante
                favoriteButton.setSelected(isFavorite);
                favoriteButton.setImageResource(
                        isFavorite
                                ? R.drawable.heart_like_solid_svgrepo_com
                                : R.drawable.ic_favorite_border
                );

                // Comunico al Fragment che è stato premuto il pulsante
                onItemClickListener.onFavoriteButtonClick(recipe);

            } else {
                onItemClickListener.onRecipeItemClick(recipe);
            }
        }
    }
}
