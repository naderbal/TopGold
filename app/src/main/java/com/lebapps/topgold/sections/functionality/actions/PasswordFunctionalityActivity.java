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
public class PasswordFunctionalityActivity extends BaseActivity {
    Functionality functionality;
    Vehicle vehicle;
    EditText etPassword;
    TextInputLayout tilPassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_functionality);
        functionality = (Functionality) getIntent().getSerializableExtra("functionality");
        vehicle = (Vehicle) getIntent().getSerializableExtra("vehicle");
        configureToolbarWithUpButton(R.id.toolbar, functionality.getFunctionalityResource());
        initViews();
    }

    private void initViews() {
        etPassword = findViewById(R.id.etPassword);
        tilPassword = findViewById(R.id.tilPassword);
        findViewById(R.id.btnSend).setOnClickListener(v -> handleSendClicked());
        etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                tilPassword.setError(null);
            }
        });
    }

    private void handleSendClicked() {
        if (validatePassword()){
            Date date = Calendar.getInstance().getTime();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH);
            final String strDate = simpleDateFormat.format(date);

            ActionHistory history = new ActionHistory(strDate, vehicle.getName(), getString(functionality.getFunctionalityResource()));
            HistoryManager.getInstance().addHistory(history);
            //MessageSender.sendSMS(vehicle.getNumber(), getFunctionalityCode(), this::handleSuccess);
        }
    }

    private void handleSuccess() {
        showToast(getString(R.string.message_sent_success));
    }

    public String getFunctionalityCode() {
        return "#" + functionality.getFunctionalityCode() + "#" +vehicle.getPassword() + "#" + getPassword();
    }

    private boolean validatePassword() {
        final String speed = etPassword.getText().toString().trim();
        if (speed.isEmpty()) {
            tilPassword.setError(getString(R.string.password_empty));
            return false;
        }
        return true;
    }

    public String getPassword() {
        return etPassword.getText().toString().trim();
    }
}
