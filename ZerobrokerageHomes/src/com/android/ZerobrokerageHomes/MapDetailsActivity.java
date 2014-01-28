package com.android.ZerobrokerageHomes;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.zero.utils.Constants;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerDragListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapDetailsActivity extends Activity implements OnMarkerDragListener {
    /** Called when the activity is first created. */
   private GoogleMap mMap;
   private double mLatitude=0,mLongitude=0;
   private Button btn_onTab;
   private AutoCompleteTextView txt_autoTextView;
   private ArrayAdapter<String> adapter;
   private ImageView Details_img,Picture_img;
   private boolean isEditProperty;
   private Intent mIntent;
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_screen);
        
         mLatitude=new Double(Constants.mLatitude);
         mLongitude=new Double(Constants.mLongitude);
         isEditProperty=getIntent().getBooleanExtra("Edit",false);
         
      try{   
    	setUpMapIfNeeded();
        txt_autoTextView=(AutoCompleteTextView) findViewById(R.id.auto_textcomplete);
        btn_onTab=(Button) findViewById(R.id.btn_Tab);
        Details_img=(ImageView) findViewById(R.id.btn_Details);
        Picture_img=(ImageView) findViewById(R.id.btn_Picture);
        Details_img.setClickable(false);
        Picture_img.setClickable(false);
        findViewById(R.id.post_btn).setClickable(false);
        
       
        mMap.setOnMapClickListener(new OnMapClickListener() {
			
			@Override
			public void onMapClick(LatLng point) {
				// TODO Auto-generated method stub
				Toast.makeText(getBaseContext(), point.latitude+ "," + point.longitude , Toast.LENGTH_SHORT).show();
				 NumberFormat formatter = new DecimalFormat("#.##");
				 Constants.USERHOUSELATI=""+formatter.format(point.latitude) ;
	             Constants.USERHOUSELONGI=""+formatter.format(point.longitude);
                 Details_img.setClickable(true);
			}
		});
        
        adapter = new ArrayAdapter<String>(this,R.layout.list_item);
        adapter.setNotifyOnChange(true);
        txt_autoTextView.setAdapter(adapter);
        txt_autoTextView.addTextChangedListener(new TextWatcher() {

		public void onTextChanged(CharSequence s, int start, int before, int count) {
		if (count%3 == 1) {
		adapter.clear();
		                GetPlaces task = new GetPlaces();
		                        //now pass the argument in the textview to the task
		                                task.execute(txt_autoTextView.getText().toString());
		        }
		}

		public void beforeTextChanged(CharSequence s, int start, int count,
		int after) {
		// TODO Auto-generated method stub
		
		}
		
		@Override
		public void afterTextChanged(Editable arg0) {
			// TODO Auto-generated method stub
	
	}
	});
      }catch(Exception e){}  
      
      if(isEditProperty){
 		  HashMap<String,String> mPropetyDetails=(HashMap<String, String>) getIntent().getSerializableExtra("AllDetails");
 		  mLatitude=Double.parseDouble(mPropetyDetails.get("Lat"));
 		  mLongitude=Double.parseDouble(mPropetyDetails.get("Long"));
 		  Constants.USERHOUSELATI=""+Double.parseDouble(mPropetyDetails.get("Lat"));
          Constants.USERHOUSELONGI=""+Double.parseDouble(mPropetyDetails.get("Long"));
          Details_img.setClickable(true);
          mIntent= new Intent(MapDetailsActivity.this,AddPropertyDetailsActivity.class).putExtra("Edit", true).putExtra("AllDetails",mPropetyDetails);
       } 
      new  MapBackgroundTask().execute();
   }
    
    
	public void onBack(View v){
    	finish();
    }
    
    public void onPost(View v){
    	
    }
    
    public void onLocationDetails(View v){
    	
    }
    
    public void onPostDetails(View v){
    	if(isEditProperty)
    		startActivity(mIntent);
    	else
    	startActivity(new Intent(MapDetailsActivity.this,AddPropertyDetailsActivity.class).putExtra("Edit", false));
    	
    	finish();
    }
    public void onPicture(View v){
    	finish();
    }
    
    public void onTap(View v){
    
    	String tag=(String) v.getTag();
    	if(tag.equalsIgnoreCase("Tap")){
    		txt_autoTextView.setVisibility(View.VISIBLE);
    		btn_onTab.setText("Search");
    		btn_onTab.setTag("Search");
    		
    	}else{
    		
    		try{
    		    
	       		InputMethodManager mHideKeyBord = (InputMethodManager)this.getSystemService(Service.INPUT_METHOD_SERVICE);
	       		mHideKeyBord.hideSoftInputFromWindow(txt_autoTextView.getWindowToken(),0);
       		 	
	    		txt_autoTextView.setVisibility(View.INVISIBLE);
	    		btn_onTab.setText("Tap");
	    		btn_onTab.setTag("Tap");
	    		Details_img.setClickable(true);
	    		  /* find the addresses  by using getFromLocationName() method with the given address*/
	    		 String cityName=""+txt_autoTextView.getText().toString().replaceAll(" ","%20");
	    		 String[] cityname=cityName.split(",");
	    		 List<Address> foundGeocode = new Geocoder(MapDetailsActivity.this).getFromLocationName(""+cityname[0], 1);
			     mLatitude=foundGeocode.get(0).getLatitude(); //getting latitude
			     mLongitude=foundGeocode.get(0).getLongitude();//getting longitude
				 txt_autoTextView.setText("");
				 mMap.addMarker(new MarkerOptions().position(new LatLng(mLatitude, mLongitude)).draggable(true));
			     mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mLatitude, mLongitude),15));
			     mMap.animateCamera(CameraUpdateFactory.zoomIn());
			    
			}catch(Exception e){
				e.printStackTrace();
			}
    		 
    	}
    }
    
    public void onResume(){
    	super.onResume();
    	setUpMapIfNeeded();
    }
   	
 private class GetPlaces extends AsyncTask<String, Void, ArrayList<String>>
  {
	    final String PLACES_API_BASE = "https://maps.googleapis.com/maps/api/place";
		final String TYPE_AUTOCOMPLETE = "/autocomplete";
		final String OUT_JSON = "/json";
		String API_KEY ="AIzaSyC1kO0QT2Z3LrV_QgsrsKytDN9riJLgN94";
		
 
  protected ArrayList<String> doInBackground(String... args)
  {

	  Log.d("gottaGo", "doInBackground");
	  ArrayList<String> resultList = null;
	  HttpURLConnection conn = null;
	  StringBuilder jsonResults = new StringBuilder();
	  try {
	      StringBuilder sb = new StringBuilder(PLACES_API_BASE + TYPE_AUTOCOMPLETE + OUT_JSON);
	      sb.append("?sensor=false&key=" + API_KEY);
	      sb.append("&components=country:ind");
	      sb.append("&input=" + URLEncoder.encode(txt_autoTextView.getText().toString(), "utf8"));
	      
	      URL url = new URL(sb.toString());
	      conn = (HttpURLConnection) url.openConnection();
	      InputStreamReader in = new InputStreamReader(conn.getInputStream());
	      
	      // Load the results into a StringBuilder
	      int read;
	      char[] buff = new char[1024];
	      while ((read = in.read(buff)) != -1) {
	          jsonResults.append(buff, 0, read);
	      }
	  } catch (MalformedURLException e) {
	      //Log.e("LOG_TAG", "Error processing Places API URL", e);
	      return resultList;
	  } catch (IOException e) {
	      //Log.e("LOG_TAG", "Error connecting to Places API", e);
	      return resultList;
	  } finally {
	      if (conn != null) {
	          conn.disconnect();
	      }
	  }

	  try {
	      // Create a JSON object hierarchy from the results
	      JSONObject jsonObj = new JSONObject(jsonResults.toString());
	      JSONArray predsJsonArray = jsonObj.getJSONArray("predictions");
	      
	      // Extract the Place descriptions from the results
	      resultList = new ArrayList<String>(predsJsonArray.length());
	      for (int i = 0; i < predsJsonArray.length(); i++) {
	          resultList.add(predsJsonArray.getJSONObject(i).getString("description"));
	      }
	  } catch (JSONException e) {
	      Log.e("LOG_TAG", "Cannot process JSON results", e);
	  }
  
  return resultList;

  }

  //then our post

  @Override
  protected void onPostExecute(ArrayList<String> result)
  {

  Log.d("YourApp", "onPostExecute : " + result.size());
  //update the adapter
  adapter = new ArrayAdapter<String>(getBaseContext(), R.layout.list_item);
  adapter.setNotifyOnChange(true);
  //attach the adapter to textview
  txt_autoTextView.setAdapter(adapter);

  for (String string : result)
  {

  Log.d("YourApp", "onPostExecute : result = " + string);
  adapter.add(string);
  adapter.notifyDataSetChanged();

  }

  Log.d("YourApp", "onPostExecute : autoCompleteAdapter" + adapter.getCount());

  }

  }

 
private void setUpMapIfNeeded() {
// Do a null check to confirm that we have not already instantiated the map.
if (mMap == null) {
 // Try to obtain the map wrap_contentfrom the SupportMapFragment.
 mMap =((MapFragment) getFragmentManager().findFragmentById(R.id.layout_map)).getMap();
 // Check if we were successful in obtaining the map.
 mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
}
}


@Override
public void onMarkerDrag(Marker arg0) {
	// TODO Auto-generated method stub
	
}


@Override
public void onMarkerDragEnd(Marker arg0) {
	// TODO Auto-generated method stub
	LatLng mLatlong=arg0.getPosition();
	
    Constants.USERHOUSELATI=""+mLatlong.latitude;
    Constants.USERHOUSELONGI=""+mLatlong.longitude;
}


@Override
public void onMarkerDragStart(Marker arg0) {
	// TODO Auto-generated method stub
	
}

 private class MapBackgroundTask extends AsyncTask<String,String,String>{

	 ProgressDialog mDialog;
	 protected void onPreExecute(){
		 mDialog=ProgressDialog.show(MapDetailsActivity.this,"",getResources().getString(R.string.msg_dialog),true);
	 }
	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	protected void onPostExecute(String arg){
		
		mDialog.dismiss();
		mMap.addMarker(new MarkerOptions().position(new LatLng(mLatitude, mLongitude)).draggable(true));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mLatitude, mLongitude),15));
        mMap.animateCamera(CameraUpdateFactory.zoomIn());
	        
	}
	 
 }
}