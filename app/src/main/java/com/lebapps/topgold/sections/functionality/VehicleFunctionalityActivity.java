package com.lebapps.topgold.sections.functionality;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.lebapps.topgold.R;
import com.lebapps.topgold.data.history.ActionHistory;
import com.lebapps.topgold.data.history.HistoryHelper;
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
        findViewById(R.id.btnSend).setOnClickListener(v -> handleSendClicked());
    }

    private void handleSendClicked() {
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH);
        final String strDate = simpleDateFormat.format(date);

        ActionHistory history = new ActionHistory(strDate, vehicle.getName(), getString(functionality.getFunctionalityResource()));
        HistoryManager.getInstance().addHistory(history);
        //MessageSender.sendSMS(vehicle.getNumber(), getFunctionalityCode(), this::handleSuccess);
    }

    private void handleSuccess() {
        showToast("Message sent successfully, please wait for reply");
    }

    public String getFunctionalityCode() {
        return "#" + functionality.getFunctionalityCode() + "#" +vehicle.getPassword() + "#";
    }
}
