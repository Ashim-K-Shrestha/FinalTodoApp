package com.example.todofinalapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;

// function for the login process
public class LoginActivity extends AppCompatActivity {
    // declaring the intent extra key
    private static final String TAG = "LoginUserTest";
    // declaring the editText variables
    EditText name, password;
    // declaring the button variables
    Button signup, login;
    // declaring a list for storing the users
    List<EUser> userList;
    // declaring a variable for the userViewModel
    private UserViewModel userViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // initializing object for the ViewModelProvider
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        // assigning the activity id with the variables
        signup = findViewById(R.id.login_activity_btn_signup);
        login = findViewById(R.id.login_activity_btn_login);
        name = findViewById(R.id.login_activity_et_name);
        password = findViewById(R.id.login_activity_et_password);

        // using shared preferences for fetching the value of the mentioned key
        SharedPreferences preference = getApplicationContext().getSharedPreferences("todo_pref",  0);
        // Interface used for modifying values in a SharedPreferences object. All changes you make in an editor
        // are batched, and not copied back to the original SharedPreferences until you call commit() or apply()
        SharedPreferences.Editor editor = preference.edit();

        // when the login button is clicked
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // fetching all the user list into the variable
                userList = userViewModel.getAllUsers();

                // for all the components of the list
                for (int i = 0; i< userList.size(); i++) {
                    // display the user name in the console
                    Log.d(TAG, userList.get(i).getName());
                    //if username and password matches the with the instance in the db
                    if(userList.get(i).getName().equalsIgnoreCase(name.getText().toString())
                            && userList.get(i).getPassword().equals(password.getText().toString())) {
                        // set as authenticated
                        editor.putBoolean("authentication",true);
                        // fetching the username
                        editor.putInt("user_id", userList.get(i).getUser_id());
                        // commiting back to the original preference
                        editor.commit();

                    }
                }

                // fetching the boolean of the preference set as false
                Boolean authentication = preference.getBoolean("authentication",false);

                // if user is authenticated
                if(authentication) {
                    // creating a new intent to launch the new activity
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    // starting the activity
                    startActivity(intent);
                }
                // else displaying error message
                else {
                    name.setError("Username or password doesn't match!");
                    // displaying a toast message with error
                    Toast.makeText(LoginActivity.this,"User not found!", Toast.LENGTH_LONG).show();
                }
            }
        });
        // when the signup button is clicked
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            // function to start the sign up activity
            public void onClick(View v) {
                // intent to start the sign up activity
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                // starting the activity
                startActivity(intent);
            }
        });
    }
}
