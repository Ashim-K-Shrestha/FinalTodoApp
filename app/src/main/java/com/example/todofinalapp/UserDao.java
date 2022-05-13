package com.example.todofinalapp;

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
public interface UserDao {

    // inserting a single query
    @Insert
    void insert(EUser todo);

    // query to select particular users
    @Query("SELECT * FROM user_table WHERE user_id=:id")
    EUser getUserById(int id);

    // query to delete a user
    @Delete
    void deleteById(EUser eUser);

    // query to fetch all users
    @Query("SELECT * FROM user_table")
    List<EUser> getAllUsers();

    // query to update a user
    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(EUser... user);
}

