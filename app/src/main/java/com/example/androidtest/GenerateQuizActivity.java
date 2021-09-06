package com.example.androidtest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class GenerateQuizActivity extends AppCompatActivity {

    public static final String EXTRA_SCORE = "com.example.androidtest.FINALSCORE";

    private TextView questionumber, question;
    private Button button1, button2, button3, button4;
    private ArrayList<Question> questionSet;
    private int currentQuestion, score, quizLength, ans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_quiz);

        // Get the Intent that started this activity and extract the number
        Intent intent = getIntent();
        quizLength = intent.getIntExtra(MainActivity.EXTRA_LENGTH, 0);
        String[] qlist = intent.getStringArrayExtra(MainActivity.EXTRA_QUESTIONS);

        // initialize visual objects
        questionumber = findViewById(R.id.questionumber);
        question = findViewById(R.id.questionBox);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button4);

        // generate questions list
        System.out.println("Made it here");
        questionSet = new ArrayList<Question>();
        makeQuestions(qlist);

        // set display to question for the first time
        currentQuestion = 0;
        score = 0;
        changeDisplay();

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(0);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(1);
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(2);
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(3);
            }
        });
    }

    /** takes an integer representing the selected answer, checks to see if it is correct, then adds
     * to score and calls to change the display to the next question, or to the end if all questions
     * are answered.*/
    private void checkAnswer(int answer) {
        if(ans==answer) score++;
        changeDisplay();
    }

    /** changes the display to the next question, or checks to see if all questions have been
     * answered and changes the display to the end. */
    private void changeDisplay(){
        if(currentQuestion >= quizLength){
            displayEnd();
        }else{
            Question currQ = questionSet.get(currentQuestion);
            question.setText(currQ.getQuestion());
            String textplaceholder = "Question " + ++currentQuestion + "/" + quizLength;
            questionumber.setText(textplaceholder);
            button1.setText(currQ.getOption1());
            button2.setText(currQ.getOption2());
            button3.setText(currQ.getOption3());
            button4.setText(currQ.getOption4());
            ans = currQ.getAnswer();
        }
    }
    /** Change to the end*/
    private void displayEnd() {
        Intent intent = new Intent(this, EndScreenActivity.class);
        intent.putExtra(EXTRA_SCORE, score);
        startActivity(intent);
    }

    /** generates question objects and places them into the question set.*/
    private void makeQuestions(String[] qlist){
        Random random = new Random();
        String[] qlistmix = new String[quizLength];
        //First pick out random questions, randomize question order. Could duplicate Qs, need fix
        for(int j = 0; j < quizLength; j++){
            qlistmix[j] = qlist[random.nextInt(qlist.length)];
        }
        //Then randomize answer order (first given response is correct answer) and generate a Question
        for(int k = 0; k < quizLength; k++){
            int[] rand2 = {0, 1, 2, 3};
            //Randomize potential answer order in an array of 0-3
            Random rnd = new Random();
            for(int h = rand2.length - 1; h > 0; h--){
                int index = rnd.nextInt(h + 1);
                int tmp = rand2[index];
                rand2[index] = rand2[h];
                rand2[h] = tmp;
            }
            //Split the given string into its parts, the question and 4 answers
            String[] qAndAs = qlistmix[k].split("\n");
            //Use the randomized array as indexes to change the order of the answers
            int newAns = rand2[0];
            String[] ansShuffle = new String[4];
            ansShuffle[rand2[0]] = qAndAs[1];
            ansShuffle[rand2[1]] = qAndAs[2];
            ansShuffle[rand2[2]] = qAndAs[3];
            ansShuffle[rand2[3]] = qAndAs[4];
            Question newQ = new Question(qAndAs[0], ansShuffle[0], ansShuffle[1], ansShuffle[2], ansShuffle[3], newAns);
            System.out.println("Question " +k+ " is generated as: \n" + newQ.toString());
            questionSet.add(newQ);
        }
    }

}