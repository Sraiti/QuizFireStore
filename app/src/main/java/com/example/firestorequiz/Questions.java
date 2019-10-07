package com.example.firestorequiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import static java.sql.Types.NULL;

public class Questions extends AppCompatActivity {



    TextView CategoryID_txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);


        CategoryID_txt =findViewById(R.id.txt_id);

        Intent a = getIntent();

        int id=a.getIntExtra("CategoryId",NULL);

        CategoryID_txt.setText(String.valueOf(id));
    }
}
