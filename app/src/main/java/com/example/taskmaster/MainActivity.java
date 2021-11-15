package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);





        Button ButtonOne = (Button) findViewById(R.id.buttonone);
        Button ButtonTwo = (Button) findViewById(R.id.buttontwo);
        Button task1 = (Button) findViewById(R.id.task1);
        Button task2 = (Button) findViewById(R.id.task2);
        Button task3 = (Button) findViewById(R.id.task3);
        Button settings = (Button) findViewById(R.id.settings);


        String buttonText1 = task1.getText().toString();
        String buttonText2 = task2.getText().toString();
        String buttonText3 = task3.getText().toString();

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


        task1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent goToDetailsPage = new Intent(MainActivity.this,Detail.class);
                goToDetailsPage.putExtra("taskTitle", buttonText1);
                startActivity(goToDetailsPage);
            }
        });
        task2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent goToDetailsPage = new Intent(MainActivity.this,Detail.class);
                goToDetailsPage.putExtra("taskTitle", buttonText2);
                startActivity(goToDetailsPage);
            }
        });
        task3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent goToDetailsPage = new Intent(MainActivity.this,Detail.class);
                goToDetailsPage.putExtra("taskTitle", buttonText3);
                startActivity(goToDetailsPage);
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

