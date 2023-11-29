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
import com.example.munchi.database.RecipeDatabase;

public class MainFragment extends Fragment {
    private RecipeDatabase recipeDB;
    public MainFragment() {
        // Required empty public constructor
    }
    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recipeDB = ((MainActivity)getActivity()).getDB();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return (inflater.inflate(R.layout.fragment_main, container, false));
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        RecyclerView viewRecipes = view.findViewById(R.id.viewRecipes);
        viewRecipes.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false));
        RecipesAdapter ra = new RecipesAdapter(getContext(),
                recipeDB.getSummary(10), false);
        viewRecipes.setAdapter(ra);
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