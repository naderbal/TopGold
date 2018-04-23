package com.lebapps.topgold.data.history;

import android.content.Context;

import com.lebapps.TopGold;
import com.lebapps.topgold.data.caching.SharedPreferencesCache;
import com.lebapps.topgold.data.vehicle.Vehicle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import rx.Subscriber;
import rx.subjects.PublishSubject;

public class HistoryManager {

    private static HistoryManager historyManager;
    private ArrayList<ActionHistory> items;
    PublishSubject<Boolean> subject = PublishSubject.create();

    private HistoryManager() {
        init();
    }

    public static HistoryManager getInstance() {
        if (historyManager == null) {
            historyManager = new HistoryManager();
        }
        return historyManager;
    }

    public void subscribeToSubject(Subscriber<Boolean> subscriber){
        subject.subscribe(subscriber);
    }

    private void init() {
        Cache cache  = getCache();
        items = cache.getItems();
    }

    public void addHistory(ActionHistory history) {
        Cache cache  = getCache();
        this.items.add(history);
        cache.storeItems(items);
    }

    public ArrayList<ActionHistory> getHistory() {
        return items;
    }

    /**
     * Returns a new instance of the cache.
     */
    private static Cache getCache() {
        Context context = TopGold.getInstance().getApplicationContext();
        return new Cache(context);
    }

    public void clearHistory() {
        items.clear();
        getCache().storeItems(items);
    }

    public void setAcc(boolean enabled) {
        getCache().storeAcc(enabled);
        subject.onNext(true);
    }

    public void setOil(boolean enabled) {
        getCache().storeOil(enabled);
        subject.onNext(true);
    }

    public void setElec(boolean enabled) {
        getCache().storeElec(enabled);
        subject.onNext(true);
    }

    public void setSpeed(boolean enabled) {
        getCache().storeSpeed(enabled);
        subject.onNext(true);
    }

    public boolean getAcc(){
        return getCache().getAcc();
    }

    public boolean getOil(){
        return getCache().getOil();
    }

    public boolean getElec(){
        return getCache().getElec();
    }

    public boolean getSpeed(){
        return getCache().getSpeed();
    }

    public boolean getElecSet() {
        return getCache().getElecSet();
    }

    public boolean getOilSet() {
        return getCache().getOilSet();
    }

    public boolean getAccSet() {
        return getCache().getAccSet();
    }

    public boolean getSpeedSet() {
        return getCache().getSpeedSet();
    }

    private static class Cache extends SharedPreferencesCache {
        private final String PREF_NAME = "history_manager";
        private final String HISTORY = "history";
        private final String ACC = "acc";
        private final String OIL = "oil";
        private final String ELEC = "elec";
        private final String SPEED = "speed";

        private Cache(Context context) {
            super(context);
        }

        @Override
        protected String getName() {
            return PREF_NAME;
        }

        private void storeAcc(boolean enabled) {
            storeBoolean(ACC, enabled);
        }

        private void storeOil(boolean enabled) {
            storeBoolean(OIL, enabled);
        }

        private void storeElec(boolean enabled) {
            storeBoolean(ELEC, enabled);
        }

        private void storeSpeed(boolean enabled) {
            storeBoolean(SPEED, enabled);
        }

        private boolean getAccSet() {
            return contains(ACC);
        }

        private boolean getOilSet() {
            return contains(OIL);
        }

        private boolean getElecSet() {
            return contains(ELEC);
        }

        private boolean getSpeedSet() {
            return contains(SPEED);
        }

        private boolean getAcc() {
            return getBoolean(ACC, false);
        }

        private boolean getOil() {
            return getBoolean(OIL, false);
        }

        private boolean getElec() {
            return getBoolean(ELEC, false);
        }

        private boolean getSpeed() {
            return getBoolean(SPEED, false);
        }

        private void storeItems(ArrayList<ActionHistory> items) {
            JSONArray jsonArray = new JSONArray();
            for (ActionHistory item : items) {
                JSONObject jsonObject = item.toJSON();
                jsonArray.put(jsonObject);
            }
            storeString(HISTORY, jsonArray.toString());
        }

        private ArrayList<ActionHistory> getItems() {
            ArrayList<ActionHistory> items = new ArrayList<>();
            try {
                // get session json from cache
                String strJson = getString(HISTORY, null);
                if (strJson != null) {
                    JSONArray array = new JSONArray(strJson);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject jsonObject = array.getJSONObject(i);
                        ActionHistory item = new ActionHistory(jsonObject);
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
