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

public class Driver_view_late_request extends AppCompatActivity implements JsonResponse , AdapterView.OnItemClickListener{
    EditText e1;
    Button b1;
    ListView l1;
    public static String solutions,log_id,late_ids;
    String[] late_id,student_name,date,complaints,statuss,val,solution;
    SharedPreferences sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_view_late_request);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());


        log_id = sh.getString("log_id","");
        l1=(ListView)findViewById(R.id.cview);
        l1.setOnItemClickListener(this);

        JsonReq jr= new JsonReq();
        jr.json_response=(JsonResponse)Driver_view_late_request.this;
        String q="/Driver_view_late_request/?log_id="+log_id;
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

                    late_id=new String[ja.length()];
                    student_name=new String[ja.length()];
                    date=new String[ja.length()];
                    complaints= new String[ja.length()];
                    statuss=new String[ja.length()];
                    solution=new String[ja.length()];

                    val= new String[ja.length()];


                    for(int i=0;i<ja.length();i++)
                    {
                        late_id[i]=ja.getJSONObject(i).getString("late_id");
                        student_name[i]=ja.getJSONObject(i).getString("student_name");
                        date[i]=ja.getJSONObject(i).getString("date");
                        complaints[i]=ja.getJSONObject(i).getString("details");

                        solution[i]=ja.getJSONObject(i).getString("reply");
                        val[i]="Student : "+student_name[i]+"\nDetails : "+complaints[i]+"\nReply : "+solution[i]+"\nDate : "+date[i];
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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        late_ids=late_id[position];
        solutions=solution[position];

if(solutions.equalsIgnoreCase("NA")) {


    final CharSequence[] items = {"Send Reply", "Cancel"};

    AlertDialog.Builder builder = new AlertDialog.Builder(Driver_view_late_request.this);
    // builder.setTitle("Add Photo!");
    builder.setItems(items, new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int item) {


            if (items[item].equals("Send Reply")) {

                startActivity(new Intent(getApplicationContext(), Driver_send_reply.class));
            }
//                    else if (items[item].equals("View Delivery Boy")) {
//
//                        startActivity(new Intent(getApplicationContext(), Userviewdeliveryboys.class));
////                    }
//                else if (items[item].equals("Rate")) {
//
//                    startActivity(new Intent(getApplicationContext(), customerrating.class));
//                }
            else if (items[item].equals("Cancel")) {
                dialog.dismiss();
            }
        }

    });
    builder.show();
}
    }
    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), Userhome.class));
    }

}
