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
        View mainLayout = this.findViewById(R.id.mainLayout);
        Button buttonTest = mainLayout.findViewById(R.id.buttonTest);
        buttonTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = recipeDB.getWritableDatabase();
                recipeDB.onUpgrade(db, 0, 0);
            }
        });
        try {
            
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}