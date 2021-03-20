package com.example.readsms;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Telephony;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
TextView textSms;
    public static String HTTPSTR = "http://";
    public static final String MPROURL = HTTPSTR + "192.168.0.31:505";//server
    public static String MAINURL = HTTPSTR + "192.168.0.31:8222";
    private static final String SMS_RECEIVED="android.provider.Telephony.SMS_RECEIVED";
    private static final String TAG="MainActivity";
    MyReciver myReciver=new MyReciver()
    {
        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public void onReceive(Context context, Intent intent) {
            super.onReceive(context, intent);

//            textSms.setText(msg);
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

    SmsAdapter smsAdapter;
    List<SmsModel> smsModels;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    String type;
    String subject="",person="",column_name="";
    TextView smsCount;
    int totalSMS = 0;
    EditText editSearch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        smsCount=(TextView) findViewById(R.id.smsCount);
        editSearch = findViewById(R.id.editSearch);
        recyclerView=findViewById(R.id.recycler_sms);
        ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.READ_SMS}, PackageManager.PERMISSION_GRANTED);

        /*Intent intent = getIntent();
        String message = intent.getStringExtra("message");
        textSms.setText(message);*/


        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                //after the change calling the method and passing the search input
                filter(editable.toString());
                String strTotal= String.valueOf(smsModels.size());
                smsCount.setText(strTotal);

            }
        });

        layoutManager= new GridLayoutManager(MainActivity.this,1);
        recyclerView.setLayoutManager(layoutManager);
        smsModels=new ArrayList<>();
        smsAdapter=new SmsAdapter(smsModels,MainActivity.this);
        recyclerView.setAdapter(smsAdapter);


    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void CheckSMS(View view) {
        Cursor cursor=getContentResolver().query(Uri.parse("content://sms"),null,null,null,null);
        cursor.moveToFirst();
//        textSms.setText(cursor.getString(12));
        getAllSms(MainActivity.this);
        smsCount.setVisibility(View.VISIBLE);
        String strTotal= String.valueOf(totalSMS);
        smsCount.setText(strTotal);

    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void getAllSms(Context context) {

        ContentResolver cr = context.getContentResolver();
        Cursor c = cr.query(Telephony.Sms.CONTENT_URI, null, null, null, null);

        if (c != null) {
            totalSMS = c.getCount();
            if (c.moveToFirst()) {
                for (int j = 0; j < totalSMS; j++) {
                    String smsDate = c.getString(c.getColumnIndexOrThrow(Telephony.Sms.DATE));
                    String number = c.getString(c.getColumnIndexOrThrow(Telephony.Sms.ADDRESS));
                    String body = c.getString(c.getColumnIndexOrThrow(Telephony.Sms.BODY));
                    String id = c.getString(c.getColumnIndexOrThrow("_id"));
                    String thread_id = c.getString(c.getColumnIndexOrThrow("thread_id"));
                    person = c.getString(c.getColumnIndexOrThrow("person"));
                    String protocol = c.getString(c.getColumnIndexOrThrow("protocol"));
                    String read = c.getString(c.getColumnIndexOrThrow("read"));
                    String status = c.getString(c.getColumnIndexOrThrow("status"));
                    String reply_path_present = c.getString(c.getColumnIndexOrThrow("reply_path_present"));
                     subject = c.getString(c.getColumnIndexOrThrow("subject"));
                    String service_center = c.getString(c.getColumnIndexOrThrow("service_center"));
                    String locked = c.getString(c.getColumnIndexOrThrow("locked"));
                    Date dateFormat= new Date(Long.valueOf(smsDate));
                    String strDate= String.valueOf(dateFormat);

                    if(read.equals("0"))
                    {
                        read="false";

                    }
                    else {
                        read="true";
                    }
                    switch (Integer.parseInt(c.getString(c.getColumnIndexOrThrow(Telephony.Sms.TYPE)))) {
                        case Telephony.Sms.MESSAGE_TYPE_INBOX:
                            type = "inbox";
                            break;
                        case Telephony.Sms.MESSAGE_TYPE_SENT:
                            type = "sent";
                            break;
                        case Telephony.Sms.MESSAGE_TYPE_OUTBOX:
                            type = "outbox";
                            break;
                        default:
                            break;
                    }


                    c.moveToNext();
                    smsModels.add(new SmsModel("Column Values",id,thread_id,number,person,strDate, protocol,read,status,type,reply_path_present,subject,body ,service_center,locked));
                    smsAdapter.notifyDataSetChanged();
                }
            }


            c.close();

        } else {
            Toast.makeText(this, "No message to show!", Toast.LENGTH_SHORT).show();
        }
    }
    private void filter(String text) {
        //new array list that will hold the filtered data
        List<SmsModel> filterdNames = new ArrayList<>();

        //looping through existing elements
        for (SmsModel item : smsModels) {
            //if the existing elements contains the search input
            if (item.getAddress().toLowerCase().contains(text.toLowerCase())||item.getBody().toLowerCase().contains(text.toLowerCase())||item.getId().toLowerCase().contains(text.toLowerCase())||item.getType().toLowerCase().contains(text.toLowerCase())||item.getRead().toLowerCase().contains(text.toLowerCase())) {
                //adding the element to filtered list
//                ||item.getBody().toLowerCase().contains(text.toLowerCase())||item.getId().toLowerCase().contains(text.toLowerCase())
                filterdNames.add(item);
            }
        }
        smsAdapter.filterList(filterdNames);
    }


//    private class GetSale extends AsyncTask<Void, Void, Void> {
//        ProgressDialog progressDialog;
//        String json, pass, jsonStr;
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            progressDialog = ProgressDialog.show(MainActivity.this, "Loading...", "Please wait!", true, false);
//        }
//
//        @Override
//        protected Void doInBackground(Void... arg0) {
//            HttpHandler sh = new HttpHandler();
//
//
//            JSONArray root = new JSONArray();
//            JSONObject ob = new JSONObject();
//
//            try {
//                ob.put("coid", null);
//                root.put(0, ob);
//
//                root.put(0, ob);
//                json = root.toString();
//                final String url = MAINURL + "/WebService.asmx/GetJsonDATA?QueryNo=137&co_code=A&invcode=F1B002&opncl=D&co_id=" + workshopexpo.JsonHCo_id + "&b_code=341&txntyp=sa&docod=xx&docno=544&user_id=1&acode=XX&sessionId=sahil&JSONStringFormat1=";
//
//
//                pass = url + json;
//
//                // Making a request to url and getting response
//                jsonStr = sh.makeServiceCall(pass);
//                Log.i(TAG, "doInBackground: RE:- " + jsonStr
//                        + pass);
//            } catch (Exception e) {
//                Log.e(TAG, "doInBackground: ", e);
//            }
//
//
//            Log.i(TAG, "Response from url: " + jsonStr);
//
//            if (jsonStr != null) {
//                try {
//                    JSONObject jsonObj = new JSONObject(jsonStr);
//
//                    // Getting JSON Array node
//                    JSONArray grp_mst = jsonObj.getJSONArray("Sales");
//
//                    // JSONArray jsonArray = new JSONArray(jsonStr);
//                    // JSONObject config = jsonArray.getJSONObject(0);
//
//                    if (!grp_mst.isNull(0)) {
//                        Log.i(TAG, "doInBackground: It  is null");
//
//                        // looping through All Contacts
//                        for (int i = 0; i < grp_mst.length(); i++) {
//                            JSONObject c = grp_mst.getJSONObject(i);
//                            // GrpItem grpItem1 = new GrpItem(c.getString("grp_code"), dir + "/" +  c.getString("image_name") + ".jpg", c.getString("invcod"));
//                            // movieList.add(grpItem1);
//
//                            String count = c.getString("Sales");
//
//                            sale = new SalesModel(count);
//
//                            salesModels.add(sale);
//
//                            //  GrpItem grpItem = new GrpItem(grpcode[k], dir + "/" + image_name[k] + ".jpg", invcode[k]);
//
//                        }
//
//
//                    } else {
//
//                    }
//
//
//                } catch (final JSONException e) {
//                    Log.e(TAG, "Json parsing error: " + e.getMessage());
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
////                                Toast.makeText(getApplicationContext(),
////                                        "Json parsing error: " + e.getMessage(),
////                                        Toast.LENGTH_LONG)
////                                        .show();
//                        }
//                    });
//
//                }
//            } else {
//                Log.e(TAG, "Couldn't get json from server.");
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Toast.makeText(getApplicationContext(),
//                                "Couldn't get json from server.",
//                                Toast.LENGTH_LONG).show();
//                    }
//                });
//
//            }
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void result) {
//            progressDialog.dismiss();
//            salesAdapter.notifyDataSetChanged();
//
//            // super.onPostExecute(result);
////            for (int i = 0; i < invcode.length; i++) {
////                    GrpItem grpItem = new GrpItem(grpcode[i], dir + "/" + image_name[i] + ".jpg", invcode[i]);
////                    movieList.add(grpItem);
////
////                }
////
////                mAdapter.notifyDataSetChanged();
//        }
//
//    }

}