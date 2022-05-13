package com.example.todofinalapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

// child class SplashActivity extends parent AppCompatActivity to inherit all the parent properties
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        /*
         * Handlers are used to schedule activities or messages to run at some point in the future.
         *
         * postDelayed() causes the Runnable r to be added to the message queue, to be run after the specified amount
         * of time elapses.
         * */
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // using shared preferences for fetching the value of the mentioned key
                SharedPreferences preferences = getApplicationContext().getSharedPreferences("todo_pref", 0);
                // setting the boolean variable's preference as false
                Boolean authentication = preferences.getBoolean("authentication", false);
                // if the user is authenticated
                if (authentication) {
                    // intent to display the main activity
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    // starting the activity
                    startActivity(intent);
                    // closing the activity
                    finish();
                } else {
                    // intent to start the login activity if not authenticated
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    // starting the activity
                    startActivity(intent);
                    // closing the activity
                    finish();
                }
            }
            // setting the delay time i.e. 3 seconds
        }, 3000);
    }
}
