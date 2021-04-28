// Group 2
// Wei Xu(101059762)

package com.wx.parking.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.wx.parking.database.models.User;
import com.wx.parking.repositories.UserRepository;

public class UserViewModel extends AndroidViewModel {
    private final UserRepository userRepository;

    public UserViewModel(@NonNull Application application) {
        super(application);
        userRepository = new UserRepository(application);
    }

    public User getUser(String email, String password) {
        return this.userRepository.getUser(email, password);
    }

    public void insertUserItem(User user) {
        userRepository.insertUserItem(user);
    }

    public void updateUserItem(User user) {
        userRepository.updateUserItem(user);
    }

    public void deleteUserItem(User user) {
        userRepository.deleteUserItem(user);
    }

}
