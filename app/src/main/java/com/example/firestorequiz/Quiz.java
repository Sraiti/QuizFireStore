package com.example.firestorequiz;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.firestorequiz.Adapters.StageAdapter;
import com.example.firestorequiz.Model.Stage;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static java.sql.Types.NULL;

public class Quiz extends AppCompatActivity {



    ImageView HeaderImage;
     private ArrayList<Stage> StageDataList = new ArrayList<>();
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
      //  GetQuestions();

        HeaderImage =findViewById(R.id.CategoryImage);
        Intent a = getIntent();
        int CategoryID =a.getIntExtra("CategoryId",NULL);
        String ImageURL = a.getStringExtra("ImageURL");

        progressBar =findViewById(R.id.progress);
        progressBar.getProgressDrawable().setColorFilter(
                Color.RED, PorterDuff.Mode.MULTIPLY);
         sharedPref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);

         editor= sharedPref.edit();


        editor.putInt(String.valueOf(R.string.CategoryId_key),CategoryID);
        editor.putString(String.valueOf(R.string.ImagePath_key),ImageURL);
        editor.apply();




        Picasso.get()
                .load(ImageURL)
                .into(HeaderImage);


        RecyclerView   recyclerView = findViewById(R.id.recycler);

       // recyclerView.setHasFixedSize(true);

        // Specify a linear layout manager.
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        StageData();


        // Specify an adapter.
        RecyclerView.Adapter<RecyclerView.ViewHolder> adapter = new StageAdapter(StageDataList,this,CategoryID,progressBar);
        recyclerView.setAdapter(adapter);

    }

    private void StageData() {
        StageDataList.addAll(Stage.CreateStages(Quiz.this));
    }


}
