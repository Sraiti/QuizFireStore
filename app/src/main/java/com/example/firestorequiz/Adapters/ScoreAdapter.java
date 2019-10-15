package com.example.firestorequiz.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firestorequiz.Model.Category;
import com.example.firestorequiz.R;
import com.example.firestorequiz.ViewHolders.ScoreViewHolder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ScoreAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    Context mContext;
    ArrayList<Category> CategoriesList;

    public ScoreAdapter(ArrayList<Category> mCategories, Context context) {
        this.mContext = context;
        this.CategoriesList = mCategories;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.score_container, viewGroup, false);
        return new ScoreViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        ScoreViewHolder scoreViewHolder = (ScoreViewHolder) holder;


        String imagePath = CategoriesList.get(position).getCategoryImage();
        Picasso.get()
                .load(imagePath)
                .into(scoreViewHolder.CategoryImage);


        scoreViewHolder.CategoryName.setText(CategoriesList.get(position).getCategoryName());
        scoreViewHolder.Score.setText(String.valueOf(CategoriesList.get(position).getCategoryPoints() + 100));


    }


    @Override
    public int getItemCount() {
        return CategoriesList.size();
    }

}
