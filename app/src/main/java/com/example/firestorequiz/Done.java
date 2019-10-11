package com.example.firestorequiz;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Done extends AppCompatActivity {


    TextView txt_DoneScore;
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_done);


        txt_DoneScore = findViewById(R.id.txt_doneScore);
        Intent a = getIntent();


        prefs = getSharedPreferences("MyPref", MODE_PRIVATE);
        int point = a.getIntExtra("Score",0);
        int pointsAll = prefs.getInt("Points", 0);
        txt_DoneScore.setText(point);


    }
}
