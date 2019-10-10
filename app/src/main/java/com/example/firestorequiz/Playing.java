package com.example.firestorequiz;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.firestorequiz.Model.Question;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import io.netopen.hotbitmapgg.library.view.RingProgressBar;


public class Playing extends AppCompatActivity implements View.OnClickListener {

    public ArrayList<Question> mQuestions = new ArrayList<>();
    RingProgressBar mRingProgressBar;
    int progress = 0;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {

                if (progress < 100) {
                    progress++;
                    mRingProgressBar.setProgress(progress);
                }
            }

        }
    };
    int index = 0;
    int trys = 3;
    TextView QuestionText;
    Button AnswerA, AnswerB, AnswerC, AnswerD;
    ImageView Heart01, Heart02, Heart03;

    int CategoryID, Stage;

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

        mRingProgressBar = findViewById(R.id.progress_bar_1);


        Intent a = getIntent();
        SharedPreferences prefs = getSharedPreferences("MyPref", MODE_PRIVATE);
        String ImageURL = prefs.getString(String.valueOf(R.string.ImagePath_key), "");

        CategoryID = prefs.getInt(String.valueOf(R.string.CategoryId_key), 8);
        Stage = a.getIntExtra("Level", 8);


        ImageView Img = findViewById(R.id.imageView);
        Picasso.get()
                .load(ImageURL)
                .into(Img);

        GetQuestions(new FireStoreCallback() {
            @Override
            public void OnCallBack(ArrayList<Question> List) {


                NextQuestion();
            }
        });

        StartTimer();

        mRingProgressBar.setOnProgressListener(new RingProgressBar.OnProgressListener() {

            @Override
            public void progressToComplete() {
                // Progress reaches the maximum callback default Max value is 100
                Toast.makeText(Playing.this, "TimeOut", Toast.LENGTH_SHORT).show();
            }
        });


        //  Toast.makeText(this, "No Questions here", Toast.LENGTH_SHORT).show();


        AnswerA.setOnClickListener(this);
        AnswerB.setOnClickListener(this);
        AnswerC.setOnClickListener(this);
        AnswerD.setOnClickListener(this);
    }

    public void NextQuestion() {


        Question question = (Question) mQuestions.get(index);

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

        index++;


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
                        callback.OnCallBack(mQuestions);
                    }
                });

    }

    public void StartTimer() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {
                    try {
                        Thread.sleep(250);
                        handler.sendEmptyMessage(0);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    @Override
    public void onClick(View v) {
        Button btn = (Button) v;

        String UserAnswer = String.valueOf(btn.getText());
        int inde = index;
        if (index > 0) {
            --inde;
        }
        if (UserAnswer == ( mQuestions.get(inde)).getCorrectAnswer()) {

            //Add points And stuff
            Toast.makeText(this, "+1", Toast.LENGTH_SHORT).show();
            StartTimer();
            NextQuestion();

        } else {
            Toast.makeText(this, "Wrong", Toast.LENGTH_SHORT).show();
            if (trys > 0) {
                --trys;
                if (trys == 2) {
                    Heart01.setVisibility(View.INVISIBLE);
                } else if (trys == 1) {
                    Heart02.setVisibility(View.INVISIBLE);
                } else if (trys == 0) {
                    Toast.makeText(this, "GameOver", Toast.LENGTH_SHORT).show();
                }
                StartTimer();
                NextQuestion();
            }else{
                Toast.makeText(this, "GameOver", Toast.LENGTH_SHORT).show();
            }
        }
        // points And stuff
    }

    private interface FireStoreCallback {
        void OnCallBack(ArrayList<Question> List);
    }

}



