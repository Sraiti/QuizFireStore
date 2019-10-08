package com.example.firestorequiz.ViewHolders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firestorequiz.R;


public class CategoryViewHolder extends RecyclerView.ViewHolder {

    private View mView;
    private int CategoryId;
    public TextView CategoryName;
    public ImageView CategoryImage;

    public CategoryViewHolder(@NonNull View itemView) {
        super(itemView);
        CategoryName = itemView.findViewById(R.id.CategoryName);
        CategoryImage = itemView.findViewById(R.id.CategoryImage);
        mView =itemView;
    }

    public View getmView() {
        return mView;
    }
    public int getCategoryId() {
        return CategoryId;
    }
}
