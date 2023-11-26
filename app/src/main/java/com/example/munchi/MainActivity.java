package com.example.munchi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.munchi.database.RecipeDatabase;

public class MainActivity extends AppCompatActivity {

    private RecipeDatabase recipeDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}