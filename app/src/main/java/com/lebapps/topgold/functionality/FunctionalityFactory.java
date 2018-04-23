package com.lebapps.topgold.functionality;

import android.content.Context;

import com.lebapps.topgold.R;

import java.util.ArrayList;

/**
 *
 */

public class FunctionalityFactory {

    public enum FunctionalityType {
        STATUS,

        ADDRESS,

        TRACKER,

        MONITOR,

        CALL,

        REBOOT,

        ACC_ON,

        ACC_OFF,

        OIL_ON,

        OIL_OFF,

        ELEC_START,

        ELEC_STOP,

        SPEED,

        STOP_SPEED,

        PASSWORD,

        TIME_ZONE
    }

    public static Functionality getFunctionality(Context context, FunctionalityType type) {
        switch (type) {
            case STATUS:
                return new Functionality(R.string.status, R.drawable.ic_status_24dp, "smslink");
            case ADDRESS:
                return new Functionality(R.string.address, R.drawable.ic_address_24dp, "dw");
            case TRACKER:
                return new Functionality(R.string.tracker, R.drawable.ic_tracker_24dp, "tracker");
            case MONITOR:
                return new Functionality(R.string.monitor, R.drawable.ic_monitor_24dp, "monitor");
            case CALL:
                return new Functionality(R.string.call, R.drawable.ic_call_24dp, "call");
            case REBOOT:
                return new Functionality(R.string.reboot, R.drawable.ic_reboot_24dp, "reboot");
            case ACC_ON:
                return new Functionality(R.string.acc_on, R.drawable.ic_acc_off_24dp, "accon");
            case ACC_OFF:
                return new Functionality(R.string.acc_off, R.drawable.ic_acc_on_24dp, "accoff");
            case OIL_ON:
                return new Functionality(R.string.oil_on, R.drawable.ic_oil_black_24dp, "supplyoil");
            case OIL_OFF:
                return new Functionality(R.string.oil_off, R.drawable.ic_oil_black_24dp, "stopoil");
            case ELEC_START:
                return new Functionality(R.string.elec_start, R.drawable.ic_elec_start_24dp, "supplyelec");
            case ELEC_STOP:
                return new Functionality(R.string.elec_stop, R.drawable.ic_elec_stop_24dp, "stopelec");
            case SPEED:
                return new Functionality(R.string.speed, R.drawable.ic_speed_24dp, "speed");
            case STOP_SPEED:
                return new Functionality(R.string.stop_speed, R.drawable.ic_stop_speed_24dp, "nospeed");
            case PASSWORD:
                return new Functionality(R.string.password, R.drawable.ic_password_24dp, "password");
            case TIME_ZONE:
                return new Functionality(R.string.timezone, R.drawable.ic_time_zone_24dp, "timezone");
            default: return null;
        }
    }

    public static ArrayList<Functionality> getAllFunctionalities(Context context) {
        ArrayList<Functionality> functionalities = new ArrayList<>();
        for (FunctionalityType functionalityType : FunctionalityType.values()) {
            functionalities.add(getFunctionality(context, functionalityType));
        }
        return functionalities;
    }
}
