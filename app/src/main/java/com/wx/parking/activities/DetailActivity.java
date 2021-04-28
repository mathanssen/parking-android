// Group 2
// Matheus Hanssen (101303562)

package com.wx.parking.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.wx.parking.R;
import com.wx.parking.database.models.Parking;
import com.wx.parking.utils.LocationUtils;
import com.wx.parking.viewmodels.ParkingViewModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DetailActivity extends AppCompatActivity {

    // Properties
    private int parkingID;
    private ParkingViewModel model;
    private Parking parking;
    private TextView tvBuildingDetail;
    private TextView tvHoursDetail;
    private TextView tvSuitNoDetail;
    private TextView tvPlateNoDetail;
    private TextView tvLocationDetail;
    private TextView tvParkingTimeDetail;
    private Double longitude;
    private Double latitude;

    // Default Function
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Get data from recycler view
        model = new ParkingViewModel(getApplication());
        this.parkingID = getIntent().getIntExtra("Parking", 10);
        this.parking = model.getParkingItem(this.parkingID);

        // Convert location
        this.latitude = parking.locationLat;
        this.longitude = parking.locationLng;
        LocationUtils location = new LocationUtils(this);
        ;
        String parkingLocation = location.getLocation(latitude, longitude);

        // Convert date
        String pattern = "MM/dd/yyyy HH:mm:ss";
        DateFormat df = new SimpleDateFormat(pattern);
        Date parkingDateDf = parking.parkingTime;
        String parkingDate = df.format(parkingDateDf);

        // Convert Hours
        String parkingHours = Integer.toString(this.parking.hours);

        // UI Components
        this.tvBuildingDetail = findViewById(R.id.tvBuildingDetail);
        this.tvBuildingDetail.setText(this.parking.buildingCode);

        this.tvHoursDetail = findViewById(R.id.tvHoursDetail);
        this.tvHoursDetail.setText(parkingHours);

        this.tvSuitNoDetail = findViewById(R.id.tvSuitNoDetail);
        this.tvSuitNoDetail.setText(this.parking.suitNo);

        this.tvPlateNoDetail = findViewById(R.id.tvPlateNoDetail);
        this.tvPlateNoDetail.setText(this.parking.plateNo);

        this.tvLocationDetail = findViewById(R.id.tvLocationDetail);
        this.tvLocationDetail.setText(parkingLocation);

        this.tvParkingTimeDetail = findViewById(R.id.tvParkingTimeDetail);
        this.tvParkingTimeDetail.setText(parkingDate);
    }

    // Go to map screen
    public void ShowRoute(View view) {
        Intent intent = new Intent(this, MapsActivity.class);
        intent.putExtra("latitude", this.latitude);
        intent.putExtra("longitude", this.longitude);
        startActivity(intent);
    }

}