package com.example.firestorequiz;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firestorequiz.Adapters.StageAdapter;
import com.example.firestorequiz.Model.Stage;
import com.example.firestorequiz.MusicBackground.MediaPlayerPresenter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static java.sql.Types.NULL;

public class Quiz extends AppCompatActivity {


    ImageView HeaderImage;
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;
    ProgressBar progressBar;
    int CategoryID;
    String CategoryName;
    private ArrayList<Stage> StageDataList = new ArrayList<>();
    MediaPlayerPresenter player;


    @Override
    protected void onUserLeaveHint() {
        super.onUserLeaveHint();
        player.pauseMusic();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        //  GetQuestions();

        player = MediaPlayerPresenter.getInstance(Quiz.this);

        HeaderImage = findViewById(R.id.CategoryImage);
        Intent a = getIntent();
        CategoryID = a.getIntExtra("CategoryId", NULL);
        CategoryName = a.getStringExtra("CategoryName");
        String ImageURL = a.getStringExtra("ImageURL");

        progressBar = findViewById(R.id.progress);
        progressBar.getProgressDrawable().setColorFilter(
                Color.RED, PorterDuff.Mode.MULTIPLY);
        sharedPref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);

        editor = sharedPref.edit();


        editor.putInt(String.valueOf(R.string.CategoryId_key), CategoryID);
        editor.putString(String.valueOf(R.string.CategoryName_key), CategoryName);
        editor.putString(String.valueOf(R.string.ImagePath_key), ImageURL);
        editor.apply();


        Picasso.get()
                .load(ImageURL)
                .into(HeaderImage);


        RecyclerView recyclerView = findViewById(R.id.recycler);

        // recyclerView.setHasFixedSize(true);

        // Specify a linear layout manager.
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        StageData();


        // Specify an adapter.
        RecyclerView.Adapter<RecyclerView.ViewHolder> adapter = new StageAdapter(StageDataList, this, CategoryID, progressBar);
        recyclerView.setAdapter(adapter);

    }

    private void StageData() {
        StageDataList.addAll(Stage.CreateStages(Quiz.this, CategoryID));
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        if (hasFocus && sharedPref.getBoolean("music", true)) {
            player.playMusic();

        }

    }
}
