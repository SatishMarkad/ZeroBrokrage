package com.android.ZerobrokerageHomes;

import java.util.LinkedHashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.JSONValue;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

import com.android.parser.HttpRequest;
import com.android.zero.utils.AlertDialogs;
import com.android.zero.utils.Constants;
public class LoginActivity extends Activity {

	private LocationManager locationManager_ = null;
	private Location location_ = null;
	private boolean isGPSEnabled_ = false, isProviderEnabled_ = false;
	private MyLocationListener myLocationListener_ = null;
   private EditText mUserName,mPassword;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login_screen);
		
		initializtion();
				
	}

	private void initializtion() {
		// TODO Auto-generated method stub

		myLocationListener_ = new MyLocationListener();
		currentLocation();
		mUserName=(EditText) findViewById(R.id.edt_userName);
		mPassword=(EditText) findViewById(R.id.edt_password);
		mUserName.setText("9881460202");
		mPassword.setText("test4");
	}

	public void onSignUP(View v){
		
	       startActivity(new Intent (this,SignUpActivity.class));
	       
	       
	}
	
	public void onLogin(View v) throws JSONException{
	
		if(new Constants().isOnline(this)){
			if(mUserName.getText().toString().length()==10&& mPassword.getText().length()>0){
				
				new BackgroundTask().execute();
			}else{
				new AlertDialogs(this,"",getResources().getString(R.string.alert_usernum_pass));
			}
		}else{
			new AlertDialogs(this,"",getResources().getString(R.string.alert_internetconnection));
		}
	}
	
	public void onForgotPassword(View v){
		
		startActivity(new Intent (LoginActivity.this,ForgotActivity.class));
	}
	
	public void onInfo(View v){
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_splash_screen, menu);
		return true;
	}

	private class BackgroundTask extends AsyncTask<String,String,String>{

		ProgressDialog mDialog;
		boolean isAuthenticated;
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			mDialog=ProgressDialog.show(LoginActivity.this, "", getResources().getString(R.string.msg_dialog), true);
		}
		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub			
			try{
				
				Map nameValuePairs = new LinkedHashMap();
			    nameValuePairs.put("MobileNumber",mUserName.getText().toString());
			    nameValuePairs.put("Password",mPassword.getText().toString());
			    nameValuePairs.put("ActivationCode",null);
			    String mConvert=JSONValue.toJSONString(nameValuePairs); 
			    String result=new HttpRequest().HttpRequestPost(getResources().getString(R.string.url)+"users/"+"signon",mConvert);
			    JSONObject jArray=new JSONObject(result);
			    Constants.USERID=jArray.getString("UserID");
			    isAuthenticated=jArray.getBoolean("isAuthenticated");
			    Constants.USERMOBILENUMBER=jArray.getString("MobileNumber");
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
			 if(isAuthenticated){
				 startActivity(new Intent (LoginActivity.this,MainActivity.class));
			     finish();
			 }else{
				 new AlertDialogs(LoginActivity.this,"" ,getResources().getString(R.string.alert_incorrectnamepass));
			 }

			mUserName.setText("");
			mPassword.setText("");
		}
	}
	
	private void currentLocation() {
		// TODO Auto-generated method stub
		try {
			locationManager_ = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

			isGPSEnabled_ = locationManager_
					.isProviderEnabled(LocationManager.GPS_PROVIDER);
			isProviderEnabled_ = locationManager_
					.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
		} catch (Exception exception) {
			exception.printStackTrace();
		}

		if (isGPSEnabled_ ) {
			// register for periodic updates on GPs
			
			locationManager_.requestLocationUpdates(
					LocationManager.GPS_PROVIDER, 0,0,
					myLocationListener_);
			
			location_ = locationManager_.getLastKnownLocation("gps");
			if (location_ == null) {
				location_ = locationManager_.getLastKnownLocation("network");
			}
			if (location_ != null) {
				Constants.mLatitude=location_.getLatitude();
				Constants.mLongitude=location_.getLongitude();
				Log.e("Latitude", ""+Constants.mLatitude);
			}

		} else if (isProviderEnabled_) {
			// register for periodic updates on GPs
			
				locationManager_.requestLocationUpdates(
						LocationManager.NETWORK_PROVIDER, 0,0,
						myLocationListener_);
						
			location_ = locationManager_.getLastKnownLocation("gps");
			if (location_ == null) {
				location_ = locationManager_.getLastKnownLocation("network");
			}
			if (location_ != null) {
				Constants.mLatitude=location_.getLatitude();
				Constants.mLongitude=location_.getLongitude();
				Log.e("Latitude", ""+Constants.mLatitude);
			}

		}
	}

	
		public class MyLocationListener implements LocationListener {
		public void onLocationChanged(Location location) {
			location_ = location;
		}

		public void onProviderDisabled(String provider) {
			//gpsDisabledDialog();
		}

		public void onProviderEnabled(String provider) {
		}

		public void onStatusChanged(String provider, int status, Bundle extras) {
		}
	}
}
