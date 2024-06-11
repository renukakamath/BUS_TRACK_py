package com.example.bustrack;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class Driver_view_attendance extends AppCompatActivity implements JsonResponse {
    EditText e1;
    Button b1;
    ListView l1;
    public static String solutions,log_id,late_ids;
    String[] student_name,date,in,out,val;
    SharedPreferences sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_view_attendance);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());


        log_id = sh.getString("log_id","");
        l1=(ListView)findViewById(R.id.cview);


        JsonReq jr= new JsonReq();
        jr.json_response=(JsonResponse)Driver_view_attendance.this;
        String q="/Driver_view_attendance/?log_id="+log_id;
        jr.execute(q);
//        e1=(EditText)findViewById(R.id.complaint);
//        b1=(Button)findViewById(R.id.btlate);




//        b1.setOnClickListener(new View.OnClickListener() {
//
//
//            @Override
//            public void onClick(View arg0) {
//                // TODO Auto-generated method stub
//                sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//                log_id = sh.getString("log_id","");
//                complaint=e1.getText().toString();
//                JsonReq jr= new JsonReq();
//                jr.json_response=(JsonResponse) Student_late_information.this;
//                String q="/Student_late_information/?complaint="+complaint+"&log_id="+log_id+"&driver_id="+sh.getString("driver_id","");
//
//                q.replace("", "%20");
//                jr.execute(q);
//
//
//
//            }
//        });


    }



    @Override
    public void response(JSONObject jo) {
        // TODO Auto-generated method stub

        try
        {

            String method=jo.getString("method");

            if(method.equalsIgnoreCase("view")){

                String status=jo.getString("status");
                if(status.equalsIgnoreCase("success"))
                {
                    JSONArray ja=(JSONArray)jo.getJSONArray("data");

                    student_name=new String[ja.length()];
                    date=new String[ja.length()];
                    in= new String[ja.length()];
                    out=new String[ja.length()];

                    val= new String[ja.length()];


                    for(int i=0;i<ja.length();i++)
                    {

                        student_name[i]=ja.getJSONObject(i).getString("student_name");
                        date[i]=ja.getJSONObject(i).getString("date");
                        in[i]=ja.getJSONObject(i).getString("in");

                        out[i]=ja.getJSONObject(i).getString("out");
                        val[i]="Student : "+student_name[i]+"\nDate : "+date[i]+"\nIn Time : "+in[i]+"\nOut Time : "+out[i];
                    }

                    l1.setAdapter(new ArrayAdapter<String>(getApplicationContext(), R.layout.cust_text,val));


                }
            }
//            if(method.equalsIgnoreCase("send")){
//
//                String status=jo.getString("status");
//                if(status.equalsIgnoreCase("success"))
//                {
//                    Toast.makeText(getApplicationContext(), "Successfully Send", Toast.LENGTH_LONG).show();
////
//                    startActivity(new Intent(getApplicationContext(), Student_late_information.class));
//                }
//                else
//                {
//                    Toast.makeText(getApplicationContext(), "Failed....", Toast.LENGTH_LONG).show();
//                }
//            }

        }
        catch(Exception e){
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "haii"+e, Toast.LENGTH_LONG).show();
        }



    }


    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), Userhome.class));
    }

}
