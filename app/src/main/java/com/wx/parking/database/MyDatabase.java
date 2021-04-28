// Group 2
// Wei Xu(101059762)
// Matheus Hanssen (101303562)

package com.wx.parking.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.wx.parking.database.dao.ParkingDao;
import com.wx.parking.database.dao.UserDao;
import com.wx.parking.database.models.Parking;
import com.wx.parking.database.models.User;

@Database(entities = { User.class, Parking.class }, version = 4, exportSchema = false)
@TypeConverters({ Converters.class })
public abstract class MyDatabase extends RoomDatabase {

    private static com.wx.parking.database.MyDatabase db;

    public static com.wx.parking.database.MyDatabase getDatabase(Context context) {
        if (db == null) {
            db = Room.databaseBuilder(context.getApplicationContext(), com.wx.parking.database.MyDatabase.class,
                    "dbRoom").allowMainThreadQueries().fallbackToDestructiveMigration().build();
        }
        return db;
    }

    public abstract UserDao userDao();

    public abstract ParkingDao parkingDao();

}
