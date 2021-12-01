package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Task;
import com.amplifyframework.datastore.generated.model.Team;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.InputStream;

public class Add extends AppCompatActivity {


    Intent chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
    Uri uri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);


        Spinner s = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.items, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);



        Button buttonthree = (Button) findViewById(R.id.buttonthree);
        TextView title = (TextView) findViewById(R.id.titleentry);
        TextView desc = (TextView) findViewById(R.id.desctask);
        Button upload = (Button) findViewById(R.id.upload);
        TextView filename = (TextView) findViewById(R.id.filename);

            filename.setText("Choose a file");


        upload.setOnClickListener(new  View.OnClickListener(){
            @Override
            public void onClick(View v){
                chooseFile.setType("*/*");
                chooseFile = Intent.createChooser(chooseFile, "Choose a File");
                startActivityForResult(chooseFile, 12);

            }
        });



        buttonthree.setOnClickListener(new  View.OnClickListener(){
            public void onClick(View v){

                        String titletaken = title.getText().toString();
                        String desctaken = desc.getText().toString();
                        String team =  s.getSelectedItem().toString();

                        Amplify.DataStore.query(
                                Team.class,Team.NAME.contains(team),
                                items -> {
                                    while (items.hasNext()) {
                                        Team item = items.next();
                                        Task item1 = Task.builder().title(titletaken).body(desctaken).state("Active").teamId(item.getId()).build();
                                        Amplify.DataStore.save(
                                                item1,
                                                success -> Log.i("COMO", "Saved item: "),
                                                error -> Log.e("Amplify", "Could not save item to DataStore", error)
                                        );

                                       String key = item1.getId();

                                        try {
                                            InputStream file = getContentResolver().openInputStream(uri);
                                            Amplify.Storage.uploadInputStream(
                                                    key,
                                                    file,
                                                    result -> Log.i("UPLOAD", "Successfully uploaded: " + result.getKey()),
                                                    storageFailure -> Log.e("UPLOAD", "Upload failed", storageFailure)
                                            );
                                        } catch (FileNotFoundException e) {
                                            e.printStackTrace();
                                        }
                                            Log.i("EMAM", "Id was stored " );
                                        Log.i("Amplify", "Id " + item.getId());
                                    }
                                },
                                failure -> Log.e("Amplify", "Could not query DataStore", failure)
                        );

                Toast.makeText(getApplicationContext(), "Task Added",Toast.LENGTH_LONG).show();
                finish();
                    }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();



    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent resultData) {
        super.onActivityResult(requestCode, resultCode, resultData);
        if (requestCode == 12 && resultCode == Activity.RESULT_OK) {
            if (resultData != null) {
                uri = resultData.getData();
                TextView filename = (TextView) findViewById(R.id.filename);

                if (uri != null) {
                    filename.setText(uri.getPath());

                } else {
                    filename.setText("Choose a file");

                }
                Toast.makeText(getApplicationContext(),uri.getPath(),Toast.LENGTH_SHORT).show();
            }
        }
    }


    public void back( View view){
        this.finish();

    }


}