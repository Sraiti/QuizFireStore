package com.example.firestorequiz;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.firestorequiz.Model.Question;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import static java.sql.Types.NULL;

public class Quiz extends AppCompatActivity {


    FirebaseFirestore db;

    ArrayList<Question> mQuestions = new ArrayList<>();
    TextView tv ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        GetQuestions();



    }

    public void GetQuestions() {
        db = FirebaseFirestore.getInstance();

        Intent a = getIntent();

        int id = a.getIntExtra("CategoryId", NULL);
        tv=findViewById(R.id.textView);

        tv.setText(String.valueOf(id));
        db.collection("Quiz")
                .whereEqualTo("CategoryId", id)
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
}
