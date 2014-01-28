package com.android.zero.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;


public class AlertDialogs {

	private Context mContext;
	private AlertDialog.Builder mDialog;
	
	
	public AlertDialogs(Context context,String mTitle,String mMsg) {
		
		// TODO Auto-generated constructor stub
		mDialog=new AlertDialog.Builder(context);
		 mDialog.setMessage(mMsg);
		 
	        // Setting Icon to Dialog
	       // alertDialog.setIcon(R.drawable.delete);
	 
	        // Setting Positive "Yes" Button
	        mDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialog,int which) {
	 
	            dialog.dismiss();
	            }
	        });
	        
	        mDialog.show();
	}
	
}