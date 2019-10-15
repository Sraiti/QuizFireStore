package com.example.firestorequiz;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.firestorequiz.Constant.FinalValues;
import com.example.firestorequiz.MusicBackground.MediaPlayerPresenter;

public class Home extends AppCompatActivity {


    ImageView Play, More, Share, ScoreBoard;
    ImageView Settings;

    MediaPlayerPresenter player;

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        if (hasFocus) {
            player.playMusic();

        }

    }
    @Override
    public void onBackPressed() {
        finishAffinity();
        System.exit(0);
    }


    @Override
    protected void onUserLeaveHint() {
        super.onUserLeaveHint();
        player.pauseMusic();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Play = findViewById(R.id.img_Play);
        More = findViewById(R.id.img_more);
        ScoreBoard = findViewById(R.id.img_score);
        Settings = findViewById(R.id.img_settings);
        Settings.setImageResource(R.drawable.ic_volume_up_black_24dp);

        player = MediaPlayerPresenter.getInstance(getApplicationContext());
        player.playMusic();

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
                if (player.isPlaying()) {
                    Settings.setImageResource(R.drawable.ic_volume_off_black_24dp);

                    player.pauseMusic();

                } else {
                    Settings.setImageResource(R.drawable.ic_volume_up_black_24dp);

                    player.playMusic();

                }
            }
        });

    }

}
