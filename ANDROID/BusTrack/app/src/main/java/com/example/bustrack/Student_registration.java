package com.example.bustrack;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Student_registration extends AppCompatActivity  implements JsonResponse, AdapterView.OnItemSelectedListener {
    EditText e1,e2,e3,e4,e5,e6,e7,e8,e9,e10,e11;
    Spinner s1,s2;
    Button b1;
    String fname,lname,batch,course,phone,parent_number,username,password;
    String[] place_name,place_id,route_name,route_id;
    String place_ids,route_ids;

    SharedPreferences sh;

    String q;
    ImageButton imgbtn;
//    GifImageView imgbtn,imgbtn1,imgbtn2,imgbtn3;


    public String encodedImage = "", path = "";
    public static String labels,pre,des,title,selectedImagePath;

    private final int GALLERY_CODE = 201;
    private final int REQUEST_TAKE_GALLERY_VIDEO = 305;
    private Uri mImageCaptureUri;

    String fln, ftype = "", upLoadServerUri;

    public static byte[] byteArray,bytearrayimage1;

    File f = null;

    Button btupim;

    private String imagename = "";
    public static Bitmap image;

    private static final int CAMERA_CODE = 101,  CROPING_CODE = 301,READ_EXTERNAL_STORAGE_PERMISSION=1,CAMERA=2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_registration);

        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        e1=(EditText)findViewById(R.id.fname);
        e2=(EditText)findViewById(R.id.lname);
        e3=(EditText)findViewById(R.id.etbatch);
        e4=(EditText)findViewById(R.id.etcourse);
        e5=(EditText)findViewById(R.id.etparent);
//        e6=(EditText)findViewById(R.id.district);
        e8=(EditText)findViewById(R.id.phone);
//        e9=(EditText)findViewById(R.id.email);
        e10=(EditText)findViewById(R.id.username);
        e11=(EditText)findViewById(R.id.password);

        s1=(Spinner)findViewById(R.id.spplace);
        s1.setOnItemSelectedListener(this);

        s2=(Spinner)findViewById(R.id.sproute);
        s2.setOnItemSelectedListener(this);

        b1=(Button)findViewById(R.id.register);

        imgbtn=(ImageButton) findViewById(R.id.imageButton);


        imgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                SharedPreferences.Editor e=sh.edit();
                e.putString("img","img1");
                e.commit();
                final CharSequence[] items = {"Choose from Gallery", "Cancel"};

                AlertDialog.Builder builder = new AlertDialog.Builder(Student_registration.this);
                builder.setTitle("Add File!");
                builder.setItems(items, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {

                        if (items[item].equals("Capture Photo"))
                        {
                            ftype = "image";
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            Date date = new Date(item);
                            SimpleDateFormat df = new SimpleDateFormat("dd-MM-yy-mm-ss");
                            imagename = df.format(date) + ".jpg";
                            f = new File(android.os.Environment.getExternalStorageDirectory(), imagename);
                            mImageCaptureUri = Uri.fromFile(f);
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
                            startActivityForResult(intent, CAMERA_CODE);

                        } else if (items[item].equals("Choose from Gallery")) {
                            ftype = "image";
                            Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(i, GALLERY_CODE);

                        }
                        else if (items[item].equals("Cancel")) {
                            dialog.dismiss();
                        }
                    }
                });
                builder.show();
                //	Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                //startActivityForResult(i, GALLERY_CODE);
            }
        });

        b1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                fname=e1.getText().toString();
                lname=e2.getText().toString();
                batch=e3.getText().toString();
                course=e4.getText().toString();
                parent_number=e5.getText().toString();
//                district=e6.getText().toString();
                phone=e8.getText().toString();
//                email=e9.getText().toString();
                username=e10.getText().toString();
                password=e11.getText().toString();



                if(fname.equalsIgnoreCase("")){
                    Toast.makeText(getApplicationContext(), "Enter All The Fields", Toast.LENGTH_LONG).show();
                    e1.setError("");
                    e1.setFocusable(true);

                }
                else if(lname.equalsIgnoreCase("")){
                    Toast.makeText(getApplicationContext(), "Enter All The Fields", Toast.LENGTH_LONG).show();
                    e2.setError("");
                    e2.setFocusable(true);

                }
                else if(batch.equalsIgnoreCase("")){
                    Toast.makeText(getApplicationContext(), "Enter All The Fields", Toast.LENGTH_LONG).show();
                    e3.setError("");
                    e3.setFocusable(true);

                }
                else if(course.equalsIgnoreCase("")){
                    Toast.makeText(getApplicationContext(), "Enter All The Fields", Toast.LENGTH_LONG).show();
                    e4.setError("");
                    e4.setFocusable(true);

                }
                else if(parent_number.equalsIgnoreCase("")){
                    Toast.makeText(getApplicationContext(), "Enter All The Fields", Toast.LENGTH_LONG).show();
                    e5.setError("");
                    e5.setFocusable(true);
                }


                else if(parent_number.length()!=10){
                    Toast.makeText(getApplicationContext(), "Enter All The Fields", Toast.LENGTH_LONG).show();
                    e5.setError("");
                    e5.setFocusable(true);
                }
//                else if(district.equalsIgnoreCase("")){
//                    Toast.makeText(getApplicationContext(), "Enter All The Fields", Toast.LENGTH_LONG).show();
//                    e6.setError("");
//                    e6.setFocusable(true);
//
//                }

                else if(phone.equalsIgnoreCase("")){
                    Toast.makeText(getApplicationContext(), "Enter All The Fields", Toast.LENGTH_LONG).show();
                    e8.setError("");
                    e8.setFocusable(true);
                }
                else if(phone.length()!=10){
                    Toast.makeText(getApplicationContext(), "Check Your Contact Number", Toast.LENGTH_LONG).show();
                    e5.setError("");
                    e5.setFocusable(true);

                }
//                else if(email.equalsIgnoreCase("")){
//                    Toast.makeText(getApplicationContext(), "Enter All The Fields", Toast.LENGTH_LONG).show();
//                    e9.setError("");
//                    e9.setFocusable(true);
//                }

                else if(username.equalsIgnoreCase("")){
                    Toast.makeText(getApplicationContext(), "Enter All The Fields", Toast.LENGTH_LONG).show();
                    e10.setError("");
                    e10.setFocusable(true);
                }
                else if(password.equalsIgnoreCase("")){
                    Toast.makeText(getApplicationContext(), "Enter All The Fields", Toast.LENGTH_LONG).show();
                    e11.setError("");
                    e11.setFocusable(true);
                }


                else {

                    sendAttach();

//                    JsonReq jr= new JsonReq();
//                    jr.json_response=(JsonResponse) Student_registration.this;
//
//                    String q="/register/?fname="+fname+"&lname="+lname+"&batch="+batch+"&course="+course+"&username="+username+"&password="+password+"&phone="+phone+"&place_ids="+place_ids+"&route_ids="+route_ids+"&parent_number="+parent_number;
//                    q.replace("", "%20");
//                    jr.execute(q);
                }
            }
        });
        JsonReq jr= new JsonReq();
        jr.json_response=(JsonResponse)Student_registration.this;
        String q="/view_place/";
        jr.execute(q);

        JsonReq jr1= new JsonReq();
        jr1.json_response=(JsonResponse)Student_registration.this;
        String q1="/view_route/";
        jr1.execute(q1);

    }



    private void sendAttach() {
        // TODO Auto-generated method stub

        try {
//	            SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//	            String uid = sh.getString("uid", "");



//	        	rid=View_waste_disposal_request.report_id;


            q = "http://" +sh.getString("ip","")+"/api/register";
//	            String q = "/user_upload_file/?image="+imagename+"&desc="+des+"&yts="+yt;
//	        	String q = "http://" +IPSetting.ip+"/TeachersHelper/api.php?action=teacher_upload_notes";
//	        	String q= "api.php?action=teacher_upload_notes&sh_id="+Teacher_view_timetable.s_id+"&desc="+des;
            Toast.makeText(getApplicationContext(), "Byte Array:"+bytearrayimage1.length, Toast.LENGTH_LONG).show();


            Map<String, byte[]> aa = new HashMap<String, byte[]>();

            aa.put("image1",bytearrayimage1);
//            aa.put("image2",bytearrayimage2);
//            aa.put("image3",bytearrayimage3);
//            aa.put("image4",bytearrayimage4);
//	            aa.put("sh_id",Teacher_view_timetable.s_id.getBytes());

//            ,,,,phone,,,

            aa.put("fname",fname.getBytes());
            aa.put("lname",lname.getBytes());
            aa.put("batch",batch.getBytes());
            aa.put("course",course.getBytes());
            aa.put("parent_number",parent_number.getBytes());

            aa.put("phone",phone.getBytes());

            aa.put("place_ids",place_ids.getBytes());
            aa.put("route_ids",route_ids.getBytes());

//            aa.put("lati",LocationService.lati.getBytes());
//            aa.put("longi",LocationService.logi.getBytes());

            aa.put("username",username.getBytes());
            aa.put("password",password.getBytes());

//			aa.put("ftype", ftype.getBytes());
            Log.d(q,"");
            FileUploadAsync fua = new FileUploadAsync(q);
            fua.json_response = (JsonResponse) Student_registration.this;
            fua.execute(aa);
            Toast.makeText(getApplicationContext(), "Byte Array:"+aa, Toast.LENGTH_LONG).show();
//	            String data = fua.getResponse();
//	            stopService(new Intent(getApplicationContext(),Capture.class));
//	            Log.d("response=======", data);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Exception upload : " + e, Toast.LENGTH_SHORT).show();
        }
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_CODE && resultCode == RESULT_OK && null != data) {

            mImageCaptureUri = data.getData();
            Toast.makeText(getApplicationContext(), "Gallery.....", Toast.LENGTH_LONG).show();
//	            System.out.println("Gallery Image URI : " + mImageCaptureUri);
            //   CropingIMG();

            Uri uri = data.getData();
            Log.d("File Uri", "File Uri: " + uri.toString());
            // Get the path
            //String path = null;
            try {
                path = FileUtils.getPath(this, uri);
                Toast.makeText(getApplicationContext(), "path : " + path, Toast.LENGTH_LONG).show();

                //tv1.setText(path.substring(path.lastIndexOf("/") + 1));

                File fl = new File(path);
                int ln = (int) fl.length();
//	                byte[] byteArray = null;

                InputStream inputStream = new FileInputStream(fl);
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                byte[] b = new byte[ln];
                int bytesRead = 0;

                while ((bytesRead = inputStream.read(b)) != -1) {
                    bos.write(b, 0, bytesRead);
                }
                inputStream.close();
                byteArray = bos.toByteArray();

                Bitmap bit = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);


                if(sh.getString("img","").equals("img1")){
                    bytearrayimage1=byteArray;
                    imgbtn.setImageBitmap(bit);
                }

//                if(sh.getString("img","").equals("img2")){
//                    bytearrayimage2=byteArray;
//                    imgbtn1.setImageBitmap(bit);
//                }
//
//                if(sh.getString("img","").equals("img3")){
//                    bytearrayimage3=byteArray;
//                    imgbtn2.setImageBitmap(bit);
//                }
//
//                if(sh.getString("img","").equals("img4")){
//                    bytearrayimage4=byteArray;
//                    imgbtn3.setImageBitmap(bit);
//                }

//				String str = Base64.encodeToString(byteArray, Base64.DEFAULT);
//				encodedImage = str;

            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
            }
        } else if (requestCode == CAMERA_CODE && resultCode == Activity.RESULT_OK) {

//	            System.out.println("Camera Image URI : " + mImageCaptureUri);
//	            //  CropingIMG();
//
            path = f.getPath();

            image = decodeFile(path); //sha corrected

            try {
                int ln = (int) f.length();

                InputStream inputStream = new FileInputStream(f);
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                byte[] b = new byte[ln];
                int bytesRead = 0;

                while ((bytesRead = inputStream.read(b)) != -1) {
                    bos.write(b, 0, bytesRead);
                }
                inputStream.close();
                byteArray = bos.toByteArray();

                String str = Base64.encodeToString(byteArray, Base64.DEFAULT);
                encodedImage = str;
                Toast.makeText(getApplicationContext(), "Camera..... " + encodedImage.length(), Toast.LENGTH_LONG).show();
                Bitmap bm = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);


                if(sh.getString("img","").equals("img1")){
                    bytearrayimage1=byteArray;
                    imgbtn.setImageBitmap(bm);
                }

//                if(sh.getString("img","").equals("img2")){
//                    bytearrayimage2=byteArray;
//                    imgbtn1.setImageBitmap(bm);
//                }
//
//                if(sh.getString("img","").equals("img3")){
//                    bytearrayimage3=byteArray;
//                    imgbtn2.setImageBitmap(bm);
//                }
//
//                if(sh.getString("img","").equals("img4")){
//                    bytearrayimage4=byteArray;
//                    imgbtn3.setImageBitmap(bm);
//                }

            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Cam : " + e.toString(), Toast.LENGTH_LONG).show();
            }
        }
        else if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_TAKE_GALLERY_VIDEO) {
                Uri selectedImageUri = data.getData();

                // OI FILE Manager
                String filemanagerstring = selectedImageUri.getPath();

                // MEDIA GALLERY
//	                selectedImagePath = getPaths(selectedImageUri);
//	                if (selectedImagePath != null) {
//
//	                	Toast.makeText(getApplicationContext(), "Helloo Files", Toast.LENGTH_LONG).show();
//	                    Intent intent = new Intent(User_upload_files.this,
//	                            User_upload_files.class);
//	                    intent.putExtra("path", selectedImagePath);
//	                    startActivity(intent);
//	                }
//	                image = decodeFile(selectedImagePath);
                try {
                    InputStream iStream =   getContentResolver().openInputStream(selectedImageUri);
                    byte[] inputData = getBytes(iStream);
                    Toast.makeText(getApplicationContext(), "Len : " + inputData.length, Toast.LENGTH_LONG).show();
                    byteArray = inputData;
                } catch(Exception e) {}
            }
        }

    }

    public byte[] getBytes(InputStream inputStream) throws Exception {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }

    public Bitmap decodeFile(String path) {//you can provide file path here
        int orientation;
        try {
            if (path == null) {
                return null;
            }
            // decode image size
            //    BitmapFactory.Options o = new BitmapFactory.Options();
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inPreferredConfig = Bitmap.Config.RGB_565;
            o.inJustDecodeBounds = true;
            // Find the correct scale value. It should be the power of 2.
            final int REQUIRED_SIZE = 70;
            int width_tmp = o.outWidth, height_tmp = o.outHeight;
            int scale = 0;
            while (true) {
                if (width_tmp / 2 < REQUIRED_SIZE
                        || height_tmp / 2 < REQUIRED_SIZE)
                    break;
                width_tmp /= 2;
                height_tmp /= 2;
                scale++;
            }
            // decode with inSampleSize
            //  BitmapFactory.Options o2 = new BitmapFactory.Options();
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inPreferredConfig = Bitmap.Config.RGB_565;
            o2.inSampleSize = scale;
            Bitmap bm = BitmapFactory.decodeFile(path, o2);
            Bitmap bitmap = bm;

            ExifInterface exif = new ExifInterface(path);

            orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);

            Log.e("ExifInteface .........", "rotation =" + orientation);

            //exif.setAttribute(ExifInterface.ORIENTATION_ROTATE_90, 90);

            Log.e("orientation", "" + orientation);
            Matrix m = new Matrix();

            if ((orientation == ExifInterface.ORIENTATION_ROTATE_180)) {
                m.postRotate(180);
                //m.postScale((float) bm.getWidth(), (float) bm.getHeight());
                // if(m.preRotate(90)){
                Log.e("in orientation", "" + orientation);
                bitmap = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), m, true);
                return bitmap;
            } else if (orientation == ExifInterface.ORIENTATION_ROTATE_90) {
                m.postRotate(90);
                Log.e("in orientation", "" + orientation);
                bitmap = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), m, true);
                return bitmap;
            } else if (orientation == ExifInterface.ORIENTATION_ROTATE_270) {
                m.postRotate(270);
                Log.e("in orientation", "" + orientation);
                bitmap = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), m, true);
                return bitmap;
            }
            return bitmap;
        } catch (Exception e) {
            return null;
        }
    }

    public String getPath(Context context, Uri uri) throws URISyntaxException {
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            String[] projection = {"_data"};
            Cursor cursor = null;

            try {
                cursor = context.getContentResolver().query(uri, projection, null, null, null);
                int column_index = cursor.getColumnIndexOrThrow("_data");
                if (cursor.moveToFirst()) {
                    return cursor.getString(column_index);
                }
            } catch (Exception e) {
                // Eat it
            }

        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }


    @Override
    public void response(JSONObject jo) {
        // TODO Auto-generated method stub
        try
        {

            String method=jo.getString("method");

            if(method.equalsIgnoreCase("view_place")){

                String status=jo.getString("status");
                if(status.equalsIgnoreCase("success"))
                {
                    JSONArray ja=(JSONArray)jo.getJSONArray("data");
                    place_name=new String[ja.length()];
                    place_id= new String[ja.length()];
                    for(int i=0;i<ja.length();i++)
                    {
                        place_name[i]=ja.getJSONObject(i).getString("place_name");
                        place_id[i]=ja.getJSONObject(i).getString("place_id");
                    }
                    s1.setAdapter(new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,place_name));
                }
            }
            if(method.equalsIgnoreCase("view_route")){

                String status=jo.getString("status");
                if(status.equalsIgnoreCase("success"))
                {
                    JSONArray ja=(JSONArray)jo.getJSONArray("data");
                    route_name=new String[ja.length()];
                    route_id= new String[ja.length()];
                    for(int i=0;i<ja.length();i++)
                    {
                        route_name[i]=ja.getJSONObject(i).getString("route_name");
                        route_id[i]=ja.getJSONObject(i).getString("route_id");
                    }
                    s2.setAdapter(new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,route_name));
                }
            }
            if(method.equalsIgnoreCase("send")){

                String status=jo.getString("status");
                if(status.equalsIgnoreCase("Success"))
                {
                    Toast.makeText(getApplicationContext(), "Registered Successfully", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(), Login.class));
                }
                else  if(status.equalsIgnoreCase("duplicate"))
                {
                    Toast.makeText(getApplicationContext(), "Username Already Exist", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(), Student_registration.class));
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Failed....", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(), Student_registration.class));
                }
            }

        }
        catch(Exception e){
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "haii"+e, Toast.LENGTH_LONG).show();
        }



    }

    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
                               long arg3) {
        // TODO Auto-generated method stub


        if(arg0.getId()==R.id.spplace) {
            place_ids=place_id[arg2];
        }
        if(arg0.getId()==R.id.sproute){
            route_ids=route_id[arg2];

        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }

}
