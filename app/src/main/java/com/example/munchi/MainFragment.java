package com.example.munchi;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavHostController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.munchi.adapter.RecipesAdapter;
import com.example.munchi.database.Recipe;
import com.example.munchi.database.RecipeDatabase;
import com.example.munchi.database.RecipeQuery;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

/**
 * The main fragment contains a RecycleView displaying the names of each
 * recipe, along with if they are vegetarian and how many people they serve.
 * The recipes are filtered according to a RecipeQuery object that is
 * generated when the fragment is loaded, based on arguments passed to it.
 * By default, the arguments create a query that includes all recipes, but
 * by using the search function it can be made more specific.
 */

public class MainFragment extends Fragment {
    private RecipeDatabase recipeDB;
    private RecipeQuery query;
    public MainFragment() {
        
    }
    public static MainFragment newInstance(RecipeQuery query) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        Object ListArray;
        args.putStringArrayList("terms", (ArrayList<String>) Arrays.asList(query.getTerms()));
        args.putBoolean("veg", query.getVeg());
        args.putInt("atLeast", query.getAtLeast());
        args.putInt("atMost", query.getAtMost());
        args.putStringArrayList("ingredientsIn",
                (ArrayList<String>)Arrays.asList(query.getIngredientsIn()));
        args.putStringArrayList("ingredientsOut",
                (ArrayList<String>)Arrays.asList(query.getIngredientsOut()));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recipeDB = ((MainActivity)getActivity()).getDB();
        if (getArguments() != null) {
            this.query = new RecipeQuery(
                    getArguments().getStringArray("terms"),
                    getArguments().getBoolean("veg"),
                    getArguments().getInt("atLeast"),
                    getArguments().getInt("atMost"),
                    getArguments().getStringArray("ingredientsIn"),
                    getArguments().getStringArray("ingredientsOut")
            );
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Recipes are loaded according to query
		
		Map<Integer, Recipe> results = ((MainActivity)getActivity()).getDB().searchRecipe(query);
        
		// The RecyclerView is filled with recipes
		
		RecyclerView viewRecipes = view.findViewById(R.id.viewRecipes);
        viewRecipes.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false));
        RecipesAdapter ra = new RecipesAdapter(getContext(), results);
        viewRecipes.setAdapter(ra);

        // Button functionality is defined

		Button buttonTest = view.findViewById(R.id.buttonTest);
        buttonTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).getDB().clearDB();
                ((MainActivity)getActivity()).testDB();
            }
        });
        Button buttonSearch = view.findViewById(R.id.buttonSearchGo);
        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v)
                        .navigate(R.id.action_mainFragment_to_searchFragment);
            }
        });
        Button buttonNew = view.findViewById(R.id.buttonNew);
        buttonNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("id", -1);
                Navigation.findNavController(v)
                        .navigate(R.id.action_mainFragment_to_editRecipeFragment, bundle);
            }
        });
    }
}