package com.example.munchi.adapter;

import android.content.Context;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.munchi.R;

import org.w3c.dom.Text;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.RecipesHolder> {
    Context context;
    Map<Integer, String> recipes;
    public RecipesAdapter(Context context, Map<Integer, String> recipes) {
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
        Collection<String> nameCol = recipes.values();
        String[] names = nameCol.toArray(new String[0]);
        holder.setName(names[position]);
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public class RecipesHolder extends RecyclerView.ViewHolder {
        private TextView name;
        public RecipesHolder(View itemView) {
            super(itemView);
            View layout = itemView.findViewById(R.id.layoutRecipe);
            name = layout.findViewById(R.id.textRecipeSmall);
        }

        public void setName(String name) {
            this.name.setText(name);
        }
    }
}
