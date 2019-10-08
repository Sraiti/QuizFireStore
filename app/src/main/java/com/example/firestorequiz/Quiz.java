package com.example.firestorequiz;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firestorequiz.Adapters.CategoryAdapter;
import com.example.firestorequiz.Adapters.StageAdapter;
import com.example.firestorequiz.Model.Question;
import com.example.firestorequiz.Model.Stage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static java.sql.Types.NULL;

public class Quiz extends AppCompatActivity {


    FirebaseFirestore db;

    ArrayList<Question> mQuestions = new ArrayList<>();

    ImageView HeaderImage;
     private ArrayList<Stage> StageDataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
      //  GetQuestions();

        HeaderImage =findViewById(R.id.CategoryImage);
        Intent a = getIntent();


        String ImageURL = a.getStringExtra("ImageURL");
        Picasso.get()
                .load(ImageURL)
                .into(HeaderImage);


        RecyclerView   recyclerView = findViewById(R.id.recycler);

       // recyclerView.setHasFixedSize(true);

        // Specify a linear layout manager.
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(Quiz.this, 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        StageData();


        // Specify an adapter.
        RecyclerView.Adapter<RecyclerView.ViewHolder> adapter = new StageAdapter(StageDataList,this);
        recyclerView.setAdapter(adapter);

    }

    private void StageData() {
        StageDataList.addAll(Stage.CreatStages(Quiz.this));
    }
    public void GetQuestions() {
        db = FirebaseFirestore.getInstance();

        Intent a = getIntent();

        int id = a.getIntExtra("CategoryId", NULL);


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
