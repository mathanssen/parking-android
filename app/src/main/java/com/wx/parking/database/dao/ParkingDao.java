// Group 2
// Wei Xu(101059762)
// Matheus Hanssen (101303562)

package com.wx.parking.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.wx.parking.database.models.Parking;
import com.wx.parking.database.models.User;

import java.util.List;

@Dao
public interface ParkingDao extends BaseDao<Parking> {
    @Insert
    @Override
    void insert(Parking parking);

    @Delete
    @Override
    void delete(Parking parking);

    @Update
    @Override
    void update(Parking parking);

    // Get all parking items
    @Query("SELECT * FROM tblParking")
    LiveData<List<Parking>> getParkingItems();

    // Get only one parking item
    @Query("SELECT * FROM tblParking WHERE ID = :id")
    Parking getParkingItem(int id);
}
