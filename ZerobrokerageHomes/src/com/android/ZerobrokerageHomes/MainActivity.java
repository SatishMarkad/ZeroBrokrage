package com.android.ZerobrokerageHomes;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.ZerobrokerageHomes.R;
import com.android.zero.utils.Constants;
import com.blundell.test.BillingHelper;
import com.blundell.test.BillingService;

public class MainActivity extends Activity implements OnClickListener{

	private static final String TAG = "BillingService";
	private TextView txt_CurrentLocation;
	String str_CurrentLocationName;
	private ImageView img_Recharge;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_screen);
		
		initializtion();
		
	}

	private void initializtion() {
		// TODO Auto-generated method stub
		
		//Start billing service
		startService(new Intent(this, BillingService.class));
        BillingHelper.setCompletedHandler(mTransactionHandler);
        
		txt_CurrentLocation=(TextView) findViewById(R.id.txt_CurrentLocation);
		 
		if(""+Constants.mLatitude!=null&&""+Constants.mLongitude!=null){
                try {
                	Geocoder geocoder;
                	List<Address> addresses;
                	geocoder = new Geocoder(this);
                	addresses = geocoder.getFromLocation(Constants.mLatitude,Constants.mLongitude, 1);
                	if(addresses.size()>0){
                	str_CurrentLocationName = addresses.get(0).getAddressLine(0)+","+ addresses.get(0).getAddressLine(1);

                    txt_CurrentLocation.setText(str_CurrentLocationName);
                    Log.e("add",""+str_CurrentLocationName);
                	}
                   
                }
                catch (IOException e) {                
                    e.printStackTrace();
                }  
            }}
          
	
   	public void onUserProfile(View v){
		
	       Intent intent = new Intent (this,UserProfileActivity.class); 
	       startActivity(intent);
	}
	
	/*public void onRecharge(View v) {
		
        if(BillingHelper.isBillingSupported()){
			BillingHelper.requestPurchase(this, "android.test.purchased"); 
			Log.e("TAG"," purchase on this device");
			// android.test.purchased or android.test.canceled or android.test.refunded or com.blundell.item.passport
        } else {
        	Log.e("TAG","Can't purchase on this device");
        	//purchaseButton.setEnabled(false); // XXX press button before service started will disable when it shouldnt
      }  
	}*/
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.img_Recharge:
			if(BillingHelper.isBillingSupported()){
				BillingHelper.requestPurchase(this, "android.test.purchased"); 
				// android.test.purchased or android.test.canceled or android.test.refunded or com.blundell.item.passport
	        } else {
	        	Log.i(TAG,"Can't purchase on this device");
	        	//purchaseButton.setEnabled(false); // XXX press button before service started will disable when it shouldnt
	        }
			
			break;
		default:
			// nada
			Log.i(TAG,"default. ID: "+v.getId());
			break;
		}
		
	}
	public void onHomeSearch(View v){
		
		startActivity(new Intent(MainActivity.this,SearchOnMapHome.class));
		
	}
	
	public void onHomeAds(View v){
		startActivity(new Intent(MainActivity.this,AddPropertyActivity.class));
	}
	
	public void onHomePreferances(View v){
		
	}
	
	public void onFavouritsHome(View v){
		startActivity(new Intent(MainActivity.this,FavouriteHomeActivity.class).putExtra("URL","Favorites"));
	}
	
	public void onSaveSearch(View v){
		startActivity(new Intent(MainActivity.this,SaveSearchActivity.class).putExtra("URL","SavedSearches"));
	}
	
	public void onNotification(View v){
		
	}
	
	public void onFeadBack(View v){
		
	}
	
	public void onReferApp(View v){
		
	}
	
	public void onHelp(View v){
	
		
	}
	
	public void onInfo(View v){
		
	}
	public Handler mTransactionHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			Log.i(TAG, "Transaction complete");
			//Log.i(TAG, "Transaction status: "+BillingHelper.latestPurchase.purchaseState);
			//Log.i(TAG, "Item purchased is: "+BillingHelper.latestPurchase.productId);
			
			/*if(BillingHelper.latestPurchase.isPurchased()){
				showItem();
			}*/
		};
	
	};

	private void showItem() {
		//purchaseableItem.setVisibility(View.VISIBLE);
	}
	
	@Override
	protected void onPause() {
		Log.i(TAG, "onPause())");
		super.onPause();
	}
	
	@Override
	protected void onDestroy() {
		BillingHelper.stopService();
		super.onDestroy();
	}
	}
