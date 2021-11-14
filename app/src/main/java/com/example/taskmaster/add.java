package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Add extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        Button buttonthree = (Button) findViewById(R.id.buttonthree);

        buttonthree.setOnClickListener(new  View.OnClickListener(){
            public void onClick(View v){

                Toast.makeText(getApplicationContext(), "Task Added",Toast.LENGTH_LONG).show();
            }
        });

    }
}