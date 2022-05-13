package com.example.todofinalapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;

// class ProfileActivity extends AppCompatActivity to inherit the parent properties
public class ProfileActivity extends AppCompatActivity {
    // defining a constant for the intent extra
    private static final String TAG = "ProfileTest";
    // declaring a UserViewModel variable
    UserViewModel userViewModel;
    // declaring an int variable to store the user id
    Integer user_id;
    // declaring text view variables
    TextView name, old_pass, new_pass;
    // declaring button variables
    Button submit, delete;
    // declaring TodoViewModel variable
    private TodoViewModel todoViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // initializing a new object of type TodoViewModel by calling the parent constructor
        todoViewModel = new ViewModelProvider(this).get(TodoViewModel.class);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        // assigning the view id with the declared variables
        name = findViewById(R.id.profile_activity_tv_name);
        old_pass = findViewById(R.id.profile_activity_tv_oldpass);
        new_pass = findViewById(R.id.profile_activity_tv_newpass);
        submit = findViewById(R.id.profile_activity_btn_submit);
        delete = findViewById(R.id.profile_activity_btn_delete);

        // using shared preferences for fetching the value of the mentioned key
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("todo_pref", 0);
        // fetching the user id preference into the declared variable
        user_id = preferences.getInt("user_id", -1);

        // getting the user id using the UserViewModel
        EUser eUser = userViewModel.getUserById(user_id);

        // setting the user name
        name.setText(eUser.getName());

        // when the submit button is clicked
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            // function to update the password
            public void onClick(View v) {
                // if the password fields are empty
                if (old_pass.getText().toString().trim().toString().equals("") ||
                        old_pass.getText().toString().trim().toString().equals("")) {
                    Toast.makeText(ProfileActivity.this, "Password field is empty!", Toast.LENGTH_SHORT).show();
                } else {
                    // if the old password is equal to the password provided
                    if (old_pass.getText().toString().equals(eUser.getPassword())) {
                        // setting the new password
                        eUser.setPassword(new_pass.getText().toString());
                        // updating the user view model
                        userViewModel.update(eUser);
                        // displaying the toast message for successful password update
                        Toast.makeText(ProfileActivity.this, "Password updated!", Toast.LENGTH_SHORT).show();
                    }
                    // displaying the incorrect password message
                    else old_pass.setError("Password is incorrect!");
                }
            }
        });

        // function to delete the user account
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // declaring a new alert dialog box object
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(ProfileActivity.this);
                // setting the message of the dialog box
                alertDialog.setMessage(getString(R.string.alert_delete))
                        // setting the components of the dialog box
                        .setTitle(getString(R.string.app_name))
                        .setIcon(R.mipmap.ic_launcher)
                        .setCancelable(false)
                        .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                            // when clicked, deletes the user, clears preferences and goes to login page
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //deleting the todos associated with this user.
                                List<ETodo> eTodo = todoViewModel.getAll();
                                // displaying the size of the to do list to the console
                                Log.d(TAG, "user_id: " + user_id + "size of etodo" + eTodo.size());
                                // for every to do in the list
                                for (int i = 0; i < eTodo.size(); i++) {
                                    // displaying the to do id with the size in the console log
                                    Log.d(TAG, "todo_id: " + eTodo.get(i).getUser_id() + " Index value: " + i + " size of etodo" + eTodo.size() + "******");
                                    // if the user id matches
                                    if (eTodo.get(i).getUser_id() == user_id) {
                                        Log.d(TAG, "todo_id: " + eTodo.get(i).getUser_id() + " size of etodo" + eTodo.size() + "******");
                                        // deleting the to do from the list
                                        todoViewModel.deleteById(eTodo.get(i));
                                    }
                                }
                                userViewModel.deleteById(eUser);
                                // using editor interface for editing the values of the sharedPreference object
                                SharedPreferences.Editor editor = preferences.edit();
                                // mark in the editor to remove all values from the preferences
                                editor.clear();
                                // committing the changes made in the preference object
                                editor.commit();
                                // defining an intent to start the login activity
                                Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
                                // staring the activity with the intent as the parameter
                                startActivity(intent);
                            }
                        })
                        // option to cancel the delete process
                        .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                // showing the alert dialog box
                alertDialog.show();
            }
        });
    }
}
