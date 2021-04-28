// Group 2
// Wei Xu(101059762)
// Matheus Hanssen (101303562)

package com.wx.parking.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.wx.parking.database.MyDatabase;
import com.wx.parking.database.dao.ParkingDao;
import com.wx.parking.database.models.Parking;
import com.wx.parking.database.models.User;

import java.util.Date;
import java.util.List;

public class ParkingRepository {
    public final ParkingDao parkingDao;
    private final MyDatabase db;

    // Created by Matheus
    private LiveData<List<Parking>> parkingItems;

    public ParkingRepository(Application application) {
        db = MyDatabase.getDatabase(application);
        parkingDao = db.parkingDao();

        // Created by Matheus
        parkingItems = parkingDao.getParkingItems();
    }

    public void insertParkingRecord(Parking parking) {
        parkingDao.insert(parking);
    }

    // Created by Matheus
    public LiveData<List<Parking>> getParkingItems() {
        return parkingItems;
    };

    public Parking getParkingItem(int id) {
        return parkingDao.getParkingItem(id);
    };
}
