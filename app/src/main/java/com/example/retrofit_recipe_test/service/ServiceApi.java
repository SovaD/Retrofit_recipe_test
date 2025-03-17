package com.example.retrofit_recipe_test.service;

import com.example.retrofit_recipe_test.entities.RecipeList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ServiceApi {
    @GET("recipes")
    Call<RecipeList> getRecipes();

}
