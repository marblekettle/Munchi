package com.example.munchi.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class RecipeDatabase extends SQLiteOpenHelper {
    public RecipeDatabase(Context context) {
        super(context, "recipeDatabase", null, 1);
    }

	// Create tables

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE recipes (" +
                "id      INTEGER PRIMARY KEY NOT NULL," +
                "name    TEXT NOT NULL," +
                "veg     BIT NOT NULL," +
                "serves  INTEGER NOT NULL," +
                "notes   TEXT NOT NULL" +
                ")");
        db.execSQL("CREATE TABLE ingredients (" +
                "id      INTEGER PRIMARY KEY NOT NULL," +
                "recipe  INTEGER NOT NULL," +
                "content TEXT NOT NULL" +
                ")");
        db.execSQL("CREATE TABLE steps (" +
                "id      INTEGER PRIMARY KEY NOT NULL," +
                "recipe  INTEGER NOT NULL," +
                "content TEXT NOT NULL" +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        clearDB();
    }

	// Remove and regenerate tables

    public void clearDB() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS recipes");
        db.execSQL("DROP TABLE IF EXISTS ingredients");
        db.execSQL("DROP TABLE IF EXISTS steps");
        onCreate(db);
    }

	/**
	 *  Generate a map linking recipe IDs to Recipe objects depending on
	 *  the query. Default options will fetch all recipes in the database.
	 */

    public Map<Integer, Recipe> searchRecipe(RecipeQuery query) {
        Map<Integer, Recipe> out = new HashMap<Integer, Recipe>();
        SQLiteDatabase db = this.getReadableDatabase();
        String[] terms = query.getTerms();
        Set<Integer> qualify = new HashSet<Integer>();
        Cursor c;
        if (terms == null || terms.length == 0) {
            c = db.query("recipes", new String[]{"id"}, null,
                    null, null, null, null, null);
		
		// Test if a term is found in the instructions

        } else {
            c = db.query("steps", new String[]{"recipe", "content"}, "content LIKE '%'||?||'%'",
                    terms, null, null,null,null);
        }
        c.moveToFirst();
        while (!c.isAfterLast()) {
            qualify.add(c.getInt(0));
            c.moveToNext();
        }
        for (Integer entry : qualify) {
            try {
                Recipe recipe = getRecipe(entry);
                if (query.getVeg() && !recipe.getVeg())
                    continue ;
                out.put(entry, recipe);
            } catch (Exception e) {

            }
        }
        return (out);
    }

	// Fetch a recipe with a specific id

    public Recipe getRecipe(int id) throws IdNotFoundError {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor rc = db.query("recipes",
                new String[]{"id", "name", "veg", "serves", "notes"}, "id=?",
                new String[]{ String.valueOf(id) },
                null, null, null, null);
        if (rc == null) {
            throw new IdNotFoundError("Id Not Found");
        }
        rc.moveToFirst();
        int recipeId = Integer.parseInt(rc.getString(0));
        Cursor ic = db.query("ingredients",
                new String[]{"id", "recipe", "content"}, "recipe=?",
                new String[]{ String.valueOf(recipeId) },
                null, null, "id", null);
        Cursor sc = db.query("steps",
                new String[]{"id", "recipe", "content"}, "recipe=?",
                new String[]{ String.valueOf(recipeId) },
                null, null, "id", null);
        ic.moveToFirst();
        List<String> ingredientList = new ArrayList<String>();
        while (!ic.isAfterLast()) {
            ingredientList.add(ic.getString(2));
            ic.moveToNext();
        }
        sc.moveToFirst();
        List<String> stepsList = new ArrayList<String>();
        while (!sc.isAfterLast()) {
            stepsList.add(sc.getString(2));
            sc.moveToNext();
        }
        Recipe recipe = new Recipe(
                rc.getString(1),
                Boolean.parseBoolean(rc.getString(2)),
                Integer.parseInt(rc.getString(3)),
                ingredientList.stream().toArray(String[]::new),
                stepsList.stream().toArray(String[]::new),
                rc.getString(4)
        );
        return (recipe);
    }

    public Integer addRecipe(Recipe recipe) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", recipe.getName());
        values.put("veg", Boolean.toString(recipe.getVeg()));
        values.put("serves", String.valueOf(recipe.getServes()));
        values.put("notes", recipe.getNotes());
        Integer id = -1;
        try {
            db.beginTransaction();
            db.insert("recipes", null, values);
            Cursor idc = db.rawQuery("SELECT rowid, id FROM recipes WHERE rowid=last_insert_rowid()", null);
            idc.moveToFirst();
            id = Integer.parseInt(idc.getString(1));
            for (String entry : recipe.getIngredients()) {
                ContentValues ingredientValues = new ContentValues();
                ingredientValues.put("recipe", id);
                ingredientValues.put("content", entry);
                db.insert("ingredients", null, ingredientValues);
            }
            for (String entry : recipe.getInstructions()) {
                ContentValues stepValues = new ContentValues();
                stepValues.put("recipe", id);
                stepValues.put("content", entry);
                db.insert("steps", null, stepValues);
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            id = -1;
        } finally {
            db.endTransaction();
            return (id);
        }
    }

    public void deleteRecipe(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            db.beginTransaction();
            db.delete("recipes", "id=?", new String[]{String.valueOf(id)});
            db.delete("ingredients", "recipe=?", new String[]{String.valueOf(id)});
            db.delete("steps", "recipe=?", new String[]{String.valueOf(id)});
            System.out.println("Nice!");
            db.setTransactionSuccessful();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            db.endTransaction();
        }
    }

    public void updateRecipe(int id, Recipe recipe) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", recipe.getName());
        values.put("veg", Boolean.toString(recipe.getVeg()));
        values.put("serves", String.valueOf(recipe.getServes()));
        values.put("notes", recipe.getNotes());
        try {
            db.beginTransaction();
            db.update("recipes", values, "id=?", new String[]{"id"});
            db.delete("ingredients", "id=?", new String[]{"id"});
            db.delete("steps", "id=?", new String[]{"id"});
            for (String entry : recipe.getIngredients()) {
                ContentValues ingredientValues = new ContentValues();
                ingredientValues.put("recipe", id);
                ingredientValues.put("content", entry);
                db.insert("ingredients", null, ingredientValues);
            }
            for (String entry : recipe.getInstructions()) {
                ContentValues stepValues = new ContentValues();
                stepValues.put("recipe", id);
                stepValues.put("content", entry);
                db.insert("steps", null, stepValues);
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            db.endTransaction();
        }
    }

    public class IdNotFoundError extends Exception {
        IdNotFoundError(String error) {
            super(error);
        }
    }

    public void testRecipes() {
        addRecipe(new Recipe(
                "Bloody Mary",
                true,
                1,
                new String[]{
                        "1 part vodka",
                        "2 parts tomato juice",
                        "Worcestershire sauce or tabasco",
                        "Black pepper",
                        "Ice cubes"
                },
                new String[]{
                        "Put the ice cubes in a large glass.",
                        "Dash in a little sauce and add some pepper.",
                        "Pour in the vodka and the tomato juice.",
                        "Stir with the celery stick.",
                        "Serve cold."
                },
                "Tasty on a hot day."));
        addRecipe(new Recipe(
                "Avocado Toast",
                true,
                2,
                new String[]{
                        "2 slices of bread",
                        "1 avocado",
                        "Salt and pepper"
                },
                new String[]{
                        "Toast the bread in a toaster until lightly browned.",
                        "Peel the avocado, slice it in half, remove the stone, and cut into thin slices.",
                        "Cover each slice of toasted bread with half of the avocado.",
                        "Sprinkle salt and pepper on the avocado to taste."
                },
                "Staple food for hipsters."
        ));
    }
}