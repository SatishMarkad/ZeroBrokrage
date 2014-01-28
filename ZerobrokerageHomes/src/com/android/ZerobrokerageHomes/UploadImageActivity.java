package com.android.ZerobrokerageHomes;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

import org.json.JSONObject;
import org.json.simple.JSONValue;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import com.android.parser.HttpRequest;
import com.android.zero.utils.AlertDialogs;
import com.android.zero.utils.BitmapConverter;
import com.android.zero.utils.Constants;
import com.blundell.test.util.Base64;
public class UploadImageActivity extends Activity {

   private String folder_path;
   private File file;
   private ImageView mImageView;
   private EditText mTitle;
   private CheckBox mDefault;
   private Bitmap mBitMap;
   private SharedPreferences mPreferance;
   private Editor mEditor;
   private int mCounter;
   
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_imageupload_screen);
		
		initializtion();
		
	}

	private void initializtion() {
		// TODO Auto-generated method stub
		folder_path = Environment.getExternalStorageDirectory()+"/ZeroBrokerageHomes_Images/";
		File image_folder = new File(Environment.getExternalStorageDirectory(), "ZeroBrokerageHomes_Images");
		if(image_folder != null && !image_folder.exists())
			image_folder.mkdirs();
		
		mImageView=(ImageView) findViewById(R.id.full_img);
		mTitle=(EditText) findViewById(R.id.edit_Title);
		mDefault=(CheckBox) findViewById(R.id.check_box);
		mPreferance=this.getSharedPreferences("Zero", MODE_PRIVATE);
		mEditor=mPreferance.edit();
	}
	
 public void onBack(View v){
		
		finish();
	}
	public void onPost(View v){
		
		if(new Constants().isOnline(this)){
			if(mTitle.getText().toString().length()>0){
				 mCounter=mPreferance.getInt("Value", 0);
				if(mCounter<5)
				    new BackgroundTask().execute();
				else
					new AlertDialogs(this,"",getResources().getString(R.string.alert_limitupload));
			}else{
				new AlertDialogs(this,"",getResources().getString(R.string.alert_fillall_details));
			}
		}else{
			new AlertDialogs(this,"",getResources().getString(R.string.alert_internetconnection));
		}
	}
	public void onCamera(View v){
		// give a predefined file name for camera image you are going to take
		file = new File(folder_path+"_"+System.currentTimeMillis()+".jpg");
	    Uri outputFileUri = Uri.fromFile(file);
		Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE); 
		cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
		startActivityForResult(cameraIntent,1);
	}
	
    public void onGallary(View v){
		
    	 Intent intent = new Intent();
         intent.setType("image/*");
         intent.setAction(Intent.ACTION_GET_CONTENT);
         startActivityForResult(Intent.createChooser(intent,
                 "Select Picture"),2);
	}
    
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) 
	{
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode ==1 && resultCode == Activity.RESULT_OK){		
			
	        mBitMap=BitmapConverter.getBitmapFromAssets(UploadImageActivity.this.getAssets(),file.getPath(), 100, 80);
			mImageView.setImageBitmap(mBitMap);
		   
		}else if(requestCode ==2 && resultCode == Activity.RESULT_OK){		
			
			Uri selectedImageUri = data.getData();
	        mBitMap =BitmapConverter.getBitmapFromAssets(UploadImageActivity.this.getAssets(),getPath(selectedImageUri), 100, 80);
			mImageView.setImageBitmap(mBitMap);
		   
		}
	}
	
	public String getPath(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }
	
	private class BackgroundTask extends AsyncTask<String,String,String>{

		ProgressDialog mDialog;
		boolean isAuthenticated;
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			mDialog=ProgressDialog.show(UploadImageActivity.this, "", getResources().getString(R.string.msg_dialog), true);
		}
		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub			
			try{
				
			    ByteArrayOutputStream bao = new ByteArrayOutputStream();
			    mBitMap.compress(Bitmap.CompressFormat.JPEG, 90, bao);
			    byte [] ba = bao.toByteArray();
			    String image64=Base64.encode(ba);
				   
				Map nameValuePairs = new LinkedHashMap();
			    nameValuePairs.put("ID",Constants.USERID);
			    nameValuePairs.put("PropertyID",Constants.PROPERTYID);
			    nameValuePairs.put("Title",mTitle.getText().toString());
			    nameValuePairs.put("BlobID","");
			    nameValuePairs.put("ContentType","image*jpeg");
			    nameValuePairs.put("PictureURL","");
			    nameValuePairs.put("isDefault",mDefault.isChecked());
			    nameValuePairs.put("PictureData",image64);
			    String mConvert=JSONValue.toJSONString(nameValuePairs);
			    String str=mConvert.replace("*","/");
			    Log.e("Request",""+str);
			    String result=new HttpRequest().HttpRequestPost(getResources().getString(R.string.url)+"Homes/"+Constants.PROPERTYID+"/Pictures",str);
			    if(result.length()>0){
			    ++mCounter;
			    mEditor.putInt("Value",mCounter);
			    mEditor.commit();
			     isAuthenticated=true;
			    }else
			      isAuthenticated=false;
				}catch(Exception e){
					isAuthenticated=false;
				}
			return null;
		}
		
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			
			 mDialog.dismiss();
			 if(isAuthenticated){
				 new AlertDialogs(UploadImageActivity.this,"" ,getResources().getString(R.string.alert_uploadsucessfull));
			     startActivity(new Intent(UploadImageActivity.this,UploadImageActivity.class));
			     finish();
			 }else
				 new AlertDialogs(UploadImageActivity.this,"" ,getResources().getString(R.string.alert_internetconnectionslow));
			
		}
	}


}
