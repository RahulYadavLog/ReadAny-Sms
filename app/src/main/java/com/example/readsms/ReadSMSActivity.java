package com.example.readsms;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReadSMSActivity extends AppCompatActivity {

    private static final int My_PERMISSION_REQUEST_RECEIVE_SMS=0;
TextView textSms,textNumber,textTime,textNumber2,textNumber3;
    private static final String SMS_RECEIVED="android.provider.Telephony.SMS_RECEIVED";
    MyReciver myReciver=new MyReciver()
    {
        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public void onReceive(Context context, Intent intent) {
            super.onReceive(context, intent);

            textSms.setText(msg);
            textNumber.setText(phone);
            textNumber2.setText(msg2);
            textNumber3.setText(msg3);
            Date dateFormat= new Date(Long.valueOf(time));
            String strDate= String.valueOf(dateFormat);
            Calendar c = Calendar.getInstance();
            c.setTimeInMillis((time) * 1000L);
            Date d = c.getTime();
            SimpleDateFormat sdf = new SimpleDateFormat("MMM dd yyyy hh:mm:ss");

            String strTime= sdf.format(d);

            textTime.setText(strDate);
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(myReciver,new IntentFilter(SMS_RECEIVED));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(myReciver);
    }
    public Pattern p = Pattern.compile("(|^)\\d{6}");
    public Pattern pnumber = Pattern.compile("[0-9]+");
    Pattern phoneNumber = Pattern.compile("(\\d{3})(\\d{3})(\\d{4})");
    ArrayList<String > number=new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_s_m_s);
        textSms=findViewById(R.id.textSms);
        textNumber=findViewById(R.id.textnumber);
        textNumber2=findViewById(R.id.textnumber2);
        textNumber3=findViewById(R.id.textnumber3);
        textTime=findViewById(R.id.textTime);

        String msg="dgssdg +9155322355";
        String str[]=msg.split(" ");

        for (int j = 0; j <str.length ; j++) {
            String strNum = str[j];
            Matcher m = pnumber.matcher(str[j]);

            if (m.find()) {
                number.add(str[j]);
            }

        }

            if(ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS)!= PackageManager.PERMISSION_GRANTED)
        {
            if (ActivityCompat.shouldShowRequestPermissionRationale(ReadSMSActivity.this,Manifest.permission.RECEIVE_SMS))
            {

            }else {
                ActivityCompat.requestPermissions(ReadSMSActivity.this,new String[]{Manifest.permission.RECEIVE_SMS},My_PERMISSION_REQUEST_RECEIVE_SMS);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode){
            case My_PERMISSION_REQUEST_RECEIVE_SMS:
            {
                if (grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
                {

                    Toast.makeText(this, "Thanks For Permission", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(this, "Well I Con't do any thinks until you permit me", Toast.LENGTH_SHORT).show();
                }
            }
        }

    }

    public void CheckSMS(View view) {
        Intent intent=new Intent(ReadSMSActivity.this,MainActivity.class);
        startActivity(intent);
    }
}