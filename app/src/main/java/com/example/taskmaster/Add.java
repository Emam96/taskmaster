package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Task;

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
                        Task item = Task.builder()
                                .title(titletaken)
                                .body(desctaken)
                                .state("Active")
                                .build();
                        Amplify.DataStore.save(
                                item,
                                success -> Log.i("Amplify", "Saved item: " + success.item().getId()),
                                error -> Log.e("Amplify", "Could not save item to DataStore", error)
                        );

                    }
                });

                Toast.makeText(getApplicationContext(), "Task Added",Toast.LENGTH_LONG).show();
                finish();

            }
        });

    }
}