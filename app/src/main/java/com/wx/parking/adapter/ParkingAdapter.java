// Group 2
// Matheus Hanssen (101303562)

package com.wx.parking.adapter;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wx.parking.R;
import com.wx.parking.activities.DetailActivity;
import com.wx.parking.database.models.Parking;
import com.wx.parking.database.models.User;
import com.wx.parking.fragments.ViewParkingFragment;
import com.wx.parking.utils.LocationUtils;
import com.wx.parking.viewmodels.ParkingViewModel;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ParkingAdapter extends RecyclerView.Adapter<ParkingAdapter.ViewHolder> {

    // Properties
    private Context context;
    private ArrayList<Parking> parking;
    private ParkingViewModel model;

    // Constructor
    public ParkingAdapter(Context context, ArrayList<Parking> parking, ParkingViewModel model) {
        this.context = context;
        this.parking = parking;
        this.model = model;
    }

    // Methods
    @NonNull
    @Override
    public ParkingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.parking_item_row, null, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(parking.get(position));

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int parkingID = parking.get(position).ID;
                Intent i = new Intent(context, DetailActivity.class);
                i.putExtra("Parking", parkingID);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return parking.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvParkingItem;
        TextView tvParkingDetail;
        private RelativeLayout layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvParkingItem = itemView.findViewById(R.id.tvName);
            tvParkingDetail = itemView.findViewById(R.id.tvDetail);
            layout = itemView.findViewById(R.id.layout);
        }

        public void bind(Parking parking) {

            // Get location
            Double parkingLat = parking.locationLat;
            Double parkingLng = parking.locationLng;
            Context context = itemView.getContext();
            LocationUtils location = new LocationUtils(context);
            ;
            String parkingLocation = location.getLocation(parkingLat, parkingLng);

            // Get date
            String pattern = "MM/dd/yyyy HH:mm:ss";
            DateFormat df = new SimpleDateFormat(pattern);
            Date parkingDateDf = parking.parkingTime;
            String parkingDate = df.format(parkingDateDf);

            // Set variables in UI
            tvParkingItem.setText(parkingDate);
            tvParkingDetail.setText(parkingLocation);
        }
    }

}
