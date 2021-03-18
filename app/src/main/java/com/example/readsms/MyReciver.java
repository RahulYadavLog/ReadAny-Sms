package com.example.readsms;

import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

public class MyReciver extends BroadcastReceiver {

    private static final String SMS_RECEIVED="android.provider.Telephony.SMS_RECEIVED";
    private static final String TAG="smsBroadCastReceiver";
    String msg,phone;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG,"Intent Received"+intent.getAction());
        Bundle bundle=intent.getExtras();
        if (bundle!=null)
        {
            Object[] mypdu=(Object[])bundle.get("pdus");
            final SmsMessage[] message=new SmsMessage[mypdu.length];
            for (int i = 0; i <mypdu.length ; i++) {

                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M)
                {
                    String format=bundle.getString("format");
                    message[i]=SmsMessage.createFromPdu((byte[])mypdu[i],format);
                }
                else {
                    message[i]=SmsMessage.createFromPdu((byte[])mypdu[i]);
                }
                msg=message[i].getMessageBody();
                phone=message[i].getOriginatingAddress();
          /*      Intent intent1 = new Intent(context, MainActivity.class);
                intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent1.putExtra("message", message[i].getMessageBody());
                context.startActivity(intent1);*/

            }
            Toast.makeText(context, "Message"+msg+"\nphoneNo"+phone, Toast.LENGTH_LONG).show();
        }

    }
}
