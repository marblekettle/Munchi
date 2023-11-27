package com.example.munchi;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.munchi.database.RecipeDatabase;

import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private RecipeDatabase recipeDB = new RecipeDatabase(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        View layo = this.findViewById(R.id.mainLayout);
        TextView recipies = layo.findViewById(R.id.recipiesView);
        Button testbtn = layo.findViewById(R.id.buttonTest);
        TextView recipe = layo.findViewById(R.id.recipeView);
        testbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = recipeDB.getWritableDatabase();
                recipeDB.onUpgrade(db, 0, 0);
            }
        });
        try {
            String txt = "";
            for (Map.Entry<Integer, String> entry : recipeDB.getNames(5).entrySet()) {
                txt = txt.concat(String.format("%d: %s\n", entry.getKey(), entry.getValue()));
            }
            recipies.setText(txt);
            String txt2 = "";
            String[] ingre = recipeDB.getRecipe(1).getInstructions();
            System.out.println(ingre[0]);
            for (Integer i = 0; i < ingre.length; i++) {
                txt2 = txt2.concat(String.format("%s\n", ingre[i]));
            }
            recipe.setText(txt2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}