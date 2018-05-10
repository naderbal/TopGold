package com.lebapps.topgold;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by naderbaltaji on 5/3/18
 */

public class PermissionsUtils {

    public static final int REQUEST_PERMISSION_MULTIPLE = 0;
    public static final int REQUEST_PERMISSION_RECEIVE_SMS = 1;
    public static final int REQUEST_PERMISSION_SEND_SMS = 2;
    public static final int REQUEST_PERMISSION_CALL = 3;
    public static final String RECEIVE_SMS = Manifest.permission.RECEIVE_SMS;
    public static final String SEND_SMS = Manifest.permission.SEND_SMS;
    public static final String CALL = Manifest.permission.CALL_PHONE;

    public static boolean checkAndRequestPermissions(Activity activity) {
        int permissionReceiveSms = ContextCompat.checkSelfPermission(activity, RECEIVE_SMS);
        int permissionLocation = ContextCompat.checkSelfPermission(activity, SEND_SMS);
        int permissionCall = ContextCompat.checkSelfPermission(activity, CALL);

        // Permission List
        List<String> listPermissionsNeeded = new ArrayList<>();

        // receive Permission
        if (permissionReceiveSms != PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, RECEIVE_SMS)) {
                Toast.makeText(activity, "Receive sms permission is required for this app", Toast.LENGTH_SHORT)
                        .show();
            }
            listPermissionsNeeded.add(RECEIVE_SMS);
        }

        // send Permission
        if (permissionLocation != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(SEND_SMS);
        }

        // call Permission
        if (permissionCall != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(CALL);
        }

        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(activity,
                    listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),
                    REQUEST_PERMISSION_MULTIPLE);
            return false;
        }

        return true;
    }

    /**
     * Requests the Camera permission. If the permission has been denied
     * previously, a SnackBar will prompt the user to grant the permission,
     * otherwise it is requested directly.
     */
    public static void requestCameraPermission(Activity activity) {
        // Here, thisActivity is the current activity
        // System.out.println("requestCameraPermission() INITIAL");
        // Toast.makeText(this, "requestCameraPermission() INITIAL",
        // Toast.LENGTH_LONG).show();
        if (ContextCompat.checkSelfPermission(activity,
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.CAMERA)) {
                // Toast.makeText(activity, "Camera permission is
                // needed for this app to run ",
                // Toast.LENGTH_SHORT).show();
                // System.out.println("requestCameraPermission() SHOW INFO");

                // Show an explanation to the user *asynchronously* -- don't
                // block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA},
                        REQUEST_PERMISSION_RECEIVE_SMS);

            } else {
                // No explanation needed, we can request the permission.
                // System.out.println("requestCameraPermission() ASK
                // PERMISSION");

                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA},
                        REQUEST_PERMISSION_RECEIVE_SMS);
            }
            // Permission is granted
        } else {
            System.out.println("requestCameraPermission() PERMISSION ALREADY GRANTED");
        }
    }

    public static void requestLocationPermission(Activity activity) {
        if (ContextCompat.checkSelfPermission(activity,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                Toast.makeText(activity, "LOCATION permission is needed to display location info ", Toast.LENGTH_SHORT)
                        .show();
                // Show an explanation to the user *asynchronously* -- don't
                // block this thread waiting for the user's response! After the
                // user sees the explanation, try again to request the
                // permission.
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        REQUEST_PERMISSION_SEND_SMS);

                Toast.makeText(activity, "REQUEST LOCATION PERMISSION", Toast.LENGTH_LONG).show();

            } else {
                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        REQUEST_PERMISSION_SEND_SMS);
                Toast.makeText(activity, "REQUEST LOCATION PERMISSION", Toast.LENGTH_LONG).show();
                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
            // Permission is granted
        } else {

        }
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean hasPermissions(Context context) {
        List<String> listPermissionsNeeded = new ArrayList<>();
        listPermissionsNeeded.add(RECEIVE_SMS);
        listPermissionsNeeded.add(SEND_SMS);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null) {
            for (String permission : listPermissionsNeeded) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }
}