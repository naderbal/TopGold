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

    private HistoryManager() {
        init();
    }

    public static HistoryManager getInstance() {
        if (historyManager == null) {
            historyManager = new HistoryManager();
        }
        return historyManager;
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

    private static class Cache extends SharedPreferencesCache {
        private final String PREF_NAME = "history_manager";
        private final String HISTORY = "history";

        private Cache(Context context) {
            super(context);
        }

        @Override
        protected String getName() {
            return PREF_NAME;
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
