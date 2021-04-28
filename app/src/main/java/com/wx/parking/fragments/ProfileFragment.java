// Group 2
// Wei Xu(101059762)

package com.wx.parking.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.wx.parking.R;
import com.wx.parking.activities.SignInActivity;
import com.wx.parking.database.models.User;
import com.wx.parking.viewmodels.UserViewModel;

public class ProfileFragment extends Fragment implements View.OnClickListener {

    private final String TAG = this.getClass().getCanonicalName();
    private TextView edtFirstname;
    private TextView edtLastname;
    private TextView edtContactNo;
    private TextView edtPlateNo;
    private Button btnUpdateAccount;
    private Button btnDeleteAccount;
    private UserViewModel userViewModel;
    private User user = new User();

    private Activity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        this.edtFirstname = v.findViewById(R.id.edtFirstname);
        this.edtLastname = v.findViewById(R.id.edtLastname);
        this.edtContactNo = v.findViewById(R.id.edtContactNo);
        this.edtPlateNo = v.findViewById(R.id.edtPlateNo);
        this.btnUpdateAccount = v.findViewById(R.id.btnUpdateAccount);
        this.btnUpdateAccount.setOnClickListener(this);
        this.btnDeleteAccount = v.findViewById(R.id.btnDeleteAccount);
        this.btnDeleteAccount.setOnClickListener(this);

        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        this.activity = getActivity();

        // Retrieve user as Argument
        this.user = (User) this.activity.getIntent().getSerializableExtra("UserObj");

        this.userViewModel = new UserViewModel(this.activity.getApplication());

        this.edtFirstname.setText(this.user.firstName);
        this.edtLastname.setText(this.user.lastName);
        this.edtContactNo.setText(this.user.contactNo);
        this.edtPlateNo.setText(this.user.plateNo);
    }

    @Override
    public void onClick(View view) {
        if (view != null) {
            switch (view.getId()) {
            case R.id.btnUpdateAccount: {
                if (this.validateData()) {
                    this.updateUserToDB();
                }
                break;
            }
            case R.id.btnDeleteAccount: {
                this.deleteUserToDB();
                break;
            }
            default:
                break;
            }
        }
    }

    // Update User
    private void updateUserToDB() {
        this.user.firstName = edtFirstname.getText().toString().trim();
        this.user.lastName = edtLastname.getText().toString().trim();
        this.user.contactNo = edtContactNo.getText().toString().trim();
        this.user.plateNo = edtPlateNo.getText().toString().trim();

        Log.d(TAG, "updateUserItem");
        this.userViewModel.updateUserItem(this.user);

        Toast.makeText(this.activity.getApplicationContext(), "User Updated", Toast.LENGTH_LONG).show();
    }

    // Delete User
    private void deleteUserToDB() {

        Log.d(TAG, "deleteUserToDB");
        this.userViewModel.deleteUserItem(this.user);

        this.activity.finish();
        this.activity.getIntent().removeExtra("UserObj");

        Intent intent = new Intent(activity, SignInActivity.class);
        startActivity(intent);
    }

    // User input Validation
    private Boolean validateData() {
        if (!this.edtPlateNo.getText().toString().trim().isEmpty()) {
            if (this.edtPlateNo.getText().toString().length() < 2
                    || this.edtPlateNo.getText().toString().length() > 8) {
                this.edtPlateNo.setError("The length of Car Plate Number must be between 2 and 8");
                return false;
            }
        }
        return true;
    }
}