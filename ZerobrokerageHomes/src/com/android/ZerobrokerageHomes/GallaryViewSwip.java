package com.android.ZerobrokerageHomes;

import com.android.zero.utils.AlertDialogs;
import com.android.zero.utils.Constants;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.LocationSource.OnLocationChangedListener;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.widget.Gallery;

public class GallaryViewSwip extends Gallery {

	public GallaryViewSwip(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
            float velocityY) {
		//super.onFling(e1, e2, 0, velocityY);

        return false;
      }
}
