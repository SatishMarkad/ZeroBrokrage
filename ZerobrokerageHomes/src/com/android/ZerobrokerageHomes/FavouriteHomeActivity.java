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
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.JSONValue;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.android.parser.HttpRequest;
import com.android.parser.LazyAdapter;
import com.android.zero.utils.AlertDialogs;
import com.android.zero.utils.Constants;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerDragListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class FavouriteHomeActivity extends Activity {
    /** Called when the activity is first created. */
  
   private ArrayList<Map<String,String>>mDataSearchHome;
   private ArrayList<Bitmap>mDataImagesHome;
   private ListView mListView;
   private String mURL;	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_homes);
		mURL=getIntent().getStringExtra("URL");
		mListView=(ListView) findViewById(R.id.layout_ListViewFavourite);
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				
				startActivity(new Intent(FavouriteHomeActivity.this,SearchOnPropertyDetailsActivity.class).putExtra("ID",mDataSearchHome.get(arg2).get("ID")));
				Constants.PROPERTYID=mDataSearchHome.get(arg2).get("PropertyID");
			}
		});
		
		new SearchNearMeTask().execute();
	}


	public void onBack(View v){
    	finish();
    }
    
   
    
    public void onSortData(View v){
    	
    	ArrayList<Map<String,String>> mDumeyData=mDataSearchHome;
    	Object obj1=mDumeyData.get(0);
    	Object obj2=mDumeyData.get(mDataSearchHome.size()-1);
    	mDumeyData.set(0, (Map<String, String>) obj2);
    	mDumeyData.set(mDataSearchHome.size()-1, (Map<String, String>) obj1);
    	ArrayList<Bitmap>mDumeyDataImage=mDataImagesHome;
    	Bitmap b1=mDataImagesHome.get(0);
    	Bitmap b2=mDataImagesHome.get(mDataSearchHome.size()-1);
    	mDumeyDataImage.set(0,b2);
    	mDumeyDataImage.set(mDataSearchHome.size()-1,b1);
    	 LazyAdapter mAdapter=new LazyAdapter(FavouriteHomeActivity.this,mDumeyData,mDumeyDataImage,2,true);
		 mListView.setAdapter(mAdapter);
		 
    }
       
    
  
 private class SearchNearMeTask extends AsyncTask<String,String,String>{

	ProgressDialog mDialog;
	
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		mDataImagesHome=new ArrayList<Bitmap>();
		mDataSearchHome=new ArrayList<Map<String,String>>();
		mDialog=ProgressDialog.show(FavouriteHomeActivity.this, "", getResources().getString(R.string.msg_dialog), true);
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	protected String doInBackground(String... arg0) {
		// TODO Auto-generated method stub	
		String result = null;
		try{
			result=new HttpRequest().HttpRequestGet(getResources().getString(R.string.url)+"Users/"+Constants.USERMOBILENUMBER+"/"+mURL);
		    JSONArray jArray=new JSONArray(result);
		    for(int i=0;i<jArray.length();i++){
		    	JSONObject obj=jArray.getJSONObject(i);
		    	Map<String,String>getData=new LinkedHashMap<String, String>();
		    	getData.put("ID",obj.getString("ID"));
		    	getData.put("PropertyID",obj.getString("PropertyID"));
		    	getData.put("HomeType",obj.getString("HomeType"));
		    	getData.put("PostedDate",obj.getString("PostedDate"));
		    	getData.put("Rent",obj.getString("Rent"));
		    	getData.put("Span",obj.getString("Span"));
		    	getData.put("AvailableOn",obj.getString("AvailableOn"));
		    	getData.put("AvailableFor",obj.getString("AvailableFor"));
		    	mDataSearchHome.add(getData);
		    	mDataImagesHome.add(new HttpRequest().ImageGet(getApplicationContext(),obj.getString("PictureURL")));				   
			      
		      }
		    
		    /*  Log.e("Result",""+jArray.getString("MobileNumber"));
		    Log.e("Result",""+jArray.getBoolean("FirstName"));*/
		    //Constants.PROPERTYID=jArray.getString("PropertyID");
		    
			}catch(Exception e){
				e.printStackTrace();
				//Log.e("Result",""+e);
			}
		
		return result;
	}
	
	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		
		double lat,lng;
		
		mDialog.dismiss();
		 if(result!=null){
			 LazyAdapter mAdapter=new LazyAdapter(FavouriteHomeActivity.this,mDataSearchHome,mDataImagesHome,2,true);
			 mListView.setAdapter(mAdapter);
			 //new AlertDialogs(SearchOnMapHome.this,"" ,getResources().getString(R.string.alert_uploaddetailsucessfull));
		 }else
			 new AlertDialogs(FavouriteHomeActivity.this,"" ,getResources().getString(R.string.alert_internetconnectionslow));
		if(mDataSearchHome.isEmpty()){
			 new AlertDialogs(FavouriteHomeActivity.this,"" ,getResources().getString(R.string.alert_nofavourties));
				
		}
	}
}


}