package com.lebapps.topgold.sections.home;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.lebapps.topgold.PermissionsUtils;
import com.lebapps.topgold.R;
import com.lebapps.topgold.data.vehicle.Vehicle;
import com.lebapps.topgold.data.vehicle.VehiclesManager;
import com.lebapps.topgold.sections.BaseActivity;
import com.lebapps.topgold.sections.functionality.FunctionalityFragment;
import com.lebapps.topgold.sections.history.HistoryFragment;
import com.lebapps.topgold.sections.settings.SettingsFragment;

import java.util.ArrayList;

/**
 *
 */
public class HomeActivity extends BaseActivity{
    // views
    private AHBottomNavigation bottomNavigation;
    private ViewPager viewPager;

    FunctionalityFragment functionalityFragment;
    HistoryFragment historyFragment;
    SettingsFragment settingsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initViews();
        initFragments();
        configureToolbar(R.string.app_name);
        setupPager();
        PermissionsUtils.checkAndRequestPermissions(this);
    }

    private void initViews() {
        bottomNavigation =  findViewById(R.id.bottomNavigation);
        viewPager =  findViewById(R.id.viewPager);

        AHBottomNavigationItem item1 = new AHBottomNavigationItem(getString(R.string.functionality), R.drawable.ic_power_settings_new_black_24dp);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(getString(R.string.history), R.drawable.ic_history_black_24dp);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem(getString(R.string.settings), R.drawable.ic_settings_black_24dp);

        ArrayList<AHBottomNavigationItem> bottomNavigationItems = new ArrayList<>();

        bottomNavigationItems.add(item1);
        bottomNavigationItems.add(item2);
        bottomNavigationItems.add(item3);

        bottomNavigation.setAccentColor(ContextCompat.getColor(getBaseContext(), R.color.colorPrimary));
        bottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);
        bottomNavigation.setBehaviorTranslationEnabled(false);
        bottomNavigation.addItems(bottomNavigationItems);

        bottomNavigation.setOnTabSelectedListener((position, wasSelected) -> {
            viewPager.setCurrentItem(position, false);
            return true;
        });
        bottomNavigation.setCurrentItem(0);
    }

    @Override
    public void onBackPressed() {
        if (viewPager.getCurrentItem() != 0) {
            viewPager.setCurrentItem(0);
            bottomNavigation.setCurrentItem(0);
        } else {
            super.onBackPressed();
        }
    }

    void initFragments() {
        functionalityFragment = new FunctionalityFragment();
        historyFragment = new HistoryFragment();
        settingsFragment = new SettingsFragment();
    }

    private void setupPager() {
        SectionsPagerAdapter pagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(pagerAdapter);
        // set view pager item as current section
        viewPager.setCurrentItem(0, false);
    }

    /**
     * Returns the fragment at the given position.
     */
    @NonNull
    private Fragment getFragmentAtPosition(int position) {
        if (position == 0) {
            return functionalityFragment;
        } else if (position == 1) {
            return historyFragment;
        } else {
            return settingsFragment;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        final ArrayList<Vehicle> vehicles = VehiclesManager.getInstance().getVehicles();
        if (vehicles.size() == 0) {
            setResult(BaseActivity.VEHICLES_EMPTY);
            finish();
        } else {
            if (requestCode == BaseActivity.RC_ADD_VEHICLE) {
                functionalityFragment.refreshVehicles();
            }
        }
    }

    /**
     * A {@link FragmentStatePagerAdapter} that returns
     * a fragment corresponding to one of the sections/tabs/pages.
     */
    private class SectionsPagerAdapter extends FragmentPagerAdapter {
        private SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return getFragmentAtPosition(position);
        }

        @Override
        public int getCount() {
            return 3;
        }
    }
}
