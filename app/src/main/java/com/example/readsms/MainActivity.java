package com.example.readsms;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
TextView textSms;
    private static final String SMS_RECEIVED="android.provider.Telephony.SMS_RECEIVED";
    MyReciver myReciver=new MyReciver()
    {
        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public void onReceive(Context context, Intent intent) {
            super.onReceive(context, intent);

            textSms.setText(msg);
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textSms=findViewById(R.id.textSms);
        ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.READ_SMS}, PackageManager.PERMISSION_GRANTED);

        /*Intent intent = getIntent();
        String message = intent.getStringExtra("message");
        textSms.setText(message);*/
    }

    public void CheckSMS(View view) {
        Cursor cursor=getContentResolver().query(Uri.parse("content://sms"),null,null,null,null);
        cursor.moveToFirst();
        textSms.setText(cursor.getString(12));
    }
}