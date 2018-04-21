package com.lebapps.topgold.data.vehicle;

import com.lebapps.topgold.data.StorageKeys;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 *
 */
public class Vehicle implements Serializable{
    private String number;
    private String password;
    private String name;

    public Vehicle(String number, String password, String name) {
        this.number = number;
        this.password = password;
        this.name = name;
    }

    public Vehicle(JSONObject jsonObject) {
        try {
            if (!jsonObject.isNull(StorageKeys.VEHICLE_NUMBER)) {
                this.number = jsonObject.getString(StorageKeys.VEHICLE_NUMBER);
            }
            if (!jsonObject.isNull(StorageKeys.VEHICLE_PASSWORD)) {
                this.password = jsonObject.getString(StorageKeys.VEHICLE_PASSWORD);
            }
            if (!jsonObject.isNull(StorageKeys.VEHICLE_NAME)) {
                this.name = jsonObject.getString(StorageKeys.VEHICLE_NAME);
            }
        } catch (JSONException ignored) {

        }
    }

    public String getNumber() {
        return number;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public JSONObject toJSON() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(StorageKeys.VEHICLE_NUMBER, number);
            jsonObject.put(StorageKeys.VEHICLE_PASSWORD, password);
            jsonObject.put(StorageKeys.VEHICLE_NAME, name);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vehicle vehicle = (Vehicle) o;

        if (number != null ? !number.equals(vehicle.number) : vehicle.number != null) return false;
        if (password != null ? !password.equals(vehicle.password) : vehicle.password != null)
            return false;
        return name != null ? name.equals(vehicle.name) : vehicle.name == null;
    }

    @Override
    public int hashCode() {
        int result = number != null ? number.hashCode() : 0;
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    public void set(Vehicle newVehicle) {
        name = newVehicle.name;
        number = newVehicle.number;
        password = newVehicle.password;
    }
}
