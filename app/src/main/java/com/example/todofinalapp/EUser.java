package com.example.todofinalapp;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

// defining the table name for users
@Entity(tableName = "user_table")
public class EUser {
    // declaring the primary key
    @PrimaryKey
    private int user_id;

    // not nullable column field
    @NonNull
    @ColumnInfo(name = "name")
    private String name;

    // not nullable column field
    @NonNull
    @ColumnInfo(name = "password")
    private String password;

    // not persisting component of the db
    @Ignore
    public EUser() {
    }

    // assigning the parameters with the components
    public EUser(int user_id, @NonNull String name, String password) {
        this.user_id = user_id;
        this.name = name;
        this.password = password;
    }

    // non persisting components
    @Ignore
    public EUser(@NonNull String name, String password) {
        this.name = name;
        this.password = password;
    }

    // getter function to get the user id
    public int getUser_id() {
        return user_id;
    }

    // setter function to set the user id
    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    // getter function to get the user name
    @NonNull
    public String getName() {
        return name;
    }

    // setter function to set the user name
    public void setName(@NonNull String name) {
        this.name = name;
    }

    // getter function to get the password
    @NonNull
    public String getPassword() {
        return password;
    }

    // setter function to set the password
    public void setPassword(@NonNull String password) {
        this.password = password;
    }
}



