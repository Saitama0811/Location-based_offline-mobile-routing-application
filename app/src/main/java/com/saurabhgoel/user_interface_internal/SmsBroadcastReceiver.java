package com.saurabhgoel.user_interface_internal;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * A broadcast receiver who listens for incoming SMS
 */
public class SmsBroadcastReceiver extends BroadcastReceiver {

    private static final String TAG = "SmsBroadcastReceiver";
String size="";
ArrayList<String> arr = new ArrayList<>();
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Telephony.Sms.Intents.SMS_RECEIVED_ACTION)) {
            String smsSender = "";
            String smsBody = "";

            for (SmsMessage smsMessage : Telephony.Sms.Intents.getMessagesFromIntent(intent)) {
                smsBody += smsMessage.getMessageBody();
                smsSender=smsMessage.getOriginatingAddress();

            }

            if (smsBody.startsWith(SmsHelper.SMS_CONDITION)) {
                //Log.d(TAG, "Sms with condition detected");
                //Toast.makeText(context, "BroadcastReceiver caught conditional SMS: " + smsBody, Toast.LENGTH_LONG).show();
            }
           Toast.makeText(context,"Message is::"+smsSender,Toast.LENGTH_SHORT).show();

            int j=0;
            if(j==0){
                j++;
                size=smsBody;
            }else{
                arr.add(smsBody);
            }
            Collections.sort(arr, new Comparator<String>(){

                @Override
                public int compare(String o1, String o2) {
                    Integer x1 = Integer.parseInt(o1.substring(o1.lastIndexOf("/")+1, o1.lastIndexOf("*")));
                    Integer x2 = Integer.parseInt(o2.substring(o2.lastIndexOf("/")+1, o2.lastIndexOf("*")));

                    return x1.compareTo(x2);

                }
            });

if(arr.size()==Integer.parseInt(size)) {
    int n = 0;
    for(int i=0;i<arr.size();i++ ) {
        String array[]= arr.get(i).split(";");
        MainActivity.lst.add(array[2]);
        MainActivity.duration.add(array[0]);
        MainActivity.distance.add(array[1]);


    }

    MainActivity.pd.dismiss();
        MainActivity.list.setAdapter(MainActivity.adapter);

}
            Log.d(TAG, "SMS detected: From " + smsSender + " With text " + smsBody);
        }
    }
}
