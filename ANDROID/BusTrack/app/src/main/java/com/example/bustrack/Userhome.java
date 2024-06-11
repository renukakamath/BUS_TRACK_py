package com.example.bustrack;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Userhome extends AppCompatActivity {

    ImageView b1,b2,b3,b4,b5,b6,b7,b8;
    SharedPreferences sh;
    TextView t1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userhome);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        b1=(ImageView)findViewById(R.id.rules);
        b2=(ImageView)findViewById(R.id.bcases);
        b3=(ImageView)findViewById(R.id.logout);
        b4=(ImageView)findViewById(R.id.app_rating);
        b5=(ImageView)findViewById(R.id.btlate);
        b6=(ImageView)findViewById(R.id.needs);
        b7=(ImageView)findViewById(R.id.btattendance);
        b8=(ImageView)findViewById(R.id.btnoti);

        t1=(TextView) findViewById(R.id.tvs);


        if(sh.getString("statuss","").equalsIgnoreCase("start")) {
            t1.setText("Stop");
        }
        else   if(sh.getString("statuss","").equalsIgnoreCase("stop")) {
            t1.setText("Start");
        }
        b8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Driver_notification.class));
            }
        });
        b7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Driver_view_attendance.class));
            }
        });
        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sh.getString("statuss","").equalsIgnoreCase("start")) {

                    SharedPreferences.Editor e = sh.edit();
                    e.putString("statuss", "stop");
                    e.commit();
                }
             else   if(sh.getString("statuss","").equalsIgnoreCase("stop")) {
                    SharedPreferences.Editor e = sh.edit();
                    e.putString("statuss", "start");
                    e.commit();
                }

             startActivity(new Intent(getApplicationContext(),Userhome.class));

            }
        });
        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Driver_view_late_request.class));
            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            startActivity(new Intent(getApplicationContext(),Driverviewstudentdetails.class));
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),AndroidBarcodeQrExample.class));
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Login.class));
            }
        });
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),App_rating.class));
            }
        });
    }
    public void onBackPressed()
    {
        // TODO Auto-generated method stub
        super.onBackPressed();
        Intent b=new Intent(getApplicationContext(),Userhome.class);
        startActivity(b);
    }
}