// Group 2
// Wei Xu(101059762)

package com.wx.parking.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.wx.parking.R;
import com.wx.parking.database.models.User;
import com.wx.parking.viewmodels.UserViewModel;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    private final String TAG = this.getClass().getCanonicalName();
    private final User user = new User();
    private EditText edtEmail;
    private EditText edtPassword;
    private EditText edtConfirmPassword;
    private EditText edtFirstname;
    private EditText edtLastname;
    private EditText edtContactNo;
    private EditText edtPlateNo;
    private Button btnCreateAccount;
    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("SignUp");
        setContentView(R.layout.activity_sign_up);

        this.edtEmail = findViewById(R.id.edtEmail);
        this.edtPassword = findViewById(R.id.edtPassword);
        this.edtConfirmPassword = findViewById(R.id.edtConfirmPassword);
        this.edtFirstname = findViewById(R.id.edtFirstname);
        this.edtLastname = findViewById(R.id.edtLastname);
        this.edtContactNo = findViewById(R.id.edtContactNo);
        this.edtPlateNo = findViewById(R.id.edtPlateNo);
        this.btnCreateAccount = findViewById(R.id.btnCreateAccount);
        this.btnCreateAccount.setOnClickListener(this);

        userViewModel = new UserViewModel(getApplication());
    }

    @Override
    public void onClick(View view) {
        if (view != null) {
            switch (view.getId()) {
            case R.id.btnCreateAccount: {
                if (this.validateData()) {
                    // save data to database
                    this.saveUserToDB();
                    // go to signIn activity
                    this.goToSignIn();
                }
                break;
            }
            default:
                break;
            }
        }
    }

    private void saveUserToDB() {
        user.email = this.edtEmail.getText().toString();
        user.password = this.edtPassword.getText().toString();
        user.firstName = this.edtFirstname.getText().toString();
        user.lastName = this.edtLastname.getText().toString();
        user.contactNo = this.edtContactNo.getText().toString();
        user.plateNo = this.edtPlateNo.getText().toString();

        this.userViewModel.insertUserItem(user);
        Toast.makeText(getApplicationContext(), "User Added", Toast.LENGTH_LONG).show();

    }

    // Move to SignIn activity
    private void goToSignIn() {
        this.finish();
        Intent intent = new Intent(this, SignInActivity.class);
        startActivity(intent);
    }

    // User input validation
    private Boolean validateData() {
        if (this.edtEmail.getText().toString().trim().isEmpty()) {
            this.edtEmail.setError("Please enter email");
            return false;
        }
        // add a check for valid email address format
        if (this.edtPassword.getText().toString().trim().isEmpty()) {
            this.edtPassword.setError("Password cannot be empty");
            return false;
        }
        if (this.edtConfirmPassword.getText().toString().trim().isEmpty()) {
            this.edtConfirmPassword.setError("Please provide confirm password");
            return false;
        }
        if (!this.edtPassword.getText().toString().equals(this.edtConfirmPassword.getText().toString())) {
            this.edtPassword.setError("Both passwords must be same");
            this.edtConfirmPassword.setError("Both passwords must be same");
            return false;
        }
        if (!this.edtPlateNo.getText().toString().trim().isEmpty()) {
            if (this.edtPlateNo.getText().toString().length() < 2 || edtPlateNo.getText().toString().length() > 8) {
                this.edtPlateNo.setError("The length of Car Plate Number must be between 2 and 8");
                return false;
            }
        }
        return true;
    }

}