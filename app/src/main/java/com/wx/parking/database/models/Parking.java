// Group 2
// Wei Xu(101059762)
// Matheus Hanssen (101303562)

package com.wx.parking.database.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "tblParking")
public class Parking {
    @PrimaryKey(autoGenerate = true)
    public int ID;
    @NonNull
    public String buildingCode;
    @NonNull
    public int hours;
    @NonNull
    public String plateNo;
    @NonNull
    public String suitNo;
    @NonNull
    public Double locationLat;
    @NonNull
    public Double locationLng;
    @NonNull
    public Date parkingTime;
    @NonNull
    @ForeignKey(entity = User.class, parentColumns = "email", childColumns = "userEmail", onDelete = ForeignKey.CASCADE)
    public String userEmail;
}
