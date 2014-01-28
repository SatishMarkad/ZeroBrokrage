package com.android.zero.utils;

import java.util.HashMap;
import java.util.Map;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.android.ZerobrokerageHomes.LoginActivity;
import com.twilio.client.Connection;
import com.twilio.client.Device;
import com.twilio.client.DeviceListener;
import com.twilio.client.PresenceEvent;
import com.twilio.client.Twilio;

public class DeviceTokenGenerater implements Twilio.InitListener,DeviceListener
{
    Device device;
    String capabilityToken,mobileNumber;
    private Connection connection;
    private Context mContext;
     public DeviceTokenGenerater(Context context,String t,String mobilen)
     {
    	 mContext=context;
    	 capabilityToken=t;
    	 mobileNumber="+91"+mobilen;
         Twilio.initialize(context, this /* Twilio.InitListener */);
     }
  
   
     
     public void onInitialized()
     {
       Log.d("token", ""+capabilityToken);
  
         try {
        	
             device = Twilio.createDevice(capabilityToken, null);
           
             Intent intent = new Intent(mContext, LoginActivity.class);
             PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
             device.setIncomingIntent(pendingIntent);
         } catch (Exception e) {
             Log.e("f", "Failed to obtain capability token: " + e.getLocalizedMessage());
         }
         
     }
  
     public void connect()
     {
    	 if (connection == null){
             Log.e("TAG", "Failed to create new connection");
             onInitialized();
    	 }
    	  Map<String,String> params=new HashMap<String, String>(1);
          params.put("MobileNumber",mobileNumber);
          connection = device.connect(params /* parameters */, null /* ConnectionListener */);
          
         
         Log.e("TAG", "create new connection="+mobileNumber);
     }
     
     @Override /* Twilio.InitListener method */
     public void onError(Exception e)
     {
         Log.e("f", "Twilio SDK couldn't start: " + e.getLocalizedMessage());
     }
  
     @Override
     protected void finalize()
     {
    	 if (connection != null)
             connection.disconnect();
         if (device != null)
             device.release();
     }


     public void handleIncomingConnection(Device inDevice, Connection inConnection)
     {
         Log.e("TAG", "Device received incoming connection");
         if (connection != null)
             connection.disconnect();
             connection = inConnection;
             connection.accept();
     }
  

	@Override
	public void onPresenceChanged(Device arg0, PresenceEvent arg1) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void onStartListening(Device arg0) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void onStopListening(Device arg0) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void onStopListening(Device arg0, int arg1, String arg2) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public boolean receivePresenceEvents(Device arg0) {
		// TODO Auto-generated method stub
		return false;
	}
 }
