package com.example.retrofit_recipe_test.entities;

import java.util.List;

public class Recipe {
    int id;
    String name;
    List<String> ingredients;
    List<String> instructions;
    String cuisine;
    String image;
    Float rating;
    List<String> mealType;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public List<String> getInstructions() {
        return instructions;
    }

    public String getCuisine() {
        return cuisine;
    }

    public String getImage() {
        return image;
    }

    public Float getRating() {
        return rating;
    }

    public List<String> getMealType() {
        return mealType;
    }
}
