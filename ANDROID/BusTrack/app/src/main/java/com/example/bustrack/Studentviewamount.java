package com.example.bustrack;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class Studentviewamount extends AppCompatActivity implements JsonResponse, AdapterView.OnItemClickListener {
    SharedPreferences sh;
    ListView l1;
    String[] amount,date,value,aid;
    public static String aids,amounts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studentviewamount);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        l1=(ListView)findViewById(R.id.lvview);
        l1.setOnItemClickListener(this);
        JsonReq JR=new JsonReq();
        JR.json_response=(JsonResponse)Studentviewamount.this;
        String q="/studentviewamount?lid="+sh.getString("log_id","");
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
                aid=new String[ja1.length()];
                amount=new String[ja1.length()];
                date=new String[ja1.length()];
//                longitude=new String[ja1.length()];
//                locationdate=new String[ja1.length()];
//                locationtime=new String[ja1.length()];
//                gender=new String[ja1.length()];
//                phone=new String[ja1.length()];
                value=new String[ja1.length()];

                for(int i = 0;i<ja1.length();i++)
                {
                    // studentname[i]=ja1.getJSONObject(i).getString("fname")+" "+ja1.getJSONObject(i).getString("lname");
                    aid[i]=ja1.getJSONObject(i).getString("assign_id");
                    amount[i]=ja1.getJSONObject(i).getString("amount");
                    date[i]=ja1.getJSONObject(i).getString("date");
//                    longitude[i]=ja1.getJSONObject(i).getString("longitude");
//                    locationtime[i]=ja1.getJSONObject(i).getString("location_time");
//                    locationdate[i]=ja1.getJSONObject(i).getString("location_date");
//                    phone[i]=ja1.getJSONObject(i).getString("phone");
//                    statuss[i]=ja1.getJSONObject(i).getString("students_status");
                    // image[i]=ja1.getJSONObject(i).getString("photo");

                    value[i]="Amount: "+amount[i]+"\nDate: "+date[i];


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


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        aids=aid[position];
        amounts=amount[position];

       if(sh.getString("usertype","").equalsIgnoreCase("Student")){

           startActivity(new Intent(getApplicationContext(), Usermakepayment.class));

        }

    }

    public void onBackPressed()
    {
        // TODO Auto-generated method stub
        super.onBackPressed();
        if(sh.getString("usertype","").equalsIgnoreCase("Parent")){
            Intent b=new Intent(getApplicationContext(),Parent_home.class);
            startActivity(b);
        }
        if(sh.getString("usertype","").equalsIgnoreCase("Student")){
            Intent b=new Intent(getApplicationContext(),Studenthome.class);
            startActivity(b);
        }

    }
}