package com.example.todofinalapp;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.Date;

/*
 * Room is an ORM (Object Relational Mapper) for android that maps java objects with
 * the database objects.
 *
 * It has three components:
 * Entity - represents a table of the db
 * Dao - methods that access the db
 * Database - has the db holders
 */
// defining the table name
@Entity(tableName = "todo_table")
public class ETodo {
    // defining the pk for the table
    // autoGenerate true generates a unique key in sqlite
    @PrimaryKey(autoGenerate = true)
    private int id;

    // not nullable
    @NonNull
    // defining a column for the table
    @ColumnInfo(name = "title")
    private String title;

    // defining a column for the table
    @ColumnInfo(name = "description")
    private String description;

    // type converters converts custom data types into known types for the db
    @TypeConverters({DateConverter.class})
    @ColumnInfo(name = "todo_date")
    private Date todoDate;

    // defining a column for the table
    @ColumnInfo(name = "priority")
    private int priority;

    // defining a column for the table
    @ColumnInfo(name = "is_completed")
    private boolean isCompleted;

    // defining a column for the table
    @ColumnInfo(name = "user_id")
    private int user_id;

    // field will not persist in the room db
    @Ignore
    public ETodo() {
    }

    // assigning the parameters with the components
    public ETodo(@NonNull String title, String description, Date todoDate, int priority, boolean isCompleted, int user_id) {
        this.title = title;
        this.description = description;
        this.todoDate = todoDate;
        this.priority = priority;
        this.isCompleted = isCompleted;
        this.user_id = user_id;
    }

    // getter function to get the user id
    public int getUser_id() {
        return user_id;
    }

    // setter function to set the user id
    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    // getter function to get the id
    public int getId() {
        return id;
    }

    // setter function to set the id
    public void setId(int id) {
        this.id = id;
    }

    // getter function to get the title
    @NonNull
    public String getTitle() {
        return title;
    }

    // setter function to set the title
    public void setTitle(@NonNull String title) {
        this.title = title;
    }

    // getter function to get the description
    public String getDescription() {
        return description;
    }

    // setter function to set the description
    public void setDescription(String description) {
        this.description = description;
    }

    // getter function to get the date
    public Date getTodoDate() {
        return todoDate;
    }

    // setter function to set the date
    public void setTodoDate(Date todoDate) {
        this.todoDate = todoDate;
    }

    // getter function to get the priority
    public int getPriority() {
        return priority;
    }

    // setter function to set the priority
    public void setPriority(int priority) {
        this.priority = priority;
    }

    // function to return the boolean value
    public boolean isCompleted() {
        return isCompleted;
    }

    // function to set the boolean value
    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }
}

