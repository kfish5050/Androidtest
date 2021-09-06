package com.example.androidtest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class EndScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        setContentView(R.layout.activity_end_screen);
        TextView scoretext = findViewById(R.id.Score);
        scoretext.setText(String.valueOf(intent.getIntExtra(GenerateQuizActivity.EXTRA_SCORE, 0)));
    }

    public void restartQuiz(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}