package com.example.readsms;

import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Telephony;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyReciver extends BroadcastReceiver {

    private static final String SMS_RECEIVED="android.provider.Telephony.SMS_RECEIVED";
    private static final String TAG="smsBroadCastReceiver";
    public Pattern p = Pattern.compile("(|^)\\d{1}");
    public Pattern pnumber = Pattern.compile("[0-9]+");
    Pattern phoneNumber = Pattern.compile("(\\d{3})(\\d{3})(\\d{4})");

    ArrayList<String > number=new ArrayList<String>();
    String msg,msg2,msg3,phone;
    long time;
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
                time=message[i].getTimestampMillis();
                phone=message[i].getOriginatingAddress();
                number.clear();
                String str[]=msg.split(" ");
                for (int j = 0; j <str.length ; j++) {
                    String strNum = str[j];
                    Matcher m = pnumber.matcher(str[j]);

                    if (m.find()) {
                        number.add(str[j]);
                    }

                }

                try {
                    if (msg != null) {
                        Matcher m = p.matcher(msg);

                        if (m.find()) {
//                            msg="(" + m.group(1) + ")-" +m.group(2) + "-" + m.group(3);

                            msg = (m.group(0));
                            msg2 = number.get(0);
                            msg3 = number.get(1);
                        }
                    }
                }
                catch(Exception e){}

          /*      Intent intent1 = new Intent(context, MainActivity.class);
                intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent1.putExtra("message", message[i].getMessageBody());
                context.startActivity(intent1);*/

            }
            Toast.makeText(context, "Message"+msg+"\nphoneNo"+phone, Toast.LENGTH_LONG).show();
        }

    }
}
