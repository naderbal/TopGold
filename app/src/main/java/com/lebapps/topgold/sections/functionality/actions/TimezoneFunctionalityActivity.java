package com.lebapps.topgold.sections.functionality.actions;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

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
public class TimezoneFunctionalityActivity extends BaseActivity {
    Functionality functionality;
    Vehicle vehicle;
    TextInputLayout tilWe;
    TextInputLayout tilHours;
    TextInputLayout tilMinutes;
    EditText etWe;
    EditText etHours;
    EditText etMinutes;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timezone_functionality);
        functionality = (Functionality) getIntent().getSerializableExtra("functionality");
        vehicle = (Vehicle) getIntent().getSerializableExtra("vehicle");
        configureToolbarWithUpButton(R.id.toolbar, functionality.getFunctionalityResource());
        initViews();
    }

    private void initViews() {
        // et
        etWe = findViewById(R.id.etWe);
        etHours = findViewById(R.id.etHours);
        etMinutes = findViewById(R.id.etMinutes);
        // til
        tilWe = findViewById(R.id.tilWe);
        tilHours = findViewById(R.id.tilHours);
        tilMinutes = findViewById(R.id.tilMinutes);

        findViewById(R.id.btnSend).setOnClickListener(v -> handleSendClicked());
        etWe.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                tilWe.setError(null);
            }
        });
        etHours.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                tilHours.setError(null);
            }
        });
        etMinutes.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                tilMinutes.setError(null);
            }
        });
    }

    private void handleSendClicked() {
        if (validateTimeZone()){
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
        return "#" + functionality.getFunctionalityCode() + "#" +vehicle.getPassword() + "#" + getTimezone();
    }

    private boolean validateTimeZone() {
        final String we = etWe.getText().toString().trim();
        final String hours = etHours.getText().toString().trim();
        final String minutes = etMinutes.getText().toString().trim();
        if (we.isEmpty()) {
            tilWe.setError(getString(R.string.weekend_empty));
            return false;
        }
        if (hours.isEmpty()) {
            tilHours.setError(getString(R.string.hours_empty));
            return false;
        }
        if(!hours.matches("\\d+(?:\\.\\d+)?")) {
            tilHours.setError(getString(R.string.hours_invalid));
            return false;
        }
        if(Integer.parseInt(hours) < 0) {
            tilHours.setError(getString(R.string.hours_negative));
            return false;
        }
        if (minutes.isEmpty()) {
            tilMinutes.setError(getString(R.string.minutes_empty));
            return false;
        }
        if(!minutes.matches("\\d+(?:\\.\\d+)?")) {
            tilHours.setError(getString(R.string.minutes_invalid));
            return false;
        }
        if(Integer.parseInt(minutes) < 0) {
            tilMinutes.setError(getString(R.string.minutes_negative));
            return false;
        }
        return true;
    }

    public String getTimezone() {
        final String we = etWe.getText().toString().trim();
        final String hours = etHours.getText().toString().trim();
        final String minutes = etMinutes.getText().toString().trim();
        return we + "#" + hours + "#" + minutes;
    }
}
