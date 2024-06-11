package com.example.bustrack;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

public class Usermakepayment extends AppCompatActivity implements JsonResponse{
    EditText e1,e2,e3,e4,e5;
    Button b1;
    String card,cvv,exp,name,amount;
    SharedPreferences sh;
    public static String parent_number,sname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usermakepayment);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        e1=(EditText)findViewById(R.id.etcard);
        e2=(EditText)findViewById(R.id.etcvv);
        e3=(EditText)findViewById(R.id.etexp);
        e4=(EditText)findViewById(R.id.etname);
        e5=(EditText)findViewById(R.id.etamount);

        e5.setText(Studentviewamount.amounts);
        e5.setEnabled(false);

        JsonReq JR = new JsonReq();
        JR.json_response = (JsonResponse) Usermakepayment.this;
        String q = "/user_get_parent_number?lid="+sh.getString("log_id","");
        q = q.replace(" ", "%20");
        JR.execute(q);

        b1=(Button)findViewById(R.id.button);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                card=e1.getText().toString();
                cvv=e2.getText().toString();
                exp=e3.getText().toString();
                name=e4.getText().toString();
                amount=e5.getText().toString();

                if (e1.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(getApplicationContext(),
                            "Enter All The Fields", Toast.LENGTH_LONG).show();
                    e1.setError("");
                    e1.setFocusable(true);

                } else if (e1.getText().toString().length() != 16) {
                    Toast.makeText(getApplicationContext(), "16 Digit",
                            Toast.LENGTH_LONG).show();
                    e1.setError("");
                    e1.setFocusable(true);

                } else if (e2.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(getApplicationContext(),
                            "Enter All The Fields", Toast.LENGTH_LONG).show();
                    e2.setError("");
                    e2.setFocusable(true);

                } else if (e2.getText().toString().length() != 3) {
                    Toast.makeText(getApplicationContext(), "3 Digit",
                            Toast.LENGTH_LONG).show();
                    e2.setError("");
                    e2.setFocusable(true);

                } else if (e3.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(getApplicationContext(),
                            "Enter All The Fields", Toast.LENGTH_LONG).show();
                    e3.setError("");
                    e3.setFocusable(true);
                } else if (e4.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(getApplicationContext(),
                            "Enter All The Fields", Toast.LENGTH_LONG).show();
                    e4.setError("");
                    e4.setFocusable(true);
                }

                else {

                    SmsManager sms = SmsManager.getDefault();
                    sms.sendTextMessage(parent_number, null, "Bus Fare Successfully Paid."+"\nStudent Name "+sname+" \nAmount "+Studentviewamount.amounts, null, null);
                    Toast.makeText(getApplicationContext(),"Successfully send",Toast.LENGTH_LONG).show();

                    JsonReq JR = new JsonReq();
                    JR.json_response = (JsonResponse) Usermakepayment.this;
                    String q = "/usermakepayment?aid="+Studentviewamount.aids + "&amount=" + Studentviewamount.amounts+"&lid="+sh.getString("log_id","");
                    q = q.replace(" ", "%20");
                    JR.execute(q);
                }
            }
        });
    }


    @Override
    public void response(JSONObject jo) {
        // TODO Auto-generated method stub

        try
        {

            String method=jo.getString("method");

            if(method.equalsIgnoreCase("user_get_parent_number")){

                String status=jo.getString("status");
                if(status.equalsIgnoreCase("success"))
                {


                    parent_number=jo.getString("data");
                    sname=jo.getString("data1");


                }
            }
            if(method.equalsIgnoreCase("usermakepayment")){

                String status=jo.getString("status");
                if (status.equalsIgnoreCase("success")) {
                    Toast.makeText(getApplicationContext(), "PAID SUCCESSFULLY", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(), Studenthome.class));
                }
                  else   if (status.equalsIgnoreCase("na")) {
                Toast.makeText(getApplicationContext(), "Already Paid", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(), Studenthome.class));
            }
            else {

                    Toast.makeText(getApplicationContext(), " failed.TRY AGAIN!!", Toast.LENGTH_LONG).show();
                }
            }

        }
        catch(Exception e){
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "haii"+e, Toast.LENGTH_LONG).show();
        }



    }
//
//    @Override
//    public void response(JSONObject jo) {
//        try {
//
//                String status = jo.getString("status");
//                Log.d("pearl", status);
//                if (status.equalsIgnoreCase("success")) {
//                    Toast.makeText(getApplicationContext(), "PAID SUCCESSFULLY", Toast.LENGTH_LONG).show();
//                    startActivity(new Intent(getApplicationContext(), Studenthome.class));
//                }
//                  else   if (status.equalsIgnoreCase("na")) {
//                Toast.makeText(getApplicationContext(), "Already Paid", Toast.LENGTH_LONG).show();
//                startActivity(new Intent(getApplicationContext(), Studenthome.class));
//            }
//            else {
//
//                    Toast.makeText(getApplicationContext(), " failed.TRY AGAIN!!", Toast.LENGTH_LONG).show();
//                }
//        }
//
//        catch (Exception e) {
//            // TODO: handle exception
//            e.printStackTrace();
//            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
//        }
//    }
    public void onBackPressed()
    {
        // TODO Auto-generated method stub
        super.onBackPressed();
        Intent b=new Intent(getApplicationContext(),Studenthome.class);
        startActivity(b);
    }
}