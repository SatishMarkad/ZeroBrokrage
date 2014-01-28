package com.android.ZerobrokerageHomes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.JSONValue;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.ViewPager.LayoutParams;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;

import com.android.parser.HttpRequest;
import com.android.zero.utils.AlertDialogs;
import com.android.zero.utils.Constants;
public class EditPropertyDetailsActivity extends Activity  {

    private Gallery mGallery;
    private ArrayList<Bitmap>mImageBitmap;
    private HashMap<String,String> mPropetyDetails;
    private String mPropertyId;
    public Button mButton;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_editpropertydetails_screen);
		
		initializtion();
		mPropertyId=getIntent().getStringExtra("ID");
	}
	
	private void initializtion() {
		// TODO Auto-generated method stub
		mButton=(Button) findViewById(R.id.button1);
		mGallery=(Gallery) findViewById(R.id.view_Gallery);
		new GalleryFlipImageSpeed(this);
		new BackgroundTaska().execute();
	}
	
	public void onBack(View v){
		finish();
	}
	
	public void onEdit(View v){
		startActivity(new Intent(EditPropertyDetailsActivity.this,MapDetailsActivity.class).putExtra("Edit", true).putExtra("AllDetails",mPropetyDetails));
        finish();
	}
	
	public void onDelete(View v){
		
		new DeActiveAddTask().execute();
	}
	
	
	private class BackgroundTaska extends AsyncTask<String,String,String>{

		ProgressDialog mDialog;
		String mMessage;
		
		
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			mDialog=ProgressDialog.show(EditPropertyDetailsActivity.this, "", getResources().getString(R.string.msg_dialog), true);
			mPropetyDetails=new HashMap<String, String>();
			mImageBitmap=new ArrayList<Bitmap>();
		}
		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub			
			try{
				
			    String result=new HttpRequest().HttpRequestGet(getResources().getString(R.string.url)+"Homes/"+mPropertyId);
			    
			    JSONObject jObject=new JSONObject(result);
			    JSONArray jArray=new JSONArray(jObject.getString("Pictures"));
			    JSONObject jExternalAmenity=new JSONObject(jObject.getString("ExternalAmenity"));
			    JSONObject jInternalAmenity=new JSONObject(jObject.getString("InternalAmenity"));
			    JSONObject jLocation=new JSONObject(jObject.getString("Location"));
			    JSONObject jPropertyType=new JSONObject(jObject.getString("PropertyType"));
			    mPropetyDetails.put("mPropertyId",mPropertyId);
			    mPropetyDetails.put("AgeOfBuilding",""+jObject.getString("AgeOfBuilding"));
			    mPropetyDetails.put("AvailableFor",""+jObject.getString("AvailableFor"));
			    mPropetyDetails.put("AvailableOn",""+jObject.getString("AvailableOn"));
			    mPropetyDetails.put("Balconies",""+jObject.getString("Balconies"));
			    mPropetyDetails.put("Baths",""+jObject.getString("Baths"));
			    mPropetyDetails.put("Beds",""+jObject.getString("Beds"));
			    mPropetyDetails.put("BuiltupArea",""+jObject.getString("BuiltupArea"));
			    mPropetyDetails.put("CarpetArea",""+jObject.getString("CarpetArea"));
			    mPropetyDetails.put("Cost",""+jObject.getString("Cost"));
			    mPropetyDetails.put("Description",""+jObject.getString("Description"));
			    mPropetyDetails.put("FloorNo",""+jObject.getString("FloorNo"));
			    mPropetyDetails.put("LeaseDeposit",""+jObject.getString("LeaseDeposit"));
			    mPropetyDetails.put("LeaseTerm",""+jObject.getString("LeaseTerm"));
			    mPropetyDetails.put("Maintenance",""+jObject.getString("Maintenance"));
			    mPropetyDetails.put("NoOfCarParkings",""+jObject.getString("NoOfCarParkings"));
			    mPropetyDetails.put("NoOfLifts",""+jObject.getString("NoOfLifts"));
			    mPropetyDetails.put("PGAccomodationFor",""+jObject.getString("PGAccomodationFor"));
			    mPropetyDetails.put("PossessionDate",""+jObject.getString("PossessionDate"));
			    mPropetyDetails.put("ReadyPossesion",""+jObject.getString("ReadyPossesion"));
			    mPropetyDetails.put("UnderConstruction",""+jObject.getString("UnderConstruction"));
			    mPropetyDetails.put("Rent",""+jObject.getString("Rent"));
			    mPropetyDetails.put("TotalBuildings",""+jObject.getString("TotalBuildings"));
			    mPropetyDetails.put("TotalFloors",""+jObject.getString("TotalFloors"));
			    
			    mPropetyDetails.put("Clubhouse",""+jExternalAmenity.getString("Clubhouse"));
			    mPropetyDetails.put("GarbageChute",""+jExternalAmenity.getString("GarbageChute"));
			    mPropetyDetails.put("Garden",""+jExternalAmenity.getString("Garden"));
			    mPropetyDetails.put("Gym",""+jExternalAmenity.getString("Gym"));
			    mPropetyDetails.put("InternetWifi",""+jExternalAmenity.getString("InternetWifi"));
			    mPropetyDetails.put("Lift",""+jExternalAmenity.getString("Lift"));
			    mPropetyDetails.put("MaintenanceStaff",""+jExternalAmenity.getString("MaintenanceStaff"));
			    mPropetyDetails.put("Playarea",""+jExternalAmenity.getString("Playarea"));
			    mPropetyDetails.put("PowerBackup",""+jExternalAmenity.getString("PowerBackup"));
			    mPropetyDetails.put("RWH",""+jExternalAmenity.getString("RWH"));
			    mPropetyDetails.put("ReservedParking",""+jExternalAmenity.getString("ReservedParking"));
			    mPropetyDetails.put("STP",""+jExternalAmenity.getString("STP"));
			    mPropetyDetails.put("Security",""+jExternalAmenity.getString("Security"));
			    mPropetyDetails.put("ServiceLift",""+jExternalAmenity.getString("ServiceLift"));
			    mPropetyDetails.put("SwimmingPool",""+jExternalAmenity.getString("SwimmingPool"));
			    mPropetyDetails.put("VisitorParking",""+jExternalAmenity.getString("VisitorParking"));
					
	    	    mPropetyDetails.put("Furnished",""+jInternalAmenity.getString("Furnished"));
			    mPropetyDetails.put("GasLeakDetector",""+jInternalAmenity.getString("GasLeakDetector"));
			    mPropetyDetails.put("Intercom",""+jInternalAmenity.getString("Intercom"));
			    mPropetyDetails.put("Inverter",""+jInternalAmenity.getString("Inverter"));
			    mPropetyDetails.put("PanicButton",""+jInternalAmenity.getString("PanicButton"));
			    mPropetyDetails.put("Pets",""+jInternalAmenity.getString("Pets"));
			    mPropetyDetails.put("PipedGas",""+jInternalAmenity.getString("PipedGas"));
			    mPropetyDetails.put("VideoDoorphone",""+jInternalAmenity.getString("VideoDoorphone"));
			    mPropetyDetails.put("WaterStorage",""+jInternalAmenity.getString("WaterStorage"));
			
			    mPropetyDetails.put("AreaLocality",""+jLocation.getString("AreaLocality"));
			    mPropetyDetails.put("BuildingSocietyName",""+jLocation.getString("BuildingSocietyName"));
			    mPropetyDetails.put("FlatHouseNumber",""+jLocation.getString("FlatHouseNumber"));
			    mPropetyDetails.put("Landmark",""+jLocation.getString("Landmark"));
			    mPropetyDetails.put("Lat",""+jLocation.getString("Lat"));
			    mPropetyDetails.put("Long",""+jLocation.getString("Long"));
			    mPropetyDetails.put("Pincode",""+jLocation.getString("Pincode"));
			    mPropetyDetails.put("State",""+jLocation.getString("State"));
			    mPropetyDetails.put("StreetRoadName",""+jLocation.getString("StreetRoadName"));
			    mPropetyDetails.put("TownCityDistrict",""+jLocation.getString("TownCityDistrict"));

			 
	    	    mPropetyDetails.put("Description",""+jPropertyType.getString("Description"));
	    	    mPropetyDetails.put("TypeID",""+jPropertyType.getString("TypeID"));
		    	   
	    	    
			      for(int i=0;i<jArray.length();i++){
			    	JSONObject obj=jArray.getJSONObject(i);
			    	mImageBitmap.add(new HttpRequest().ImageGet(getApplicationContext(),obj.getString("PictureURL")));				   
			      
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
			mGallery.setAdapter(new GalleryAdapter());
		}
	}

class GalleryFlipImageSpeed extends Gallery {

		public GalleryFlipImageSpeed(Context context) {
			super(context);
			// TODO Auto-generated constructor stub
		}

		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
	            float velocityY) {
			//super.onFling(e1, e2, 0, velocityY);

	        return false;
	      }
		
	
}
	class GalleryAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mImageBitmap.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			try{
			 mButton.setText(""+position);
			}catch(Exception e){e.printStackTrace();}
			return 0;
		}

		@SuppressWarnings("deprecation")
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			ImageView mImageView=new ImageView(EditPropertyDetailsActivity.this);
			mImageView.setImageBitmap(mImageBitmap.get(position));
			mImageView.setScaleType(ImageView.ScaleType.FIT_XY);
            mImageView.setLayoutParams(new Gallery.LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT));
            System.gc();
           
            
            return mImageView;
		 }
		}
	
	private class DeActiveAddTask extends AsyncTask<String,String,String>{

		ProgressDialog mDialog;
		boolean isAuthenticated;
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			mDialog=ProgressDialog.show(EditPropertyDetailsActivity.this, "", getResources().getString(R.string.msg_dialog), true);
		}
		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub			
			try{
				
				Map nameValuePairs = new LinkedHashMap();
			    nameValuePairs.put("MobileNumber",Constants.USERMOBILENUMBER);
			    nameValuePairs.put("PropertyID",mPropertyId);
			    String mConvert=JSONValue.toJSONString(nameValuePairs);
			    String result=new HttpRequest().HttpRequestPost(getResources().getString(R.string.url)+"MyAds/DeactivateAd",mConvert);
			    JSONObject jArray=new JSONObject(result);
			    isAuthenticated=jArray.getBoolean("isSuccess");
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
				 new AlertDialogs(EditPropertyDetailsActivity.this,"" ,getResources().getString(R.string.alert_deactivate));
			 }else
				 new AlertDialogs(EditPropertyDetailsActivity.this,"" ,getResources().getString(R.string.alert_internetconnectionslow));
			}
	}
}