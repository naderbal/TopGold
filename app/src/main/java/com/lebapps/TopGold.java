package com.lebapps;

import android.app.Application;

/**
 *
 */

public class TopGold extends Application {
    private static TopGold instance;

    public static TopGold getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}
