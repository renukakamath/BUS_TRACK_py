package com.example.bustrack;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class Parent_home extends AppCompatActivity {

    ImageView b1,b2,b3,b4,b5,b6,b7,b8;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_home);
        b3=(ImageView)findViewById(R.id.btlogout);
        b1=(ImageView)findViewById(R.id.bus);
        b2=(ImageView)findViewById(R.id.pay);
        b4=(ImageView)findViewById(R.id.btattendance);
//        b5=(ImageView)findViewById(R.id.scanqr);
//        b6=(ImageView)findViewById(R.id.app_rating);
        b7=(ImageView)findViewById(R.id.btdriver);
        b8=(ImageView)findViewById(R.id.btnoti);


        b8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Student_view_notification.class));
            }
        });

        b7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Student_view_driver_details.class));
            }
        });
//
//
//        b6.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getApplicationContext(),App_rating.class));
//            }
//        });
//
//        b5.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getApplicationContext(),View_my_qr.class));
//            }
//        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Studentviewbus.class));
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Studentviewamount.class));

            }
        });
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(),Parent_view_attendance.class));
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Login.class));
            }
        });
    }
    public void onBackPressed()
    {
        // TODO Auto-generated method stub
        super.onBackPressed();
        Intent b=new Intent(getApplicationContext(),Parent_home.class);
        startActivity(b);
    }
}