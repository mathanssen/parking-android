// Group 2
// Matheus Hanssen (101303562)

package com.wx.parking.utils;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class LocationUtils {

    private Context context;

    public LocationUtils(Context context) {
        this.context = context;
    }

    public String getLocation(Double lat, Double lng) {
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(lat, lng, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String address = addresses.get(0).getAddressLine(0);

        return address;
    }

}
