package com.example.bustrack;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class Studentviewseats extends AppCompatActivity implements JsonResponse{
    SharedPreferences sh;
    ListView l1;
    String[] seat,value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studentviewseats);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        l1=(ListView)findViewById(R.id.lvview);
        //l1.setOnItemClickListener(this);
        JsonReq JR=new JsonReq();
        JR.json_response=(JsonResponse)Studentviewseats.this;
        String q="/studentviewseats?lid="+sh.getString("log_id","");
        q=q.replace(" ","%20");
        JR.execute(q);
    }

    @Override
    public void response(JSONObject jo) {

        try {

            String status=jo.getString("status");
            Log.d("pearl",status);


            if(status.equalsIgnoreCase("success")){
                JSONArray ja1=(JSONArray)jo.getJSONArray("data");
               // regno=new String[ja1.length()];
                seat=new String[ja1.length()];
//                latitide=new String[ja1.length()];
//                longitude=new String[ja1.length()];
//                locationdate=new String[ja1.length()];
//                locationtime=new String[ja1.length()];
//                gender=new String[ja1.length()];
//                phone=new String[ja1.length()];
                value=new String[ja1.length()];

                for(int i = 0;i<ja1.length();i++)
                {
                    // studentname[i]=ja1.getJSONObject(i).getString("fname")+" "+ja1.getJSONObject(i).getString("lname");
                  //  regno[i]=ja1.getJSONObject(i).getString("register_number");
                    seat[i]=ja1.getJSONObject(i).getString("seat_capacity");
//                    latitide[i]=ja1.getJSONObject(i).getString("latitude");
//                    longitude[i]=ja1.getJSONObject(i).getString("longitude");
//                    locationtime[i]=ja1.getJSONObject(i).getString("location_time");
//                    locationdate[i]=ja1.getJSONObject(i).getString("location_date");
//                    phone[i]=ja1.getJSONObject(i).getString("phone");
//                    statuss[i]=ja1.getJSONObject(i).getString("students_status");
                    // image[i]=ja1.getJSONObject(i).getString("photo");

                    value[i]="No Of Seat: "+seat[i];


                }
                ArrayAdapter<String> ar=new ArrayAdapter<String>(getApplicationContext(),R.layout.cust_text,value);
                l1.setAdapter(ar);
//                Custimage cc=new Custimage(this,image,studentname,place,routename,course,batch,gender,phone,statuss);
//                l1.setAdapter(cc);
            }


        }

        catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }
    public void onBackPressed()
    {
        // TODO Auto-generated method stub
        super.onBackPressed();
        Intent b=new Intent(getApplicationContext(),Studenthome.class);
        startActivity(b);
    }

}