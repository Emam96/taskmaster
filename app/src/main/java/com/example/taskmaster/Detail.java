package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class Detail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        String taskName = getIntent().getStringExtra("taskTitle");
        TextView tasktitle = findViewById(R.id.taskTitle);
        tasktitle.setText(taskName);

        String desc = getIntent().getStringExtra("desc");
        TextView descstuff = findViewById(R.id.desc);
        descstuff.setText(desc);

        String state = getIntent().getStringExtra("state");
        TextView statestuff = findViewById(R.id.statestate);
        statestuff.setText(state);



    }
}