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
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager.LayoutParams;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewFlipper;
import com.android.parser.HttpRequest;
import com.android.zero.utils.AlertDialogs;
import com.android.zero.utils.Constants;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
public class SearchOnPropertyDetailsActivity extends Activity {

    private Gallery mGallery;
    //SummeryDetails
    private ArrayList<Bitmap>mImageBitmap;
    private Button mButton;
    private ViewFlipper mViewFliper;
    private ImageView imgFavrites;
    //propetyDetails
    private HashMap<String,String> mPropetyDetails;
    //LocationDetails
    private GoogleMap mMap;
    private double mLatitude=0,mLongitude=0;
    //ContactDtails
    private String mEmail="",mMobileNum="",mFavouritId="";
    private boolean isFavAddRemove;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_searchpropertydetails_screen);
		
		initializtionSummery();
	}
	
	private void initializtionSummery() {
		// TODO Auto-generated method stub
		mFavouritId=getIntent().getStringExtra("ID");
		mButton=(Button) findViewById(R.id.button1);
		mGallery=(Gallery) findViewById(R.id.view_Gallery);
		mViewFliper=(ViewFlipper) findViewById(R.id.view_Fliper);
		imgFavrites=(ImageView) findViewById(R.id.img_Favrites);
		mMap =((MapFragment) getFragmentManager().findFragmentById(R.id.layout_map)).getMap();
		 // Check if we were successful in obtaining the map.
		 mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
		new GalleryFlipImageSpeed(this);
		new BackgroundTask().execute();
		if(mFavouritId.equalsIgnoreCase("null")){
		     imgFavrites.setBackgroundResource(R.drawable.fav_home_button_navbar);
		     isFavAddRemove=false;
		}else{
			 imgFavrites.setBackgroundResource(R.drawable.fav_home_button_selected_navbar); 
			 isFavAddRemove=true;
		}
	}
	
	public void onBack(View v){
		finish();
	}
	
	public void onFavrites(View v){
	   
		new AddActiveTask().execute();
	}
	public void onSummery(View v){ 
		mGallery.setVisibility(View.VISIBLE);
		mViewFliper.removeAllViews();
	}
	
	public void onDetails(View v){
		mGallery.setVisibility(View.GONE);
		mViewFliper.removeAllViews();
		View mView=View.inflate(this,R.layout.activity_searchpropertydetails,null);
		mViewFliper.addView(mView);
		mViewFliper.setVisibility(View.VISIBLE);
		new SearchOnPropertyDetails(this,mView,mPropetyDetails);
	}
	
	public void onLocation(View v){
		
	   SearchOnPropertyMap();
	}
	
	
	public void onContact(View v){ 
		if(mMobileNum.length()>0&&mEmail.length()>0){
			mGallery.setVisibility(View.GONE);
			mViewFliper.removeAllViews();
			View mView=View.inflate(this,R.layout.activity_searchpropertydetails_contact,null);
			mViewFliper.addView(mView);
			mViewFliper.setVisibility(View.VISIBLE);
			new SearchOnPropertyContact(this,mView,mMobileNum,mEmail);
		}else{
			
			Toast.makeText(getApplicationContext(), "No ContactDetails",Toast.LENGTH_LONG).show();
		}
	}

	
	
	
	private class BackgroundTask extends AsyncTask<String,String,String>{

		ProgressDialog mDialog;
		String mMessage;
		
		
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			mDialog=ProgressDialog.show(SearchOnPropertyDetailsActivity.this, "", getResources().getString(R.string.msg_dialog), true);
			mImageBitmap=new ArrayList<Bitmap>();
			mPropetyDetails=new HashMap<String, String>();
		}
		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub			
			try{
				
			   String result=new HttpRequest().HttpRequestGet(getResources().getString(R.string.url)+"Homes/"+Constants.PROPERTYID);
			   //Log.e("data",result);
			   JSONObject jObject=new JSONObject(result);
			    propertyDetails(jObject);
			    contatDetails();
			    JSONArray jArray=new JSONArray(jObject.getString("Pictures"));
			    JSONObject jLocation=new JSONObject(jObject.getString("Location"));
			    mLatitude=new Double(""+jLocation.getString("Lat"));
			    mLongitude=new Double(""+jLocation.getString("Long"));
			      for(int i=0;i<jArray.length();i++){
			    	JSONObject obj=jArray.getJSONObject(i);
			    	mImageBitmap.add(new HttpRequest().ImageGet(getApplicationContext(),obj.getString("PictureURL")));				   
			      
			      }
			    
			      //Check if is Paid or Not
			        Map nameValuePairs = new LinkedHashMap();
		 	        nameValuePairs.put("PropertyID", Integer.parseInt(Constants.PROPERTYID));
				    nameValuePairs.put("UserID",Integer.parseInt(Constants.USERID));
				    String mConvert=JSONValue.toJSONString(nameValuePairs);
				    result=new HttpRequest().HttpRequestPost(getResources().getString(R.string.url)+"Homes/"+"CheckIfPaid",mConvert);
				   
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
			///new AddActiveTask().execute();
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
			return position;
		}

		@SuppressWarnings("deprecation")
		@Override 
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			ImageView mImageView=new ImageView(SearchOnPropertyDetailsActivity.this);
			mImageView.setImageBitmap(mImageBitmap.get(position));
			mImageView.setScaleType(ImageView.ScaleType.FIT_XY);
            mImageView.setLayoutParams(new Gallery.LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT));
            System.gc();
           
            
            return mImageView;
		 }
		}
	
	private class AddActiveTask extends AsyncTask<String,String,String>{
       
		ProgressDialog mDialog;
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			mDialog=ProgressDialog.show(SearchOnPropertyDetailsActivity.this, "", getResources().getString(R.string.msg_dialog), true);
		}
		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub			
			try{
				 
				if(!isFavAddRemove){
			        Map nameValuePairs = new LinkedHashMap();
		 	        nameValuePairs.put("PropertyID",Integer.parseInt(Constants.PROPERTYID));
				    nameValuePairs.put("UserID",Integer.parseInt(Constants.USERID));
				    String mConvert=JSONValue.toJSONString(nameValuePairs);
			        String result=new HttpRequest().HttpRequestPost(getResources().getString(R.string.url)+"Users/"+Constants.USERMOBILENUMBER+"/Favorites",mConvert);
			        JSONObject jObject=new JSONObject(result);
			        String mFavId=jObject.getString("ID");
			        if(mFavId.length()>0&& !mFavId.equalsIgnoreCase("null")){
			        	mFavouritId=mFavId;
			        	isFavAddRemove=true;
			        }
				}else{
				  int result=new HttpRequest().HttpRequestDeleteStatusCode(getResources().getString(R.string.url)+"Users/"+Constants.USERMOBILENUMBER+"/Favorites/"+mFavouritId);
				  if(result==200||result==201||result==203||result==204){
						mFavouritId="null";
						isFavAddRemove=false;
					}
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
			 if(!isFavAddRemove){
				 imgFavrites.setImageResource(R.drawable.fav_home_button_navbar); 
			 }else {
				 imgFavrites.setImageResource(R.drawable.fav_home_button_selected_navbar); 
			 }
			 if(!new Constants().isOnline(SearchOnPropertyDetailsActivity.this)){
				 new AlertDialogs(SearchOnPropertyDetailsActivity.this,"" ,getResources().getString(R.string.alert_internetconnectionslow));
			 }
		}
	}

	public void propertyDetails(JSONObject jObject) {
		// TODO Auto-generated method stub
		try{
		    JSONObject jExternalAmenity=new JSONObject(jObject.getString("ExternalAmenity"));
		    JSONObject jInternalAmenity=new JSONObject(jObject.getString("InternalAmenity"));
		    JSONObject jLocation=new JSONObject(jObject.getString("Location"));
		    JSONObject jPropertyType=new JSONObject(jObject.getString("PropertyType"));
		    mPropetyDetails.put("AgeOfBuilding",""+jObject.getString("AgeOfBuilding"));
		    mPropetyDetails.put("AvailableFor",""+jObject.getString("AvailableFor"));
		    mPropetyDetails.put("AvailableFor",""+jObject.getString("Utilities"));
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
		    mPropetyDetails.put("Pincode",""+jLocation.getString("Pincode"));
		    mPropetyDetails.put("State",""+jLocation.getString("State"));
		    mPropetyDetails.put("StreetRoadName",""+jLocation.getString("StreetRoadName"));
		    mPropetyDetails.put("TownCityDistrict",""+jLocation.getString("TownCityDistrict"));

		 
 	      mPropetyDetails.put("Description",""+jPropertyType.getString("Description"));
 	      mPropetyDetails.put("TypeID",""+jPropertyType.getString("TypeID"));
	    	
 	    

		}catch(Exception e){}
	}

	public void contatDetails() {
		// TODO Auto-generated method stub
		try{
		    Map nameValuePairs = new LinkedHashMap();
		    nameValuePairs.put("UserID",Constants.USERID);
		    String mRequest=JSONValue.toJSONString(nameValuePairs);
		    String result=new HttpRequest().HttpRequestPost("http://www.zerobrokeragehomes.com/webapi/Homes/ContactInformation",mRequest);
		    JSONObject jObject=new JSONObject(result);
	        mMobileNum=jObject.getString("MobileNumber");
	        mEmail=jObject.getString("Email");
		    
	  }catch(Exception e){
		  Log.e("",""+e);
	  }
	}
	
public void SearchOnPropertyMap() {
        
	 mGallery.setVisibility(View.GONE);
	 mViewFliper.removeAllViews();
	 mViewFliper.setVisibility(View.GONE);
     new GetPlaces().execute();
       
    } 
private class GetPlaces extends AsyncTask<String, String, String> {
    
    ProgressDialog mDialog;
	String mMessage;
	ArrayList<String> mPlaceName;
	ArrayList<String> mLatLong;
	
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		mDialog=ProgressDialog.show(SearchOnPropertyDetailsActivity.this, "",getResources().getString(R.string.msg_dialog), true);
		mPlaceName=new ArrayList<String>();
		mLatLong=new ArrayList<String>();
	}

protected String doInBackground(String...args) {

  try{
	  String result=new HttpRequest().HttpRequestGet("https://maps.googleapis.com/maps/api/place/search/json?location="+mLatitude+","+mLongitude+"&radius=500 &sensor=false&key=AIzaSyC1kO0QT2Z3LrV_QgsrsKytDN9riJLgN94");
	  //Log.e("data",""+result);
        JSONObject jObject=new JSONObject(result);
	    JSONArray jArray=new JSONArray(jObject.getString("results"));
	    for(int i=0;i<jArray.length();i++){
	    	JSONObject obj=jArray.getJSONObject(i);
	    	String geo=obj.getString("geometry");
	    	JSONObject loc=new JSONObject(geo);
	    	String loca=loc.getString("location");
	    	JSONObject objLat=new JSONObject(loca);
	    	/* Log.e("data",""+objLat.getString("lat")+"=="+objLat.getString("lng"));
	    	 Log.e("Name",""+obj.getString("name"));*/
	    	 mPlaceName.add(""+obj.getString("name"));
	    	 mLatLong.add(""+objLat.getString("lat")+"=="+objLat.getString("lng"));
	      }
	    
  }catch(Exception e){
	  Log.e("",""+e);
  }
 
return null;

}

//then our post

@Override
protected void onPostExecute(String result){


  for(int i=0;i<mPlaceName.size();i++){
	    String[] mSplit=mLatLong.get(i).split("==");
	    double lat=Double.parseDouble(mSplit[0]);
	    double lng=Double.parseDouble(mSplit[1]);
	    MarkerOptions marker=new MarkerOptions().position(new LatLng(lat, lng)).title(mPlaceName.get(i));
	    marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
	    mMap.addMarker(marker);
       
  }
  mMap.addMarker(new MarkerOptions().position(new LatLng(mLatitude, mLongitude)));
  mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mLatitude, mLongitude),15));
  mMap.animateCamera(CameraUpdateFactory.zoomIn());
  
  mDialog.dismiss();
 
}

}

  	
}