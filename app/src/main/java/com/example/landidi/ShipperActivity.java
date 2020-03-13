package com.example.landidi;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class ShipperActivity extends SimpleActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipper);
        BottomNavigationView navShipperView = findViewById(R.id.nav_shipper_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        new AppBarConfiguration.Builder(
                R.id.shipper_home, R.id.shipper_message, R.id.shipper_info)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_shipper_fragment);
        NavigationUI.setupWithNavController(navShipperView, navController);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_demand, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.add_demand) {
            startActivity(new Intent(ShipperActivity.this, AddDemandActivity.class));
        }
        return true;
    }
}
