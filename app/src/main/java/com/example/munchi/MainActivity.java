package com.example.munchi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavHostController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.munchi.adapter.RecipesAdapter;
import com.example.munchi.database.RecipeDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The Main Activity contains the RecipeDatabase object which allows for
 * communication between the SQLite database and the application. All
 * fragments can access this through getActivity().getDB(). The testDB()-
 * method instantly adds two preset recipes to the database.
 */

public class MainActivity extends AppCompatActivity {

    private RecipeDatabase recipeDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recipeDB = new RecipeDatabase(this);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    public RecipeDatabase getDB() {
        return (recipeDB);
    }

    public void testDB() {
        recipeDB.testRecipes();
    }
}