package com.example.firestorequiz.ViewHolders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firestorequiz.R;

public class ScoreViewHolder extends RecyclerView.ViewHolder {


    public TextView CategoryName, Score;
    public ImageView CategoryImage;

    public ScoreViewHolder(@NonNull View itemView) {
        super(itemView);
        CategoryName = itemView.findViewById(R.id.CategoryName_score);
        CategoryImage = itemView.findViewById(R.id.img_CategoryScore);
        Score = itemView.findViewById(R.id.txt_Score);
    }


}
