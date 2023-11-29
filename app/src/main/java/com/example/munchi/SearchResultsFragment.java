package com.example.munchi;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.munchi.adapter.RecipesAdapter;
import com.example.munchi.database.Recipe;
import com.example.munchi.database.RecipeQuery;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchResultsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchResultsFragment extends Fragment {
    private RecipeQuery query;

    public SearchResultsFragment() {
        // Required empty public constructor
    }

    public static SearchResultsFragment newInstance(RecipeQuery query) {
        SearchResultsFragment fragment = new SearchResultsFragment();
        Bundle args = new Bundle();
        Object ListArray;
        args.putStringArrayList("terms", (ArrayList<String>)Arrays.asList(query.getTerms()));
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
        View view = inflater.inflate(R.layout.fragment_search_results, container, false);
        Button btnBack = view.findViewById(R.id.btnBackToSearch);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v)
                        .navigate(R.id.action_searchResultsFragment_to_searchFragment);
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Map<Integer, Recipe> results = ((MainActivity)getActivity()).getDB().searchRecipe(query);
        RecyclerView viewResults = view.findViewById(R.id.viewResults);
        viewResults.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false));
        RecipesAdapter ra = new RecipesAdapter(getContext(), results, true);
        viewResults.setAdapter(ra);
    }
}