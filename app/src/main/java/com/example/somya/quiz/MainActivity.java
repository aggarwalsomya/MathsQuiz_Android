package com.example.somya.quiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {

    Button startquiz;
    Spinner dropdown;
    public static int opertype = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startquiz = (Button) findViewById(R.id.startquiz);
        dropdown = (Spinner)findViewById(R.id.options);
        String[] items = new String[]{"Addition", "Subtraction", "Multiplication"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);

        startquiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("Selected dropdown item:",dropdown.getSelectedItem().toString());
                opertype = getOperationType(dropdown.getSelectedItem().toString());
                Intent in = new Intent(getApplicationContext(), QuizActivity.class);
                startActivity(in);
            }
        });
    }

    private int getOperationType(String operation) {
        if(operation.contentEquals("Addition"))
            return 0;
        else if(operation.contentEquals("Subtraction"))
            return 1;
        else if(operation.contentEquals("Multiplication"))
            return 2;
        else
            return -1;
    }
}
