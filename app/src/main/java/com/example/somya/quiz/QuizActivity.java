package com.example.somya.quiz;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;
import java.util.Random;

public class QuizActivity extends AppCompatActivity {
    TextView input1;
    TextView input2;
    TextView sign;
    TextView equal;
    TextView quesLabel;
    TextView quesNumber;
    TextView timer;
    EditText answer;
    Button enter;
    public static int score = 0;
    static int quesCount = 0;
    int number1;
    int number2;
    int millisecs = 5000;
    CounterClass counter = new CounterClass(millisecs,1000);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init();

        displayQuestion();

        answer.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                String inputAns_ = answer.getText().toString().trim();
                int inputAns = -1;
                if(inputAns_.length() > 0)
                    inputAns = Integer.parseInt(inputAns_);
                boolean result = checkAnswer(number1, number2, inputAns, MainActivity.opertype);
                if (result) {
                    quesCount++;
                    counter.cancel();
                    displayToastNotification("Correct!");
                    score++;
                    if(quesCount >= 10) {
                        Intent in = new Intent(getApplicationContext(), ScoreActivity.class);
                        startActivity(in);
                    } else {
                        displayQuestion();
                    }
                }
            }
            public void afterTextChanged(Editable s) {}
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }
        });
    }

    /**
     * This function will initialise all the widgets in the layout
     */
    private void init() {
        setContentView(R.layout.activity_quiz);

        input1 = (TextView) findViewById(R.id.input1);
        input2 = (TextView) findViewById(R.id.input2);
        sign = (TextView) findViewById(R.id.sign);
        equal = (TextView) findViewById(R.id.equal);
        answer = (EditText) findViewById(R.id.answer);
        quesLabel = (TextView) findViewById(R.id.quesLabel);
        quesNumber = (TextView) findViewById(R.id.quesNumber);
        timer = (TextView) findViewById(R.id.timer);
        enter = (Button) findViewById(R.id.enter);
    }

    /**
     * function to move to the next screen - score or next question
     */
    public void moveNext() {
        counter.cancel();
        readAnswerAndUpdateScore();
        quesCount++;
        if(quesCount >= 10) {
            Intent in = new Intent(getApplicationContext(), ScoreActivity.class);
            startActivity(in);
        } else {
            displayQuestion();
        }
    }

    /**
     * Appends the input from the keypad to the text field.
     */
    public void inputAnswer(View v){
        answer.append(v.getTag().toString());
    }

    /**
     * Display the toast notification
     */
    private void displayToastNotification(CharSequence text) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        final Toast toast = Toast.makeText(context, text, duration);
        toast.show();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                toast.cancel();
            }
        }, 500);
    }

    /**
     * Reads the answer from the text field and update score and toast.
     */
    private void readAnswerAndUpdateScore() {
        int inputAns = -1;
        if (answer.getText().toString().trim().length() > 0) {
            Log.v("Answer:", answer.getText().toString());
            inputAns = Integer.parseInt(answer.getText().toString());
        }
        boolean result = checkAnswer(number1, number2, inputAns, MainActivity.opertype);
        if(result) {
            displayToastNotification("Correct!");
            score++;
        } else {
            displayToastNotification("Wrong!");
        }
        Log.v("score:",Integer.toString(score));
    }

    //class for the counter.
    public class CounterClass extends CountDownTimer {

        public CounterClass(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @TargetApi(Build.VERSION_CODES.GINGERBREAD)
        @Override
        public void onTick(long millisUntilFinished) {
            timer.setText(String.format(Locale.US,"%d",millisUntilFinished / 1000));
        }

        @Override
        public void onFinish() {
            moveNext();
        }
    }

    //method to display the question
    private void displayQuestion() {

        counter.start();

        quesNumber.setText(String.format(Locale.US,"%d",quesCount + 1));
        answer.setText("");
        number1 = generateRandomNumber();
        number2 = generateRandomNumber();
        while(number2 < number1) {
            number2 = generateRandomNumber();
        }

        //number2 should be smaller than number1 in case of - operation
        input1.setText(String.format(Locale.US,"%d",number1));
        input2.setText(String.format(Locale.US,"%d",number2));

        if(MainActivity.opertype == 0)
            sign.setText("+");
        else if(MainActivity.opertype == 1)
            sign.setText("-");
        else
            sign.setText("*");
    }

    //generates the random number for input
    private int generateRandomNumber() {
        Random rand = new Random();
        int minimum = 0;
        int maximum = 9;
        return (minimum + rand.nextInt((maximum - minimum) + 1));
    }

    //checks the answer based on the input from user
    private boolean checkAnswer(int val1, int val2, int answer, int operation) {
        int expectedOutput = -1;

        switch(operation) {
            case 0:
                expectedOutput = val1 + val2;
                break;
            case 1:
                expectedOutput = val1 - val2;
                break;
            case 2:
                expectedOutput = val1 * val2;
                break;
            default:
        }
        return (answer == expectedOutput);
    }



}
