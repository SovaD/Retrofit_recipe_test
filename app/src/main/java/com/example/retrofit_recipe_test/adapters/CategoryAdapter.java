package com.example.retrofit_recipe_test.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.retrofit_recipe_test.R;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    List<String> categoryList;
    Context context;
    private OnClickListener onClickListener;
    public int selectedPosition = -1;

    public CategoryAdapter(Context context, List<String> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_category_item, parent, false);
        CategoryAdapter.ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String name = categoryList.get(position);

        if (position == selectedPosition)
            holder.tCategory.setTextColor(context.getResources().getColor(R.color.yellow));
        else
            holder.tCategory.setTextColor(context.getResources().getColor(R.color.gray));

        holder.tCategory.setText(name);

        holder.itemView.setOnClickListener(view -> {
            if (onClickListener != null) {
                onClickListener.onClick(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tCategory;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tCategory = itemView.findViewById(R.id.tCategory);

            itemView.setOnClickListener(view -> {
                if (onClickListener != null) {
                    onClickListener.onClick(getAdapterPosition());
                }
            });
        }
    }
}
