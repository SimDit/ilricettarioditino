package it.SimDit.IlRicettarioDiTino.ui.main;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.google.android.material.button.MaterialButton;

import it.SimDit.IlRicettarioDiTino.R;

public class RecipesDetailFragment extends Fragment {

    public static final String ARG_MEAL_ID = "idMeal";

    public RecipesDetailFragment() {
        // Required empty public constructor
    }

    public static RecipesDetailFragment newInstance(String param1, String param2) {
        RecipesDetailFragment fragment = new RecipesDetailFragment();
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
        return inflater.inflate(R.layout.fragment_recipes_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String idMeal = getArguments() != null
                ? getArguments().getString(ARG_MEAL_ID)
                : null;

        MaterialButton favoriteButton =
                view.findViewById(R.id.favoriteButton);

        favoriteButton.setOnClickListener(v -> {

            boolean isFavorite = !favoriteButton.isSelected();

            favoriteButton.setSelected(isFavorite);

            favoriteButton.setText(isFavorite ? "♥" : "♡");

        });

    }
}