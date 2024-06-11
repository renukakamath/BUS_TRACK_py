package com.example.bustrack;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

public class Student_view_driver_details extends AppCompatActivity implements JsonResponse{

    SharedPreferences sh;
    ImageView img1;
    TextView t1,t2;
    public static String name,phone,driver_id;
    Button b1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_view_driver_details);

        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        img1=(ImageView) findViewById(R.id.imgdriver);
        t1=(TextView) findViewById(R.id.tvname);
        t2=(TextView) findViewById(R.id.tvphone);

        b1=(Button)findViewById(R.id.btlate);

        if(sh.getString("usertype","").equalsIgnoreCase("Parent")){

            b1.setVisibility(View.GONE);

        }
        else  if(sh.getString("usertype","").equalsIgnoreCase("Student")){

            b1.setVisibility(View.VISIBLE);
            b1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    startActivity(new Intent(getApplicationContext(),Student_late_information.class));
                }
            });

        }


        JsonReq JR=new JsonReq();
        JR.json_response=(JsonResponse)Student_view_driver_details.this;
        String q="/Student_view_driver_details?lid="+sh.getString("log_id","");
        q=q.replace(" ","%20");
        JR.execute(q);




    }
    @Override
    public void response(JSONObject jo) {

        try {

            String status=jo.getString("status");
            Log.d("pearl",status);


            if(status.equalsIgnoreCase("success")){
//                JSONArray ja1=(JSONArray)jo.getJSONArray("data");
                t1.setText(jo.getString("data"));
                t2.setText(jo.getString("data2"));

                String photo=jo.getString("data1");
                driver_id =jo.getString("data3");

                SharedPreferences.Editor e = sh.edit();
                e.putString("driver_id", driver_id);
                e.commit();

                String pth = "http://"+sh.getString("ip", "")+"/"+photo;
                pth = pth.replace("~", "");
//	       Toast.makeText(context, pth, Toast.LENGTH_LONG).show();

                Log.d("-------------", pth);
                Picasso.with(getApplicationContext())
                        .load(pth)
                        .placeholder(R.drawable.ic_launcher_background)
                        .error(R.drawable.ic_launcher_background).into(img1);

//                amount=new String[ja1.length()];
//                date=new String[ja1.length()];
////                longitude=new String[ja1.length()];
////                locationdate=new String[ja1.length()];
////                locationtime=new String[ja1.length()];
////                gender=new String[ja1.length()];
////                phone=new String[ja1.length()];
//                value=new String[ja1.length()];

//                for(int i = 0;i<ja1.length();i++)
//                {
//                    // studentname[i]=ja1.getJSONObject(i).getString("fname")+" "+ja1.getJSONObject(i).getString("lname");
//                    aid[i]=ja1.getJSONObject(i).getString("assign_id");
//                    amount[i]=ja1.getJSONObject(i).getString("amount");
//                    date[i]=ja1.getJSONObject(i).getString("date");
////                    longitude[i]=ja1.getJSONObject(i).getString("longitude");
////                    locationtime[i]=ja1.getJSONObject(i).getString("location_time");
////                    locationdate[i]=ja1.getJSONObject(i).getString("location_date");
////                    phone[i]=ja1.getJSONObject(i).getString("phone");
////                    statuss[i]=ja1.getJSONObject(i).getString("students_status");
//                    // image[i]=ja1.getJSONObject(i).getString("photo");
//
//                    value[i]="Amount: "+amount[i]+"\nDate: "+date[i];


//                }
//                ArrayAdapter<String> ar=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,value);
//                l1.setAdapter(ar);
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