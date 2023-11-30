package com.example.munchi.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.munchi.R;
import com.example.munchi.database.Recipe;

import java.util.Map;

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.RecipesHolder> {
    Context context;
    Map<Integer, Recipe> recipes;
    public RecipesAdapter(Context context, Map<Integer, Recipe> recipes) {
        this.context = context;
        this.recipes = recipes;
    }

    @Override
    public RecipesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_recipe, parent, false);
        return (new RecipesHolder(v));
    }

    @Override
    public void onBindViewHolder(RecipesHolder holder, int position) {
        Integer[] idCol = recipes.keySet().toArray(new Integer[recipes.size()]);
        holder.setRecipe(idCol[position], recipes.get(idCol[position]));
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public class RecipesHolder extends RecyclerView.ViewHolder {
        private Integer id;
        private Recipe recipe;
        private View layout;
        public RecipesHolder(View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.layoutRecipe);
        }

        public void setRecipe(Integer id, Recipe recipe) {
            this.id = id;
            TextView name = layout.findViewById(R.id.txtSmallRecipe);
            TextView veg = layout.findViewById(R.id.txtSmallVeg);
            TextView serves = layout.findViewById(R.id.txtSmallServes);
            name.setText(recipe.getName());
            veg.setText(recipe.getVeg() ? "V" : "");
            serves.setText(String.valueOf(recipe.getServes()));
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("id", id);
                    Navigation.findNavController(v).navigate(R.id.
                            action_mainFragment_to_recipeFragment, bundle);
                }
            });
        }
    }
}
