package com.example.munchi.database;

public class RecipeQuery {
    private String[] terms;
    private Boolean veg;
    private Integer atLeast;
    private Integer atMost;
    private String[] ingredientsIn;
    private String[] ingredientsOut;
    public RecipeQuery(
            String[] terms,
            Boolean veg,
            Integer atLeast,
            Integer atMost,
            String[] ingredientsIn,
            String[] ingredientsOut) {
        this.terms = terms;
        this.veg = veg;
        this.atLeast = atLeast;
        this.atMost = atMost;
        this.ingredientsIn = ingredientsIn;
        this.ingredientsOut = ingredientsOut;
    }

    public String[] getTerms() {
        return terms;
    }

    public Boolean getVeg() {
        return veg;
    }

    public Integer getAtLeast() {
        return atLeast;
    }

    public Integer getAtMost() {
        return atMost;
    }

    public String[] getIngredientsIn() {
        return ingredientsIn;
    }

    public String[] getIngredientsOut() {
        return ingredientsOut;
    }
}
