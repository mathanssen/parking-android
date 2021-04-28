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

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {
    private final String TAG = this.getClass().getCanonicalName();
    private EditText edtEmail;
    private EditText edtPassword;
    private Button btnSignIn;
    private Button btnCreateAccount;
    private UserViewModel userViewModel;
    private User user = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("SignIn");
        setContentView(R.layout.activity_sign_in);

        this.edtEmail = findViewById(R.id.edtEmail);
        this.edtPassword = findViewById(R.id.edtPassword);

        this.btnSignIn = findViewById(R.id.btnSignIn);
        this.btnSignIn.setOnClickListener(this);

        this.btnCreateAccount = findViewById(R.id.btnCreateAccount);
        this.btnCreateAccount.setOnClickListener(this);

        userViewModel = new UserViewModel(getApplication());
    }

    @Override
    public void onClick(View view) {
        if (view != null) {
            switch (view.getId()) {
            case R.id.btnCreateAccount: {
                Intent signUpIntent = new Intent(this, SignUpActivity.class);
                startActivity(signUpIntent);
                break;
            }
            case R.id.btnSignIn: {
                // verify the user
                validateLogin();
                break;
            }
            default:
                break;
            }
        }
    }

    // User Login validation
    private void validateLogin() {
        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();

        if (email.isEmpty()) {
            this.edtEmail.setError("Please enter email");
        } else if (password.isEmpty()) {
            this.edtPassword.setError("Password cannot be empty");
        } else if (!email.isEmpty()) {
            if (!password.isEmpty()) {
                user = this.userViewModel.getUser(email, password);

                if (user != null) {
                    Intent intent = new Intent(this, MainActivity.class);
                    // Send login user as argument
                    intent.putExtra("UserObj", user);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "User or Password is incorrect!", Toast.LENGTH_LONG).show();
                }
            }
        }

    }
}