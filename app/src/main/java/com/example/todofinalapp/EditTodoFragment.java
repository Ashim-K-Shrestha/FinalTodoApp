package com.example.todofinalapp;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

// creating a class extending Fragment parent class
public class EditTodoFragment extends Fragment {

    // declaring variable for high priority to do activity
    public static final int HIGH_PRIORITY = 1;
    // declaring variable for medium priority to do activity
    public static final int MEDIUM_PRIORITY = 2;
    // declaring variable for low priority to do activity
    public static final int LOW_PRIORITY = 3;
    // declaring variable to display the user id in the activity log for debugging
    private static final String TAG = "TodoTest";

    // declaring a view variable
    View rootView;
    // declaring editText variables
    EditText todoTitle, todoDescription, txtDate;
    // declaring a radio group variable
    RadioGroup todoImp;
    // declaring buttons variable
    Button SaveBtn, CancelBtn;
    // declaring checkbox variable
    CheckBox checked;
    // declaring error boolean variable
    Boolean error;
    // declaring an id variable for intent extra
    int todoId;

    @Override
    /*
     * LayoutInflator class is used to instantiate an object to the contents of an xml file.
     * It creates a view or layout object from the xml layouts.
     * */
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        /*
         * here, rootview is the xml file, fragment_edit_todo is the parent layout where it will be added
         * and false means not attaching yet as it could be null.
         * */
        rootView = inflater.inflate(R.layout.fragment_edit_todo, container, false);

        // assigning the id of the views with the variables and adding in the root view
        todoTitle = rootView.findViewById(R.id.edit_fragment_txt_name);
        todoDescription = rootView.findViewById(R.id.edit_fragment_txt_description);
        txtDate = rootView.findViewById(R.id.edit_fragment_txt_date);
        todoImp = rootView.findViewById(R.id.edit_fragment_rg_priority);
        checked = rootView.findViewById(R.id.edit_fragment_chk_complete);
        SaveBtn = rootView.findViewById(R.id.edit_fragment_btn_save);
        CancelBtn = rootView.findViewById(R.id.edit_fragment_btn_cancel);

        // running to do updating function
        loadUpdateData();

        /*
         * Interface definition for a callback to be invoked when a touch event is dispatched to this view
         * */
        txtDate.setOnTouchListener(new View.OnTouchListener() {
            @Override
            /*
             * Called when a touch event is dispatched to a view. This allows listeners to get a chance to
             * respond before the target view.
             * */
            public boolean onTouch(View v, MotionEvent event) {
                // when the user presses down on the screen where date view is located
                if (event.getAction() == MotionEvent.ACTION_DOWN)
                    // invoking the function to display the calendar
                    DisplayTestDate();
                return false;

            }
        });

        // function for saving the updates made
        SaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveTodo();
            }
        });

        // function for canceling the update
        CancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowAlertCancel();
            }
        });

        // returning the root view activity that has all the fragments
        return rootView;
    }

    // function to display the calendar
    void DisplayTestDate() {
        // getting the instance of calendar
        Calendar calendar = Calendar.getInstance();
        // declaring a variable for the day, month and year
        int cDay = calendar.get(Calendar.DAY_OF_MONTH);
        int cMonth = calendar.get(Calendar.MONTH);
        int cYear = calendar.get(Calendar.YEAR);

        // instantiating the date picker dialog box
        DatePickerDialog pickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            // function for assigning the picked date into the declared variables
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                txtDate.setText(year + "-" + month + "-" + dayOfMonth);
            }
        }, cYear, cMonth, cDay);

        // displaying the dialog box for date
        pickerDialog.show();
    }

    // function to display the alert dialog box when canceling the update process
    void ShowAlertCancel() {
        // instantiating an alert dialog box
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
        // defining the components of the alert dialog box
        alertDialog.setMessage(getString(R.string.alert_cancel))
                .setTitle(getString(R.string.app_name))
                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(false)
                // setting the function when the 'ok' button is clicked
                .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    // using an intent to start a new activity
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        startActivity(intent);
                    }
                })
                // setting the function for the cancel button
                .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        // displaying the alert dialog box
        alertDialog.show();
    }

    // function to edit the existing to do activity
    void loadUpdateData() {
        // storing the activity in the variable with the intent
        todoId = getActivity().getIntent().getIntExtra("TodoId", -1);
        /*
         * viewModel class helps data to survive configuration changes like screen rotations.
         *
         * The viewModelProvider gives the TodoViewModel.
         * */
        TodoViewModel viewModel = new ViewModelProvider(this).get(TodoViewModel.class);

        // if the id is not equal to -1 then
        if (todoId != -1) {
            // setting the button text
            SaveBtn.setText("Update");
            // retrieving the to do using the id for update
            ETodo todo = viewModel.getTodoById(todoId);
            // setting the updated to do title
            todoTitle.setText(todo.getTitle());
            // setting the updated to do description
            todoDescription.setText(todo.getDescription());
            // defining the date format
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

            // setting th updated date for the to do
            txtDate.setText(format.format(todo.getTodoDate()));
            // switch case for the priority of the to do
            switch (todo.getPriority()) {
                case 1:
                    todoImp.check(R.id.edit_fragment_rb_high);
                    break;
                case 2:
                    todoImp.check(R.id.edit_fragment_rb_medium);
                    break;
                case 3:
                    todoImp.check(R.id.edit_fragment_rb_low);
                    break;
            }
            // setting the checkbox as checked
            checked.setChecked(todo.isCompleted());
        }
    }

    // function to save the to do
    void SaveTodo() {
        // setting error boolean value to false
        error = false;
        // instantiating a new object of ETodo class
        ETodo eTodo = new ETodo();
        // Instantiating a new date object
        Date todoDate = new Date();
        // declaring variable for checked priority with assigned value
        int checkedPriority = -1;
        // declaring variable for the priority with assigned value
        int priority = 0;
        // try statement
        try {
            // initializing a new date format
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            // setting the date value to the object
            todoDate = format.parse(txtDate.getText().toString());
            // catch statement for handling errors
        } catch (ParseException ex) {
            ex.printStackTrace();
            // setting the error
            txtDate.setError("Invalid date!");
        }
        // fetching the priority of the activity from the radio buttons
        checkedPriority = todoImp.getCheckedRadioButtonId();

        // switch case for setting the selected priority
        switch (checkedPriority) {
            case R.id.edit_fragment_rb_high:
                priority = HIGH_PRIORITY;
                break;
            case R.id.edit_fragment_rb_medium:
                priority = MEDIUM_PRIORITY;
                break;
            case R.id.edit_fragment_rb_low:
                priority = LOW_PRIORITY;
                break;
        }

        // using shared preferences for fetching the value of the mentioned key
        SharedPreferences preferences = getContext().getSharedPreferences("todo_pref", 0);
        // fetching the user id into the variable
        int user_id = preferences.getInt("user_id", 0);

        // debugging the user id
        Log.d(TAG, "*** user_id: " + user_id + " ****");

        // if the title is null or description is null or date is null or the priority is not set
        if (todoTitle.getText().toString().trim().equals("") || todoDescription.getText().toString().trim().equals("")
                || txtDate.getText().toString().trim().equals("") || checkedPriority == -1) {
            // displaying the error toast message
            Toast.makeText(getContext(), "Fill all the text fields and select priority!", Toast.LENGTH_SHORT).show();
            // setting boolean value to true
            error = true;
        } else {

            // setting the respective components for the to do
            eTodo.setTitle(todoTitle.getText().toString());
            eTodo.setDescription(todoDescription.getText().toString());
            eTodo.setTodoDate(todoDate);
            eTodo.setPriority(priority);
            eTodo.setCompleted(checked.isChecked());
            eTodo.setUser_id(user_id);
        }

        // initializing object for the TodoViewModel class
        TodoViewModel viewModel = new ViewModelProvider(this).get(TodoViewModel.class);

        // if there is no error and if priority is set
        if (!error) {
            if (todoId != -1) {
                // setting the id to the to do
                eTodo.setId(todoId);
                // updating the to do
                viewModel.update(eTodo);
                // else inserting into the to do
            } else viewModel.insert(eTodo);

            // displaying a success toast message
            Toast.makeText(getActivity(), "Todo Saved", Toast.LENGTH_SHORT).show();
            // initializing an intent to start another activity
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
        }
    }
}
