package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                TaskDatabase db = Room.databaseBuilder(getApplicationContext(),
                        TaskDatabase.class, "task").build();
                TaskDao taskDao = db.taskDao();
                List<Task> tasks =   taskDao.getAll();

                RecyclerView recyclerView = findViewById(R.id.tasksview);
                recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                recyclerView.setAdapter(new TaskAdapter(tasks));
            }
        });






        Button ButtonOne = (Button) findViewById(R.id.buttonone);
        Button ButtonTwo = (Button) findViewById(R.id.buttontwo);
        Button settings = (Button) findViewById(R.id.settings);


        settings.setOnClickListener(new  View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(MainActivity.this, Settings.class));

            }
        });


        ButtonOne.setOnClickListener(new  View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(MainActivity.this, Add.class));

            }
        });

        ButtonTwo.setOnClickListener(new  View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(MainActivity.this, All.class));
            }
        });


    }



    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        String username = sharedPreferences.getString("username","User's tasks");
        TextView nameLabel = findViewById(R.id.nameLabel);
        nameLabel.setText(username+" tasks");





    }





}

