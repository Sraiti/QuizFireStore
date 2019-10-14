package com.example.firestorequiz;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.firestorequiz.Constant.FinalValues;

public class Home extends AppCompatActivity {


    ImageView Play, More, Share, ScoreBoard, Settings;

    public static MediaPlayer MainTheme;
    @Override
    public void onBackPressed() {
        finishAffinity();
        System.exit(0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Play = findViewById(R.id.img_Play);
        More = findViewById(R.id.img_more);
        ScoreBoard = findViewById(R.id.img_score);
        Settings = findViewById(R.id.img_settings);
        MainTheme = MediaPlayer.create(getApplicationContext(), R.raw.main_music);
        MainTheme.start();


        ScoreBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent a = new Intent(Home.this, ScoreBoard.class);
                startActivity(a);
            }
        });


        More.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(FinalValues.StoreUrl));
                startActivity(Intent.createChooser(intent, "More Of Our Apps"));
            }
        });


        Share = findViewById(R.id.img_share);

        Share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_SUBJECT, FinalValues.ShareText);
                i.putExtra(Intent.EXTRA_TEXT, FinalValues.AppUrl);
                startActivity(i);
            }
        });

        Play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(Home.this, MainActivity.class);
                startActivity(a);
            }
        });

        Settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }

        });

    }
}
