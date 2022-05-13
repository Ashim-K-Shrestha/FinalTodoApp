package com.example.todofinalapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.List;

/*
 * class to list down all the to do
 */
public class ListTodoFragment extends Fragment {
    // declaring a constant for the intent extra
    private static final String TAG = "TodoTest";
    // declaring a view variable
    View rootView;
    // declaring a recycler view variable
    RecyclerView rvListTodo;
    // declaring a todo view model variable
    TodoViewModel viewModel;
    // declaring a linear layout manager
    LinearLayoutManager manager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * Inflate fragment layout, selects recycle view,
     * set it's layout to linear using linear layout manager,
     * finally call the updateRV method and listen for ItemTouch using ItemTouchHelper.
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_list_todo, container, false);
        // assigning the id of the recycler view to the variable
        rvListTodo = rootView.findViewById(R.id.list_todo_rv);
        viewModel = new ViewModelProvider(this).get(TodoViewModel.class);

        // initializing a new layout manager object
        manager = new LinearLayoutManager(getActivity());
        // setting the orientation to vertical using linear layout manager class
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        // setting the layout manager
        rvListTodo.setLayoutManager(manager);
        // calling the recycler view updating function
        updateRV();

        /*
        * ItemTouchHelper is a utility class to add swipe to dismiss and drag & drop support to the RecyclerView.
        *
        * SimpleCallback - A simple wrapper to the default Callback which you can construct with drag and swipe directions
        * and this class will handle the flag callbacks.
        *
        * Here, the function added is swiping left or right for deleting a to do
        * */
        new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    /**
                     * when the view is moved
                     *
                     * @param recyclerView
                     * @param viewHolder
                     * @param target
                     * @return
                     */
                    @Override

                    public boolean onMove(@NonNull RecyclerView recyclerView,
                                          @NonNull RecyclerView.ViewHolder viewHolder,
                                          @NonNull RecyclerView.ViewHolder target) {
                        return false;
                    }

                    /**
                     * When the view is swiped
                     *
                     * @param viewHolder
                     * @param direction
                     */
                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                        // storing all the to dos in a list
                        List<ETodo> todoList = viewModel.getAllTodos().getValue();
                        // defining an adaptor for the to do list
                        TodoAdaptor adaptor = new TodoAdaptor(todoList);
                        // fetching the view's position using viewHolder class
                        ETodo todo = adaptor.getTodoAt(viewHolder.getAdapterPosition());
                        // deleting the to do
                        viewModel.deleteById(todo);
                    }
                    // attaching the list to the recycler view
                }).attachToRecyclerView(rvListTodo);


        // returning the root view inflater with all the fragments
        return rootView;
    }

    /*
     * function to update the recycler view.
     *
     * fetch all the todos using getAllTodos function and observe them using observer.
     *
     * The observer helps to notify about the changes that occurred.
     */
    void updateRV() {
        // fetching and observing all the to do
        viewModel.getAllTodos().observe(this, new Observer<List<ETodo>>() {
            @Override
            // function to access or modify the preference data returned by the to do
            public void onChanged(List<ETodo> eTodos) {
                SharedPreferences preferences = getContext().getSharedPreferences("todo_pref", 0);
                // getting the data for the user id and storing into a variable
                int user_id = preferences.getInt("user_id", 0);

                // displaying the user id in the console
                Log.d(TAG, "user_id: " + user_id + "size of etodo" + eTodos.size());
                // for every to do
                for (int i = 0; i < eTodos.size(); i++) {
//                    Log.d(TAG, "********user id of this todo is: " + eTodos.get(i).getUser_id() + " and title: " + eTodos.get(i).getTitle() + "***********");
                    // if the user id is not equals to the fetched user id
                    if (eTodos.get(i).getUser_id() != user_id) {
//                        Log.d(TAG, "user id of this todo is: " + eTodos.get(i).getUser_id() + " and title: " + eTodos.get(i).getTitle() + "user_id we are checking with : " + user_id );
                        // removing the to do
                        eTodos.remove(i);
                        i--;
                    }
                }
                // initializing a new adaptor object
                TodoAdaptor adaptor = new TodoAdaptor(eTodos);
                rvListTodo.setAdapter(adaptor);
            }
        });
    }

    // creating a class that extends the recycler view
    private class TodoHolder extends RecyclerView.ViewHolder {
        // declaring textView variables
        TextView title, date, desc;
        // declaring checkbox variable
        CheckBox checkBox;
        // declaring to do adapter variable
        TodoAdaptor adaptor;


        /**
         * @param inflater
         * @param parent
         */
        public TodoHolder(LayoutInflater inflater, ViewGroup parent) {
            // invoking the inflater class object from parent constructor
            super(inflater.inflate(R.layout.list_item_todo, parent, false));
            // assigning the id of the view to the variables
            title = itemView.findViewById(R.id.list_item_todo_tv_title);
            date = itemView.findViewById(R.id.list_item_todo_tv_text);
            desc = itemView.findViewById(R.id.list_item_todo_tv_desc);
            checkBox = itemView.findViewById(R.id.list_item_todo_cb_iscomplete);

            // initializing a new adaptor object for fetching the values of all to do
            adaptor = new TodoAdaptor(viewModel.getAllTodos().getValue());

            // when the title is clicked
            title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadUpdateItem();
                }
            });
            // when the date is clicked
            date.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadUpdateItem();
                }
            });

            // when the checkbox is clicked
            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                // function to fetch the position of the
                public void onClick(View v) {
                    // Returns the Adapter position of the item represented by this ViewHolder with
                    // respect to the RecyclerView.Adapter that bound it.
                    ETodo todo = adaptor.getTodoAt(getAdapterPosition());
                    // setting the to do as completed
                    todo.setCompleted(!todo.isCompleted());
                    // updating the to do list
                    viewModel.update(todo);
                }
            });
        }

        // function to load the to do after update
        void loadUpdateItem() {
            // storing the position of adaptor into a variable
            int i = getAdapterPosition();
            // fetching the to do at the position of the adaptor using the ETodo class
            ETodo todo = adaptor.getTodoAt(i);
            // initializing an intent to start a new activity
            Intent intent = new Intent(getActivity(), EditActivity.class);
            // declaring an intent extra with the to do id as the key
            intent.putExtra("TodoId", todo.getId());
            // starting the activity with the intent
            startActivity(intent);
            // displaying a toast message
            Toast.makeText(getContext(), "Update Item: " + todo.getId(), Toast.LENGTH_LONG).show();
        }

        // function to set the content to each components
        public void bind(ETodo todo) {
            // defining the date format
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            // setting the title of to do
            title.setText(todo.getTitle());
            // setting the description of to do
            desc.setText(todo.getDescription());
            // setting the date of to do
            date.setText(sdf.format(todo.getTodoDate()));
            // setting the checkbox as completed
            checkBox.setChecked(todo.isCompleted());
        }
    }

    // function to bind all the to do components
    private class TodoAdaptor extends RecyclerView.Adapter<TodoHolder> {
        // declaring a list with all the to do
        List<ETodo> eTodoList;

        // assigning the to do lists into a new list variable
        public TodoAdaptor(List<ETodo> todoList) {
            eTodoList = todoList;
        }

        @NonNull
        @Override
        // function to create a new recycler view from the activity
        public TodoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new TodoHolder(layoutInflater, parent);
        }

        @Override
        // function called by the recycler view to display the data at the specified position
        public void onBindViewHolder(@NonNull TodoHolder holder, int position) {
            // fetching the position of the to do list
            ETodo todo = eTodoList.get(position);
            // creating a new linear layout for the new view
            LinearLayout layout = (LinearLayout) ((ViewGroup) holder.title.getParent());

            // switch case statement for setting the bg color of the priority type
            switch (todo.getPriority()) {
                case 1:
                    layout.setBackgroundColor(getResources().getColor(R.color.color_high));
                    break;
                case 2:
                    layout.setBackgroundColor(getResources().getColor(R.color.color_medium));
                    break;
                case 3:
                    layout.setBackgroundColor(getResources().getColor(R.color.color_low));
                    break;
            }
            // binding the to do views
            holder.bind(todo);
        }

        @Override
        // function to return the size of the list
        public int getItemCount() {
            return eTodoList.size();
        }

        // function to return the position of the to do list
        public ETodo getTodoAt(int position) {
            return eTodoList.get(position);
        }
    }
}
