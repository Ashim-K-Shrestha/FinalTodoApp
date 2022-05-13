package com.example.todofinalapp;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

// creating a public class
public class TodoRepository {
    // declaring a TodoDao variable
    private TodoDao mTodoDAO;
    // declaring a ETodo list array
    private LiveData<List<ETodo>> allTodoList;

    // function to fetch all the to do from the room database
    public TodoRepository(Application application){
        // fetching the application's database
        TodoRoomDatabase database = TodoRoomDatabase.getDatabase(application);
        // setting the database into the variable
        mTodoDAO = database.mTodoDao();
        // fetching all the to do into the variable
        allTodoList = mTodoDAO.getAllTodos();
    }

    // function to return the data from the database
    public TodoDao getmTodoDAO() {
        return mTodoDAO;
    }

    // setting the DAO
    public void setmTodoDAO(TodoDao mTodoDAO) {
        this.mTodoDAO = mTodoDAO;
    }

    // function to display all the to do
    public LiveData<List<ETodo>> getAllTodoList() {
        return allTodoList;
    }

    // setter function to set the to do list
    public void setAllTodoList(LiveData<List<ETodo>> allTodoList) {
        this.allTodoList = allTodoList;
    }

    // function to insert a to do
    public void insert(ETodo eTodo){
        new insertTodoAysncTask(mTodoDAO).execute(eTodo);
    }

    // function to update a to do
    public void update(ETodo eTodo) {
        new updateTodoAysncTask(mTodoDAO).execute(eTodo);
    }

    // function to update the completed to do
    public void updateIsCompleted(int id, boolean is_completed) {mTodoDAO.updateIsComplete(id, is_completed);}

    // function to delete the to do
    public void deleteById(ETodo eTodo){
        new deleteByIdTodoAysnc(mTodoDAO).execute(eTodo);
    }

    // function to delete all the to do
    public void deleteAll(int id) { mTodoDAO.deleteAll(id);}

    // getter function to fetch all the to do
    public List<ETodo> getAll() { return  mTodoDAO.getAll();}

    // function to delete all completed to do
    public void deleteAllCompleted(int id, boolean is_completed) {
        mTodoDAO.deleteAllCompleted(id, is_completed);
    }

    // getter function to fetch a particular to do using id
    public ETodo getTodoById(int id){
        return mTodoDAO.getTodoById(id);
    }

    /*
    * an asynchronous task is defined by a computation that runs on a background thread and
    * whose result is published on the UI thread. An asynchronous task is defined by 3 generic types,
    * called Params, Progress and Result, and 4 steps, called onPreExecute, doInBackground, onProgressUpdate
    * and onPostExecute.
    */
    // function to insert a to do into the room db
    private static class insertTodoAysncTask extends AsyncTask<ETodo, Void, Void> {
        private TodoDao mTodoDao;
        private insertTodoAysncTask(TodoDao todoDAO){
            mTodoDao=todoDAO;
        }

        @Override
        // This step is used to perform background computation that can take a long time
        protected Void doInBackground(ETodo... eTodos) {
            mTodoDao.insert(eTodos[0]);
            return null;
        }
    }

    // function to update a to do in the DAO
    private static class updateTodoAysncTask extends AsyncTask<ETodo, Void, Void> {
        private TodoDao mTodoDao;
        private updateTodoAysncTask(TodoDao todoDAO){
            mTodoDao=todoDAO;
        }

        @Override
        // This step is used to perform background computation that can take a long time
        protected Void doInBackground(ETodo... eTodos) {
            mTodoDao.update(eTodos[0]);
            return null;
        }
    }

    // function to delete a to do from the DAO
    private static class deleteByIdTodoAysnc extends AsyncTask<ETodo, Void, Void>{
        private TodoDao mTodoDao;
        private deleteByIdTodoAysnc(TodoDao todoDAO){
            mTodoDao=todoDAO;
        }

        @Override
        // This step is used to perform background computation that can take a long time
        protected Void doInBackground(ETodo... eTodos) {
            mTodoDao.deleteById(eTodos[0]);
            return null;
        }
    }
}
