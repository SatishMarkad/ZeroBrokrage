package com.android.ZerobrokerageHomes;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.parser.HttpRequest;
import com.android.zero.utils.AlertDialogs;
import com.android.zero.utils.Constants;
import com.android.ZerobrokerageHomes.R;

public class ForgotActivity extends Activity {

   private RelativeLayout mLayout1;	
   private EditText mUserMobileNo;
   private TextView txt_Message;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_forgotpwd_screen);
		
		initializtion();
		
	}

	private void initializtion() {
		// TODO Auto-generated method stub
		
		mLayout1=(RelativeLayout) findViewById(R.id.layout_centerforgotpwd);
		txt_Message= (TextView) findViewById(R.id.txt_forgotpwdMsg);
		mUserMobileNo=(EditText) findViewById(R.id.edt_userNameforgot);
	}

	
	public void onGenrateVerificationCode(View v) throws JSONException{
		
		if(new Constants().isOnline(this)){
			if(mUserMobileNo.getText().toString().length()==10){
				new BackgroundTask().execute();
			}else{
				new AlertDialogs(this,"",getResources().getString(R.string.alert_mobileNum));
			}
		}else{
			new AlertDialogs(this,"",getResources().getString(R.string.alert_internetconnection));
		}
		InputMethodManager in=(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		in.hideSoftInputFromWindow(mUserMobileNo.getWindowToken(),0);
	}
	
	
	
	public void onInfo(View v){
		
		
	}
	
	
	private class BackgroundTask extends AsyncTask<String,String,String>{

		ProgressDialog mDialog;
		String mMessage;
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			mDialog=ProgressDialog.show(ForgotActivity.this, "", getResources().getString(R.string.msg_dialog), true);
		}
		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub			
			try{
				ArrayList<NameValuePair> nameValuePairs = new  ArrayList<NameValuePair>();
			    nameValuePairs.add(new BasicNameValuePair("MobileNumber",mUserMobileNo.getText().toString()));
			    String result=new HttpRequest().HttpRequestPost(getResources().getString(R.string.url)+"Users/"+"ForgotPassword", nameValuePairs);
			    JSONObject jArray=new JSONObject(result);
			    /*Log.e("MobileNumber",""+jArray.getString("MobileNumber"));
			    Log.e("userExists",""+jArray.getString("userExists"));
			    Log.e("emailSent",""+jArray.getBoolean("emailSent"));
			    Log.e("Message",""+jArray.getString("Message"));*/
			    mMessage=""+jArray.getString("Message");
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
			try{
				if(new Constants().isOnline(ForgotActivity.this)){
			
					if(!mMessage.equalsIgnoreCase("null")&&!mMessage.equals("")){
						txt_Message.setText(mMessage);
						txt_Message.setVisibility(View.VISIBLE);
						mLayout1.setVisibility(View.GONE);
					}else{
						new AlertDialogs(ForgotActivity.this,"",getResources().getString(R.string.alert_internetconnectionslow));
					}
				}else{
					new AlertDialogs(ForgotActivity.this,"",getResources().getString(R.string.alert_internetconnection));
				}
			}catch(Exception e){}
			
		}
	}
}
