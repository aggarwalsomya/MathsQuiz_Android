package com.example.somya.quiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ScoreActivity extends AppCompatActivity {

    TextView scorelabel;
    TextView score;
    Button restart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        scorelabel = (TextView) findViewById(R.id.scorelabel);
        score = (TextView) findViewById(R.id.score);
        restart = (Button) findViewById(R.id.restart);

        Log.v("Score is:", Integer.toString(QuizActivity.score));
        score.setText(Integer.toString(QuizActivity.score));

        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QuizActivity.score = 0;
                QuizActivity.quesCount = 0;
                Log.v("Game will restart","");
                Intent in = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(in);
            }
        });
    }
}
