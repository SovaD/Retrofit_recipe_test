package com.example.retrofit_recipe_test.entities;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RecipeList {
   @SerializedName("recipes")
    List<Recipe> recipes;

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }
}
