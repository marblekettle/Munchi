package com.example.munchi.database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecipeDatabase {

    private Map<Integer, Recipe> recipeMap = new HashMap<Integer, Recipe>();
    private int newId = 0;

    public RecipeDatabase() {

    }

    public Recipe[] searchRecipe(String term) {
        List<Recipe> out = new ArrayList<Recipe>();
        for (Map.Entry<Integer, Recipe> entry : recipeMap.entrySet()) {
            String[] instr = entry.getValue().getInstructions();
            for (String step : instr) {
                int index = step.indexOf(term);
                if (index != -1) {
                    out.add(entry.getValue());
                }
            }
        }
        return ((Recipe[]) out.toArray());
    }

    public Map<Integer, String> getNames() {
        Map<Integer, String> out = new HashMap<Integer, String>();
        for (Map.Entry<Integer, Recipe> entry : recipeMap.entrySet()) {
            out.put(entry.getKey(), entry.getValue().getName());
        }
        return (out);
    }

    public Recipe getRecipe(int id) throws Exception {
        if (!recipeMap.containsKey(id)) {
            throw new IdNotFoundError("Id Not Found");
        }
        return (recipeMap.get(id));
    }

    public void addRecipe(Recipe recipe) {
        recipeMap.put(newId, recipe);
        newId += 1;
    }

    public void deleteRecipe(int id) {
        recipeMap.remove(id);
    }

    public void updateRecipe(int id, Recipe recipe) {
        recipeMap.replace(id, recipe);
    }

    class IdNotFoundError extends Exception {
        IdNotFoundError(String error) {
            super(error);
        }
    }
}