// Group 2
// Wei Xu(101059762)
// Matheus Hanssen (101303562)

package com.wx.parking.fragments;

import android.app.Activity;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wx.parking.R;
import com.wx.parking.adapter.ParkingAdapter;
import com.wx.parking.database.models.Parking;
import com.wx.parking.database.models.User;
import com.wx.parking.viewmodels.ParkingViewModel;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ViewParkingFragment extends Fragment {

    // Properties
    private RecyclerView recyclerView;
    private ParkingViewModel model;
    private ParkingAdapter adapter;
    private ArrayList<Parking> parkingItems = new ArrayList<>();

    // Default Function
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_view_parking, container, false);

        // UI Components
        model = new ParkingViewModel(getActivity().getApplication());
        adapter = new ParkingAdapter(getActivity().getApplicationContext(), parkingItems, model);

        // Recycler View settings
        recyclerView = v.findViewById(R.id.recyclerview);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplication()));
        recyclerView.addItemDecoration(
                new DividerItemDecoration(getActivity().getApplication(), DividerItemDecoration.VERTICAL));

        // Display the items
        model.getAllParkingItems().observe(getViewLifecycleOwner(), parking -> {
            parkingItems.removeAll(parkingItems);
            User user = (User) this.getActivity().getIntent().getSerializableExtra("UserObj");
            for (int i = 0; i < model.getAllParkingItems().getValue().size(); i++) {
                if (model.getAllParkingItems().getValue().get(i).userEmail.equals(user.email)) {
                    parkingItems.add(model.getAllParkingItems().getValue().get(i));
                }
            }
            Collections.reverse(parkingItems);
            adapter.notifyDataSetChanged();
        });

        return v;
    }
}