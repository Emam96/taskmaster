package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class Add extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);



        Button buttonthree = (Button) findViewById(R.id.buttonthree);
        TextView title = (TextView) findViewById(R.id.titleentry);


        TextView desc = (TextView) findViewById(R.id.desctask);





        buttonthree.setOnClickListener(new  View.OnClickListener(){
            public void onClick(View v){

                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        String titletaken = title.getText().toString();
                        String desctaken = desc.getText().toString();
                        TaskDatabase db = Room.databaseBuilder(getApplicationContext(),
                                TaskDatabase.class, "task").build();
                        TaskDao taskDao = db.taskDao();
                        Task test = new Task(titletaken, desctaken, "Active");
                        taskDao.insert(test);

                    }
                });

                Toast.makeText(getApplicationContext(), "Task Added",Toast.LENGTH_LONG).show();
                startActivity(new Intent(Add.this, MainActivity.class));

            }
        });

    }
}