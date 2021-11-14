package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        Button ButtonOne = (Button) findViewById(R.id.buttonone);
        Button ButtonTwo = (Button) findViewById(R.id.buttontwo);

        ButtonOne.setOnClickListener(new  View.OnClickListener(){
            public void onClick(View v){
                startActivity(new Intent(MainActivity.this, Add.class));

            }
        });

        ButtonTwo.setOnClickListener(new  View.OnClickListener(){
            public void onClick(View v){
                startActivity(new Intent(MainActivity.this, All.class));
            }
        });

    }

}

