package com.example.todofinalapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

/*
 * The first activity that is displayed after the splash activity and it is the container that
 * hosts the ListTodoFragment activity.
 *
 * The floating button at the button directs to the EditActivity activity.
 */

// MainActivity extends to parent class and inherits all the parent properties
public class MainActivity extends AppCompatActivity {
    // declaring a constant for the intent extra
    private static final String TAG = "UserList";
    // declaring a fragment manager variable
    FragmentManager fragmentManager;
    // declaring a fragment variable
    Fragment fragment;
    // declaring a shared preferences variable
    SharedPreferences preferences;
    // declaring a floatingActionButton variable
    FloatingActionButton floatingActionButton;
    // declaring a TodoViewModel variable
    private TodoViewModel todoViewModel;
    // declaring a UserViewModel variable
    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initializing a new object of type TodoViewModel by calling the parent constructor
        todoViewModel = new ViewModelProvider(this).get(TodoViewModel.class);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        // using shared preferences for fetching the value of the mentioned key
        preferences = getApplicationContext().getSharedPreferences("todo_pref", 0);
        // fetching the user id value into the variable
        int user_id = preferences.getInt("user_id", 0);
        // fetching the user using the view model
        EUser eUser = userViewModel.getUserById(user_id);

        // assigning the view id with the variable
        Toolbar toolbar = findViewById(R.id.toolbar);
        // function to set the toolbar as the app bar of the activity
        setSupportActionBar(toolbar);
        // assigning the view id with the variable
        TextView toolbarTitle = (TextView) findViewById(R.id.toolbarTitle);
        // fetching the user name into the title variable
        toolbarTitle.setText(eUser.getName());

        // when the tool bar is clicked
        toolbarTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            // function to start the profile activity using an intent
            public void onClick(View v) {
                // defining an intent to start a new activity
                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                // starting the activity with the intent as the parameter
                startActivity(intent);
            }
        });
        // setting the icon before the toolbar title
        getSupportActionBar().setIcon(R.drawable.ic_account);

        // storing the users list into a list
        List<EUser> users = userViewModel.getAllUsers();

        // for every users, display the names into the console
        for (EUser user : users) {
            Log.d(TAG, "onCreate: " + user.getName());
        }

        // assigning the view id with the defined variable
        floatingActionButton = findViewById(R.id.btn_activity_main_floating);

        // when the floating button is clicked
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            // function to start the editActivity activity
            public void onClick(View view) {
                // declaring an intent to start a new activity i.e. EditActivity
                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                // starting the activity with the intent as the paremeter
                startActivity(intent);
            }
        });
        // calling the fragment manager
        fragmentManager = getSupportFragmentManager();
        // creating a new fragment
        fragment = new ListTodoFragment();
        // starting a series of edit operations on the Fragments associated with this FragmentManager
        fragmentManager.beginTransaction()
                .replace(R.id.list_todo_container, fragment)
                .commit();
    }

    @Override
    //
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    /**
     * @param item
     * @return
     */
    @Override
    // function to delete the to do
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // fetching the user id using preference
        int user_id = preferences.getInt("user_id", -1);
        // switch case statement
        switch (item.getItemId()) {
            // to delete all the to do associated with the user
            case R.id.mnu_delete_all:
                // deleting all to do using the view model
                todoViewModel.deleteAll(user_id);
                // toast message to display the message
                Toast.makeText(getApplicationContext(), "All todos deleted!", Toast.LENGTH_LONG).show();
                break;

            case R.id.mnu_delete_cpmpleted:
                // if the user id is not equals to -1
                if (user_id != -1) {
                    // calling function to delete all the completed to do
                    todoViewModel.deleteAllCompleted(user_id, true);
                } else {
                    // toast to display the failure message
                    Toast.makeText(getApplicationContext(), "Failed to delete!", Toast.LENGTH_LONG).show();
                }
                break;

            case R.id.mnu_logout:
                // using editor interface for editing the values of the sharedPreference object
                SharedPreferences.Editor editor = preferences.edit();
                // mark in the editor to remove all values from the preferences
                editor.clear();
                // committing the changes made in the preference object
                editor.commit();
                // defining an intent to start the login activity
                Intent intent = new Intent(this, LoginActivity.class);
                // staring the activity with the intent as the parameter
                startActivity(intent);
                // closing the activity
                finish();
                break;
        }
        // returning the parent class function
        return super.onOptionsItemSelected(item);
    }
}