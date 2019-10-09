package com.example.firestorequiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.firestorequiz.Model.Question;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import io.netopen.hotbitmapgg.library.view.RingProgressBar;

import static java.sql.Types.NULL;


public class Playing extends AppCompatActivity {

    RingProgressBar mRingProgressBar ;

    int progress =0;

    FirebaseFirestore db;

    ArrayList<Question> mQuestions = new ArrayList<>();

    Handler handler =new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what==0){

                if (progress<100){
                    progress++;
                    mRingProgressBar.setProgress(progress);
                }
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playing);



        SharedPreferences prefs = getSharedPreferences("MyPref", MODE_PRIVATE);

        String ImageURL = prefs.getString(String.valueOf(R.string.ImagePath_key),"");
        int CategoryID = prefs.getInt(String.valueOf(R.string.CategoryId_key),8);

        mRingProgressBar =   findViewById(R.id.progress_bar_1);

        ImageView Img = findViewById(R.id.imageView);

        Picasso.get()
                .load(ImageURL)
                .into(Img);

        StartTimer();
        mRingProgressBar.setOnProgressListener(new RingProgressBar.OnProgressListener()
        {

            @Override
            public void progressToComplete()
            {
                // Progress reaches the maximum callback default Max value is 100
                Toast.makeText(Playing.this, "TimeOut", Toast.LENGTH_SHORT).show();
            }
        });



    }

    public void GetQuestions(int CategoryID,int Stage) {

        db = FirebaseFirestore.getInstance();
        db.collection("Categories").
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("TAG", document.getId() + " => " + document.getData());
                                mQuestions.add(document.toObject(Question.class));
                            }
                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                    }
                });

    }
        public void StartTimer(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i =0;i<100;i++){
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
}
