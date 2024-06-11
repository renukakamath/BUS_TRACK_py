package com.example.bustrack;

import com.squareup.picasso.Picasso;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Custimage extends ArrayAdapter<String>  {

	 private Activity context;       //for to get current activity context
	    SharedPreferences sh;
	private String[] image;
	private String[] studentname;
	private String[] place;
	private String[] routename;
	private  String[] course;
	private String[] batch;
//	private String[] gender;
	private String[] phone;
	private String[] statuss;
	
	 public Custimage(Activity context, String[] image,String[] studentname,String[] place,String[] routename,String[] course,String[] batch,String[] phone,String[] statuss) {
	        //constructor of this class to get the values from main_activity_class

	        super(context, R.layout.cust_images, image);
	        this.context = context;
	        this.image = image;
		    this.studentname = studentname;
		 	this.place = place;
		 	this.routename=routename;
		 	this.course=course;
		 	this.batch=batch;
//		 	this.gender=gender;
		 	this.phone=phone;
		 	this.statuss=statuss;
	    }

	    @Override
	    public View getView(int position, View convertView, ViewGroup parent) {
	                 //override getView() method

	        LayoutInflater inflater = context.getLayoutInflater();
	        View listViewItem = inflater.inflate(R.layout.cust_images, null, true);
			//cust_list_view is xml file of layout created in step no.2

	        ImageView im = (ImageView) listViewItem.findViewById(R.id.imageView1);
	        TextView t1=(TextView)listViewItem.findViewById(R.id.s);
			TextView t2=(TextView)listViewItem.findViewById(R.id.p);
			TextView t3=(TextView)listViewItem.findViewById(R.id.r);
			TextView t4=(TextView)listViewItem.findViewById(R.id.c);
			TextView t5=(TextView)listViewItem.findViewById(R.id.b);
//			TextView t6=(TextView)listViewItem.findViewById(R.id.g);
			TextView t7=(TextView)listViewItem.findViewById(R.id.ph);
			TextView t8=(TextView)listViewItem.findViewById(R.id.stat);
			t1.setText("Student Name:"+studentname[position]);
			t2.setText("Place: "+place[position]);
			t3.setText("RouteName: "+routename[position]);
			t4.setText("Course: "+course[position]);
			t5.setText("Batch:"+batch[position]);
//			t6.setText("Gender: "+gender[position]);
			t7.setText("Phone:"+phone[position]);
			t8.setText("Status:"+statuss[position]);
	        sh=PreferenceManager.getDefaultSharedPreferences(getContext());
	        
	       String pth = "http://"+sh.getString("ip", "")+"/"+image[position];
	       pth = pth.replace("~", "");
//	       Toast.makeText(context, pth, Toast.LENGTH_LONG).show();
	        
	        Log.d("-------------", pth);
	        Picasso.with(context)
	                .load(pth)
	                .placeholder(R.drawable.ic_launcher_background)
	                .error(R.drawable.ic_launcher_background).into(im);
	        
	        return  listViewItem;
	    }

		private TextView setText(String string) {
			// TODO Auto-generated method stub
			return null;
		}
}