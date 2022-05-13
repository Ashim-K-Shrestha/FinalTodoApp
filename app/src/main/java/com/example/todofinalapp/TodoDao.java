package com.example.todofinalapp;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

/*
 * DAO (Data Access Objects) define the methods that are used to access the database
 * */
@Dao
public interface TodoDao {
    // inserting a single query
    @Insert
    void insert(ETodo todo);

    // query to delete the particular to do using user id
    @Query("DELETE FROM todo_table WHERE user_id = :id")
    void deleteAll(int id);

    // query to delete all the to do that belong to a user and are completed
    @Query("DELETE FROM todo_table WHERE user_id=:id AND is_completed = :is_completed")
    void deleteAllCompleted(int id, boolean is_completed);

    @Delete
    void deleteById(ETodo todo);

    // selecting a particular to do
    @Query("SELECT * FROM todo_table WHERE id=:id")
    ETodo getTodoById(int id);

    // query to update the to do
    // onConflict gives the constraint violation errors
    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(ETodo... todo);

    // query to update the to do as completed
    @Query("UPDATE todo_table SET is_completed = :is_completed WHERE id = :id")
    void updateIsComplete(int id, boolean is_completed);

    /**
     * Observable queries are read operations that emit new values whenever there are changes
     * to any of the tables that are referenced by the query.
     *
     * @return list of todos in desc
     */
    @Query("SELECT * FROM todo_table ORDER BY todo_date, priority desc")
    LiveData<List<ETodo>> getAllTodos();

    @Query("SELECT * FROM todo_table ORDER BY todo_date, priority desc")
    List<ETodo> getAll();
}

