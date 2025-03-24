package com.example.retrofit_recipe_test;

import android.nfc.Tag;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.retrofit_recipe_test.adapters.CategoryAdapter;
import com.example.retrofit_recipe_test.adapters.OnClickListener;
import com.example.retrofit_recipe_test.adapters.RecipeAdapter;
import com.example.retrofit_recipe_test.entities.Recipe;
import com.example.retrofit_recipe_test.entities.RecipeList;
import com.example.retrofit_recipe_test.service.RetrofitClient;
import com.example.retrofit_recipe_test.service.ServiceApi;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    EditText etToSearch;
    RecyclerView categoryRV, recipeRV;
    private static final String TAG = "tagggggg";
    CategoryAdapter categoryAdapter;
    //    RecipeAdapter recipeAdapter;
    String category = "all";

    List<String> categoryList;
    List<Recipe> allREcipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        categoryRV = findViewById(R.id.rvCategoryList);
        recipeRV = findViewById(R.id.rvRecipeList);
        etToSearch = findViewById(R.id.etToSearch);
        loadData();

        etToSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
filterRecipe();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void filterRecipe() {
        String text = etToSearch.getText().toString().toLowerCase();
        List<Recipe> newList = allREcipes.stream()
                .filter(recipe -> recipe.getCuisine().toLowerCase().contains(text)
                || recipe.getName().toLowerCase().contains(text)
                || recipe.getIngredients().contains(text))
                .collect(Collectors.toList());
        setDataToRecyclerView(newList);
    }

    private void loadData() {
        ServiceApi serviceApi = RetrofitClient.getClient().create(ServiceApi.class);

        try {
            Call<RecipeList> call = serviceApi.getRecipes();
            call.enqueue(new Callback<RecipeList>() {
                @Override
                public void onResponse(Call<RecipeList> call, Response<RecipeList> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        List<Recipe> recipes = response.body().getRecipes();
                        allREcipes = recipes;
                        fillCategoryList(recipes);
                        setDataToRecyclerView(recipes);
//                        for(Recipe recipe: recipes){
//                            Log.i(TAG,recipe.getName()+"\t"+recipe.getCuisine());
//                        }
                    } else {
                        Log.e(TAG, "Error");
                    }
                }

                @Override
                public void onFailure(Call<RecipeList> call, Throwable throwable) {
                    Log.e(TAG, throwable.getMessage());
                }
            });
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }


    }

    private void fillCategoryList(List<Recipe> recipes) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        categoryRV.setLayoutManager(linearLayoutManager);
        categoryList = recipes.stream()
                .map(Recipe::getCuisine)
                .distinct()
                .collect(Collectors.toList());
        Log.i(TAG, Arrays.toString(categoryList.toArray()));
        Collections.sort(categoryList);
        categoryList.add(0, "All");
        categoryAdapter = new CategoryAdapter(getApplicationContext(), categoryList);
        categoryAdapter.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(int position) {
                categoryAdapter.notifyItemChanged(categoryAdapter.selectedPosition);
                categoryAdapter.selectedPosition = position;
                categoryAdapter.notifyItemChanged(categoryAdapter.selectedPosition);

                category = categoryList.get(position);
                Log.i(TAG, category);

                setDataToRecyclerView(recipes);
            }
        });
        categoryRV.setAdapter(categoryAdapter);
    }

    private void setDataToRecyclerView(List<Recipe> recipes) {
        //___sort
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recipeRV.setLayoutManager(linearLayoutManager);

        if (category.toLowerCase().equals("all")) {
           RecipeAdapter recipeAdapter = new RecipeAdapter(MainActivity.this, recipes);
            recipeAdapter.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(int position) {
                    //____________________Intent
                }
            });
            recipeRV.setAdapter(recipeAdapter);
        } else {
            List<Recipe> newList = recipes.stream()
                    .filter(recipe -> recipe.getCuisine().equals(category))
                    .collect(Collectors.toList());
          RecipeAdapter  recipeAdapter = new RecipeAdapter(getApplicationContext(), newList);
            recipeAdapter.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(int position) {
                    //____________________
                }
            });
            recipeRV.setAdapter(recipeAdapter);
        }

    }
}