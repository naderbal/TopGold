package com.lebapps.topgold.data.messages;

import android.telephony.SmsManager;

/**
 *
 */

public class MessageSender {

    public static void sendSMS(String phoneNo, String msg, Runnable runnable) {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNo, null, msg, null, null);
            runnable.run();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
