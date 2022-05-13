package com.example.todofinalapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

public class EditActivity extends AppCompatActivity {

    // declaring a fragment manager for the app fragments
    FragmentManager fragmentManager;

    // declaring a fragment variable
    Fragment fragment;

    @Override

    // function to execute the existing code with the user defined codes
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setting the layout of the activity_edit activity
        setContentView(R.layout.activity_edit);

        /*
         * fragment manager helps in managing the fragments belonging to an app.
         *
         * getSupportFragmentManager() returns the fragment manager for interacting with
         * the activity's fragments.
         * */
        fragmentManager = getSupportFragmentManager();

        // instantiating object of EditTodoFragment class
        fragment = new EditTodoFragment();

        /*
         * beginTransaction() carries out the editing operations on the fragments of the fragment manager.
         *
         * here, the id edit_fragment_container is replaced with fragment.
         * */
        fragmentManager.beginTransaction()
                .replace(R.id.edit_fragment_container, fragment)
                .commit();
    }
}
