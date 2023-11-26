package com.example.munchi.database;

public class Ingredient {
    private Integer amount;
    private String unit;
    private String ingredient;

    public Ingredient(Integer amount, String unit, String ingredient) {
         this.amount = amount;
         this.unit = unit;
         this.ingredient = ingredient;
    }

    public String getString() {
        return (String.format("%i %s %s", amount, unit, ingredient));
    }

    public Integer getAmount() {
        return (amount);
    }

    public String getUnit() {
        return (unit);
    }

    public String getIngredient() {
        return (ingredient);
    }
}
