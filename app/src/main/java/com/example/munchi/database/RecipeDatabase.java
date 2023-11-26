package com.example.munchi.database;

import java.util.List;
import java.util.Map;

public class RecipeDatabase {

    private Map<Integer, Recipe> recipeMap;
    private int newId;

    public RecipeDatabase() {
        newId = 0;
    }

    public Recipe[] searchRecipe(String term) {
        List<Recipe> out = null;
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
        Map<Integer, String> out = null;
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