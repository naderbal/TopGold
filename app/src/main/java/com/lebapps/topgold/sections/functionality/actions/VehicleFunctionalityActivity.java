package com.lebapps.topgold.sections.functionality.actions;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Button;
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
public class VehicleFunctionalityActivity extends BaseActivity {
    Functionality functionality;
    Vehicle vehicle;
    private Button btnSend;
    private Button btnCall;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_functionality);
        functionality = (Functionality) getIntent().getSerializableExtra("functionality");
        vehicle = (Vehicle) getIntent().getSerializableExtra("vehicle");
        configureToolbarWithUpButton(R.id.toolbar, functionality.getFunctionalityResource());
        initViews();
    }

    private void initViews() {
        btnSend = findViewById(R.id.btnSend);
        btnCall = findViewById(R.id.btnCall);
        btnSend.setOnClickListener(v -> handleSendClicked());
        btnCall.setOnClickListener(v -> handleCallClicked());
    }

    private void handleCallClicked() {
        String phoneNumber = vehicle.getNumber();
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        startActivity(intent);
    }

    private void handleSendClicked() {
        if (PermissionsUtils.hasPermissions(this)) {
            sendMessage();
        } else {
            PermissionsUtils.checkAndRequestPermissions(this);
        }
    }

    private void sendMessage() {
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH);
        final String strDate = simpleDateFormat.format(date);

        ActionHistory history = new ActionHistory(strDate, vehicle.getName(), getString(functionality.getFunctionalityResource()));
        HistoryManager.getInstance().addHistory(history);
        MessageSender.sendSMS(vehicle.getNumber(), getFunctionalityCode(), this::handleSuccess);
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
        }
    }

    private void handleSuccess() {
        showToast(getString(R.string.message_sent_successfully));
        final String code = functionality.getFunctionalityCode();
        if (code.equals("supplyoil")) {
            HistoryManager.getInstance().setOil(true);
        } else if(code.equals("stopoil")) {
            HistoryManager.getInstance().setOil(false);
        } else if (code.equals("accon")) {
            HistoryManager.getInstance().setAcc(true);
        } else if(code.equals("accoff")) {
            HistoryManager.getInstance().setAcc(false);
        } else if(code.equals("supplyelec")){
            HistoryManager.getInstance().setElec(true);
        } else if (code.equals("stopelec")) {
            HistoryManager.getInstance().setElec(false);
        } else if (code.equals("nospeed")){
            HistoryManager.getInstance().setSpeed(false);
        } else if (code.equals("call") || code.equals("monitor")) {
            btnSend.setVisibility(View.GONE);
            btnCall.setVisibility(View.VISIBLE);
        }
    }

    public String getFunctionalityCode() {
        if (functionality.getFunctionalityCode().equals("dw")) {
            return "#dw#";
        } else if (functionality.getFunctionalityCode().equals("begin")) {
            return "#begin#";
        }
        return "#" + functionality.getFunctionalityCode() + "#" +vehicle.getPassword() + "#";
    }
}
