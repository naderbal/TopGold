package com.lebapps.topgold.sections.settings;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lebapps.topgold.R;
import com.lebapps.topgold.sections.BaseActivity;
import com.lebapps.topgold.sections.add_vehicle.AddVehicleActivity;
import com.lebapps.topgold.sections.edit_vehicle.EditVehicleActivity;

/**
 *
 */

public class SettingsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.tvAddVehicle).setOnClickListener(v -> openAddVehicle());
        view.findViewById(R.id.tvEditVehicle).setOnClickListener(v -> openEditVehicle());
        view.findViewById(R.id.tvAboutUs).setOnClickListener(v -> openAboutUs());
    }

    private void openAboutUs() {

    }

    private void openEditVehicle() {
        Intent intent = new Intent(getActivity(), EditVehicleActivity.class);
        startActivity(intent);
    }

    private void openAddVehicle() {
        Intent intent = new Intent(getActivity(), AddVehicleActivity.class);
        intent.putExtra("with_back", true);
        startActivityForResult(intent, BaseActivity.RC_ADD_VEHICLE);
    }

}
