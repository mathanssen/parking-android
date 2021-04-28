// Group 2
// Wei Xu(101059762)

package com.wx.parking.database.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class User implements Serializable {
    @PrimaryKey
    @NonNull
    public String email;
    public String password;
    public String firstName;
    public String lastName;
    public String contactNo;
    public String plateNo;
}
