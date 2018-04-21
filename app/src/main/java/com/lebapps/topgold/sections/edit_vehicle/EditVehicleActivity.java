package com.lebapps.topgold.sections.edit_vehicle;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.jaredrummler.materialspinner.MaterialSpinner;
import com.lebapps.topgold.R;
import com.lebapps.topgold.data.vehicle.Vehicle;
import com.lebapps.topgold.data.vehicle.VehiclesManager;
import com.lebapps.topgold.sections.BaseActivity;

import java.util.ArrayList;

/**
 *
 */
public class EditVehicleActivity extends BaseActivity {
    private final int PICK_CONTACT = 10;
    private static final String[] phoneProjection = new String[] { ContactsContract.CommonDataKinds.Phone.DATA };
    // views
    private EditText etNumber, etPass, etName;
    private Button btnSubmit, btnContacts;
    private MaterialSpinner spinner;
    Vehicle vehicle;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_vehicle);
        initViews();
        configureSpinner();
        handleVehicleSelected(0);
    }

    private void configureSpinner() {
        spinner = findViewById(R.id.spinner);
        setVehicles();
    }

    private void setVehicles() {
        final ArrayList<Vehicle> vehicles = VehiclesManager.getInstance().getVehicles();
        spinner.setItems(getVehiclesNames(vehicles));
        spinner.setOnItemSelectedListener((view1, position, id, item) -> handleVehicleSelected(position));
    }

    private void handleVehicleSelected(int position) {
        vehicle = getSelectedVehicle(position);
        bindViews();
    }

    private Vehicle getSelectedVehicle(int position) {
        final ArrayList<Vehicle> vehicles = VehiclesManager.getInstance().getVehicles();
        if (vehicles.size() > position) {
            return vehicles.get(position);
        }
        return null;
    }

    private ArrayList<String> getVehiclesNames(ArrayList<Vehicle> vehicles) {
        ArrayList<String> arrayList = new ArrayList<>();
        for (Vehicle vehicle : vehicles) {
            arrayList.add(vehicle.getName());
        }
        return arrayList;
    }

    private void bindViews() {
        etNumber.setText(vehicle.getNumber());
        etPass.setText(vehicle.getPassword());
        etName.setText(vehicle.getName());
    }

    private void initViews() {
        etNumber = findViewById(R.id.etNumber);
        etPass = findViewById(R.id.etPassword);
        etName = findViewById(R.id.etName);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnContacts = findViewById(R.id.btnContacts);

        btnSubmit.setOnClickListener(v -> handleSubmit());
        btnContacts.setOnClickListener(v -> openContacts());
    }

    private void openContacts() {
        Intent intent= new Intent(Intent.ACTION_PICK,  ContactsContract.Contacts.CONTENT_URI);
        intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
        startActivityForResult(intent, PICK_CONTACT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case (PICK_CONTACT) :
                if (resultCode == Activity.RESULT_OK) {
                    Uri contactUri = data.getData();
                    if (null == contactUri) return;
                    //no tampering with Uri makes this to work without READ_CONTACTS permission
                    Cursor cursor = getContentResolver().query(contactUri, phoneProjection, null, null, null);
                    if (null == cursor) return;
                    try {
                        while (cursor.moveToNext()) {
                            String number = cursor.getString(0);
                            // ... use "number" as you wish
                            etNumber.setText(number);
                        }
                    } finally {
                        cursor.close();
                    }
                }
                break;
        }
    }

    private void handleSubmit() {
        final String number = etNumber.getText().toString().trim();
        final String pass = etPass.getText().toString().trim();
        final String name = etName.getText().toString().trim();
        if (number.isEmpty()) {
            showToast(getString(R.string.number_required));
            return;
        }
        if (pass.isEmpty()) {
            showToast(getString(R.string.pass_required));
            return;
        }
        if (name.isEmpty()) {
            showToast(getString(R.string.name_required));
            return;
        }
        Vehicle newVehicle = new Vehicle(number, pass, name);
        VehiclesManager.getInstance().editVehicle(vehicle, newVehicle);
        showToast(getString(R.string.vehicle_updated));
        setResult(RESULT_CODE_VEHICLE_EDITED);
        finish();
    }

}
