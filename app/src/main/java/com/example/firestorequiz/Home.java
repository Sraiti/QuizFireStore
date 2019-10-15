package com.example.firestorequiz;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

import com.example.firestorequiz.Constant.FinalValues;
import com.example.firestorequiz.Services.BackgroundMusic;

public class Home extends AppCompatActivity {


    ImageView Play, More, Share, ScoreBoard;
    Switch Settings;


    @Override
    public void onBackPressed() {
        finishAffinity();
        System.exit(0);
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Intent myService = new Intent(Home.this, BackgroundMusic.class);
        stopService(myService);

    }

    private boolean mIsBound = false;
    private BackgroundMusic mServ;



    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Play = findViewById(R.id.img_Play);
        More = findViewById(R.id.img_more);
        ScoreBoard = findViewById(R.id.img_score);
        Settings = findViewById(R.id.img_settings);

        Intent music = new Intent(this, BackgroundMusic.class);
        startService(music);


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

        Settings.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    Intent myService = new Intent(Home.this, BackgroundMusic.class);
                    startService(myService);
                } else {
                    Intent myService = new Intent(Home.this, BackgroundMusic.class);
                    stopService(myService);

                }
            }
        });

    }
}
