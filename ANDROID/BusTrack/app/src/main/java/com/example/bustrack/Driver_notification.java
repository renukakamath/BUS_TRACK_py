package com.example.bustrack;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class Driver_notification extends AppCompatActivity implements JsonResponse {
    EditText e1;
    Button b1;
    ListView l1;
    String complaint,log_id;
    String[] date,complaints,statuss,val,solution;
    SharedPreferences sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_notification);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());


        log_id = sh.getString("log_id","");
        l1=(ListView)findViewById(R.id.cview);

        JsonReq jr= new JsonReq();
        jr.json_response=(JsonResponse)Driver_notification.this;
        String q="/Driver_view_notification/?log_id="+log_id;
        jr.execute(q);
        e1=(EditText)findViewById(R.id.complaint);
        b1=(Button)findViewById(R.id.btlate);




        b1.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                log_id = sh.getString("log_id","");
                complaint=e1.getText().toString();
                JsonReq jr= new JsonReq();
                jr.json_response=(JsonResponse) Driver_notification.this;
                String q="/Driver_send_notification/?complaint="+complaint+"&log_id="+log_id;

                q.replace("", "%20");
                jr.execute(q);

            }
        });


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


                    date=new String[ja.length()];
                    complaints= new String[ja.length()];


                    val= new String[ja.length()];


                    for(int i=0;i<ja.length();i++)
                    {

                        date[i]=ja.getJSONObject(i).getString("date");
                        complaints[i]=ja.getJSONObject(i).getString("details");


                        val[i]="\nDetails : "+complaints[i]+"\nDate : "+date[i];
                    }

                    l1.setAdapter(new ArrayAdapter<String>(getApplicationContext(), R.layout.cust_text,val));


                }
            }
            if(method.equalsIgnoreCase("send")){

                String status=jo.getString("status");
                if(status.equalsIgnoreCase("success"))
                {
                    Toast.makeText(getApplicationContext(), "Successfully Send", Toast.LENGTH_LONG).show();
//
                    startActivity(new Intent(getApplicationContext(), Driver_notification.class));
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Failed....", Toast.LENGTH_LONG).show();
                }
            }

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
