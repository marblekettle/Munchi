package com.example.munchi.adapter;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.munchi.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Arrays;
import java.util.List;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientHolder> {

    private Context context;
    private List<String> ingredients;

    public IngredientAdapter(Context context, String[] ingredients) {
        this.context = context;
        this.ingredients = Arrays.asList(ingredients);
    }

    @Override
    public IngredientHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_ingredient, parent, false);
        return (new IngredientHolder(v));
    }

    @Override
    public void onBindViewHolder(IngredientHolder holder, int position) {
        holder.setIngredient(ingredients.get(position));
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    public class IngredientHolder extends RecyclerView.ViewHolder {

        private View layout;
        public IngredientHolder(View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.layoutIngredient);
            FloatingActionButton delIng = layout.findViewById(R.id.btnDelIngredient);
            delIng.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println("delete");
                }
            });
        }
        public void setIngredient(String ingredient) {
            TextView text = layout.findViewById(R.id.txtIngredient);
            text.setText(ingredient);
        }

        public String getIngredient() {
            TextView text = layout.findViewById(R.id.txtIngredient);
            String out = text.getText().toString();
            return (out);
        }
    }
}
