package com.lebapps.topgold.sections.functionality.actions;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import com.lebapps.topgold.PermissionsUtils;
import com.lebapps.topgold.R;
import com.lebapps.topgold.data.history.ActionHistory;
import com.lebapps.topgold.data.history.HistoryManager;
import com.lebapps.topgold.data.messages.MessageSender;
import com.lebapps.topgold.data.vehicle.Vehicle;
import com.lebapps.topgold.functionality.Functionality;
import com.lebapps.topgold.sections.BaseActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 *
 */
public class AdminFunctionalityActivity extends BaseActivity {
    Functionality functionality;
    Vehicle vehicle;
    TextInputLayout tilPhone1;
    TextInputLayout tilPhone2;
    TextInputLayout tilPhone3;
    TextInputLayout tilPhone4;
    EditText etPhone1;
    EditText etPhone2;
    EditText etPhone3;
    EditText etPhone4;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_functionality);
        functionality = (Functionality) getIntent().getSerializableExtra("functionality");
        vehicle = (Vehicle) getIntent().getSerializableExtra("vehicle");
        configureToolbarWithUpButton(R.id.toolbar, functionality.getFunctionalityResource());
        initViews();
    }

    private void initViews() {
        // et
        etPhone1 = findViewById(R.id.etPhone1);
        etPhone2 = findViewById(R.id.etPhone2);
        etPhone3 = findViewById(R.id.etPhone3);
        etPhone4 = findViewById(R.id.etPhone4);
        // til
        tilPhone1 = findViewById(R.id.tilPhone1);
        tilPhone2 = findViewById(R.id.tilPhone2);
        tilPhone3 = findViewById(R.id.tilPhone3);
        tilPhone4 = findViewById(R.id.tilPhone4);

        findViewById(R.id.btnSend).setOnClickListener(v -> handleSendClicked());
        etPhone1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                tilPhone1.setError(null);
            }
        });
        etPhone2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                tilPhone2.setError(null);
            }
        });
        etPhone3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                tilPhone3.setError(null);
            }
        });
        etPhone4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                tilPhone4.setError(null);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    sendMessage();
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(this, R.string.permissions_denied, Toast.LENGTH_SHORT).show();
                    finish();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    private void handleSendClicked() {
        if (PermissionsUtils.hasPermissions(this)) {
            sendMessage();
        } else {
            PermissionsUtils.checkAndRequestPermissions(this);
        }
    }

    private void sendMessage() {
        if (validatePhone()){
            Date date = Calendar.getInstance().getTime();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH);
            final String strDate = simpleDateFormat.format(date);

            ActionHistory history = new ActionHistory(strDate, vehicle.getName(), getString(functionality.getFunctionalityResource()));
            HistoryManager.getInstance().addHistory(history);
            MessageSender.sendSMS(vehicle.getNumber(), getFunctionalityCode(), this::handleSuccess);
        }
    }

    private void handleSuccess() {
        showToast(getString(R.string.message_sent_success));
    }

    public String getFunctionalityCode() {
        return "#" + functionality.getFunctionalityCode() + "#" +vehicle.getPassword() + "#" + getPhones();
    }

    private boolean validatePhone() {
        final String phone1 = etPhone1.getText().toString().trim();
        if (phone1.isEmpty()) {
         tilPhone1.setError(getString(R.string.phone_1_error));
         return false;
        }
        return true;
    }

    public String getPhones() {
        final String phone1 = etPhone1.getText().toString().trim();
        final String phone2 = etPhone2.getText().toString().trim();
        final String phone3 = etPhone3.getText().toString().trim();
        final String phone4 = etPhone4.getText().toString().trim();

        String phone = "#" + phone1 + "#";
        if (!phone2.isEmpty()) {
            phone = phone + "#" + phone2 + "#";
        }
        if (!phone3.isEmpty()) {
            phone = phone + "#" +  phone3 + "#";
        }
        if (!phone4.isEmpty()) {
            phone = phone + "#" + phone4 + "#";
        }
        return phone;
    }
}
