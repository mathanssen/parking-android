// Group 2
// Wei Xu(101059762)

package com.wx.parking.fragments;

import android.content.pm.PackageManager;
import android.icu.util.Calendar;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.google.android.gms.maps.model.LatLng;
import com.wx.parking.R;
import com.wx.parking.database.models.Parking;
import com.wx.parking.database.models.User;
import com.wx.parking.utils.LocationManager;
import com.wx.parking.viewmodels.ParkingViewModel;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class AddParkingFragment extends Fragment implements View.OnClickListener {

    private final String TAG = this.getClass().getCanonicalName();
    private final Parking parking = new Parking();
    private TextView edtBuildingCode;
    private TextView edtPlateNo;
    private TextView edtSuitNo;
    private TextView edtParkingLocation;
    private RadioButton radio_1hour;
    private RadioButton radio_4hour;
    private RadioButton radio_12hour;
    private RadioButton radio_24hour;
    private Button btnGetCurrentLocation;
    private Button btnCreateParking;
    private int hours = 0;
    private ParkingViewModel parkingViewModel;

    private LocationManager locationManager;
    private Geocoder geocoder;
    private List<Address> addresses;
    private List<Address> addressesLL;
    private LatLng location;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_parking, container, false);

        this.edtBuildingCode = v.findViewById(R.id.edtBuildingCode);
        this.edtPlateNo = v.findViewById(R.id.edtPlateNo);
        this.edtSuitNo = v.findViewById(R.id.edtSuitNo);
        this.edtParkingLocation = v.findViewById(R.id.edtParkingLocation);

        this.radio_1hour = v.findViewById(R.id.radio_1hour);
        this.radio_1hour.setOnClickListener(this);
        this.radio_4hour = v.findViewById(R.id.radio_4hour);
        this.radio_4hour.setOnClickListener(this);
        this.radio_12hour = v.findViewById(R.id.radio_12hour);
        this.radio_12hour.setOnClickListener(this);
        this.radio_24hour = v.findViewById(R.id.radio_24hour);
        this.radio_24hour.setOnClickListener(this);

        this.btnGetCurrentLocation = v.findViewById(R.id.btnGetCurrentLocation);
        this.btnGetCurrentLocation.setOnClickListener(this);
        this.btnCreateParking = v.findViewById(R.id.btnCreateParking);
        this.btnCreateParking.setOnClickListener(this);

        this.locationManager = LocationManager.getInstance();
        this.locationManager.checkPermissions(getActivity());

        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Get Login user data
        User user = (User) this.getActivity().getIntent().getSerializableExtra("UserObj");

        this.parkingViewModel = new ParkingViewModel(this.getActivity().getApplication());

        // User login user email as parking record email
        this.parking.userEmail = user.email;

        // Set initial value of Car plate No from Login user
        this.edtPlateNo.setText(user.plateNo);

        geocoder = new Geocoder(getActivity(), Locale.getDefault());
    }

    @Override
    public void onClick(View view) {
        if (view != null) {
            switch (view.getId()) {
            case R.id.radio_1hour: {
                if (((RadioButton) view).isChecked()) {
                    this.hours = 1;
                }
                break;
            }
            case R.id.radio_4hour: {
                if (((RadioButton) view).isChecked()) {
                    this.hours = 4;
                }
                break;
            }
            case R.id.radio_12hour: {
                if (((RadioButton) view).isChecked()) {
                    this.hours = 12;
                }
                break;
            }
            case R.id.radio_24hour: {
                if (((RadioButton) view).isChecked()) {
                    this.hours = 24;
                }
                break;
            }

            case R.id.btnGetCurrentLocation: {
                if (this.locationManager.locationPermissionGranted) {
                    this.getLocation();
                }
                break;
            }

            case R.id.btnCreateParking: {
                if (this.validateData()) {
                    this.CreateParkingToDB();
                }
                break;
            }
            default:
                break;
            }
        }
    }

    // Get current address and set to Parking Location TextEdit
    private void getLocation() {
        this.locationManager.getLastLocation(this.getActivity()).observe(this, new Observer<Location>() {
            @Override
            public void onChanged(Location loc) {
                if (loc != null) {
                    // Get Latitude and Longitude current location
                    location = new LatLng(loc.getLatitude(), loc.getLongitude());

                    try {
                        // Convert to address
                        addresses = geocoder.getFromLocation(loc.getLatitude(), loc.getLongitude(), 1);

                        String address = addresses.get(0).getAddressLine(0);

                        String streetNo = addresses.get(0).getSubThoroughfare();
                        String street = addresses.get(0).getThoroughfare();
                        String city = addresses.get(0).getLocality();
                        String state = addresses.get(0).getAdminArea();

                        Log.d(TAG, "getAddressLine:" + address);

                        // Update Parking Location TextEdit
                        edtParkingLocation.setText(streetNo + " " + street + ", " + city + ", " + state);
                    } catch (IOException e) {
                        Log.e(TAG, e.getLocalizedMessage());
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    // Create Parking Record in DB
    private void CreateParkingToDB() {
        this.parking.buildingCode = this.edtBuildingCode.getText().toString().trim();
        this.parking.plateNo = this.edtPlateNo.getText().toString().trim();
        this.parking.suitNo = this.edtSuitNo.getText().toString().trim();
        this.parking.hours = this.hours;
        this.parking.parkingTime = Calendar.getInstance().getTime();

        try {
            // Convert address to Latitude and Longitude
            addressesLL = geocoder.getFromLocationName(this.edtParkingLocation.getText().toString().trim(), 1);

            if (!addressesLL.isEmpty()) {
                // Convert success
                Address addressLL = addressesLL.get(0);
                this.parking.locationLat = addressLL.getLatitude();
                this.parking.locationLng = addressLL.getLongitude();
                this.parkingViewModel.insertParkingRecord(this.parking);
                Toast.makeText(this.getActivity().getApplicationContext(), "Parking Record Created!", Toast.LENGTH_LONG)
                        .show();
            } else {
                // Can not find location associated with input
                Toast.makeText(this.getActivity().getApplicationContext(), "Parking Location can not been found!",
                        Toast.LENGTH_LONG).show();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // Validate user input
    private boolean validateData() {
        // Must check
        if (this.edtBuildingCode.getText().toString().trim().isEmpty()) {
            this.edtBuildingCode.setError("Please enter Building Code");
            return false;
        }
        if (this.edtPlateNo.getText().toString().trim().isEmpty()) {
            this.edtPlateNo.setError("Please enter Car Plate Number");
            return false;
        }
        if (this.edtSuitNo.getText().toString().trim().isEmpty()) {
            this.edtSuitNo.setError("Please enter Suit No. of Host");
            return false;
        }
        if (this.edtParkingLocation.getText().toString().trim().isEmpty()) {
            this.edtParkingLocation.setError("Please enter Parking location");
            return false;
        }

        if (this.hours == 0) {
            Toast.makeText(getActivity().getApplicationContext(), "Please select No. of hours intended to park",
                    Toast.LENGTH_LONG).show();
            return false;
        }

        // Length check
        if (this.edtBuildingCode.getText().toString().length() != 5) {
            this.edtBuildingCode.setError("The length of Building code must be exactly 5");
        }

        if (this.edtPlateNo.getText().toString().length() < 2 || this.edtPlateNo.getText().toString().length() > 8) {
            this.edtPlateNo.setError("The length of Car Plate Number must be between 2 and 8");
            return false;
        }

        if (this.edtSuitNo.getText().toString().length() < 2 || this.edtSuitNo.getText().toString().length() > 5) {
            this.edtSuitNo.setError("The length of Suit no. of host must be between 2 and 5");
            return false;
        }

        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
            @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == this.locationManager.LOCATION_PERMISSION_REQUEST_CODE) {
            this.locationManager.locationPermissionGranted = (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED);

            if (this.locationManager.locationPermissionGranted) {
                // start receiving location and display that on screen
                Log.d(TAG, "LocationPermissionGranted " + this.locationManager.locationPermissionGranted);
            }
            return;
        }
    }
}