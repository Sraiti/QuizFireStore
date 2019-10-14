package com.example.firestorequiz.ViewHolders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firestorequiz.R;


public class CategoryViewHolder extends RecyclerView.ViewHolder {

    public TextView CategoryName;
    public ImageView CategoryImage;
    private View mView;
    private int CategoryId;

    public CategoryViewHolder(@NonNull View itemView) {
        super(itemView);
        CategoryName = itemView.findViewById(R.id.CategoryName_score);
        CategoryImage = itemView.findViewById(R.id.CategoryImage);
        mView = itemView;
    }

    public View getmView() {
        return mView;
    }

    public int getCategoryId() {
        return CategoryId;
    }
}
