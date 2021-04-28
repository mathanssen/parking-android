// Group 2
// Wei Xu(101059762)

package com.wx.parking.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.wx.parking.database.models.User;

@Dao
public interface UserDao extends BaseDao<User> {

    @Query("SELECT * FROM User WHERE email = :userEmail AND password = :pwd")
    User getUser(String userEmail, String pwd);

    @Insert
    @Override
    void insert(User user);

    @Delete
    @Override
    void delete(User user);

    @Update
    @Override
    void update(User user);
}

