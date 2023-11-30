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

    /**
     FloatingActionButton delIng = layout.findViewById(R.id.btnDelIngredient);
     delIng.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
    System.out.println(String.valueOf(getAdapterPosition()));
    }
    });*/

    @Override
    public void onBindViewHolder(IngredientHolder holder, int position) {
        TextView txt = holder.itemView.findViewById(R.id.txtIngredient);
        txt.setText(ingredients.get(position));
        FloatingActionButton del = holder.itemView.findViewById(R.id.btnDelIngredient);
        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    public class IngredientHolder extends RecyclerView.ViewHolder {

        public IngredientHolder(View itemView) {
            super(itemView);
        }
    }
}
