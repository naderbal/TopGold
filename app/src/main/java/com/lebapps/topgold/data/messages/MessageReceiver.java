package com.lebapps.topgold.data.messages;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

import rx.Subscriber;
import rx.subjects.PublishSubject;

/**
 *
 */
public class MessageReceiver extends BroadcastReceiver {

    static PublishSubject<String> subject = PublishSubject.create();

    public static void subscribeToSPublish(Subscriber<String> subscriber) {
        subject.subscribe(subscriber);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")){
            Bundle bundle = intent.getExtras();           //---get the SMS message passed in---
            SmsMessage[] msgs = null;
            if (bundle != null){
                //---retrieve the SMS message received---
                try{
                    Object[] pdus = (Object[]) bundle.get("pdus");
                    msgs = new SmsMessage[pdus.length];
                    for(int i = 0; i < msgs.length; i++){
                        msgs[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
                        String msgBody = msgs[i].getMessageBody();
                        Log.i("Message", msgBody);
                        subject.onNext(msgBody);
                    }
                } catch(Exception ignored){

                }
            }
        }
    }

}
