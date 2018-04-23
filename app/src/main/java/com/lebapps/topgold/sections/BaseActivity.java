package com.lebapps.topgold.sections;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.lebapps.topgold.R;

/**
 *
 */

public class BaseActivity extends AppCompatActivity {
    // result code
    public static final int RESULT_CODE_VEHICLE_ADDED = 50;
    public static final int RESULT_CODE_VEHICLE_EDITED = 51;
    // rc
    public static final int RC_HOME = 10;
    public static final int RC_ADD_VEHICLE = 11;
    public static final int VEHICLES_EMPTY = 13;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void showToast(String message){
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    // TOOLBAR //

    /**
     * Sets the toolbar as the activity's action bar.
     */
    protected void configureToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }

    /**
     * Sets the toolbar as the activity's action bar.
     */
    protected void configureToolbar(int resTitle) {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setTitle(resTitle);
        }
    }

    /**
     * Configures this activity's toolbar.
     */
    protected void configureToolbarWithUpButton(int toolbarId) {
        Toolbar toolbar = findViewById(toolbarId);
        // set toolbar
        setSupportActionBar(toolbar);
        // add click listener on toolbar back arrow
        toolbar.setNavigationOnClickListener(view -> onBackPressed());
        ActionBar supportActionBar = getSupportActionBar();
        // configure back action
        if (supportActionBar != null) {
            supportActionBar.setDisplayShowTitleEnabled(false);
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setDisplayShowHomeEnabled(true);
        }
    }

    /**
     * Configures this activity's toolbar.
     */
    protected void configureToolbarWithUpButton(int toolbarId, int resTitle) {
        Toolbar toolbar = findViewById(toolbarId);
        // set toolbar
        setSupportActionBar(toolbar);
        // add click listener on toolbar back arrow
        toolbar.setNavigationOnClickListener(view -> onBackPressed());
        ActionBar supportActionBar = getSupportActionBar();
        // configure back action
        if (supportActionBar != null) {
            supportActionBar.setDisplayShowTitleEnabled(true);
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setDisplayShowHomeEnabled(true);
            supportActionBar.setTitle(resTitle);
        }
    }

    /**
     * Sets a title to the toolbar.
     * @param resTitle the string resource id of the title.
     */
    protected void setToolbarTitle(int resTitle) {
        // get action bar
        ActionBar supportActionBar = getSupportActionBar();
        // validate
        if (supportActionBar != null) {
            // set title
            supportActionBar.setTitle(resTitle);
            supportActionBar.setDisplayShowTitleEnabled(true);
        }
    }
}
