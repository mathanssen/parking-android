// Group 2
// Matheus Hanssen (101303562)

package com.wx.parking.activities;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;

import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.wx.parking.R;
import com.wx.parking.utils.LocationManager;

import java.io.IOException;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    // Properties
    private GoogleMap mMap;
    private final Float DEFAULT_ZOOM = 15.0f;
    private LocationManager locationManager;
    private LocationCallback locationCallback;
    private Double latitude;
    private Double longitude;
    private LatLng currentLocation;

    // Default Function
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Get coordinates
        this.latitude = getIntent().getDoubleExtra("latitude", 43.651070);
        this.longitude = getIntent().getDoubleExtra("longitude", -79.347015);

        // Obtain the SupportMapFragment
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        this.locationManager = LocationManager.getInstance();
        this.locationManager.checkPermissions(this);

        // Get current location
        if (this.locationManager.locationPermissionGranted) {
            locationCallback = new LocationCallback() {
                @Override
                public void onLocationResult(LocationResult locationResult) {
                    if (locationResult != null) {
                        return;
                    }
                    for (Location loc : locationResult.getLocations()) {
                        currentLocation = new LatLng(loc.getLatitude(), loc.getLongitude());
                        mMap.animateCamera(CameraUpdateFactory.newLatLng(currentLocation));
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, DEFAULT_ZOOM));
                        mMap.addMarker(new MarkerOptions().position(currentLocation).title("Current Location"));
                    }
                }
            };
            this.locationManager.requestLocationUpdates(this, locationCallback);
        }
    }

    // Set parking location when the map is ready
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng parkingLocation = new LatLng(this.latitude, this.longitude);
        mMap.addMarker(new MarkerOptions().position(parkingLocation).title("Parking Location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(parkingLocation));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(parkingLocation, DEFAULT_ZOOM));

        if (googleMap != null) {
            googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            this.setupGoogleMapScreenSetting(googleMap);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.locationManager.stopLocationUpdates(this, this.locationCallback);
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.locationManager.requestLocationUpdates(this, this.locationCallback);
    }

    // Map settings
    private void setupGoogleMapScreenSetting(GoogleMap gMap) {
        gMap.setBuildingsEnabled(false);
        gMap.setIndoorEnabled(false);
        gMap.setTrafficEnabled(false);

        UiSettings myUiSettings = gMap.getUiSettings();

        myUiSettings.setZoomControlsEnabled(true);
        myUiSettings.setZoomGesturesEnabled(true);
        myUiSettings.setMyLocationButtonEnabled(true);
        myUiSettings.setScrollGesturesEnabled(true);
        myUiSettings.setRotateGesturesEnabled(true);
    }

}