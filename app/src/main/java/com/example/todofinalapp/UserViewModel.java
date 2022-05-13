package com.example.todofinalapp;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import java.util.List;

// child class extending to parent class to inherit the parent properties
public class UserViewModel extends AndroidViewModel {
    // declaring a UserRepository variable
    UserRepository repository;

    // function to instantiate a new object for the UserRepository
    public UserViewModel(@NonNull Application application) {
        super(application);
        repository = new UserRepository(application);
    }

    // function to insert a user into the repository
    public void insert(EUser user) {
        repository.insert(user);
    }

    // function to update a user in the repository
    public void update(EUser user) {
        repository.update(user);
    }

    // function to delete a particular user from the repository
    public void deleteById(EUser user) {
        repository.deleteById(user);
    }

    // function to fetch a user from the repository
    public EUser getUserById(int id) {
        return repository.getUserById(id);
    }

    // function to fetch all the users from the repository
    public List<EUser> getAllUsers() {
        return repository.getAllUsers();
    }
}

