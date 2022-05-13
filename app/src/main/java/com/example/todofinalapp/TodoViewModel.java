package com.example.todofinalapp;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

// child class extending parent class to inherit all the parent properties
public class TodoViewModel extends AndroidViewModel {
    // declaring TodoRepository variable
    private TodoRepository repository;
    // declaring a liveData class list array

    // liveData is an observable data holder class. Unlike a regular observable,
    // liveData is lifecycle-aware, meaning it respects the lifecycle of other app components,
    // such as activities, fragments, or services
    private LiveData<List<ETodo>> allTodos;

    // function to instantiate a new object for the TodoRepository and fetch the to do into the repository
    public TodoViewModel(@NonNull Application application) {
        super(application);
        repository = new TodoRepository(application);
        allTodos = repository.getAllTodoList();
    }

    // function to return all the to do
    public LiveData<List<ETodo>> getAllTodos() {
        return allTodos;
    }

    // function to insert a to do into the TodoRepository
    public void insert(ETodo todo) {
        repository.insert(todo);
    }

    // function to update a to do in the TodoRepository
    public void update(ETodo todo) {
        repository.update(todo);
    }

    // function to update completed to do in the TodoRepository
    public void updateIsCompleted(int id, boolean is_completed) {
        repository.updateIsCompleted(id, is_completed);
    }

    // function to delete a particular to do in the TodoRepository
    public void deleteById(ETodo eTodo) {
        repository.deleteById(eTodo);
    }

    // function to delete all to do from the TodoRepository
    public void deleteAll(int id) {
        repository.deleteAll(id);
    }

    // function to list all the to do in the TodoRepository
    public List<ETodo> getAll() {
        return repository.getAll();
    }

    // function to fetch a to do from the TodoRepository
    public ETodo getTodoById(int id) {
        return repository.getTodoById(id);
    }

    // function to delete all completed to do from the TodoRepository
    public void deleteAllCompleted(int id, boolean is_completed) {
        repository.deleteAllCompleted(id, is_completed);
    }
}

