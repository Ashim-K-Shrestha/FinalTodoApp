package com.example.todofinalapp;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/*
 * Defines the database configuration and serves as the app's main access point to the persisted data
 */
@Database(entities = {ETodo.class, EUser.class}, version = 1, exportSchema = false)
public abstract class TodoRoomDatabase extends RoomDatabase {
    // declaring a TodoRoomDatabase variable
    public static TodoRoomDatabase INSTANCE;
    // the callback database for the room database
    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(INSTANCE).execute();
        }

    };

    // function to create an instance in the room db
    public static TodoRoomDatabase getDatabase(Context context) {
        // if the instance is null
        if (INSTANCE == null) {
            // synchronized means only one method can use the thread at a time
            synchronized (TodoRoomDatabase.class) {
                if (INSTANCE == null) {
                    // databaseBuilder creates a RoomDatabase.Builder for a persistent database.
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            TodoRoomDatabase.class,
                            "todo.db")
                            // disabling the main thread query check for room
                            .allowMainThreadQueries()
                            // allows Room to destructively recreate database tables if Migrations that would migrate
                            // old database schemas to the latest schema version are not found
                            .fallbackToDestructiveMigration()
                            // adds a RoomDatabase.Callback to this database
                            .addCallback(roomCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * The database class must define an abstract method that has zero arguments
     * and returns an instance of the DAO class, for each DAO class.
     *
     * @return TodoDao
     */

    // creating an abstract method for TodoDao interface
    public abstract TodoDao mTodoDao();

    // creating an abstract method for UserDao interface
    public abstract UserDao mUserDao();

    // class for populating both to do and users database
    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        // declaring TodoDao variable
        private TodoDao todoDao;
        // declaring UserDao variable
        private UserDao userDao;

        // function to populate both the dbs
        private PopulateDbAsyncTask(TodoRoomDatabase db) {
            todoDao = db.mTodoDao();
            userDao = db.mUserDao();
        }

        @Override
        // This step is used to perform background computation that can take a long time
        protected Void doInBackground(Void... voids) {
            /**
             * Inserting data in todo_table
             */
            // initializing a new object of class date
            Date todoDate = new Date();
            // try catch statement
            try {
                // defining the date format
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                todoDate = format.parse("2020/11/22");
            }
            // defining exception
            catch (ParseException ex) {
                ex.printStackTrace();
            }

            // inserting into the todo db
            todoDao.insert(new ETodo("Get some milk!", "Milk costs around 1$ per litre. So, buy three litres!",
                    todoDate, 1, false, 0));
            todoDao.insert(new ETodo("Go jogging with you friends.", "Jogging is a good cardio and is also beneficial for your mental health!",
                    todoDate, 2, false, 0));
            todoDao.insert(new ETodo("Get some milk!", "Milk costs around 1$ per litre. So, buy three litres!",
                    todoDate, 3, false, 0));

            /*
             * Inserting data in user_table
             */
            userDao.insert(new EUser(0, "Bishant", "123"));
            return null;
        }
    }
}

