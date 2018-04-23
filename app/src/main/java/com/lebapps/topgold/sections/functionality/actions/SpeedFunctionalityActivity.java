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
public class SpeedFunctionalityActivity extends BaseActivity {
    Functionality functionality;
    Vehicle vehicle;
    EditText etSpeed;
    TextInputLayout tilSpeed;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speed_functionality);
        functionality = (Functionality) getIntent().getSerializableExtra("functionality");
        vehicle = (Vehicle) getIntent().getSerializableExtra("vehicle");
        configureToolbarWithUpButton(R.id.toolbar, functionality.getFunctionalityResource());
        initViews();
    }

    private void initViews() {
        etSpeed = findViewById(R.id.etSpeed);
        tilSpeed = findViewById(R.id.tilSpeed);
        findViewById(R.id.btnSend).setOnClickListener(v -> handleSendClicked());
        etSpeed.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                tilSpeed.setError(null);
            }
        });
    }

    private void handleSendClicked() {
        if (validateSpeed()){
            Date date = Calendar.getInstance().getTime();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH);
            final String strDate = simpleDateFormat.format(date);

            ActionHistory history = new ActionHistory(strDate, vehicle.getName(), getString(functionality.getFunctionalityResource()));
            HistoryManager.getInstance().addHistory(history);
            MessageSender.sendSMS(vehicle.getNumber(), getFunctionalityCode(), this::handleSuccess);
        }
    }

    private void handleSuccess() {
        HistoryManager.getInstance().setSpeed(true);
        showToast(getString(R.string.message_sent_success));
    }

    public String getFunctionalityCode() {
        return "#" + functionality.getFunctionalityCode() + "#" +vehicle.getPassword() + "#" + getSpeed();
    }

    private boolean validateSpeed() {
        final String speed = etSpeed.getText().toString().trim();
        if (speed.isEmpty()) {
            tilSpeed.setError(getString(R.string.speed_empty));
            return false;
        }
        if(!speed.matches("\\d+(?:\\.\\d+)?")) {
            tilSpeed.setError(getString(R.string.speed_error));
            return false;
        }
        if(Integer.parseInt(speed) < 0) {
            tilSpeed.setError(getString(R.string.speed_negative_error));
            return false;
        }
        return true;
    }

    public String getSpeed() {
        final String speed = etSpeed.getText().toString().trim();
        if (Integer.parseInt(speed) < 100) {
            return "0"+speed;
        }
        return speed;
    }
}
