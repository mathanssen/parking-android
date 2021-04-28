// Group 2
// Wei Xu(101059762)
// Matheus Hanssen (101303562)

package com.wx.parking.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.room.ForeignKey;

import com.wx.parking.database.models.Parking;
import com.wx.parking.database.models.User;
import com.wx.parking.repositories.ParkingRepository;

import java.util.Date;
import java.util.List;

public class ParkingViewModel extends AndroidViewModel {

    private final ParkingRepository parkingRepository;

    public ParkingViewModel(@NonNull Application application) {
        super(application);
        this.parkingRepository = new ParkingRepository(application);
    }

    public void insertParkingRecord(Parking parking) {
        parkingRepository.insertParkingRecord(parking);
    }

    public LiveData<List<Parking>> getAllParkingItems() {
        return parkingRepository.getParkingItems();
    }

    public Parking getParkingItem(int id) {
        return parkingRepository.getParkingItem(id);
    };

}
