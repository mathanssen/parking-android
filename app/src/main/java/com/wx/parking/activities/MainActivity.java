// Group 2
// Wei Xu(101059762)

package com.wx.parking.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.wx.parking.R;
import com.wx.parking.database.models.User;

public class MainActivity extends AppCompatActivity {

    private final String TAG = this.getClass().getCanonicalName();
    private BottomNavigationView bottomNavigationView;
    private NavHostFragment navHostFragment;
    private NavController nav;
    private AppBarConfiguration appBarConfiguration;
    private final User user = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
        nav = Navigation.findNavController(this, R.id.fragment);

        appBarConfiguration = new AppBarConfiguration.Builder(R.id.addParkingFragment, R.id.viewParkingFragment,
                R.id.profileFragment).build();

        NavigationUI.setupWithNavController(bottomNavigationView, nav);
        NavigationUI.setupActionBarWithNavController(this, nav, appBarConfiguration);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.signout_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        finish();
        getIntent().removeExtra("UserObj");
        Intent intent = new Intent(this, SignInActivity.class);
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }
}