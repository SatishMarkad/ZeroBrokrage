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
import com.android.parser.LazyAdapter.ViewHolder;
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

public class SearchOnMapHome extends Activity implements OnMarkerDragListener {
    /** Called when the activity is first created. */
   private GoogleMap mMap;
   private double mLatitude=0,mLongitude=0;
   private Button btn_onTab,btn_Sort,btn_ListView,btn_Map;
   private AutoCompleteTextView txt_autoTextView;
   private ArrayAdapter<String> adapter;
   private ImageView Details_img,Picture_img;
   private boolean isSaveSearch,isFavAddRemove;///,isEditProperty;
   private Intent mIntent;
   private LinearLayout mFilterLayout,mPGAccomdationLayout,mLayout_SateliteMode,mLayoutListView;
   private RelativeLayout mLayoutMapFragment;
   private TextView txt_Looking,txt_Budget,txt_Deposit,txt_Redius;
   private SeekBar bar_Looking,bar_Budget,bar_Deposit,bar_Redius;
   private String isRentSalePG="RENT",mPGAccomdation="", mAddress="",
		     misBudgetSpecifiedAsRange="true",mBudgetMin="0"
		    ,misDepositSpecifiedAsRange="true",mDepositMin="0";
   private ArrayList<Map<String,String>>mDataSearchHome;
   private ArrayList<Bitmap>mDataImagesHome;
   private ListView mListView;
   	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchonmap_screen);
        
         mLatitude=new Double(Constants.mLatitude);
         mLongitude=new Double(Constants.mLongitude);
         isSaveSearch=getIntent().getBooleanExtra("SaveSearch",false);
        /// isEditProperty=getIntent().getBooleanExtra("Edit",false);
         Constants.USERHOUSELATI=""+Constants.mLatitude;
		 Constants.USERHOUSELONGI=""+Constants.mLongitude;
      try{   
    	setUpMapIfNeeded();
    	setFilterLayout();
        
       
        mMap.setOnMapClickListener(new OnMapClickListener() {
			
			@Override
			public void onMapClick(LatLng point) {
				// TODO Auto-generated method stub
				Toast.makeText(getBaseContext(), point.latitude+ "," + point.longitude , Toast.LENGTH_SHORT).show();
				 NumberFormat formatter = new DecimalFormat("#.##");
				 Constants.USERHOUSELATI=""+formatter.format(point.latitude) ;
	             Constants.USERHOUSELONGI=""+formatter.format(point.longitude);
			}
		});
        
        txt_autoTextView=(AutoCompleteTextView) findViewById(R.id.auto_textcomplete);
        btn_onTab=(Button) findViewById(R.id.btn_Tab);
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
      
      if(isSaveSearch){
    	  isFromSaveSearchData();
      } 
      
      /*if(isEditProperty){
 		  HashMap<String,String> mPropetyDetails=(HashMap<String, String>) getIntent().getSerializableExtra("AllDetails");
 		  mLatitude=Double.parseDouble(mPropetyDetails.get("Lat"));
 		  mLongitude=Double.parseDouble(mPropetyDetails.get("Long"));
 		  Constants.USERHOUSELATI=""+Double.parseDouble(mPropetyDetails.get("Lat"));
          Constants.USERHOUSELONGI=""+Double.parseDouble(mPropetyDetails.get("Long"));
          Details_img.setClickable(true);
          mIntent= new Intent(SearchOnMapHome.this,AddPropertyDetailsActivity.class).putExtra("Edit", true).putExtra("AllDetails",mPropetyDetails);
       } */
   }
    
	
	private void isFromSaveSearchData() {
		// TODO Auto-generated method stub
		Constants.USERHOUSELATI=getIntent().getStringExtra("Lat");
	    Constants.USERHOUSELONGI=getIntent().getStringExtra("Long");
	    mAddress=""+getIntent().getStringExtra("Address");
	    bar_Redius.setSecondaryProgress(Integer.parseInt(getIntent().getStringExtra("Radius")));
	    isRentSalePG=getIntent().getStringExtra("AvailableFor");
	    int bads=(int)Math.round(Float.parseFloat(getIntent().getStringExtra("Beds")));
	    bar_Looking.setSecondaryProgress(bads);
	    misBudgetSpecifiedAsRange=getIntent().getStringExtra("isBudgetSpecifiedAsRange");
	    mBudgetMin=getIntent().getStringExtra("BudgetMin");
	    bar_Budget.setSecondaryProgress(Integer.parseInt(getIntent().getStringExtra("BudgetMax")));
	    misDepositSpecifiedAsRange=getIntent().getStringExtra("isDepositSpecifiedAsRange");
	    mDepositMin=getIntent().getStringExtra("DepositMin");
	    bar_Deposit.setSecondaryProgress(Integer.parseInt(getIntent().getStringExtra("DepositMax")));
	    mPGAccomdation=getIntent().getStringExtra("PGAccomodationFor");
	}


	private void setFilterLayout() {
		// TODO Auto-generated method stub
		mListView=(ListView) findViewById(R.id.layout_ListView);
		mFilterLayout=(LinearLayout) findViewById(R.id.layout_FilterProperty);
		mLayoutMapFragment=(RelativeLayout) findViewById(R.id.layout_MapFragment);
		mLayoutListView=(LinearLayout) findViewById(R.id.layout_ListViewAll);
		mLayout_SateliteMode=(LinearLayout) findViewById(R.id.layout_Satelite);
		mPGAccomdationLayout=(LinearLayout) findViewById(R.id.Layout_PGAccumdation);
		btn_Map=(Button) findViewById(R.id.btn_Map);
	    btn_Sort=(Button) findViewById(R.id.btn_Sort);
	    btn_ListView=(Button) findViewById(R.id.btn_List);
		txt_Looking=(TextView) findViewById(R.id.txt_Looking);
		txt_Redius= (TextView) findViewById(R.id.txt_Redius);
		txt_Budget=(TextView) findViewById(R.id.txt_Budget);
		txt_Deposit=(TextView) findViewById(R.id.txt_Deposit);
		bar_Looking=(SeekBar) findViewById(R.id.seekbar_Looking);
		bar_Budget=(SeekBar) findViewById(R.id.seekbar_Budget);
		bar_Deposit=(SeekBar) findViewById(R.id.seekbar_Deposit);
		bar_Redius=(SeekBar) findViewById(R.id.seekbar_Redius);
		
		
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				
				startActivity(new Intent(SearchOnMapHome.this,SearchOnPropertyDetailsActivity.class).putExtra("ID",mDataSearchHome.get(arg2).get("FavoriteID")));
				Constants.PROPERTYID=mDataSearchHome.get(arg2).get("PropertyID");
			}
		});
		bar_Looking.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
				txt_Looking.setText("Beds \t"+progress);
				bar_Looking.setSecondaryProgress(progress);
			}
		});
		
		bar_Redius.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
				txt_Redius.setText("Radius \t"+progress);
				bar_Redius.setSecondaryProgress(progress);
			}
		});
		
       bar_Budget.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
				txt_Budget.setText("Budeget \t"+progress);
				bar_Budget.setSecondaryProgress(progress);
			}
		});

		bar_Deposit.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
				txt_Deposit.setText("Deposit \t"+progress);
				bar_Deposit.setSecondaryProgress(progress);
			}
		});
		
		new SearchNearMeTask().execute();
	
	}

    public void onResume(){
    	super.onResume();
    	setUpMapIfNeeded();
    }
	public void onBack(View v){
    	finish();
    }
    
    public void onSaveList(View v){
    	new SaveSearchData().execute();
    }
    
    public void onPG(View v){
    	isRentSalePG="PG";
    }
    
    public void onRent(View v){
    	isRentSalePG="RENT";
    }
    public void onBuy(View v){
    	isRentSalePG="SALE";
    }
    
    public void onBoys(View v){
    	mPGAccomdation="Boys";
    }
    
    public void onGirls(View v){
    	mPGAccomdation="Girls";
    }
    public void onUniSex(View v){
    	mPGAccomdation="Boys";
    }
    
    public void onFilter(View v){
    	
    	mPGAccomdationLayout.setVisibility(View.VISIBLE);
		txt_Deposit.setVisibility(View.VISIBLE);
		bar_Deposit.setVisibility(View.VISIBLE);
		
    	if(isRentSalePG.equalsIgnoreCase("Rent")){
    		mPGAccomdationLayout.setVisibility(View.GONE);
    	}else if(isRentSalePG.equalsIgnoreCase("PG")){
    		mPGAccomdationLayout.setVisibility(View.VISIBLE);
    	}else if(isRentSalePG.equalsIgnoreCase("Sale")){
    		mPGAccomdationLayout.setVisibility(View.GONE);
    		txt_Deposit.setVisibility(View.GONE);
    		bar_Deposit.setVisibility(View.GONE);
    	}
    	
    	btn_onTab.setVisibility(View.GONE);
    	mFilterLayout.setVisibility(View.VISIBLE);
    }
    
    public void onShowOnList(View v){
    	
    	mLayoutMapFragment.setVisibility(View.GONE);
    	mLayout_SateliteMode.setVisibility(View.GONE);
    	btn_onTab.setVisibility(View.GONE);
    	btn_ListView.setVisibility(View.GONE);
    	btn_Map.setVisibility(View.VISIBLE);
    	btn_Sort.setVisibility(View.VISIBLE);
    	mLayoutListView.setVisibility(View.VISIBLE);
    	mFilterLayout.setVisibility(View.GONE);
    }
    
    public void onMapView(View v){
    	
    	mLayoutMapFragment.setVisibility(View.VISIBLE);
    	mLayout_SateliteMode.setVisibility(View.VISIBLE);
    	btn_onTab.setVisibility(View.VISIBLE);
    	btn_ListView.setVisibility(View.VISIBLE);
    	btn_Map.setVisibility(View.GONE);
    	btn_Sort.setVisibility(View.GONE);
    	mLayoutListView.setVisibility(View.GONE);
    	mFilterLayout.setVisibility(View.GONE);
    }
    
    public void onSortData(View v){
    	
    	try{
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
	    	 LazyAdapter mAdapter=new LazyAdapter(SearchOnMapHome.this,mDumeyData,mDumeyDataImage,1,true);
			 mListView.setAdapter(mAdapter);
    	}catch(Exception e){}
		 
    }
    public void onSearch(View v){
    	btn_onTab.setVisibility(View.GONE);
    	mFilterLayout.setVisibility(View.GONE);
    	new SearchNearMeTask().execute();
    }
    
    public void onSatelite(View v){
	   mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
    }
    
    public void onHybrit(View v){
      mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
    }
   
    public void onTap(View v){
    
    	String tag=(String) v.getTag();
    	if(tag.equalsIgnoreCase("Tap")){
    		txt_autoTextView.setVisibility(View.VISIBLE);
    		btn_onTab.setText("Search");
    		btn_onTab.setTag("Search");
    		
    	}else{
    		
    		try{
    		txt_autoTextView.setVisibility(View.INVISIBLE);
    		btn_onTab.setText("Tap");
    		btn_onTab.setTag("Tap");
    		  /* find the addresses  by using getFromLocationName() method with the given address*/
    		 String cityName=""+txt_autoTextView.getText().toString().replaceAll(" ","%20");
    		 String[] cityname=cityName.split(",");
    		 List<Address> foundGeocode = new Geocoder(SearchOnMapHome.this).getFromLocationName(""+cityname[0], 1);
		     mLatitude=foundGeocode.get(0).getLatitude(); //getting latitude
		     mLongitude=foundGeocode.get(0).getLongitude();//getting longitude
			 txt_autoTextView.setText("");
			 mMap.addMarker(new MarkerOptions().position(new LatLng(mLatitude, mLongitude)).draggable(true));
		     mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mLatitude, mLongitude),15));
		     mMap.animateCamera(CameraUpdateFactory.zoomIn());
		        
    		 InputMethodManager mHideKeyBord = (InputMethodManager)this.getSystemService(Service.INPUT_METHOD_SERVICE);
    		 mHideKeyBord.hideSoftInputFromWindow(txt_autoTextView.getWindowToken(),0);
    		 
			}catch(Exception e){
				e.printStackTrace();
			}
    		 
    	}
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
	      Log.e("LOG_TAG", "Error processing Places API URL", e);
	      return resultList;
	  } catch (IOException e) {
	      Log.e("LOG_TAG", "Error connecting to Places API", e);
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
 mMap.setOnMarkerDragListener(SearchOnMapHome.this);
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
	LatLng point=arg0.getPosition();
	
    Constants.USERHOUSELATI=""+point.latitude;
    Constants.USERHOUSELONGI=""+point.longitude;
    Toast.makeText(getBaseContext(), point.latitude+ "," + point.longitude , Toast.LENGTH_SHORT).show();
	
}


@Override
public void onMarkerDragStart(Marker arg0) {
	// TODO Auto-generated method stub
}


private class SearchNearMeTask extends AsyncTask<String,String,String>{

	ProgressDialog mDialog;
	
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		mDataImagesHome=new ArrayList<Bitmap>();
		mDataSearchHome=new ArrayList<Map<String,String>>();
		mDialog=ProgressDialog.show(SearchOnMapHome.this, "", getResources().getString(R.string.msg_dialog), true);
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	protected String doInBackground(String... arg0) {
		// TODO Auto-generated method stub	
		String result = null;
		try{
			Map<String, Object> nameValuePairs = new  LinkedHashMap<String, Object>();
			
			nameValuePairs.put("Lat",Float.parseFloat(Constants.USERHOUSELATI));
		    nameValuePairs.put("Long",Float.parseFloat(Constants.USERHOUSELONGI));
			nameValuePairs.put("Address",mAddress);
		    nameValuePairs.put("Radius",bar_Redius.getSecondaryProgress());
		    nameValuePairs.put("AvailableFor",isRentSalePG);
		    nameValuePairs.put("Beds",bar_Looking.getSecondaryProgress());
		    nameValuePairs.put("isBudgetSpecifiedAsRange",Boolean.parseBoolean(misBudgetSpecifiedAsRange));
		    nameValuePairs.put("BudgetMin",Integer.parseInt(mBudgetMin));
		    nameValuePairs.put("BudgetMax",Integer.parseInt(""+bar_Budget.getSecondaryProgress()));
		    nameValuePairs.put("isDepositSpecifiedAsRange",Boolean.parseBoolean(misDepositSpecifiedAsRange));
		    nameValuePairs.put("DepositMin",Integer.parseInt(mDepositMin));
		    nameValuePairs.put("DepositMax",bar_Deposit.getSecondaryProgress());
		    nameValuePairs.put("PGAccomodationFor",mPGAccomdation);
		    nameValuePairs.put("UserId",1);
		    nameValuePairs.put("Page",0);
		    nameValuePairs.put("RecordsPerPage",4);
		    nameValuePairs.put("FetchAll",true);
		    String mConvert=JSONValue.toJSONString(nameValuePairs);
		    result=new HttpRequest().HttpRequestPost(getResources().getString(R.string.url)+"Homes/PropertiesNearMe",mConvert);
		   //Log.e("Result",""+mConvert);
		    JSONArray jArray=new JSONArray(result);
		    for(int i=0;i<jArray.length();i++){
		    	JSONObject obj=jArray.getJSONObject(i);
		    	Map<String,String>getData=new LinkedHashMap<String, String>();
		    	getData.put("PropertyID",obj.getString("PropertyID"));
		    	getData.put("Lat",obj.getString("Lat"));
		    	getData.put("Long",obj.getString("Long"));
		    	getData.put("Address",obj.getString("Address"));
		    	getData.put("Beds",obj.getString("Beds"));
		    	getData.put("Distance",obj.getString("Distance"));
		    	getData.put("Rent",obj.getString("Rent"));
		    	getData.put("AvailableOn",obj.getString("AvailableOn"));
		    	getData.put("FavoriteID",obj.getString("FavoriteID"));
		    	mDataSearchHome.add(getData);
		    	mDataImagesHome.add(new HttpRequest().ImageGet(getApplicationContext(),obj.getString("PictureURL")));				   
			      
		      }
		    
		      Thread.sleep(1500);
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
		for(int a=0;a<mDataSearchHome.size();a++){
			
		    lat=Double.parseDouble(mDataSearchHome.get(a).get("Lat"));
		    lng=Double.parseDouble(mDataSearchHome.get(a).get("Long"));
		    MarkerOptions marker= new MarkerOptions().position(new LatLng(lat,lng));
			marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
		    mMap.addMarker(marker);
			
		}
		    
		mMap.addMarker(new MarkerOptions().position(new LatLng(mLatitude, mLongitude)));
		mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mLatitude,mLongitude),15));
		mMap.animateCamera(CameraUpdateFactory.zoomIn());
	       /* mMap.addMarker(new MarkerOptions().position(new LatLng(mLatitude, mLongitude)).draggable(true));
	        
	        */
	        
		mDialog.dismiss();
		 if(result!=null){
			 SearchOnMapAdapter mAdapter=new SearchOnMapAdapter();
			 mListView.setAdapter(mAdapter);
			 //new AlertDialogs(SearchOnMapHome.this,"" ,getResources().getString(R.string.alert_uploaddetailsucessfull));
		 }else
			 new AlertDialogs(SearchOnMapHome.this,"" ,getResources().getString(R.string.alert_internetconnectionslow));
		
	}
}

private class SaveSearchData extends AsyncTask<String,String,String>{

	ProgressDialog mDialog;
	boolean isFavourites;
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		mDialog=ProgressDialog.show(SearchOnMapHome.this, "", getResources().getString(R.string.msg_dialog), true);
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	protected String doInBackground(String... arg0) {
		// TODO Auto-generated method stub	
		 int result;
		try{
			Map<String, Object> nameValuePairs = new  LinkedHashMap<String, Object>();
			
			nameValuePairs.put("ID",0);
			nameValuePairs.put("UserId",Integer.parseInt(Constants.USERID));
			nameValuePairs.put("Lat",Float.parseFloat(Constants.USERHOUSELATI));
		    nameValuePairs.put("Long",Float.parseFloat(Constants.USERHOUSELONGI));
			nameValuePairs.put("Address","null");
		    nameValuePairs.put("Radius",bar_Redius.getSecondaryProgress());
		    nameValuePairs.put("AvailableFor",isRentSalePG);
		    nameValuePairs.put("Beds",bar_Looking.getSecondaryProgress());
		    nameValuePairs.put("isBudgetSpecifiedAsRange",true);
		    nameValuePairs.put("BudgetMin",0);
		    nameValuePairs.put("BudgetMax",bar_Budget.getSecondaryProgress());
		    nameValuePairs.put("isDepositSpecifiedAsRange",true);
		    nameValuePairs.put("DepositMin",0);
		    nameValuePairs.put("DepositMax",bar_Deposit.getSecondaryProgress());
		    nameValuePairs.put("PGAccomodationFor",mPGAccomdation);
		    nameValuePairs.put("SavedDate","");
		    String mConvert=JSONValue.toJSONString(nameValuePairs);
		    result=new HttpRequest().HttpRequestPostStatusCode(getResources().getString(R.string.url)+"Users/"+Constants.USERMOBILENUMBER+"/SavedSearches",mConvert);
		  
			    if(result==200||result==201||result==203||result==204){
					isFavourites=true;
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
			new AlertDialogs(SearchOnMapHome.this,"" ,getResources().getString(R.string.alert_savesearchrecord));
		 }else{
			 new AlertDialogs(SearchOnMapHome.this,"" ,getResources().getString(R.string.alert_nosavesearchrecord));
				 
		 }
		 if(!new Constants().isOnline(SearchOnMapHome.this)){
			 new AlertDialogs(SearchOnMapHome.this,"" ,getResources().getString(R.string.alert_internetconnectionslow));
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
    	   if(mDataSearchHome.get(position).get("FavoriteID").equalsIgnoreCase("null")){
    		   holder.isFavimage.setBackgroundResource(R.drawable.unselected_add_to_favourite); 
    		   holder.isFavimage.setTag(""+position+","+false);
    	   }else{
    		   holder.isFavimage.setBackgroundResource(R.drawable.add_home_as_favourite); 
    		   holder.isFavimage.setTag(""+position+","+true);
    	   }
    	   holder.isFavimage.setOnClickListener(new View. OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String mId=(String) arg0.getTag();
				String[]mSplit=mId.split(",");
				isFavAddRemove=Boolean.parseBoolean(mSplit[1]);
				 new AddActiveTask().execute(mSplit[0]);
	 		}
		});
         return vi;
        }
	}

private class AddActiveTask extends AsyncTask<String,String,String>{

	ProgressDialog mDialog;
	private boolean isFavourites;
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		mDialog=ProgressDialog.show(SearchOnMapHome.this, "", getResources().getString(R.string.msg_dialog), true);
	}
	@Override
	protected String doInBackground(String... arg0) {
		// TODO Auto-generated method stub			
		try{
			
			int index=Integer.parseInt(arg0[0]); 
			if(!isFavAddRemove){
		        Map nameValuePairs = new LinkedHashMap();
	 	        nameValuePairs.put("PropertyID",Integer.parseInt(mDataSearchHome.get(index).get("PropertyID")));
			    nameValuePairs.put("UserID",Integer.parseInt(Constants.USERID));
			    String mConvert=JSONValue.toJSONString(nameValuePairs);
		        String result=new HttpRequest().HttpRequestPost(getResources().getString(R.string.url)+"Users/"+Constants.USERMOBILENUMBER+"/Favorites",mConvert);
		        JSONObject jObject=new JSONObject(result);
		        String mFavId=jObject.getString("ID");
		        if(mFavId.length()>0&& !mFavId.equalsIgnoreCase("null")){
		        	isFavourites=true;
		        	mDataSearchHome.get(index).put("FavoriteID",mFavId);
		        }else{
		        	isFavourites=false;
		        }
			}else{
			  int result=new HttpRequest().HttpRequestDeleteStatusCode(getResources().getString(R.string.url)+"Users/"+Constants.USERMOBILENUMBER+"/Favorites/"+mDataSearchHome.get(index).get("FavoriteID"));
			  if(result==200||result==201||result==203||result==204){
					isFavourites=true;
					mDataSearchHome.get(index).put("FavoriteID","null");
				}else{
					isFavourites=false;
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
		 if(isFavourites){
			 SearchOnMapAdapter mAdapter=new SearchOnMapAdapter();
			 mListView.setAdapter(mAdapter);
		 }
		 if(!new Constants().isOnline(SearchOnMapHome.this)){
			 new AlertDialogs(SearchOnMapHome.this,"" ,getResources().getString(R.string.alert_internetconnectionslow));
		 }
	}
}


}