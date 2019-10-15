package com.example.firestorequiz;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.firestorequiz.Constant.FinalValues;
import com.example.firestorequiz.MusicBackground.MediaPlayerPresenter;

public class Done extends AppCompatActivity {


    TextView txt_DoneScore;
    ImageView Share, Home;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent A = new Intent(Done.this, Home.class);
        startActivity(A);
        finish();
    }


    @Override
    protected void onUserLeaveHint() {
        super.onUserLeaveHint();
        player.pauseMusic();
    }

    MediaPlayerPresenter player;

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        if (hasFocus) {
            player.playMusic();

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_done);

        player = MediaPlayerPresenter.getInstance(getApplicationContext());

        txt_DoneScore = findViewById(R.id.txt_doneScore);
        Share = findViewById(R.id.img_share);
        Home = findViewById(R.id.img_home);

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


        Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent A = new Intent(Done.this, Home.class);
                startActivity(A);
                finish();
            }
        });
        Intent a = getIntent();

        int point = a.getIntExtra("Score", 0);
        txt_DoneScore.setText(" " + point);


    }
}
