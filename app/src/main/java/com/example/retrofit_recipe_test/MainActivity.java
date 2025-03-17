package com.example.retrofit_recipe_test;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.retrofit_recipe_test.entities.Recipe;
import com.example.retrofit_recipe_test.entities.RecipeList;
import com.example.retrofit_recipe_test.service.RetrofitClient;
import com.example.retrofit_recipe_test.service.ServiceApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {


    private static final String TAG = "tagggggg";

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
    loadData();
    }

    private void loadData() {
        ServiceApi serviceApi = RetrofitClient.getClient().create(ServiceApi.class);

        try {
            Call<RecipeList> call = serviceApi.getRecipes();
            call.enqueue(new Callback<RecipeList>() {
                @Override
                public void onResponse(Call<RecipeList> call, Response<RecipeList> response) {
                    if (response.isSuccessful() && response.body()!=null)
                    {
                        List<Recipe> recipes = response.body().getRecipes();
                        for(Recipe recipe: recipes){
                            Log.i(TAG,recipe.getName()+"\t"+recipe.getCuisine());
                        }
                    }
                    else{
                        Log.e(TAG,"Error");
                    }
                }

                @Override
                public void onFailure(Call<RecipeList> call, Throwable throwable) {
                    Log.e(TAG,throwable.getMessage());
                }
            });
        }
        catch (Exception e){
            Log.e(TAG,e.getMessage());
        }


    }
}