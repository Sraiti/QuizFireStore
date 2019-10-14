package com.example.firestorequiz;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firestorequiz.Adapters.ScoreAdapter;
import com.example.firestorequiz.DB.CategoryDbHelper;
import com.example.firestorequiz.Model.Category;

import java.util.ArrayList;

public class ScoreBoard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_board);


        CategoryDbHelper dbHelper = new CategoryDbHelper(this);
        RecyclerView recyclerView = findViewById(R.id.recycler_score);

        ArrayList<Category> mCategories = new ArrayList<>(dbHelper.GetAllCategories());

        if (!mCategories.isEmpty()) {
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);

            RecyclerView.Adapter<RecyclerView.ViewHolder> adapter = new ScoreAdapter(mCategories, this);
            recyclerView.setAdapter(adapter);
        }

    }
}