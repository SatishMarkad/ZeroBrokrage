package com.android.ZerobrokerageHomes;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.android.parser.HttpRequest;
import com.android.parser.LazyAdapter;
import com.android.zero.utils.Constants;

public class AddPropertyActivity extends Activity  {

  private ArrayList<ArrayList<String>> mRentSalePGArray,mAddDetailsList=new ArrayList<ArrayList<String>>();
  private ArrayList<Bitmap>mImageBitMap=new ArrayList<Bitmap>();
  private ListView mListView;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_addproperty_screen);
		
		initializtion();
		
	}

	private void initializtion() {
		// TODO Auto-generated method stub
		
		mListView=(ListView) findViewById(R.id.layout_ListView);
		new BackgroundTask().execute();
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				
				startActivity(new Intent(AddPropertyActivity.this,EditPropertyDetailsActivity.class).putExtra("ID",mAddDetailsList.get(arg2).get(3)));
				
			}
		});
	}

	
	public void onRent(View v){
		mRentSalePGArray=new ArrayList<ArrayList<String>>();
		
		for(int i=0;i<mAddDetailsList.size();i++){
			ArrayList<String> mArrayRent=new ArrayList<String>();
			mArrayRent.add(mAddDetailsList.get(i).get(10));
			mArrayRent.add(mAddDetailsList.get(i).get(8));
			mArrayRent.add(mAddDetailsList.get(i).get(1));
			mRentSalePGArray.add(mArrayRent);
		}
		
		LazyAdapter mAdapter=new LazyAdapter(AddPropertyActivity.this,mRentSalePGArray,mImageBitMap,0);
		mListView.setAdapter(mAdapter);
		
	}
	
	public void onPG(View v){
		
		
	}
	
	public void onBuy(View v){
		
		mRentSalePGArray=new ArrayList<ArrayList<String>>();
		
		for(int i=0;i<mAddDetailsList.size();i++){
			ArrayList<String> mArrayRent=new ArrayList<String>();
			mArrayRent.add(mAddDetailsList.get(i).get(10));
			mArrayRent.add(mAddDetailsList.get(i).get(8));
			mArrayRent.add(mAddDetailsList.get(i).get(9));
			mRentSalePGArray.add(mArrayRent);
		}
		
		LazyAdapter mAdapter=new LazyAdapter(AddPropertyActivity.this,mRentSalePGArray,mImageBitMap,0);
		mListView.setAdapter(mAdapter);
		
	}
	
	public void onBack(View v){
		
		finish();
	}
	
	public void onCreateNew(View v){
		
		SharedPreferences sharedPreferences = this.getSharedPreferences("Zero", MODE_PRIVATE);
					        Editor editor = sharedPreferences.edit();
				             editor.putInt("Value", 0);
				              editor.commit();

		startActivity(new Intent(AddPropertyActivity.this,MapDetailsActivity.class));
		
	}
  private class BackgroundTask extends AsyncTask<String,String,String>{

		ProgressDialog mDialog;
		String mMessage;
		
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			mDialog=ProgressDialog.show(AddPropertyActivity.this, "", getResources().getString(R.string.msg_dialog), true);
			mImageBitMap.clear();
			mAddDetailsList.clear();
		}
		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub			
			try{
				
			    String result=new HttpRequest().HttpRequestGet(getResources().getString(R.string.url)+"MyAds/"+Constants.USERMOBILENUMBER);
			    JSONArray jArray=new JSONArray(result);
			    Log.e("ADDD",""+result);
			    /*Log.e("MobileNumber",""+jArray.getString("MobileNumber"));
			    Log.e("userExists",""+jArray.getString("userExists"));
			    Log.e("emailSent",""+jArray.getBoolean("emailSent"));
			    Log.e("Message",""+jArray.getString("Message"));*/
			    JSONObject jsonObject;
			   for(int i=0;i<jArray.length();i++){
				   jsonObject =jArray.getJSONObject(i);
				   ArrayList<String> mDetails=new ArrayList<String>();
				   mDetails.add(jsonObject.getString("AvailableFor"));
				   mDetails.add(jsonObject.getString("AreaLocality"));
				   mDetails.add(jsonObject.getString("State"));
				   mDetails.add(jsonObject.getString("PropertyID"));
				   mDetails.add(jsonObject.getString("BuildingSocietyName"));
				   mDetails.add(jsonObject.getString("FlatHouseNumber"));
				   mDetails.add(jsonObject.getString("HomeStyle"));
				   mDetails.add(jsonObject.getString("HomeType"));
				   mDetails.add(jsonObject.getString("LeaseDeposit"));
				   mDetails.add(jsonObject.getString("PGAccomodationFor"));
				   mDetails.add(jsonObject.getString("Rent"));
				   mDetails.add(jsonObject.getString("Span"));
				   mDetails.add(jsonObject.getString("UserID"));
				  
				   mAddDetailsList.add(mDetails);
				   mImageBitMap.add(new HttpRequest().ImageGet(getApplicationContext(),jsonObject.getString("PictureURL")));				   
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
			LazyAdapter mAdapter=new LazyAdapter(AddPropertyActivity.this,mAddDetailsList,mImageBitMap,0);
			mListView.setAdapter(mAdapter);
			
		}
	}
}
