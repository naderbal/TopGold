package com.lebapps.topgold.data.vehicle;

import android.content.Context;

import com.lebapps.TopGold;
import com.lebapps.topgold.data.caching.SharedPreferencesCache;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import rx.Subscriber;
import rx.subjects.PublishSubject;

public class VehiclesManager {

    private static VehiclesManager cartManager;
    private ArrayList<Vehicle> items;
    private PublishSubject<Integer> reservationsTracker;

    private VehiclesManager() {
        init();
    }

    public static VehiclesManager getInstance() {
        if (cartManager == null) {
            cartManager = new VehiclesManager();
        }
        return cartManager;
    }

    private void init() {
        Cache cache  = getCache();
        items = cache.getItems();
        reservationsTracker = PublishSubject.create();
        // emit items size
        reservationsTracker.onNext(items.size());
    }

    public void addVehicle(Vehicle vehicle) {
        Cache cache  = getCache();
        this.items.add(vehicle);
        cache.storeItems(items);
        // emit items size
        reservationsTracker.onNext(items.size());
    }

    public void addVehicles(ArrayList<Vehicle> items) {
        Cache cache  = getCache();
        this.items.clear();
        this.items.addAll(items);
        cache.storeItems(items);
        // emit items size
        reservationsTracker.onNext(items.size());
    }

    public void editVehicle(Vehicle oldVehicle, Vehicle newVehicle) {
        Cache cache = getCache();
        final ArrayList<Vehicle> items = cache.getItems();
        for (Vehicle vehicle : items) {
            if (vehicle.equals(oldVehicle)) {
                vehicle.set(newVehicle);
            }
        }
        cache.storeItems(items);
        this.items = items;
    }

    public void deleteVehicle(Vehicle vehicle) {
        Cache cache = getCache();
        final ArrayList<Vehicle> items = cache.getItems();
        for (Vehicle item : items) {
            if (item.equals(vehicle)) {
                items.remove(item);
            }
        }
        cache.storeItems(items);
        this.items = items;
    }

    public ArrayList<Vehicle> getVehicles() {
        return items;
    }

    public void subscribeToTracker(Subscriber<Integer> subscriber) {
        reservationsTracker.subscribe(subscriber);
    }

    /**
     * Returns a new instance of the cache.
     */
    private static Cache getCache() {
        Context context = TopGold.getInstance().getApplicationContext();
        return new Cache(context);
    }

    public void clearVehicles() {
        items.clear();
        getCache().storeItems(items);
        reservationsTracker.onNext(items.size());
    }

    private static class Cache extends SharedPreferencesCache {
        private final String PREF_NAME = "vehicles_manager";
        private final String VEHICLES = "vehicles";

        private Cache(Context context) {
            super(context);
        }

        @Override
        protected String getName() {
            return PREF_NAME;
        }

        private void storeItems(ArrayList<Vehicle> items) {
            JSONArray jsonArray = new JSONArray();
            for (Vehicle item : items) {
                JSONObject jsonObject = item.toJSON();
                jsonArray.put(jsonObject);
            }
            storeString(VEHICLES, jsonArray.toString());
        }

        private ArrayList<Vehicle> getItems() {
            ArrayList<Vehicle> items = new ArrayList<>();
            try {
                // get session json from cache
                String strJson = getString(VEHICLES, null);
                if (strJson != null) {
                    JSONArray array = new JSONArray(strJson);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject jsonObject = array.getJSONObject(i);
                        Vehicle item = new Vehicle(jsonObject);
                        items.add(item);
                    }
                    // create session object

                }
            } catch (JSONException e) {

            }
            return items;
        }
    }
}
