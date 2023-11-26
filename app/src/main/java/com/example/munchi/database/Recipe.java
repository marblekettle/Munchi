package com.example.munchi.database;

public class Recipe {

    private String name;
    private Boolean veg;
    private Integer serves;
    private Ingredient[] ingredients;
    private String[] instructions;
    private String notes;
    public Recipe(
        String name,
        Boolean veg,
        Integer serves,
        Ingredient[] ingredients,
        String[] instructions,
        String notes) {
        this.name = name;
        this.veg = veg;
        this.serves = serves;
        this.ingredients = ingredients;
        this.instructions = instructions;
        this.notes = notes;
    }

    public String getName() {
        return (name);
    }

    public Boolean getVeg() {
        return (veg);
    }

    public Integer getServes() {
        return (serves);
    }

    public Ingredient[] getIngredients() {
        return (ingredients);
    }

    public String[] getInstructions() {
        return (instructions);
    }

    public String getNotes() {
        return notes;
    }
}
