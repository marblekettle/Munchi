package com.example.munchi;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Layout;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.munchi.database.Ingredient;
import com.example.munchi.database.Recipe;
import com.example.munchi.database.RecipeDatabase;

import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private RecipeDatabase recipeDB = new RecipeDatabase();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        testRecipes();
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        View layo = this.findViewById(R.id.mainLayout);
        TextView recipe = layo.findViewById(R.id.recipeView);
        try {
            String txt = "";
            for (Map.Entry<Integer, String> entry : recipeDB.getNames().entrySet()) {
                txt = txt.concat(String.format("%d: %s\n", entry.getKey(), entry.getValue()));
            }
            recipe.setText(txt);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            recipe.setText("Oops");
        }
    }

    private void testRecipes() {
        recipeDB.addRecipe(new Recipe(
                "Bloody Mary",
                true,
                1,
                new String[]{
                        "1 part vodka",
                        "2 parts tomato juice",
                        "Worcestershire sauce or tabasco",
                        "Black pepper",
                        "Ice cubes"
                },
                new String[]{
                        "Put the ice cubes in a large glass.",
                        "Dash in a little sauce and add some pepper.",
                        "Pour in the vodka and the tomato juice.",
                        "Stir with the celery stick.",
                        "Serve cold."
                },
                "Tasty on a hot day."));
        recipeDB.addRecipe(new Recipe(
                "Avocado Toast",
                true,
                2,
                new String[]{
                        "2 slices of bread",
                        "1 avocado",
                        "Salt and pepper"
                },
                new String[]{
                        "Toast the bread in a toaster until lightly browned.",
                        "Peel the avocado, slice it in half, remove the stone, and cut into thin slices.",
                        "Cover each slice of toasted bread with half of the avocado.",
                        "Sprinkle salt and pepper on the avocado to taste."
                },
                "Staple food for hipsters."
        ));
    }
}