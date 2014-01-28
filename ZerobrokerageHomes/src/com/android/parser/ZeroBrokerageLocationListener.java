package com.android.parser;

import com.android.zero.utils.Constants;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

public class ZeroBrokerageLocationListener implements LocationListener  {
	
	
	
	public void onLocationChanged(Location location) {
		Constants.mLatitude=location.getLatitude();
		Constants.mLongitude=location.getLongitude();
		Log.e("Latitude", ""+Constants.mLatitude);
	}

	public void onProviderDisabled(String provider) {
		//gpsDisabledDialog();
	}

	public void onProviderEnabled(String provider) {
	}

	public void onStatusChanged(String provider, int status, Bundle extras) {
	}
}
