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
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
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

public class SaveSearchActivity extends Activity {
    /** Called when the activity is first created. */
  
   private ArrayList<Map<String,String>>mDataSearchHome;
   private ArrayList<Bitmap>mDataImagesHome;
   private ListView mListView;
   private String mURL;	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_homes);
		mURL=getIntent().getStringExtra("URL");
		TextView txt=(TextView) findViewById(R.id.layout_Header);
		txt.setText("Save Search Home");
		mListView=(ListView) findViewById(R.id.layout_ListViewFavourite);
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				
				Intent getData=new Intent(SaveSearchActivity.this,SearchOnMapHome.class);
				getData.putExtra("SaveSearch",true);
				getData.putExtra("Lat",mDataSearchHome.get(arg2).get("Lat"));
		    	getData.putExtra("Long",mDataSearchHome.get(arg2).get("Long"));
				getData.putExtra("Address",mDataSearchHome.get(arg2).get("Address"));
			    getData.putExtra("Radius",mDataSearchHome.get(arg2).get("Radius"));
			    getData.putExtra("AvailableFor",mDataSearchHome.get(arg2).get("AvailableFor"));
			    getData.putExtra("Beds",mDataSearchHome.get(arg2).get("Beds"));
			    getData.putExtra("isBudgetSpecifiedAsRange",mDataSearchHome.get(arg2).get("isBudgetSpecifiedAsRange"));
			    getData.putExtra("BudgetMin",mDataSearchHome.get(arg2).get("BudgetMin"));
			    getData.putExtra("BudgetMax",mDataSearchHome.get(arg2).get("BudgetMax"));
			    getData.putExtra("isDepositSpecifiedAsRange",mDataSearchHome.get(arg2).get("isDepositSpecifiedAsRange"));
			    getData.putExtra("DepositMin",mDataSearchHome.get(arg2).get("DepositMin"));
			    getData.putExtra("DepositMax",mDataSearchHome.get(arg2).get("DepositMax"));
			    getData.putExtra("PGAccomodationFor",mDataSearchHome.get(arg2).get("PGAccomodationFor"));
		    	
				startActivity(getData);
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
    	 LazyAdapter mAdapter=new LazyAdapter(SaveSearchActivity.this,mDumeyData,mDumeyDataImage,2,true);
		 mListView.setAdapter(mAdapter);
		 
    }
       
    
  
 private class SearchNearMeTask extends AsyncTask<String,String,String>{

	ProgressDialog mDialog;
	
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		mDataImagesHome=new ArrayList<Bitmap>();
		mDataSearchHome=new ArrayList<Map<String,String>>();
		mDialog=ProgressDialog.show(SaveSearchActivity.this, "", getResources().getString(R.string.msg_dialog), true);
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	protected String doInBackground(String... arg0) {
		// TODO Auto-generated method stub	
		String result = null;
		try{
			result=new HttpRequest().HttpRequestGet(getResources().getString(R.string.url)+"Users/"+Constants.USERMOBILENUMBER+"/"+mURL);
		  // Log.e("Result",""+result);
			JSONArray jArray=new JSONArray(result);
		    for(int i=0;i<jArray.length();i++){
		    	JSONObject obj=jArray.getJSONObject(i);
		    	Map<String,String>getData=new LinkedHashMap<String, String>();
		    	getData.put("ID",obj.getString("ID"));
		    	getData.put("Lat",obj.getString("Lat"));
		    	getData.put("Long",obj.getString("Long"));
				getData.put("Address",obj.getString("Address"));
			    getData.put("Radius",obj.getString("Radius"));
			    getData.put("AvailableFor",obj.getString("AvailableFor"));
			    getData.put("Beds",obj.getString("Beds"));
			    getData.put("isBudgetSpecifiedAsRange",obj.getString("isBudgetSpecifiedAsRange"));
			    getData.put("BudgetMin",obj.getString("BudgetMin"));
			    getData.put("BudgetMax",obj.getString("BudgetMax"));
			    getData.put("isDepositSpecifiedAsRange",obj.getString("isDepositSpecifiedAsRange"));
			    getData.put("DepositMin",obj.getString("DepositMin"));
			    getData.put("DepositMax",obj.getString("DepositMax"));
			    getData.put("PGAccomodationFor",obj.getString("PGAccomodationFor"));
		    	mDataSearchHome.add(getData);
		    	Bitmap bmp=BitmapFactory.decodeResource(getResources(),R.drawable.app_launch);
		    	mDataImagesHome.add(bmp);				   
			      
		      }
		    
		   
			}catch(Exception e){
				e.printStackTrace();
				Log.e("Result",""+e);
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
			 SearchOnMapAdapter mAdapter=new SearchOnMapAdapter();
			 mListView.setAdapter(mAdapter);
			}else
			 new AlertDialogs(SaveSearchActivity.this,"" ,getResources().getString(R.string.alert_internetconnectionslow));
		if(mDataSearchHome.isEmpty()){
			 new AlertDialogs(SaveSearchActivity.this,"" ,getResources().getString(R.string.alert_nosaveSearch));
				
		}
	}
}

 private class SearchOnMapAdapter extends BaseAdapter {
	   
		public int getCount() {
			
			return mDataSearchHome.size();
	    }

	    public Object getItem(int position) {
	        return position;
	    }

	    public long getItemId(int position) {
	        return position;
	    }
	    
	    public  class ViewHolder{
	        public TextView text1,text2;
	        public ImageView isFavimage,icon;
	    }

	    public View getView(int position, View convertView, ViewGroup parent) {
	        View vi=convertView;
	        ViewHolder holder;
	        if(convertView==null){
	         LayoutInflater  inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	        	vi = inflater.inflate(R.layout.custome_listview_mapscreen, null);

	            holder=new ViewHolder();
	            holder.text1 = (TextView)vi.findViewById(R.id.layout_custome_TextView);
				holder.text2 = (TextView)vi.findViewById(R.id.layout_custome_TextView_Day);	
				holder.icon = (ImageView)vi.findViewById(R.id.layout_custome_ImageView);
				holder.isFavimage = (ImageView)vi.findViewById(R.id.layout_custome_Favourites);
	            vi.setTag(holder);
	        }
	        else
	            holder=(ViewHolder)vi.getTag();
	      
	           holder.text1.setText(mDataSearchHome.get(position).get("Beds")+"BHK  ("+mDataSearchHome.get(position).get("Distance")+" Kms)"+"\n"+"Rent :"+mDataSearchHome.get(position).get("Rent")+" k/month"+"\n"+"Available on :"+mDataSearchHome.get(position).get("AvailableOn"));
	           holder.text2.setText("day ago");
	    	   holder.icon.setImageBitmap(mDataImagesHome.get(position));
	    	   holder.isFavimage.setTag(""+position);
	    	  // if(isDelete){
	    		   holder.isFavimage.setImageResource(R.drawable.delete_property);  
	    	   //}
	    	   holder.isFavimage.setOnClickListener(new View. OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					String mId=(String) arg0.getTag();
					new RemoveRecord().execute(mId);
		 		}
			});
	         return vi;
	        }
		}

 private class RemoveRecord extends AsyncTask<String,String,String>{

		ProgressDialog mDialog;
		private boolean isFavourites;
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			mDialog=ProgressDialog.show(SaveSearchActivity.this, "", getResources().getString(R.string.msg_dialog), true);
		}
		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub			
			try{
				int index=Integer.parseInt(arg0[0]);
				int result=new HttpRequest().HttpRequestDeleteStatusCode(getResources().getString(R.string.url)+"Users/"+Constants.USERMOBILENUMBER+"/"+mURL+"/"+mDataSearchHome.get(index).get("ID"));
			      Log.e("RE",""+result);
				    if(result==200||result==201||result==203||result==204){
						isFavourites=true;
						mDataImagesHome.remove(index);
						mDataSearchHome.remove(index);
					}else{
						isFavourites=false;
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
			if(isFavourites){
				new AlertDialogs(SaveSearchActivity.this,"" ,getResources().getString(R.string.alert_savesearchrecorddelete));   
				SearchOnMapAdapter mAdapter=new SearchOnMapAdapter();
				mListView.setAdapter(mAdapter);
				
			}else{
				 new AlertDialogs(SaveSearchActivity.this,"" ,getResources().getString(R.string.alert_nosavesearchrecorddelete));
					 
			 }
			 if(!new Constants().isOnline(SaveSearchActivity.this)){
				 new AlertDialogs(SaveSearchActivity.this,"" ,getResources().getString(R.string.alert_internetconnectionslow));
			 }
		
		
	}
		}

}