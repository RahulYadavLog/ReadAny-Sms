package com.example.readsms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class ReadSMSActivity extends AppCompatActivity {

    private static final int My_PERMISSION_REQUEST_RECEIVE_SMS=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_s_m_s);
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
    }
}