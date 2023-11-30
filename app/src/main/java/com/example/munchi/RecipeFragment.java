package com.example.munchi;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.munchi.database.Recipe;
import com.example.munchi.database.RecipeDatabase;

/**
 * The recipe fragment loads information about a specific recipe from the
 * database and displays it. Each recipe is identified by the primary key
 * 'id'. 
 */

public class RecipeFragment extends Fragment {
    private Integer id;

    public RecipeFragment() {
        
    }

    public static RecipeFragment newInstance(Integer id) {
        RecipeFragment fragment = new RecipeFragment();
        Bundle args = new Bundle();
        args.putInt("id", id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = getArguments().getInt("id");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe, container, false);
        putRecipe(view);

		// Define button functionality

        Button back = view.findViewById(R.id.btnBackFromRecipe);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v)
                        .navigate(R.id.action_recipeFragment_to_mainFragment);
            }
        });
        Button edit = view.findViewById(R.id.btnEditRecipe);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("id", id);
                Navigation.findNavController(v)
                        .navigate(R.id.action_recipeFragment_to_editRecipeFragment, bundle);
            }
        });
        Button del = view.findViewById(R.id.btnDeleteRecipe);
        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).getDB().deleteRecipe(id);
                Navigation.findNavController(v)
                        .navigate(R.id.action_recipeFragment_to_mainFragment);
            }
        });
        return (view);
    }

	// Fills in the different textviews with recipe data

    private void putRecipe(View v) {
        TextView recipeName = v.findViewById(R.id.txtRecipeName);
        TextView serves = v.findViewById(R.id.txtServes);
        TextView veg = v.findViewById(R.id.txtVeg);
        TextView ingredients = v.findViewById(R.id.txtIngredients);
        TextView steps = v.findViewById(R.id.txtSteps);
        TextView notes = v.findViewById(R.id.txtNotes);
        try {
            Recipe recipe = ((MainActivity)getActivity()).getDB().getRecipe(id);
            recipeName.setText(recipe.getName());
            serves.setText("Serves " + String.valueOf(recipe.getServes()));
            if (recipe.getVeg()) {
                veg.setText("Vegetarian");
            }
            String txtIng = "";
            for (String entry : recipe.getIngredients()) {
                txtIng += String.format("- %s\n", entry);
            }
            ingredients.setText(txtIng);
            String txtIns = "";
            String[] makeSteps = recipe.getInstructions();
            for (int i = 0; i < makeSteps.length; i++) {
                txtIns += String.format("%d: %s\n\n", i + 1, makeSteps[i]);
            }
            steps.setText(txtIns);
            notes.setText(recipe.getNotes());
        } catch (RecipeDatabase.IdNotFoundError e) {
            recipeName.setText("Recipe not found");
        }
    }
}