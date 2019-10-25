package com.example.firestorequiz;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.AsyncTask;
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

import com.airbnb.lottie.LottieAnimationView;
import com.example.firestorequiz.Ads.ConsentSDK;
import com.example.firestorequiz.Constant.FinalValues;
import com.example.firestorequiz.DB.CategoryDbHelper;
import com.example.firestorequiz.Model.Question;
import com.example.firestorequiz.Model.Stage;
import com.example.firestorequiz.MusicBackground.MediaPlayerPresenter;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.shreyaspatil.MaterialDialog.MaterialDialog;
import com.shreyaspatil.MaterialDialog.interfaces.DialogInterface;
import com.shreyaspatil.MaterialDialog.interfaces.OnShowListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import antonkozyriatskyi.circularprogressindicator.CircularProgressIndicator;


public class Playing extends AppCompatActivity implements View.OnClickListener {


    public ArrayList<Question> mQuestions = new ArrayList<>();
    List<Button> Buttons = new ArrayList<>();


    CircularProgressIndicator circularProgress;
    CountDownTimer countDownTimer;

    TextView QuestionText, TxtScore;
    Button AnswerA, AnswerB, AnswerC, AnswerD;
    ImageView Heart01, Heart02, Heart03;

    int ProgressValue = 0;
    int index = 0, score = 0, totalQues, Souls = 3;
    boolean isRunning;

    long TIMEOUT = FinalValues.TIMEOUT;
    long INTERVAL = FinalValues.INTERVAL;
    int WinningPrize = FinalValues.WinningPrize;

    int CategoryID, Stage, StageRequir;
    String CategoryName, ImageURL;

    SharedPreferences prefs;
    SharedPreferences.Editor editor;

    MediaPlayer Correct, Wrong;
    MaterialDialog mDialog;
    LottieAnimationView animationView;
    MediaPlayerPresenter player;

    private AdView adView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playing);


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

        player = MediaPlayerPresenter.getInstance(Playing.this);

        Correct = MediaPlayer.create(Playing.this, R.raw.correct);
        Wrong = MediaPlayer.create(Playing.this, R.raw.wrong);
        circularProgress.setMaxProgress(15);


        prefs = getSharedPreferences("MyPref", MODE_PRIVATE);
        editor = prefs.edit();
        editor.apply();

        ImageURL = prefs.getString(String.valueOf(R.string.ImagePath_key), "");
        CategoryName = prefs.getString(String.valueOf(R.string.CategoryName_key), "");
        CategoryID = prefs.getInt(String.valueOf(R.string.CategoryId_key), 8);
        Intent a = getIntent();
        Stage = a.getIntExtra("Level", 8);

        StageRequir = GetStageRequ(Stage);

        Buttons.add(AnswerA);
        Buttons.add(AnswerB);
        Buttons.add(AnswerC);
        Buttons.add(AnswerD);

        mDialog = new MaterialDialog.Builder(this)
                .setTitle("Next Stage Is Unlocked ")
                .setMessage("Play It NOW !!")
                .setCancelable(false)
                .setPositiveButton("Play", R.drawable.ic_stars_black_24dp, new MaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {

                        new AccessingDB(Playing.this).execute();
                        Intent intent2 = new Intent(Playing.this, Quiz.class);
                        intent2.putExtra("CategoryId", CategoryID);
                        intent2.putExtra("CategoryName", CategoryName);
                        intent2.putExtra("ImageURL", ImageURL);
                        startActivity(intent2);
                        finish();
                    }
                })
                .setNegativeButton("Keep Playing!", R.drawable.ic_arrow_back_black_24dp, new MaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        dialogInterface.dismiss();
                        circularProgress.setCurrentProgress(0);
                        ProgressValue = 0;
                        countDownTimer.start();
                        isRunning = true;
                    }
                })
                .setAnimation(R.raw.unlocking)
                .build();


        animationView = mDialog.getAnimationView();


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


        adView = findViewById(R.id.adView);
        adView.loadAd(ConsentSDK.getAdRequest(this));


    }


    public void NextQuestion(int index) {

        buttonsStatue(true);

        if (index < totalQues) {


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
                if (!isRunning) {
                    countDownTimer.start();
                }

            } catch (Exception Ex) {
                countDownTimer = null;
            }

        } else {

            if (score > StageRequir) {
                new AccessingDB(Playing.this).execute();

            }

            Intent done = new Intent(Playing.this, Done.class);
            done.putExtra("Score", score);
            done.putExtra("Stage", Stage);
            done.putExtra("CategoryID", CategoryID);
            countDownTimer.cancel();
            startActivity(done);
            finish();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        countDownTimer = null;
    }


    public void points(boolean statue) {


        if (statue) {
            score += 100;
            if (score == StageRequir) {
                mDialog.show();
            }
        } else {
            Wrong.start();
            Souls = Souls - 1;
            if (Souls <= 0) {
                Heart03.setVisibility(View.INVISIBLE);
                if (countDownTimer != null)
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

    public void buttonsStatue(boolean st) {
        if (!st) {
            for (Button item : Buttons) {
                item.setClickable(false);
            }

        } else {
            for (Button item : Buttons) {
                item.setClickable(true);
            }
        }
    }

    @Override
    public void onClick(View v) {

        countDownTimer.cancel();
        isRunning = false;

        final Button ClickedButton = (Button) v;
        buttonsStatue(false);

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
                buttonsStatue(false);
            }

            @Override
            public void onAnimationEnd(Animation animation) {


                if (ClickedButton.getText().equals(mQuestions.get(index).getCorrectAnswer())) {
                    Correct.start();

                    ClickedButton.setBackground(getResources().getDrawable(R.drawable.mybuttoncorret));

                    points(true);

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            ClickedButton.setBackground(getResources().getDrawable(R.drawable.button_background));
                            if (!animationView.isAnimating())
                                NextQuestion(++index);
                        }
                    }, 3000);


                } else {

                    points(false);


                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            for (int i = 0; i < Buttons.size(); i++) {

                                if (Buttons.get(i).getText().equals(mQuestions.get(index).getCorrectAnswer())) {
                                    Buttons.get(i).setBackground(getResources().getDrawable(R.drawable.mybuttoncorret));
                                    break;
                                }

                            }
                        }
                    }, 1000);

                    ClickedButton.setBackground(getResources().getDrawable(R.drawable.mybuttonwrong));


                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            for (int i = 0; i < Buttons.size(); i++) {
                                Buttons.get(i).setBackground(getResources().getDrawable(R.drawable.mybutton));
                            }
                            if (!animationView.isAnimating())
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

    @Override
    protected void onResume() {
        super.onResume();


        mDialog.setOnShowListener(new OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                countDownTimer.cancel();
                isRunning = false;
                ProgressValue = 0;
            }
        });


        countDownTimer = new CountDownTimer(TIMEOUT, INTERVAL) {

            @Override
            public void onTick(long millisUntilFinished) {
                circularProgress.setCurrentProgress(ProgressValue);
                ProgressValue++;
                isRunning = true;

            }

            @Override
            public void onFinish() {
                if (++index > totalQues) {
                    // Toast.makeText(Playing.this, "Finished", Toast.LENGTH_SHORT).show();
                    points(false);
                    countDownTimer.cancel();
                    isRunning = false;


                } else {
                    //Toast.makeText(Playing.this, FinalValues.TIMEOUT_MESSAGE, Toast.LENGTH_SHORT).show();
                    points(false);
                }
                if (countDownTimer != null) {
                    countDownTimer.cancel();
                    isRunning = false;
                    NextQuestion(++index);

                }
            }
        };
    }

    private interface FireStoreCallback {
        void OnCallBack(ArrayList<Question> List);
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && prefs.getBoolean("music", true)) {
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
        if (prefs.getBoolean("music", true)) {
            player.pauseMusic();
        }
    }

    public class AccessingDB extends AsyncTask<Void, Void, Void> {
        private final Context mContext;

        public AccessingDB(Context context) {
            super();
            this.mContext = context;
        }

        protected Void doInBackground(Void... params) {
            // using this.mContext
            CategoryDbHelper categoryDbHelper = new CategoryDbHelper(mContext);
            categoryDbHelper.AddStage(new Stage(1 + Stage, CategoryID, score, 1));
            categoryDbHelper.UpdateCategoryPoints(CategoryID, score);
//            categoryDbHelper.UpdateStageStatue(new Stage(Stage, CategoryID, score, 1));
            return null;
        }
    }


}



