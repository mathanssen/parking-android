// Group 2
// Wei Xu(101059762)

package com.wx.parking.database.dao;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Update;

public interface BaseDao<T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(T t);

    @Delete
    void delete(T t);

    @Update
    void update(T t);

}
