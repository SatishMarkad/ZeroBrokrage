package com.android.ZerobrokerageHomes;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.JSONValue;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.parser.HttpRequest;
import com.android.zero.utils.AlertDialogs;
import com.android.zero.utils.Constants;
import com.android.zero.utils.DeviceTokenGenerater;
import com.twilio.client.Connection;
import com.twilio.client.Device;
public class SignUpActivity extends Activity {

  private RelativeLayout mLayout_Linear1,mLayout_Linear2,mLayout_Linear3;	
  private TextView txt_VerifyMobileNum,txt_SignUpMobileNum; 
  private EditText edt_UserMobile,edt_verifyCode,edt_SignUpPass,edt_SignEmail,edt_SignUpFristName,edt_SignUpLastName;
  private String str_MobileNum,str_VerifyCode,str_Message,capabilityToken;
  DeviceTokenGenerater phone;
  InputMethodManager mInput;
   protected void onCreate(Bundle savedInstanceState) {
	   
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_signup_screen);
		
		initializtion();
		
	}

	private void initializtion() {
		// TODO Auto-generated method stub
		mInput=(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		mLayout_Linear1=(RelativeLayout) findViewById(R.id.layout_step1);
		mLayout_Linear2=(RelativeLayout) findViewById(R.id.layout_step2);
		mLayout_Linear3=(RelativeLayout) findViewById(R.id.layout_step3);
		
		txt_VerifyMobileNum=(TextView) findViewById(R.id.txt_VerificationCodeStep2);
		txt_SignUpMobileNum=(TextView) findViewById(R.id.txt_VerificationCodeStep3);
		
		edt_UserMobile=(EditText) findViewById(R.id.edt_userNameSignUp);
		edt_verifyCode=(EditText) findViewById(R.id.edt_userNameSignUp2);
		edt_SignUpPass=(EditText) findViewById(R.id.edt_signUpPass);
		edt_SignEmail=(EditText) findViewById(R.id.edt_SignUpEmail);
		edt_SignUpFristName=(EditText) findViewById(R.id.edt_SignUpuserName);
		edt_SignUpLastName=(EditText) findViewById(R.id.edt_SignUpLastName);
		
	}	
	
	public void onGenrateVerificationCode(View v) throws JSONException{

		if(new Constants().isOnline(this)){
			if(edt_UserMobile.getText().toString().length()==10){
				new BackgroundTask1().execute();
			}else{
				new AlertDialogs(this,"",getResources().getString(R.string.alert_mobileNum));
			}
		}else{
			new AlertDialogs(this,"",getResources().getString(R.string.alert_internetconnection));
		}
		mInput.hideSoftInputFromWindow(edt_UserMobile.getWindowToken(),0);
	}
	
	public void onVerifyPhone(View v){
		
		if(new Constants().isOnline(this)){
			if(edt_verifyCode.getText().toString().length()>0){
				new BackgroundTask2().execute();
			}else{
				new AlertDialogs(this,"",getResources().getString(R.string.alert_verificoderequired));
			}
		}else{
			new AlertDialogs(this,"",getResources().getString(R.string.alert_internetconnection));
		}
		
		mInput.hideSoftInputFromWindow(edt_verifyCode.getWindowToken(),0);
	}
	
	public void onCallMeNow(View v){
		phone.connect();

	}

	public void onRegister(View v){
	
		Pattern pattern = Pattern.compile(".+@.+\\.[a-z]+");
		Matcher matcher = pattern.matcher(edt_SignEmail.getText().toString());
		boolean matchFound = matcher.matches();
		if(matchFound){
			if(edt_SignUpPass.getText().toString().length()>0&&
					edt_SignUpFristName.getText().toString().length()>0&&edt_SignUpLastName.getText().toString().length()>0){
				new BackgroundTask3().execute();
			}else{
				new AlertDialogs(this,"",getResources().getString(R.string.alert_fillall_details));
			}
		}else{
			new AlertDialogs(this,"",getResources().getString(R.string.alert_email));
		}
	}
	public void onInfo(View v){
		
	 Toast.makeText(getBaseContext(), "Info",100).show();
	}
		
	private class BackgroundTask1 extends AsyncTask<String,String,String>{

		ProgressDialog mDialog;
		
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			mDialog=ProgressDialog.show(SignUpActivity.this, "", getResources().getString(R.string.msg_dialog), true);
		}
		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub			
			try{
				
				ArrayList<NameValuePair> nameValuePairs = new  ArrayList<NameValuePair>();
			    nameValuePairs.add(new BasicNameValuePair("MobileNumber",edt_UserMobile.getText().toString()));
			    String result=new HttpRequest().HttpRequestPost(getResources().getString(R.string.url_verifyPhone)+"CheckUser", nameValuePairs);
			    JSONObject jArray=new JSONObject(result);
			    str_MobileNum=jArray.getString("MobileNumber");
			    str_VerifyCode=jArray.getString("VerificationCode");
			    str_Message=jArray.getString("Message");
			    
			    if(!str_Message.contentEquals(getResources().getString(R.string.alert_userexists))){
			     capabilityToken = new HttpRequest().HttpRequestGet(getResources().getString(R.string.url_verifyPhone)+"GetCapabilityToken");
	             JSONObject jArray1=new JSONObject(capabilityToken);
	             capabilityToken=jArray1.getString("CapabilityToken");
	             phone=new DeviceTokenGenerater(SignUpActivity.this,capabilityToken,edt_UserMobile.getText().toString());
	             Log.e("Result",""+capabilityToken);
			    }
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
			if(!str_Message.contentEquals(getResources().getString(R.string.alert_userexists))){
				mLayout_Linear1.setVisibility(View.GONE);
				mLayout_Linear2.setVisibility(View.VISIBLE);
				txt_VerifyMobileNum.setText("Mobile Number\n"+str_MobileNum);
				txt_VerifyMobileNum.setVisibility(View.VISIBLE);
			}else{
				new AlertDialogs(SignUpActivity.this,"" ,getResources().getString(R.string.alert_userexists));
			}
		}
	}

private class BackgroundTask2 extends AsyncTask<String,String,String>{

	ProgressDialog mDialog;
	String isVerified;
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		mDialog=ProgressDialog.show(SignUpActivity.this, "", getResources().getString(R.string.msg_dialog), true);
	}
	@Override
	protected String doInBackground(String... arg0) {
		// TODO Auto-generated method stub			
		try{
			ArrayList<NameValuePair> nameValuePairs = new  ArrayList<NameValuePair>();
		    nameValuePairs.add(new BasicNameValuePair("MobileNumber",edt_UserMobile.getText().toString()));
		    nameValuePairs.add(new BasicNameValuePair("VerificationCode",str_VerifyCode));
		    String result=new HttpRequest().HttpRequestPost(getResources().getString(R.string.url_verifyPhone)+"VerifyPhone", nameValuePairs);
		    JSONObject jArray=new JSONObject(result);
		    
		    isVerified=""+jArray.getString("Verified");
		    /*Log.e("Result",""+jArray.getString("Verified"));
		    Log.e("Result",""+jArray.getString("MobileNumber"));
		    Log.e("Result",""+jArray.getBoolean("VerificationCode"));
		    */
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
		if(isVerified.contentEquals("true")){
			mLayout_Linear2.setVisibility(View.GONE);
			mLayout_Linear3.setVisibility(View.VISIBLE);
			txt_SignUpMobileNum.setText("Mobile Number\n"+str_MobileNum);
			
		}else{
			new AlertDialogs(SignUpActivity.this,"" ,getResources().getString(R.string.alert_invalidverificode));
		}
	}
}


private class BackgroundTask3 extends AsyncTask<String,String,String>{

	ProgressDialog mDialog;
	String sEmail="",sMobileNum="";
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		mDialog=ProgressDialog.show(SignUpActivity.this, "", getResources().getString(R.string.msg_dialog), true);
	}
	@Override
	protected String doInBackground(String... arg0) {
		// TODO Auto-generated method stub			
		try{
			Map nameValuePairs = new LinkedHashMap();
		    nameValuePairs.put("UserID",str_MobileNum);
		    nameValuePairs.put("MobileNumber",str_MobileNum);
		    nameValuePairs.put("Password",edt_SignUpPass.getText().toString());
		    nameValuePairs.put("Email",edt_SignEmail.getText().toString());
		    nameValuePairs.put("FirstName",edt_SignUpFristName.getText().toString());
		    nameValuePairs.put("LastName",edt_SignUpLastName.getText().toString());
		    nameValuePairs.put("Balance","0");
		    String json=JSONValue.toJSONString(nameValuePairs);
		    String result=new HttpRequest().HttpRequestPost(getResources().getString(R.string.url_verifyPhone)+"Register",json);
		    JSONObject jArray=new JSONObject(result);
		    sEmail=jArray.getString("Email");
		    sMobileNum=""+jArray.getString("MobileNumber");
		    
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
		if(sMobileNum.equalsIgnoreCase("null")&&!sMobileNum.equals("")&&sEmail.equalsIgnoreCase("null")&&!sEmail.equals("")){
			showAlertDialog(getResources().getString(R.string.alert_signUpSuccess));
		}else{
			
			showAlertDialog(getResources().getString(R.string.alert_signUpUnSuccess));
		}
		
	}
	protected void showAlertDialog(String str){
		AlertDialog.Builder	mDialog=new AlertDialog.Builder(SignUpActivity.this);
		 mDialog.setMessage(str);
		 
	        // Setting Icon to Dialog
	       // alertDialog.setIcon(R.drawable.delete);
	 
	        // Setting Positive "Yes" Button
	        mDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialog,int which) {
	 
	            dialog.dismiss();
	            startActivity(new Intent (SignUpActivity.this,LoginActivity.class));
	    	    finish();
	            }
	        });
	        
	        mDialog.show();
	}
}

@Override
public void onNewIntent(Intent intent)
{
    super.onNewIntent(intent);
    setIntent(intent);
}

@Override
public void onResume()
{
    super.onResume();

    Intent intent = getIntent();
    Device device = intent.getParcelableExtra(Device.EXTRA_DEVICE);
    Connection connection = intent.getParcelableExtra(Device.EXTRA_CONNECTION);
    if (device != null && connection != null) {
        intent.removeExtra(Device.EXTRA_DEVICE);
        intent.removeExtra(Device.EXTRA_CONNECTION);
        phone.handleIncomingConnection(device, connection);
    }
}

}
