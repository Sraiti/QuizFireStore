package com.example.firestorequiz;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.firestorequiz.Constant.FinalValues;
import com.example.firestorequiz.DB.CategoryDbHelper;
import com.example.firestorequiz.Model.Category;
import com.example.firestorequiz.Model.Question;
import com.example.firestorequiz.Model.Stage;
import com.example.firestorequiz.MusicBackground.MediaPlayerPresenter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.shreyaspatil.MaterialDialog.MaterialDialog;
import com.shreyaspatil.MaterialDialog.interfaces.DialogInterface;
import com.shreyaspatil.MaterialDialog.interfaces.OnDismissListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import antonkozyriatskyi.circularprogressindicator.CircularProgressIndicator;


public class Playing extends AppCompatActivity implements View.OnClickListener {

    public ArrayList<Question> mQuestions = new ArrayList<>();


    CircularProgressIndicator circularProgress;

    TextView QuestionText, TxtScore;
    Button AnswerA, AnswerB, AnswerC, AnswerD;
    ImageView Heart01, Heart02, Heart03;

    CountDownTimer countDownTimer;
    int ProgressValue = 0;
    int index = 0, score = 0, totalQues, Souls = 3;

    long TIMEOUT = FinalValues.TIMEOUT;
    long INTERVAL = FinalValues.INTERVAL;
    int WinningPrize = FinalValues.WinningPrize;

    int CategoryID, Stage;
    String CategoryName, ImageURL;

    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    CategoryDbHelper categoryDbHelper;

    MediaPlayer Correct, Wrong;

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
        super.onBackPressed();
        //Just In case
        countDownTimer.cancel();
        Intent A = new Intent(Playing.this, Home.class);
        startActivity(A);
        finish();

    }

    @Override
    protected void onUserLeaveHint() {
        super.onUserLeaveHint();
        player.pauseMusic();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playing);
        player = MediaPlayerPresenter.getInstance(Playing.this);

        QuestionText = findViewById(R.id.txt_Ques);
        AnswerA = findViewById(R.id.btn_Answer1);
        AnswerB = findViewById(R.id.btn_Answer02);
        AnswerC = findViewById(R.id.btn_Answer03);
        AnswerD = findViewById(R.id.btn_Answer04);
        Heart01 = findViewById(R.id.img_try3);
        Heart02 = findViewById(R.id.img_try1);
        Heart03 = findViewById(R.id.img_try2);
        circularProgress = findViewById(R.id.circular_progress);
        TxtScore = findViewById(R.id.txt_Scored);


        Correct = MediaPlayer.create(Playing.this, R.raw.correct);
        Wrong = MediaPlayer.create(Playing.this, R.raw.wrong);

        circularProgress.setAnimationEnabled(true);
        circularProgress.setMaxProgress(15);

        Intent a = getIntent();
        prefs = getSharedPreferences("MyPref", MODE_PRIVATE);
        editor = prefs.edit();
        editor.apply();
        ImageURL = prefs.getString(String.valueOf(R.string.ImagePath_key), "");
        CategoryName = prefs.getString(String.valueOf(R.string.CategoryName_key), "");
        CategoryID = prefs.getInt(String.valueOf(R.string.CategoryId_key), 8);
        Stage = a.getIntExtra("Level", 8);


        categoryDbHelper = new CategoryDbHelper(this);

        ImageView Img = findViewById(R.id.imageView);
        Picasso.get()
                .load(ImageURL)
                .into(Img);

        GetQuestions(new FireStoreCallback() {
            @Override
            public void OnCallBack(ArrayList<Question> List) {
                totalQues = mQuestions.size();
                NextQuestion(index);
            }
        });

        AnswerA.setOnClickListener(this);
        AnswerB.setOnClickListener(this);
        AnswerC.setOnClickListener(this);
        AnswerD.setOnClickListener(this);

    }


    public void NextQuestion(int index) {


        if (index < totalQues) {
            AnswerA.setClickable(true);
            AnswerB.setClickable(true);
            AnswerC.setClickable(true);
            AnswerD.setClickable(true);

            circularProgress.setCurrentProgress(0);
            ProgressValue = 0;


            Question question = mQuestions.get(index);

            String text = question.getQuestion();
            String Answer1 = question.getAnswer0();
            String Answer2 = question.getAnswer01();
            String Answer3 = question.getAnswer02();
            String Answer4 = question.getAnswer03();

            QuestionText.setText(text);
            AnswerA.setText(Answer1);
            AnswerB.setText(Answer2);
            AnswerC.setText(Answer3);
            AnswerD.setText(Answer4);

            try {
                countDownTimer.start();

            } catch (Exception Ex) {
                countDownTimer = null;
            }

        } else {
            Intent done = new Intent(Playing.this, Done.class);
            done.putExtra("Score", score);
            done.putExtra("Stage", Stage);
            done.putExtra("CategoryID", CategoryID);
            countDownTimer.cancel();
            startActivity(done);
            finish();
            return;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        countDownTimer = null;
    }


    public void points(boolean statue) {


        if (statue) {
            //get points from db
            int points = categoryDbHelper.getPoints(CategoryID);
            //Add new points
            score += 100;
            //update db points
            Category cat = new Category(CategoryID, CategoryName, ImageURL);
            categoryDbHelper.AddPoints(cat, WinningPrize + points);
            //wont change
            //Toast.makeText(this, FinalValues.CorrectToastMessage, Toast.LENGTH_SHORT).show();
            //Discarded

            int StageRequir = GetStageRequ(1 + Stage);

            if (score == StageRequir) {
                countDownTimer=null;
                MaterialDialog mDialog = new MaterialDialog.Builder(this)
                        .setTitle("Next Stage Is Unlocked ")
                        .setMessage("Play It NOW !!")
                        .setCancelable(false)
                        .setPositiveButton("Play", R.drawable.ic_stars_black_24dp, new MaterialDialog.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                categoryDbHelper.AddStage(new Stage(Stage, CategoryID, score, 1));
                                Intent intent2 = new Intent(Playing.this, Playing.class);
                                intent2.putExtra("Level", 1 + Stage);
                                startActivity(intent2);
                                finish();
                            }
                        })
                        .setNegativeButton("Keep Playing", R.drawable.ic_arrow_back_black_24dp, new MaterialDialog.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {

                                dialogInterface.dismiss();
                            }
                        })
                        .setAnimation(R.raw.unlocking)
                        .setCancelable(true)
                        .build();

                mDialog.setOnDismissListener(new OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        onResume();
                    }
                });
                // Show Dialog
                mDialog.show();
            }

        } else {
            Souls = Souls - 1;

            if (Souls <= 0) {
                Heart03.setVisibility(View.INVISIBLE);
                countDownTimer.cancel();
                Intent done = new Intent(Playing.this, Done.class);
                done.putExtra("Score", score);
                done.putExtra("CategoryID", CategoryID);
                done.putExtra("Stage", Stage);
                startActivity(done);
                finish();
                return;
            }

            if (Souls == 2) {
                Heart01.setVisibility(View.INVISIBLE);
            } else if (Souls == 1) {
                Heart02.setVisibility(View.INVISIBLE);
            }

        }


    }

    public void GetQuestions(final FireStoreCallback callback) {


        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("Questions")
                .whereEqualTo("categoryID", CategoryID)
                .whereEqualTo("stage", Stage)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("TAG", document.getId() + " => " + document.getData());
                                Question Qu = document.toObject(Question.class);
                                mQuestions.add(Qu);
                            }
                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                        Collections.shuffle(mQuestions);
                        callback.OnCallBack(mQuestions);
                        totalQues = mQuestions.size();

                    }
                });
    }


    @Override
    public void onClick(View v) {

        final Button ClickedButton = (Button) v;

        AnswerA.setClickable(false);
        AnswerB.setClickable(false);
        AnswerC.setClickable(false);
        AnswerD.setClickable(false);


        ClickedButton.setBackground(getResources().getDrawable(R.drawable.mybuttonpanding));

        Animation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(250); //You can manage the blinking time with this parameter
        anim.setStartOffset(20);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(4);
        ClickedButton.startAnimation(anim);

        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {


                if (ClickedButton.getText().equals(mQuestions.get(index).getCorrectAnswer())) {
                    Correct.start();


                    countDownTimer.cancel();
                    ClickedButton.setBackground(getResources().getDrawable(R.drawable.mybuttoncorret));

                    points(true);


                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            ClickedButton.setBackground(getResources().getDrawable(R.drawable.button_background));
                            ClickedButton.clearAnimation();
                            NextQuestion(++index);
                        }
                    }, 3000);


                } else {
                    Wrong.start();

                    countDownTimer.cancel();
                    points(false);


                    final List<Button> Butttons = new ArrayList<>();
                    Butttons.add(AnswerA);
                    Butttons.add(AnswerB);
                    Butttons.add(AnswerC);
                    Butttons.add(AnswerD);

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            for (int i = 0; i < Butttons.size(); i++) {

                                if (Butttons.get(i).getText().equals(mQuestions.get(index).getCorrectAnswer())) {
                                    Butttons.get(i).setBackground(getResources().getDrawable(R.drawable.mybuttoncorret));
                                    break;
                                }

                            }
                        }
                    }, 1000);

                    ClickedButton.setBackground(getResources().getDrawable(R.drawable.mybuttonwrong));


                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            for (int i = 0; i < Butttons.size(); i++) {
                                Butttons.get(i).setBackground(getResources().getDrawable(R.drawable.mybutton));
                            }

                            NextQuestion(++index);
                        }
                    }, 3000);


                }
                TxtScore.setText(String.valueOf(score));

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


    }


    @Override
    protected void onResume() {
        super.onResume();

        countDownTimer = new CountDownTimer(TIMEOUT, INTERVAL) {

            @Override
            public void onTick(long millisUntilFinished) {
                circularProgress.setCurrentProgress(ProgressValue);
                ProgressValue++;
            }

            @Override
            public void onFinish() {
                if (++index > totalQues) {
                    // Toast.makeText(Playing.this, "Finished", Toast.LENGTH_SHORT).show();
                    countDownTimer.cancel();
                    points(false);

                } else {
                    //Toast.makeText(Playing.this, FinalValues.TIMEOUT_MESSAGE, Toast.LENGTH_SHORT).show();
                }
                if (countDownTimer != null) {
                    countDownTimer.cancel();
                    NextQuestion(++index);

                }
            }
        };
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

    private interface FireStoreCallback {
        void OnCallBack(ArrayList<Question> List);
    }
}



