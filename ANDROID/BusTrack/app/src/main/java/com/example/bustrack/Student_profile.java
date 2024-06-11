package com.example.bustrack;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

public class Student_profile extends AppCompatActivity implements JsonResponse{

    EditText e1,e2,e3,e4,e5,e6;
    TextView t1;
    ImageView img1;
    Button b1;
    String fname,lname,batch,course,phone,parent_number;
    SharedPreferences sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile);

        t1=(TextView)findViewById(R.id.tvname);
        e1=(EditText) findViewById(R.id.etfname);
        e2=(EditText) findViewById(R.id.etlname);
        e3=(EditText) findViewById(R.id.etcourse);
        e4=(EditText) findViewById(R.id.etbatch);
        e5=(EditText) findViewById(R.id.etphone);
        e6=(EditText) findViewById(R.id.etparent);

        img1=(ImageView)findViewById(R.id.imageView2);
//        b1=(Button)findViewById(R.id.btupdate);

        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());


        if(sh.getString("profile","").equalsIgnoreCase("qr_result")){

            JsonReq JR=new JsonReq();
            JR.json_response=(JsonResponse)Student_profile.this;
            String q="/Student_qr_profile?lid="+sh.getString("log_id","")+"&contents="+sh.getString("contents","");
            q=q.replace(" ","%20");
            JR.execute(q);
        }
        else{
            JsonReq JR=new JsonReq();
            JR.json_response=(JsonResponse)Student_profile.this;
            String q="/Student_profile?lid="+sh.getString("log_id","");
            q=q.replace(" ","%20");
            JR.execute(q);
        }



//        b1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//
//            }
//        });

    }

    @Override
    public void response(JSONObject jo) {
        try {

            String status=jo.getString("status");
            Log.d("pearl",status);


            if(status.equalsIgnoreCase("success")) {
                JSONArray ja1 = (JSONArray) jo.getJSONArray("data");

                t1.setText( ja1.getJSONObject(0).getString("fname") + ja1.getJSONObject(0).getString("lname"));
                e1.setText( ja1.getJSONObject(0).getString("fname"));
                e2.setText( ja1.getJSONObject(0).getString("lname"));
                e3.setText( ja1.getJSONObject(0).getString("course"));
                e4.setText( ja1.getJSONObject(0).getString("batch"));
                e5.setText( ja1.getJSONObject(0).getString("phone"));
                e6.setText( ja1.getJSONObject(0).getString("parent_number"));

                String pro_pic=ja1.getJSONObject(0).getString("student_image");

                String pth = "http://"+sh.getString("ip", "")+"/"+pro_pic;
                pth = pth.replace("~", "");
//	       Toast.makeText(context, pth, Toast.LENGTH_LONG).show();

                Log.d("-------------", pth);
                Picasso.with(getApplicationContext())
                        .load(pth)
                        .placeholder(R.drawable.ic_launcher_background)
                        .error(R.drawable.ic_launcher_background).into(img1);

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

        if(sh.getString("usertype","").equalsIgnoreCase("Student")) {
            Intent b = new Intent(getApplicationContext(), Studenthome.class);
            startActivity(b);
        }
        else  if(sh.getString("usertype","").equalsIgnoreCase("driver")) {
            Intent b = new Intent(getApplicationContext(), Userhome.class);
            startActivity(b);
        }
    }
}