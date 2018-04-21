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
import com.lebapps.topgold.R;
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
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.RECEIVE_SMS, Manifest.permission.SEND_SMS},
                10);
    }

    private void initViews() {
        bottomNavigation =  findViewById(R.id.bottomNavigation);
        viewPager =  findViewById(R.id.viewPager);

        AHBottomNavigationItem item1 = new AHBottomNavigationItem("Functionality", R.drawable.ic_power_settings_new_black_24dp);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem("History", R.drawable.ic_history_black_24dp);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem("Settings", R.drawable.ic_settings_black_24dp);

        ArrayList<AHBottomNavigationItem> bottomNavigationItems = new ArrayList<>();

        bottomNavigationItems.add(item1);
        bottomNavigationItems.add(item2);
        bottomNavigationItems.add(item3);

        bottomNavigation.setAccentColor(ContextCompat.getColor(getBaseContext(), R.color.colorAccent));
        bottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);
        bottomNavigation.setBehaviorTranslationEnabled(false);
        bottomNavigation.addItems(bottomNavigationItems);

        bottomNavigation.setOnTabSelectedListener((position, wasSelected) -> {
            viewPager.setCurrentItem(position, false);
            return true;
        });
        bottomNavigation.setCurrentItem(0);
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
        if (requestCode == BaseActivity.RC_ADD_VEHICLE) {
            functionalityFragment.refreshVehicles();
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