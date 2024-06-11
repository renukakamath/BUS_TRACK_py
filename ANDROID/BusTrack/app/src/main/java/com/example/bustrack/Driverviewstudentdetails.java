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

public class Driverviewstudentdetails extends AppCompatActivity implements JsonResponse{
    SharedPreferences sh;
    ListView l1;
    String[] studentname,place,routename,image,course,batch,dob,gender,phone,statuss;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driverviewstudentdetails);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        l1=(ListView)findViewById(R.id.lvview);
        JsonReq JR=new JsonReq();
        JR.json_response=(JsonResponse)Driverviewstudentdetails.this;
        String q="/driverviestudentdetails?lid="+sh.getString("log_id","");
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
                studentname=new String[ja1.length()];
                place=new String[ja1.length()];
                routename=new String[ja1.length()];
                image=new String[ja1.length()];
                course=new String[ja1.length()];
                batch=new String[ja1.length()];
//                gender=new String[ja1.length()];
                phone=new String[ja1.length()];
                statuss=new String[ja1.length()];

                for(int i = 0;i<ja1.length();i++)
                {
                    studentname[i]=ja1.getJSONObject(i).getString("fname")+" "+ja1.getJSONObject(i).getString("lname");
                    place[i]=ja1.getJSONObject(i).getString("place_name");
                    routename[i]=ja1.getJSONObject(i).getString("route_name");
                    image[i]=ja1.getJSONObject(i).getString("qr_code");
                    course[i]=ja1.getJSONObject(i).getString("course");
                    batch[i]=ja1.getJSONObject(i).getString("batch");
//                    gender[i]=ja1.getJSONObject(i).getString("gender");
                    phone[i]=ja1.getJSONObject(i).getString("phone");
                    statuss[i]=ja1.getJSONObject(i).getString("students_status");
                   // image[i]=ja1.getJSONObject(i).getString("photo");



                }
//                ArrayAdapter<String> ar=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,value);
//                l1.setAdapter(ar);
                Custimage cc=new Custimage(this,image,studentname,place,routename,course,batch,phone,statuss);
                l1.setAdapter(cc);
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
        Intent b=new Intent(getApplicationContext(),Userhome.class);
        startActivity(b);
    }
}