package com.example.androidtest;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_LENGTH = "com.example.androidtest.QUIZLENGTH";
    public static final String EXTRA_QUESTIONS = "com.example.androidtest.QUESTIONSTRINGS";
    //public static final String QUESTION_FILE = "questionlist.txt";
    public ArrayList<String> qlist;
    public String[] qlist2;
    public int qiter;
    private TextView qsAvailable;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        qsAvailable = (TextView) findViewById(R.id.numQuestions);
        qlist = new ArrayList<String>();
        try {
            packageQuestions();
        } catch (IOException e) {
            System.out.println("File handling error");
            e.printStackTrace();
        }
        qlist2 = new String[qlist.size()];
        for (int i = 0; i < qlist.size(); i++){
            qlist2[i] = qlist.get(i);
        }
    }

    /** Called when the user taps the Send button */
    public void initializeQuiz(View view) {
        Intent intent = new Intent(this, GenerateQuizActivity.class);
        EditText editText = (EditText) findViewById(R.id.editTextNumber);
        String message = editText.getText().toString();
        int numOfQs = Integer.parseInt(message);
        if(numOfQs > qiter){
            Toast toast = Toast.makeText(this, R.string.error_ntb, Toast.LENGTH_SHORT);
            toast.show();
        }else {
            intent.putExtra(EXTRA_LENGTH, numOfQs);
            intent.putExtra(EXTRA_QUESTIONS, qlist2);
            startActivity(intent);
        }
    }

    /** This method creates a file scanner to read the question file found in raw. It then packages
     * it into a String array to be sent into the intent once the button is pushed.*/
    public void packageQuestions() throws IOException {
        InputStream qs = getResources().openRawResource(R.raw.questionlist);
        BufferedReader br = new BufferedReader(new InputStreamReader(qs));
        while (br.ready()){
            String questLine = "";
            for(int sep = 0; sep < 5; sep++){
                questLine = questLine.concat(br.readLine());
                questLine = questLine.concat("\n");
            }
            qlist.add(questLine);
            qiter++;
            br.readLine(); // throw away the new line character between questions
        }
        br.close();
        qsAvailable.setText(String.valueOf(qiter));
    }
}