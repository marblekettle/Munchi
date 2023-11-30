package com.example.munchi;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {

    View layout;
    public SearchFragment() {
        // Required empty public constructor
    }
    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        layout = view;
        Button btnSearch = view.findViewById(R.id.buttonSearch);
        Button btnBack = view.findViewById(R.id.buttonBackFromSearch);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                getQuery(bundle);
                Navigation.findNavController(v)
                        .navigate(R.id.action_searchFragment_to_mainFragment, bundle);
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v)
                        .navigate(R.id.action_searchFragment_to_mainFragment);
            }
        });
        return view;
    }

    public void getQuery(Bundle b) {
        EditText txtSearch = layout.findViewById(R.id.textSearch);
        CheckBox boxVeg = layout.findViewById(R.id.boxSearchVeg);
        ArrayList<String> terms = new ArrayList<String>(
                Arrays.asList(txtSearch.getText().toString().split(",")));
        b.putStringArray("terms", terms.toArray(new String[terms.size()]));
        b.putBoolean("veg", boxVeg.isChecked());
        b.putInt("atLeast", -1);
        b.putInt("atMost", -1);
        b.putStringArray("ingredientsIn", new String[0]);
        b.putStringArray("ingredientsOut", new String[0]);
    }
}