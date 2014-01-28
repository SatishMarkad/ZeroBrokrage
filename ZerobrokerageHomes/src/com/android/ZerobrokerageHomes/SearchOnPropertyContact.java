package com.android.ZerobrokerageHomes;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class SearchOnPropertyContact  {
    /** Called when the activity is first created. */
   private TextView mText_Call,mText_Email,mText_SMS;
   private ImageView onCall,onEmail,onSMS;
	
   public SearchOnPropertyContact(final Context context,View view,String number,String email) {
        //  setContentView(R.layout.activity_searchpropertydetails_contact);
         
   
		mText_Call=(TextView)view. findViewById(R.id.contcat_call);
		mText_Email=(TextView)view. findViewById(R.id.contcat_email);
		mText_SMS=(TextView)view. findViewById(R.id.contcat_sms);
		onCall=(ImageView) view.findViewById(R.id.onCall);
		onEmail=(ImageView) view.findViewById(R.id.onEmail);
		onSMS=(ImageView) view.findViewById(R.id.onSMS);
		mText_Call.setText(number);
		mText_Email.setText(email);
		mText_SMS.setText(number);
		onCall.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent callIntent = new Intent(Intent.ACTION_CALL);
				callIntent.setData(Uri.parse("tel:"+mText_Call.getText().toString()));
				context.startActivity(callIntent);
			}
		});
		
		onEmail.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Intent.ACTION_SENDTO); // it's not ACTION_SEND
				 intent.setType("text/plain");
				 intent.putExtra(Intent.EXTRA_SUBJECT, "Inquiry about the property");
				 intent.putExtra(Intent.EXTRA_TEXT, "I am interested in this property. Please acknowledge with response");
				 intent.setData(Uri.parse("mailto:"+mText_Email.getText().toString())); // or just "mailto:" for blank
				 intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // this will make such that when user returns to your app, your app is displayed, instead of the email app.
				 context.startActivity(intent);
			}
		});
		
		onSMS.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent sendIntent = new Intent(Intent.ACTION_VIEW);
				sendIntent.putExtra("sms_body", "I am interested in this property.");
				sendIntent.putExtra("address", ""+mText_SMS.getText().toString());
				sendIntent.setType("vnd.android-dir/mms-sms");
				context.startActivity(sendIntent);
			}
		});
	}
}