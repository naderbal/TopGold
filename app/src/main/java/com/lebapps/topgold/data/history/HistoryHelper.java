package com.lebapps.topgold.data.history;

import android.content.Context;

import com.lebapps.topgold.data.vehicle.Vehicle;
import com.lebapps.topgold.functionality.Functionality;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;
import java.util.Locale;

/**
 *
 */

public class HistoryHelper {

    public static String generateHistoryMessage(Context context, Functionality functionality, Vehicle vehicle) {
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH);
        final String strDate = simpleDateFormat.format(date);

        return strDate+"\n"+
                vehicle.getName()+"\n"+
                context.getString(functionality.getFunctionalityResource());
    }

}
