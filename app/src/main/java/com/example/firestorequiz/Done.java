package com.example.firestorequiz;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.firestorequiz.Constant.FinalValues;
import com.example.firestorequiz.DB.CategoryDbHelper;
import com.example.firestorequiz.MusicBackground.MediaPlayerPresenter;
import com.shreyaspatil.MaterialDialog.MaterialDialog;
import com.shreyaspatil.MaterialDialog.interfaces.DialogInterface;

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

        player = MediaPlayerPresenter.getInstance(Done.this);

        txt_DoneScore = findViewById(R.id.txt_doneScore);
        Share = findViewById(R.id.img_share);
        Home = findViewById(R.id.img_home);

        CategoryDbHelper categoryDbHelper = new CategoryDbHelper(this);

        Intent a = getIntent();

        int point = a.getIntExtra("Score", 0);
        final int Stage = a.getIntExtra("Stage", 8);
        int CategoryID = a.getIntExtra("CategoryID", 8);

        int UserTotalpoints = categoryDbHelper.getPoints(CategoryID);

        int StageRequir = GetStageRequ(1 + Stage);

        if (UserTotalpoints >= StageRequir) {
            MaterialDialog mDialog = new MaterialDialog.Builder(this)
                    .setTitle("Next Stage Is Unlocked ")
                    .setMessage("Play It NOW !!")
                    .setCancelable(false)
                    .setPositiveButton("Play", R.drawable.ic_stars_black_24dp, new MaterialDialog.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int which) {
                            Intent intent2 = new Intent(Done.this, Playing.class);
                            intent2.putExtra("Level", 1 + Stage);
                            startActivity(intent2);
                            finish();
                        }
                    })
                    .setNegativeButton("Go home", R.drawable.ic_home_blacktiny_24dp, new MaterialDialog.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int which) {
                            Intent a = new Intent(Done.this, MainActivity.class);
                            startActivity(a);
                            finish();
                            dialogInterface.dismiss();
                        }
                    })
                    .setAnimation(R.raw.unlocking)
                    .setCancelable(true)
                    .build();

            // Show Dialog
            mDialog.show();
        }



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


        txt_DoneScore.setText(" " + point);
    }

    public int GetStageRequ(int StageID) {
        switch (StageID) {
            case 1:
                return FinalValues.Stage2;
            case 2:
                return FinalValues.Stage3;
            case 3:
                return FinalValues.Stage4;
            case 4:
                return FinalValues.Stage5;
            case 5:
                return FinalValues.Stage6;
            case 6:
                return FinalValues.Stage7;
            case 7:
                return FinalValues.Stage8;
            case 8:
                return FinalValues.Stage9;
            case 9:
                return FinalValues.Stage10;
            default:
                break;
        }
        return 0;
    }
}
