package com.example.firestorequiz;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.firestorequiz.Constant.FinalValues;
import com.example.firestorequiz.DB.CategoryDbHelper;
import com.example.firestorequiz.Model.Category;
import com.example.firestorequiz.Model.Question;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
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

            countDownTimer.start();

        } else {
            Intent done = new Intent(Playing.this, Done.class);
            done.putExtra("Score", score);
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
            Toast.makeText(this, FinalValues.CorrectToastMessage, Toast.LENGTH_SHORT).show();
            //Discarded


        } else {
            Toast.makeText(this, "Wrong", Toast.LENGTH_SHORT).show();
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
        if (ClickedButton.getText().equals(mQuestions.get(index).getCorrectAnswer())) {
            Correct.start();


            countDownTimer.cancel();
            ClickedButton.setBackground(getResources().getDrawable(R.drawable.mybuttoncorret));

            points(true);


            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    ClickedButton.setBackground(getResources().getDrawable(R.drawable.mybutton));
                    NextQuestion(++index);
                }
            }, 1000);


        } else {
            Wrong.start();

            Souls = Souls - 1;
            countDownTimer.cancel();
            points(false);

            if (Souls <= 0) {
                Heart03.setVisibility(View.INVISIBLE);
                countDownTimer.cancel();
                Intent done = new Intent(Playing.this, Done.class);
                done.putExtra("Score", score);
                startActivity(done);
                finish();
                return;
            }

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
            }, 500);

            ClickedButton.setBackground(getResources().getDrawable(R.drawable.mybuttonwrong));


            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    for (int i = 0; i < Butttons.size(); i++) {
                        Butttons.get(i).setBackground(getResources().getDrawable(R.drawable.mybutton));
                    }

                    NextQuestion(++index);
                }
            }, 1200);


            if (Souls == 2) {
                Heart01.setVisibility(View.INVISIBLE);
            } else if (Souls == 1) {
                Heart02.setVisibility(View.INVISIBLE);
            }
        }
        TxtScore.setText(String.valueOf(score));

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
                    Toast.makeText(Playing.this, "Finished", Toast.LENGTH_SHORT).show();
                    countDownTimer.cancel();


                } else {
                    Toast.makeText(Playing.this, FinalValues.TIMEOUT_MESSAGE, Toast.LENGTH_SHORT).show();
                }
                countDownTimer.cancel();
                if (countDownTimer != null)
                    NextQuestion(++index);
            }
        };
    }

    private interface FireStoreCallback {
        void OnCallBack(ArrayList<Question> List);
    }
}



