package com.example.taskmaster;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.amazonaws.mobileconnectors.cognitoauth.tokens.AccessToken;
import com.amplifyframework.auth.AuthChannelEventName;
import com.amplifyframework.auth.AuthUserAttribute;
import com.amplifyframework.auth.AuthUserAttributeKey;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.core.InitializationStatus;
import com.amplifyframework.datastore.generated.model.Task;
import com.amplifyframework.datastore.generated.model.Team;
import com.amplifyframework.hub.HubChannel;

import java.util.ArrayList;
import java.util.List;

public class Settings extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        Spinner s = (Spinner) findViewById(R.id.spinner1);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.items, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);

        Button preferencesButton = findViewById(R.id.save);
        Button login = (Button)  findViewById(R.id.login);

        preferencesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText name = findViewById(R.id.name);
                String userName = name.getText().toString();
                String team = s.getSelectedItem().toString();
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Settings.this);
                sharedPreferences.edit().putString("username",userName).apply();
                sharedPreferences.edit().putString("team",team).apply();
                Toast.makeText(Settings.this,"Saved!", Toast.LENGTH_LONG).show();
                finish();
            }
        });


        login.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                Amplify.Auth.fetchAuthSession(
                        user -> {
                            if (user.isSignedIn()) {
                                Amplify.Auth.signOut(
                                        () -> Log.i("LOGOUT", "Signed out successfully"),
                                        error -> Log.e("LOGOUT", error.toString())
                                );

                            } else {
                                Amplify.Auth.signInWithWebUI(
                                        Settings.this,
                                        result -> Log.i("LOGIN", result.toString()),
                                        error -> Log.e("LOGIN", error.toString())
                                );

                            }
                        },
                        failure -> Log.e("Amplify", "Could not query DataStore", failure)
                );
            }
        });
    }



    @SuppressLint("SetTextI18n")
    @Override
    protected void onResume() {
        super.onResume();


        ImageView done = findViewById(R.id.done);
        TextView username = findViewById(R.id.username);
        done.setVisibility(View.INVISIBLE);
        username.setText("");
        Button login = (Button)  findViewById(R.id.login);
        Amplify.Auth.fetchAuthSession(
                user -> {
                    if (user.isSignedIn()) {
                        String data = Amplify.Auth.getCurrentUser().getUsername();
                        username.setText(data);
                        done.setVisibility(View.VISIBLE);


                    }
                },
                failure -> Log.e("Amplify", "Could not query DataStore", failure)
        );

        Amplify.Auth.fetchAuthSession(
                user -> {
                    if (user.isSignedIn()) {

                        login.setText("Log out");
                    } else {

                        login.setText("Log in");
                    }
                },
                failure -> Log.e("Amplify", "Could not query DataStore", failure)
        );

    }

    public void back( View view){
        this.finish();
    }

}