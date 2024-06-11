package com.example.bustrack;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class App_rating extends Activity implements JsonResponse{

    RatingBar ratingBar;
    Button getRating;
    SharedPreferences sh;
    Float rated;
    EditText e1;
    String rat,review,rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_rating);
        getRating = (Button) findViewById(R.id.getRating);
        ratingBar = (RatingBar) findViewById(R.id.rating);

        e1=(EditText)findViewById(R.id.etreview);

        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());


        JsonReq JR=new JsonReq();
        JR.json_response=(JsonResponse)App_rating.this;
        String q = "/View_app_rating?loginid="+sh.getString("log_id","");
        q=q.replace(" ", "%20");
        JR.execute(q);

        getRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float rating =  ratingBar.getRating();
                review=e1.getText().toString();

                JsonReq JR=new JsonReq();
                JR.json_response=(JsonResponse)App_rating.this;
                String q = "/App_rating?rating="+rating+"&review="+review+"&loginid="+sh.getString("log_id","");
                JR.execute(q);
                Log.d("pearl",q);
            }
        });
    }

    @Override
    public void response(JSONObject jo) {
        // TODO Auto-generated method stub


        try {
            String method=jo.getString("method");
            if(method.equalsIgnoreCase("View_app_rating"))
            {
                try{
//                    Toast.makeText(getApplicationContext(),"helloooooooo", Toast.LENGTH_SHORT).show();
                    String status=jo.getString("status");
                    Log.d("result", status);

                    if(status.equalsIgnoreCase("success")){

                        rat=jo.getString("data");
                        rated=Float.parseFloat(rat);
                        e1.setText(jo.getString("data1"));
                        Toast.makeText(getApplicationContext(),rated+"", Toast.LENGTH_SHORT).show();
                        ratingBar.setRating(rated);



                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "no data", Toast.LENGTH_LONG).show();
                    }

                }catch(Exception e)
                {
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                }
            }else if(method.equalsIgnoreCase("App_rating"))
            {
                try {
                    String status=jo.getString("status");
                    if(status.equalsIgnoreCase("success"))
                    {
                        Toast.makeText(getApplicationContext()," Added success", Toast.LENGTH_LONG).show();
                        if(sh.getString("usertype","").equalsIgnoreCase("driver")){

                            Intent b=new Intent(getApplicationContext(),Userhome.class);
                            startActivity(b);
                        }
                        else if(sh.getString("usertype","").equalsIgnoreCase("student")){

                            Intent b=new Intent(getApplicationContext(),Studenthome.class);
                            startActivity(b);
                        }
//                        startActivity(new Intent(getApplicationContext(),App_rating.class));
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "Rating failed",Toast.LENGTH_LONG ).show();
                    }
                } catch (Exception e){
                    // TODO: handle exception
                }
            }

        }

        catch(Exception e)
        {
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }

    }




    public void onBackPressed()
    {
        // TODO Auto-generated method stub
        super.onBackPressed();

        if(sh.getString("usertype","").equalsIgnoreCase("driver")){

            Intent b=new Intent(getApplicationContext(),Userhome.class);
            startActivity(b);
        }
        else if(sh.getString("usertype","").equalsIgnoreCase("student")){

            Intent b=new Intent(getApplicationContext(),Studenthome.class);
            startActivity(b);
        }


    }

}

