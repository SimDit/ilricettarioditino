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
import it.SimDit.IlRicettarioDiTino.model.Ingredients;

/**
 * Custom adapter that extends RecyclerView.Adapter to show an ArrayList of Ingredients
 * with a RecyclerView.
 */
public class IngredientsRecyclerViewAdapter extends
        RecyclerView.Adapter<IngredientsRecyclerViewAdapter.IngredientsViewHolder> {

    /**
     * Interface to associate a click listener with
     * a RecyclerView item.
     */
    //Useful to handle all the logic about the Item Click or Button Click outside the adapter to notify the activity that use the adapter get the news when button is clicked
    public interface OnItemClickListener {
        void onIngredientItemClick(Ingredients ingredient);
    }

    private final List<Ingredients> ingredientsList;
    private final OnItemClickListener onItemClickListener;

    public IngredientsRecyclerViewAdapter(List<Ingredients> ingredientsList, OnItemClickListener onItemClickListener) {
        this.ingredientsList = ingredientsList;
        this.onItemClickListener = onItemClickListener;
    }

    //You can do the inflate ot the layout cell
    @NonNull
    @Override
    public IngredientsRecyclerViewAdapter.IngredientsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.ingredient_list_item, parent, false);

        return new IngredientsViewHolder(view);
    }

    //Binding data call
    @Override
    public void onBindViewHolder(@NonNull IngredientsViewHolder holder, int position) {
        holder.bind(ingredientsList.get(position));
    }

    @Override
    public int getItemCount() {
        if (ingredientsList != null) {
            return ingredientsList.size();
        }
        return 0;
    }

    /**
     * Custom ViewHolder to bind data to the RecyclerView items.
     */
    public class IngredientsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private final TextView strIngredient;
        private final ImageView imageIngredient;

        //Constructor usefull to get all the reference from the ingredient_list_item
        public IngredientsViewHolder(@NonNull View itemView) {
            super(itemView);
            strIngredient = itemView.findViewById(R.id.ingredient_name);
            imageIngredient = itemView.findViewById(R.id.ingredient_image);
            //Listener to the cell item click
            itemView.setOnClickListener(this);

        }
        //Useful for binding data in the ViewHolder
        public void bind(Ingredients ingredient) {
            strIngredient.setText(ingredient.getStrIngredient());
            Glide.with(itemView)
                    .load(ingredient.getStrThumb())
                    .into(imageIngredient);
        }

        @Override
        public void onClick(View v) {
            int position = getBindingAdapterPosition();

            if (position == RecyclerView.NO_POSITION) {
                return;
            }
            Ingredients ingredient = ingredientsList.get(position);
            onItemClickListener.onIngredientItemClick(ingredient);

        }
    }
}