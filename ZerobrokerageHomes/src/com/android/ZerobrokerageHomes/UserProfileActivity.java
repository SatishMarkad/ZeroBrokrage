package com.android.ZerobrokerageHomes;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
import org.json.simple.JSONValue;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.parser.HttpRequest;
import com.android.zero.utils.Constants;
import com.android.ZerobrokerageHomes.R;
import com.google.android.gms.internal.bn;

public class UserProfileActivity extends Activity {

    private TextView txt_Details,edt_Mobile;
    private ImageView img_LogOut,btn_Edit;
    private ScrollView mLayout_Edit;
    private EditText edt_Email,edt_Pass,edt_FristName,edt_LastName;
    private boolean isEditTrue;
    private String str_Details,str_Name,str_Last,str_Email,str_Pass,str_Mobile,str_UserId;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_userprofile_screen);
		
		initializtion();
		
	}

	private void initializtion() {
		// TODO Auto-generated method stub
		
		txt_Details= (TextView) findViewById(R.id.txt_profileDetails);
		img_LogOut=(ImageView) findViewById(R.id.btn_logout);
		mLayout_Edit=(ScrollView) findViewById(R.id.layout_EditProfile);
		btn_Edit=(ImageView) findViewById(R.id.btn_Edit);
		edt_FristName=(EditText) findViewById(R.id.edt_UpdateuserName); 
		edt_LastName=(EditText) findViewById(R.id.edt_UpdateLastName);
		edt_Pass=(EditText) findViewById(R.id.edt_UpdatePass);
		edt_Email=(EditText) findViewById(R.id.edt_UpdateUpEmail);
		edt_Mobile=(TextView) findViewById(R.id.edt_UpdateMobile);
		new BackgroundTask().execute();
	}

	public void onLogOut(View v){
	      
	       finish();
	       
	}
	
	
	
	public void onBack(View v){
		
		if(isEditTrue){
			
		      img_LogOut.setVisibility(View.VISIBLE);
		      mLayout_Edit.setVisibility(View.GONE);
		      txt_Details.setVisibility(View.VISIBLE);
		      isEditTrue=false;
		      btn_Edit.setVisibility(View.VISIBLE);
		}else{
	       finish();
		}
	}
	
	public void onEdit(View v){
		
	      isEditTrue=true;
	      img_LogOut.setVisibility(View.GONE);
	      mLayout_Edit.setVisibility(View.VISIBLE);
	      txt_Details.setVisibility(View.GONE);
	      btn_Edit.setVisibility(View.GONE);
	}
	
	public void onInfo(View v){
		
	}
	
	public void onUpdate(View v){
		
		new UpdateProfielBackgroundTask().execute();
	}
	

	private class BackgroundTask extends AsyncTask<String,String,String>{

		ProgressDialog mDialog;
		
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			mDialog=ProgressDialog.show(UserProfileActivity.this, "", getResources().getString(R.string.msg_dialog), true);
		}
		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub			
			try{
				
			    String result=new HttpRequest().HttpRequestGet(getResources().getString(R.string.url)+"Users/"+Constants.USERMOBILENUMBER);
			    JSONObject jArray=new JSONObject(result);
			    Log.e("Result",""+jArray.getString("FirstName"));
			    Log.e("Result",""+jArray.getString("LastName"));
			    Log.e("Result",""+jArray.getString("MobileNumber"));
			    Log.e("Result",""+jArray.getString("Email"));
			    Log.e("Result",""+jArray.getString("Balance"));
			    /*str_Details=jArray.getString("FirstName")+" "+jArray.getString("LastName")+"\n\n"+jArray.getString("MobileNumber")
		    		    +"\n\n"+jArray.getString("Email")+"\n\n Account Balance :$"+jArray.getString("Balance");*/
		    
			    str_Details="\nFirstName : "+jArray.getString("FirstName")+"\n\nLastName : "+jArray.getString("LastName")+"\n\nMobileNumber : "+jArray.getString("MobileNumber")
			    		    +"\n\nEmailAddress  : "+jArray.getString("Email");
			    
			    str_UserId=jArray.getString("UserID");
			    str_Email=jArray.getString("Email");
			    str_Last=jArray.getString("LastName");
			    str_Mobile=jArray.getString("MobileNumber");
			    str_Name=jArray.getString("FirstName");
			    str_Pass=jArray.getString("Password");
				}catch(Exception e){
					e.printStackTrace();
				}
			return "";
		}
		
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			
			mDialog.dismiss();
			try{
			txt_Details.setText(str_Details);
			img_LogOut.setVisibility(View.VISIBLE);
			
			edt_Email.setText(str_Email);
			edt_FristName.setText(str_Name);
			edt_LastName.setText(str_Last);
			edt_Pass.setText(str_Pass);
			edt_Mobile.setText(str_Mobile);
			
		    img_LogOut.setVisibility(View.VISIBLE);
	        mLayout_Edit.setVisibility(View.GONE);
	        txt_Details.setVisibility(View.VISIBLE);
	        isEditTrue=false;
	        btn_Edit.setVisibility(View.VISIBLE);
;			}catch(Exception e){
				
				
			}
		}
	}
	
	private class UpdateProfielBackgroundTask extends AsyncTask<String,String,String>{

		ProgressDialog mDialog;
		String result;
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			mDialog=ProgressDialog.show(UserProfileActivity.this, "", getResources().getString(R.string.msg_dialog), true);
		}
		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub			
			try{
				Map nameValuePairs = new LinkedHashMap();
			    nameValuePairs.put("MobileNumber",str_Mobile);
			    nameValuePairs.put("FirstName",edt_FristName.getText().toString());
			    nameValuePairs.put("LastName",edt_LastName.getText().toString());
			    nameValuePairs.put("Email",edt_Email.getText().toString());
			    String mConvert=JSONValue.toJSONString(nameValuePairs);
			    result=new HttpRequest().HttpRequestPost(getResources().getString(R.string.url)+"users/updateuser",mConvert);
			    Log.e("Result:",""+mConvert);
			    Log.e("Responce:",""+result);
				}catch(Exception e){
					e.printStackTrace();
				}
			return null;
		}
		
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			
			mDialog.dismiss();
			      new BackgroundTask().execute();
			     // Toast.makeText(getBaseContext(), "Update successfully",Toast.LENGTH_LONG).show();
			
		}
	}

}
