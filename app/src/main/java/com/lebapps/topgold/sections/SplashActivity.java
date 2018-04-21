package com.lebapps.topgold.sections;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.lebapps.topgold.R;
import com.lebapps.topgold.data.vehicle.VehiclesManager;
import com.lebapps.topgold.data.vehicle.Vehicle;
import com.lebapps.topgold.sections.add_vehicle.AddVehicleActivity;
import com.lebapps.topgold.sections.home.HomeActivity;

import java.util.ArrayList;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        final ArrayList<Vehicle> vehicles = VehiclesManager.getInstance().getVehicles();
        if (vehicles.size() > 0) {
            openHome();
        } else {
            openAddVehicle();
        }
    }

    private void openHome() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivityForResult(intent, BaseActivity.RC_HOME);
    }

    private void openAddVehicle() {
        Intent intent = new Intent(this, AddVehicleActivity.class);
        intent.putExtra("with_back", false);
        startActivityForResult(intent, BaseActivity.RC_ADD_VEHICLE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == BaseActivity.RC_ADD_VEHICLE) {
            if (resultCode == BaseActivity.RESULT_CODE_VEHICLE_ADDED){
                openHome();
            } else {
                finish();
            }
        } else {
            finish();
        }
    }
}
