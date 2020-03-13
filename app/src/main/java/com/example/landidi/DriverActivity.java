package com.example.landidi;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class DriverActivity extends SimpleActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver);
        BottomNavigationView navDriverView = findViewById(R.id.nav_driver_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        new AppBarConfiguration.Builder(
                R.id.driver_home, R.id.driver_message, R.id.driver_info)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_driver_fragment);
        NavigationUI.setupWithNavController(navDriverView, navController);

    }
}
