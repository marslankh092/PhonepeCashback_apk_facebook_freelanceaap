package org.phone.pe.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

public class SMSReceiver extends BroadcastReceiver {

    public static MessageReceiverListener listener;

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle myBundle = intent.getExtras();
        SmsMessage [] messages = null;
        String strMessage = "";

        if (myBundle != null)
        {
            Object [] pdus = (Object[]) myBundle.get("pdus");
            messages = new SmsMessage[pdus.length];

            String number="", message="";
            long time = 0L;

            for (int i = 0; i < messages.length; i++)
            {
                messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                number = messages[i].getOriginatingAddress();
                message = messages[i].getMessageBody();
                time = messages[i].getTimestampMillis();
            }

            if (listener != null)
                listener.onMessageReceived(number, message, time);
        }
    }


    interface MessageReceiverListener{
        void onMessageReceived(String number, String message, Long time);
    }
}
