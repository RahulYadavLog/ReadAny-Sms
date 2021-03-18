package com.example.readsms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
TextView textSms;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textSms=findViewById(R.id.textSms);
        ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.READ_SMS}, PackageManager.PERMISSION_GRANTED);

        Intent intent = getIntent();
        String message = intent.getStringExtra("message");
        textSms.setText(message);
    }

    public void CheckSMS(View view) {
        Cursor cursor=getContentResolver().query(Uri.parse("content://sms"),null,null,null,null);
        cursor.moveToFirst();
        textSms.setText(cursor.getString(12));
    }
}