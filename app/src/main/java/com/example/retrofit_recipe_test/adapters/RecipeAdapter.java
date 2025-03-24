package com.example.retrofit_recipe_test.adapters;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.retrofit_recipe_test.R;
import com.example.retrofit_recipe_test.entities.Recipe;

import java.util.ArrayList;
import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder> {
    List<Recipe> recipeList;
    Context context;

    private OnClickListener onClickListener;
    public int selectedPosition = -1;

    public RecipeAdapter(Context context, List<Recipe> recipeList) {
        this.context = context;
        this.recipeList = recipeList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_recipe_item, parent, false);
        RecipeAdapter.ViewHolder viewHolder = new RecipeAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Recipe recipe = recipeList.get(position);
        Log.i("Taggg", recipe.getName());
        holder.ratingBar.setRating(recipe.getRating());
        holder.tName.setText(recipe.getName());
//        holder.iv.setImageURI(Uri.parse(recipe.getImage()));
        Glide.with(context)
                        .load(Uri.parse(recipe.getImage()))
                                .into(holder.iv);
        holder.itemView.setOnClickListener(view -> {
            if (onClickListener != null) {
                onClickListener.onClick(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv;
        TextView tName;
        RatingBar ratingBar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tName = itemView.findViewById(R.id.tName);
            iv = itemView.findViewById(R.id.iv);
            ratingBar = itemView.findViewById(R.id.ratingBar);

            itemView.setOnClickListener(view -> {
                if (onClickListener != null) {
                    onClickListener.onClick(getAdapterPosition());
                }
            });
        }
    }
}
