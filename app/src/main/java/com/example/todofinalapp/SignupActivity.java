package com.example.todofinalapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;

public class SignupActivity extends AppCompatActivity {
    // declaring constant variable for the intent extra
    private static final String TAG = "UserTest";
    // declaring editText variables
    EditText name, password, confirmPassword;
    // declaring button variables
    Button signup, login;
    // declaring a list array
    List<EUser> userList;
    // declaring boolean variable and assigning value as false
    Boolean error = false;
    // declaring a private UserViewModel variable
    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        // initializing a new object of type TodoViewModel by calling the parent constructor
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        // assigning the view id with the declared variables
        signup = findViewById(R.id.login_activity_btn_login);
        login = findViewById(R.id.signup_activity_btn_login);
        name = findViewById(R.id.login_activity_et_name);
        password = findViewById(R.id.login_activity_et_password);
        confirmPassword = findViewById(R.id.signup_activity_et_confirm_pass);

        // when the button view is clicked
        signup.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // boolean error set as false
                error = false;
                // fetching all the users into the list variable
                userList = userViewModel.getAllUsers();
                // initializing a new object of the EUser class
                EUser eUser = new EUser();
                // setting the user id plus 1 the size of the user list
                eUser.setUser_id(userList.size() + 1);
                // setting the name of the user
                eUser.setName(name.getText().toString());
                // setting the password
                eUser.setPassword(password.getText().toString());

                // if the set passwords are null
                if (password.getText().toString().trim().equals("") ||
                        confirmPassword.getText().toString().trim().equals("")) {
                    // error boolean value set to true
                    error = true;
                    // displaying unsuccessful toast message
                    Toast.makeText(SignupActivity.this, "Password field shouldn't be empty!", Toast.LENGTH_SHORT).show();
                }
                // if the password and confirm password do not match
                if (!password.getText().toString().equals(confirmPassword.getText().toString())) {
                    // set error to true
                    error = true;
                    // setting the error message
                    password.setError("Password must match confirm password!");
                }
                // validating for a unique username
                for (int i = 0; i < userList.size(); i++) {
                    // if if the username matches to already existing name
                    if (name.getText().toString().equalsIgnoreCase(userList.get(i).getName())) {
                        // displaying the username in the console
                        Log.d(TAG, userList.get(i).getName());
                        // displaying the error message
                        name.setError("User name already exists!");
                        // setting the error as true
                        error = true;
                        break;
                    }

                }

                // if there are no errors then
                if (!error) {
                    // inserting a new user into the user view model
                    userViewModel.insert(eUser);
                    // displaying the successful toast message
                    Toast.makeText(SignupActivity.this, "Registered successfully!", Toast.LENGTH_LONG).show();
                    // error boolean set as false
                    error = false;
                } else {
                    // displaying the unsuccessful toast message
                    Toast.makeText(SignupActivity.this, "Registration Failed!", Toast.LENGTH_LONG).show();
                }
            }
        });

        // when the login button is clicked
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            // function to start a new activity i.e. loginActivity
            public void onClick(View v) {
                // declaring an intent to start a new activity
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                // starting the activity
                startActivity(intent);
            }
        });
    }
}
