package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.amplifyframework.core.Amplify;

import java.io.File;

public class Detail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        TextView location = findViewById(R.id.location);





        String taskName = getIntent().getStringExtra("taskTitle");
        TextView tasktitle = findViewById(R.id.taskTitle);
        tasktitle.setText(taskName);

        String desc = getIntent().getStringExtra("desc");
        TextView descstuff = findViewById(R.id.desc);
        descstuff.setText(desc);

        String state = getIntent().getStringExtra("state");
        TextView statestuff = findViewById(R.id.statestate);
        statestuff.setText(state);

        String key = getIntent().getStringExtra("key");

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Detail.this);
        String locationData = sharedPreferences.getString(key,"No Location Found");

        location.setText(locationData);

        Amplify.Storage.downloadFile(
                key,
                new File(getApplicationContext().getFilesDir() + "/download.txt"),
                result ->{ Log.i("MyAmplifyApp", "Successfully downloaded: " + result.getFile().getAbsolutePath());
                    File imgFile = new  File(result.getFile().getAbsolutePath());
                    if(imgFile.exists()){
                        Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                        ImageView taskimage=findViewById(R.id.imagee);
                        taskimage.setImageBitmap(myBitmap);
                    }

                },
                error -> Log.e("MyAmplifyApp",  "Download Failure", error)
        );

    }

    public void back( View view){

        this.finish();

    }

}