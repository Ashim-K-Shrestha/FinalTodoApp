package com.example.todofinalapp;

import android.app.Application;
import android.os.AsyncTask;

import java.util.List;

// creating a public UserRepository class
public class UserRepository {
    // declaring a UserDao variable
    private UserDao mUserDao;

    // function to fetch all the users from the room database
    public UserRepository(Application app) {
        TodoRoomDatabase database = TodoRoomDatabase.getDatabase(app);
        mUserDao = database.mUserDao();
    }

    // function to insert a user
    public void insert(EUser eUser) {
        mUserDao.insert(eUser);
    }

    // function to update a user
    public void update(EUser eUser) {
        new updateUserAysncTask(mUserDao).execute(eUser);
    }

    // function to delete a particular user
    public void deleteById(EUser eUser) {
        new deleteUserAysncTask(mUserDao).execute(eUser);
    }

    // function to retrieve a user
    public EUser getUserById(int id) {
        return mUserDao.getUserById(id);
    }

    // function to fetch all the users from the list
    public List<EUser> getAllUsers() {
        return mUserDao.getAllUsers();
    }

    /*
     * an asynchronous task is defined by a computation that runs on a background thread and
     * whose result is published on the UI thread. An asynchronous task is defined by 3 generic types,
     * called Params, Progress and Result, and 4 steps, called onPreExecute, doInBackground, onProgressUpdate
     * and onPostExecute.
     */
    // function to update a user into the room db
    private static class updateUserAysncTask extends AsyncTask<EUser, Void, Void> {
        private UserDao mUserDao;

        private updateUserAysncTask(UserDao userDao) {
            mUserDao = userDao;
        }

        @Override
        // This step is used to perform background computation (update) that can take a long time
        protected Void doInBackground(EUser... user) {
            mUserDao.update(user[0]);
            return null;
        }
    }

    // function to delete a user from the room database
    private static class deleteUserAysncTask extends AsyncTask<EUser, Void, Void> {
        private UserDao mUserDao;

        private deleteUserAysncTask(UserDao userDao) {
            mUserDao = userDao;
        }

        @Override
        // This step is used to perform background computation (update) that can take a long time
        protected Void doInBackground(EUser... user) {
            mUserDao.deleteById(user[0]);
            return null;
        }
    }
}

