package com.example.firestorequiz;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.firestorequiz.Constant.FinalValues;
import com.example.firestorequiz.DB.CategoryDbHelper;
import com.example.firestorequiz.Model.Stage;
import com.example.firestorequiz.MusicBackground.MediaPlayerPresenter;
import com.shreyaspatil.MaterialDialog.MaterialDialog;
import com.shreyaspatil.MaterialDialog.interfaces.DialogInterface;

public class Done extends AppCompatActivity {


    TextView txt_DoneScore;
    ImageView Share, Home;
    SharedPreferences preferences;

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

        if (hasFocus && preferences.getBoolean("music", true)) {
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

        preferences = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        CategoryDbHelper categoryDbHelper = new CategoryDbHelper(this);

        Intent a = getIntent();

        int point = a.getIntExtra("Score", 0);
        final int Stage = a.getIntExtra("Stage", 8);
        int CategoryID = a.getIntExtra("CategoryID", 8);


        int StageRequir = GetStageRequ(Stage);

        if (point >= StageRequir) {
            categoryDbHelper.AddStage(new Stage(1 + Stage, CategoryID, 0, 1));
            categoryDbHelper.UpdateStageStatue(new Stage(Stage, CategoryID, point, 1));
        } else {
            MaterialDialog mDialog = new MaterialDialog.Builder(this)
                    .setTitle("Replay The Stage")
                    .setMessage("Play Again?")
                    .setCancelable(false)
                    .setPositiveButton("Play", R.drawable.ic_stars_black_24dp, new MaterialDialog.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int which) {
                            Intent intent2 = new Intent(Done.this, Playing.class);
                            intent2.putExtra("Level", Stage);
                            startActivity(intent2);
                            finish();

                        }
                    })
                    .setNegativeButton("Not now", R.drawable.ic_arrow_back_black_24dp, new MaterialDialog.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int which) {

                            dialogInterface.dismiss();

                        }
                    })
                    .setAnimation(R.raw.lock)
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
