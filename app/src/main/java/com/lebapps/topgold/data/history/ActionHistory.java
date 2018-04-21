package com.lebapps.topgold.data.history;

import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 */

public class ActionHistory {
    String date;
    String carName;
    String functionality;

    public ActionHistory(String date, String carName, String functionality) {
        this.date = date;
        this.carName = carName;
        this.functionality = functionality;
    }

    public ActionHistory(JSONObject jsonObject) {
        try {
            if (!jsonObject.isNull("date")) {
                date = jsonObject.getString("date");
            }
            if (!jsonObject.isNull("car_name")) {
                carName = jsonObject.getString("car_name");
            }
            if (!jsonObject.isNull("functionality")) {
                functionality = jsonObject.getString("functionality");
            }
        } catch(JSONException ignored) {

        }
    }

    public String getDate() {
        return date;
    }

    public String getCarName() {
        return carName;
    }

    public String getFunctionality() {
        return functionality;
    }

    public JSONObject toJSON() {
        JSONObject jsonObject = new JSONObject();
        try {
            if (date != null) jsonObject.put("date", date);
            if (carName != null) jsonObject.put("car_name", carName);
            if (functionality != null) jsonObject.put("functionality", functionality);
        } catch (Exception ignored){}
        return jsonObject;
    }
}
