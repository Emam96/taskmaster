package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.AWSDataStorePlugin;
import com.amplifyframework.datastore.generated.model.Task;
import com.amplifyframework.datastore.generated.model.Team;
import com.amplifyframework.storage.s3.AWSS3StoragePlugin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.SlideInRightAnimationAdapter;
import jp.wasabeef.recyclerview.animators.FadeInAnimator;
import jp.wasabeef.recyclerview.animators.FadeInRightAnimator;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

public class MainActivity extends AppCompatActivity {


    List<Task> tasks = new ArrayList<>();
    TaskAdapter adapter = new TaskAdapter(tasks);
    Handler handler = new Handler();

    Runnable runnable = new Runnable() {
        @SuppressLint("NotifyDataSetChanged")
        @Override
        public void run() {
            adapter.notifyDataSetChanged();
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        try {
            Amplify.addPlugin(new AWSApiPlugin());
            Amplify.addPlugin(new AWSS3StoragePlugin());
            Amplify.addPlugin(new AWSCognitoAuthPlugin());
            Amplify.addPlugin(new AWSDataStorePlugin());
            Amplify.configure(getApplicationContext());
            Log.i("Tutorial", "Initialized Amplify");
        } catch (AmplifyException failure) {
            Log.e("Tutorial", "Could not initialize Amplify", failure);
        }
        Amplify.DataStore.observe(Task.class,
                started -> Log.i("Tutorial", "Observation began."),
                change -> Log.i("Tutorial", change.item().toString()),
                failure -> Log.e("Tutorial", "Observation failed.", failure),
                () -> Log.i("Tutorial", "Observation complete.")
        );
        Amplify.DataStore.observe(Team.class,
                started -> Log.i("Tutorial", "Observation began."),
                change -> Log.i("Tutorial", change.item().toString()),
                failure -> Log.e("Tutorial", "Observation failed.", failure),
                () -> Log.i("Tutorial", "Observation complete.")
        );


        Amplify.Auth.fetchAuthSession(
                result -> Log.i("AmplifyQuickstart", result.toString()),
                error -> Log.e("AmplifyQuickstart", error.toString())
        );





//  List tasks = new ArrayList();

        Amplify.DataStore.query(
                Task.class,Task.TEAM_ID.eq("7ddc0fad-18be-4690-94f9-27efaff0cbae"),
                items -> {
                    while (items.hasNext()) {
                        Task item = items.next();
                        tasks.add(item);
                        Log.i("Amplify", "Id " + item.getId());
                    }

                },
                failure -> Log.e("Amplify", "Could not query DataStore", failure)
        );


                // REVERSED
                RecyclerView recyclerView = findViewById(R.id.tasksview);
                recyclerView.setAdapter(adapter);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
                linearLayoutManager.setReverseLayout(true);
                linearLayoutManager.setStackFromEnd(true);
                recyclerView.setLayoutManager(linearLayoutManager);

                // ANIMATION
                recyclerView.setAdapter(new ScaleInAnimationAdapter(adapter));

                ImageView ButtonOne = (ImageView) findViewById(R.id.buttonone);
                ImageView ButtonTwo =  findViewById(R.id.buttontwo);
                ImageView settings =  findViewById(R.id.settings);





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



    @SuppressLint("SetTextI18n")
    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        String username = sharedPreferences.getString("username","User's tasks");
        String team = sharedPreferences.getString("team","team");
        TextView nameLabel = findViewById(R.id.nameLabel);
        TextView teamLabel = findViewById(R.id.teamname);
        nameLabel.setText(username+"'s" +" Tasks");
        teamLabel.setText(team);


        Amplify.DataStore.query(
                Team.class,Team.NAME.contains(team),
                items -> {
                    while (items.hasNext()) {
                        Team item = items.next();

                        Amplify.DataStore.query(
                                Task.class,Task.TEAM_ID.eq( item.getId()),
                                itemss -> {
                                    tasks.clear();
                                    while (itemss.hasNext()) {
                                        Task item1 = itemss.next();
                                        tasks.add(item1);
                                        Log.i("DUCK", "list " + item1.getTeamId());

                                    }
                                    handler.post(runnable);
                                },
                                failure -> Log.e("Amplify", "Could not query DataStore", failure)
                        );
                        Log.i("Amplify", "Id " + item.getId());
                    }
                    handler.post(runnable);
                },
                failure -> Log.e("Amplify", "Could not query DataStore", failure)
        );
    }
}

