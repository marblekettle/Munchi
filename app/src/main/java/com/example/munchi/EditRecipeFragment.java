package com.example.munchi;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.example.munchi.adapter.IngredientAdapter;
import com.example.munchi.database.Recipe;
import com.example.munchi.database.RecipeDatabase;

import java.util.ArrayList;
import java.util.List;

public class EditRecipeFragment extends Fragment {

    private Integer id;
    private View layout;
    private String[] ingredientList;
    public EditRecipeFragment() {
        // Required empty public constructor
    }

    public static EditRecipeFragment newInstance(Integer id) {
        EditRecipeFragment fragment = new EditRecipeFragment();
        Bundle args = new Bundle();
        args.putInt("id", id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.id = getArguments().getInt("id");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_recipe, container, false);
        return (view);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        layout = view;
        RecipeDatabase db = ((MainActivity)getActivity()).getDB();
        RecyclerView ingredients = view.findViewById(R.id.listIngredients);
        ingredients.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false));
        if (id == -1) {
            ingredientList = new String[] {""};
        } else {
            try {
                TextView newRecipe = view.findViewById(R.id.txtNewRecipe);
                newRecipe.setText("Edit Recipe");
                Recipe recipe = db.getRecipe(id);
                FromRecipe(view, recipe);
            } catch (RecipeDatabase.IdNotFoundError e) {
                throw new RuntimeException(e);
            }
        }
        IngredientAdapter ia = new IngredientAdapter(getContext(), ingredientList);
        ingredients.setAdapter(ia);
        Button btnCancel = view.findViewById(R.id.btnCancelEdit);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (id == -1) {
                    Navigation.findNavController(v)
                            .navigate(R.id.action_editRecipeFragment_to_mainFragment);
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putInt("id", id);
                    Navigation.findNavController(v)
                            .navigate(R.id.action_editRecipeFragment_to_recipeFragment, bundle);
                }
            }
        });
        Button btnSave = view.findViewById(R.id.btnSaveEdit);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                if (id == -1) {
                    Integer newId = db.addRecipe(ToRecipe(layout));
                    bundle.putInt("id", newId);
                } else {
                    db.updateRecipe(id, ToRecipe(layout));
                    bundle.putInt("id", id);
                }
                Navigation.findNavController(v)
                        .navigate(R.id.action_editRecipeFragment_to_recipeFragment, bundle);
            }
        });
    }

    private Recipe ToRecipe(View v) {

        TextView name = v.findViewById(R.id.txtEditName);
        CheckBox veg = v.findViewById(R.id.boxVeg);
        TextView serves = v.findViewById(R.id.txtEditServes);
        TextView notes = v.findViewById(R.id.txtEditNotes);
        System.out.println(name);
        System.out.println(serves);
        System.out.println(notes);
        Recipe recipe = new Recipe(
                name.getText().toString(),
                veg.isChecked(),
                Integer.parseInt(serves.getText().toString()),
                (String[]) null,
                (String[]) null,
                notes.getText().toString()
        );
        return (recipe);
    }

    private void FromRecipe(View v, Recipe recipe) {
        TextView name = v.findViewById(R.id.txtEditName);
        CheckBox veg = v.findViewById(R.id.boxVeg);
        TextView serves = v.findViewById(R.id.txtEditServes);
        TextView notes = v.findViewById(R.id.txtEditNotes);
        name.setText(recipe.getName());
        veg.setChecked(recipe.getVeg());
        serves.setText(String.valueOf(recipe.getServes()));
        notes.setText(recipe.getNotes());
        ingredientList = recipe.getIngredients();
    }
}