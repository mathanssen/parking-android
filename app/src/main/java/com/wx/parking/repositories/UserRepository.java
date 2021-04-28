// Group 2
// Wei Xu(101059762)

package com.wx.parking.repositories;

import android.app.Application;

import com.wx.parking.database.MyDatabase;
import com.wx.parking.database.dao.UserDao;
import com.wx.parking.database.models.User;

public class UserRepository {
    private final String TAG = this.getClass().getCanonicalName();
    private final MyDatabase db;
    private final UserDao userDao;

    public UserRepository(Application application) {
        db = MyDatabase.getDatabase(application);
        userDao = db.userDao();
    }

    public User getUser(String email, String password) {
        return userDao.getUser(email, password);
    }

    public void insertUserItem(User user) {
        userDao.insert(user);
    }

    public void updateUserItem(User user) {
        userDao.update(user);
    }

    public void deleteUserItem(User user) {
        userDao.delete(user);
    }
}
