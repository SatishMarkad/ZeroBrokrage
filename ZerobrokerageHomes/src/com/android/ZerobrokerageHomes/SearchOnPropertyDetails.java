package com.android.ZerobrokerageHomes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.JSONValue;

import com.android.ZerobrokerageHomes.SearchOnPropertyDetailsActivity.GalleryAdapter;
import com.android.parser.HttpRequest;
import com.android.zero.utils.Constants;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;
import android.widget.ViewFlipper;

public class SearchOnPropertyDetails  {
  
	
 private LinearLayout layout_AddressDetails,layout_OtherDetails,layout_InternalDetails,layout_ExternalDetails;
 private TextView txt_FlatHouse,txt_Building,txt_Street,txt_Area,txt_Town,txt_LandMark,txt_State,txt_Pin;
 private TextView txt_TypeId,txt_Beds,txt_Baths,txt_Balconi,txt_BuildUpArea,txt_CarpetArea,
		 txt_Rent,txt_LeaseTerm,txt_LeasDeposit,txt_Maintance,txt_Utilities,
		 txt_AvialableFor,txt_AvailableDate,
		 txt_AgeOfBuilding,txt_TotalBuilding,
		 txt_FloorNo,txt_TotalFloor,txt_NoOfLift,txt_NoOfPackage;   
 private ImageView img_Furnished,img_Inverter,img_PipedGas,img_GasLeakDetector,img_PanicButton,img_VideoDoorphone,img_Intercom,
                  img_Pets,img_WaterStorage;
 
 private ImageView img_Clubhouse,img_Gym,img_MaintenanceStaff,img_ReservedParking,img_ServiceLift,img_VisitorParking,img_GarbageChute,
 img_InternetWifi,img_Playarea,img_RWH,img_STP,img_Garden,img_Lift,img_PowerBackup,img_Security,img_SwimmingPool;
 
 private HashMap<String,String> mPropetyDetails;
 private Context mContext;
 private View mView;
 
 private ImageView onNextAddressPage,onBackOtherDetailsPage,onNextOtherDetailsPage,onBackInternalAmenitiesPage,onNextInternalAmenitiesPage,onBackExternalAmenitiesPage;
public SearchOnPropertyDetails(Context context,View viewF,HashMap<String,String> mPropetyD) {
	// TODO Auto-generated method stub
	
	//(R.layout.activity_searchpropertydetails);
	mContext=context;
	mPropetyDetails=mPropetyD;
	mView=viewF;
	initilization();
	
}
 
 private void initilization() {
	// TODO Auto-generated method stub
	layout_AddressDetails=(LinearLayout)mView.findViewById(R.id.layout_propertyAddress);
	layout_OtherDetails=(LinearLayout)mView. findViewById(R.id.layout_propertyOtherDetails);
	layout_InternalDetails=(LinearLayout)mView. findViewById(R.id.layout_propertyInternalAminities);
	layout_ExternalDetails=(LinearLayout)mView. findViewById(R.id.layout_propertyExternalAmenities);
	initilizationAddress();
	initilizationOther();
	initilizationInnternal();
	initilizationExternal();
	initilizationBackNextButton();
	new ProperyDetailsTask().execute();
}

 
 private void initilizationExternal() {
	// TODO Auto-generated method stub
	 img_Clubhouse=(ImageView)mView. findViewById(R.id.ExternalAmenities_Clubhouse);
     img_Gym=(ImageView) mView.findViewById(R.id.externalAmenities_Gym);
     img_MaintenanceStaff=(ImageView) mView.findViewById(R.id.externalAmenities_MaintenanceStaff);
     img_ReservedParking=(ImageView) mView.findViewById(R.id.externalAmenities_ReservedParking);
     img_ServiceLift=(ImageView) mView.findViewById(R.id.externalAmenities_ServiceLift);
     img_VisitorParking=(ImageView) mView.findViewById(R.id.externalAmenities_VisitorParking);
     img_GarbageChute=(ImageView) mView.findViewById(R.id.externalAmenities_GarbageChute);
	 img_InternetWifi=(ImageView) mView.findViewById(R.id.externalAmenities_InternetWifi);
	 img_Playarea=(ImageView) mView.findViewById(R.id.externlAmenities_Playarea);
	 img_RWH=(ImageView) mView.findViewById(R.id.externalAmenities_RWH);
	 img_STP=(ImageView)mView.findViewById(R.id.externlAmenities_STP);
	 img_Garden=(ImageView)mView. findViewById(R.id.externalAmenities_Garden);
	 img_Lift=(ImageView)mView. findViewById(R.id.externlAmenities_Lift);
	 img_PowerBackup=(ImageView) mView.findViewById(R.id.externalAmenities_PowerBackup);
	 img_Security=(ImageView)mView. findViewById(R.id.externlAmenities_Security);
	 img_SwimmingPool=(ImageView)mView. findViewById(R.id.externalAmenities_SwimmingPool);
	 
}

private void initilizationInnternal() {
	// TODO Auto-generated method stub
	img_Furnished=(ImageView)mView. findViewById(R.id.internalAmenities_Furnished);
	img_Inverter=(ImageView)mView. findViewById(R.id.internalAmenities_Inverter);
	img_PipedGas=(ImageView)mView. findViewById(R.id.internalAmenities_PipedGas);
	img_GasLeakDetector=(ImageView) mView.findViewById(R.id.internalAmenities_GasLeakDetector);
	img_PanicButton=(ImageView)mView. findViewById(R.id.internalAmenities_PanicButton);
	img_VideoDoorphone=(ImageView)mView. findViewById(R.id.internalAmenities_VideoDoorphone);
	img_Intercom=(ImageView)mView. findViewById(R.id.internalAmenities_Intercom);
    img_Pets=(ImageView) mView.findViewById(R.id.internalAmenities_Pets);
    img_WaterStorage=(ImageView) mView.findViewById(R.id.internalAmenities_WaterStorage);
}

private void initilizationOther() {
	// TODO Auto-generated method stub
	
	 txt_TypeId=(TextView)mView. findViewById(R.id.otherDetails_TypeId);
	 txt_Beds=(TextView) mView.findViewById(R.id.otherDetails_Beds);
	 txt_Baths=(TextView) mView.findViewById(R.id.otherDetails_Baths);
	 txt_Balconi=(TextView)mView. findViewById(R.id.otherDetails_Balconies);
	 txt_BuildUpArea=(TextView)mView. findViewById(R.id.otherDetails_BuiltArea);
	 txt_CarpetArea=(TextView)mView. findViewById(R.id.otherDetails_CarpetArea);
	 txt_Rent=(TextView)mView. findViewById(R.id.otherdetails_Rent);
	 txt_LeaseTerm=(TextView)mView. findViewById(R.id.otherDetails_LeaseTerm);
	 txt_LeasDeposit=(TextView) mView.findViewById(R.id.otherDetails_LeaseDeposit);
	 txt_Maintance=(TextView)mView. findViewById(R.id.otherDetails_Maintenance);
	 txt_Utilities=(TextView) mView.findViewById(R.id.otherDetails_Utilities);
	 txt_AvialableFor=(TextView)mView. findViewById(R.id.otherdetails_AvailableFor);
	 txt_AvailableDate=(TextView)mView. findViewById(R.id.otherDetails_AvailableDate);
	 txt_AgeOfBuilding=(TextView)mView. findViewById(R.id.otherDetails_AgeofBuilding);
	 txt_TotalBuilding=(TextView)mView. findViewById(R.id.otherDetails_TotalBuilding);
	 txt_FloorNo=(TextView)mView. findViewById(R.id.otherDetails_FloorNo);
	 txt_TotalFloor=(TextView)mView. findViewById(R.id.otherDetails_TotalFloors);
	 txt_NoOfLift=(TextView)mView. findViewById(R.id.otherDetails_NoofLifts);
	 txt_NoOfPackage=(TextView)mView. findViewById(R.id.otherDetails_NoofParkings); 
}

private void initilizationAddress() {
	// TODO Auto-generated method stub
	txt_FlatHouse=(TextView) mView.findViewById(R.id.address_Flat);
	txt_Building=(TextView)mView.findViewById(R.id.address_Building);
	txt_Street=(TextView)mView.findViewById(R.id.address_Street);
	txt_Area=(TextView)mView. findViewById(R.id.address_Area);
	txt_Town=(TextView) mView.findViewById(R.id.address_Town);
	txt_LandMark=(TextView)mView. findViewById(R.id.address_Landmark);
	txt_State=(TextView) mView.findViewById(R.id.address_State);
	txt_Pin=(TextView)mView. findViewById(R.id.address_Pin);
}

private void initilizationBackNextButton() {
	// TODO Auto-generated method stub
	 onNextAddressPage=(ImageView) mView.findViewById(R.id.onNextAddressPage);
	 onBackOtherDetailsPage=(ImageView) mView.findViewById(R.id.onBackOtherDetailsPage);
	 onNextOtherDetailsPage=(ImageView) mView.findViewById(R.id.onNextOtherDetailsPage);
	 onBackInternalAmenitiesPage=(ImageView) mView.findViewById(R.id.onBackInternalAmenitiesPage);
	 onNextInternalAmenitiesPage=(ImageView) mView.findViewById(R.id.onNextInternalAmenitiesPage);
	 onBackExternalAmenitiesPage=(ImageView)mView.findViewById(R.id.onBackExternalAmenitiesPage);
	 onNextAddressPage.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			layout_AddressDetails.setVisibility(v.GONE);
			layout_OtherDetails.setVisibility(v.VISIBLE);
		}
	});
	 onBackOtherDetailsPage.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				layout_AddressDetails.setVisibility(v.VISIBLE);
				layout_OtherDetails.setVisibility(v.GONE);
			}
		}); 
	 onNextOtherDetailsPage.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				 layout_OtherDetails.setVisibility(v.GONE);
				 layout_InternalDetails.setVisibility(v.VISIBLE);
			}
		}); 
	 onBackInternalAmenitiesPage.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				layout_OtherDetails.setVisibility(v.VISIBLE);
				 layout_InternalDetails.setVisibility(v.GONE);
			}
		}); 
	 onNextInternalAmenitiesPage.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				 layout_InternalDetails.setVisibility(v.GONE);
				 layout_ExternalDetails.setVisibility(v.VISIBLE);
			}
		}); 
	 onBackExternalAmenitiesPage.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				layout_InternalDetails.setVisibility(v.VISIBLE);
				 layout_ExternalDetails.setVisibility(v.GONE);
			}
		}); 
}


 
private class ProperyDetailsTask extends AsyncTask<String,String,String>{

		ProgressDialog mDialog;
		
		
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			mDialog=ProgressDialog.show(mContext, "",mContext.getResources().getString(R.string.msg_dialog), true);
			
		}
		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub			
			
			return null;
		}
		
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			mDialog.dismiss();
			setEditPropertyDetails();
		}
	}

	private void setEditPropertyDetails() {
		// TODO Auto-generated method stub
	
        txt_FlatHouse.setText(mPropetyDetails.get("FlatHouseNumber"));
        txt_Building.setText(mPropetyDetails.get("BuildingSocietyName"));
        txt_Street.setText(mPropetyDetails.get("StreetRoadName"));
        txt_Area.setText(mPropetyDetails.get("AreaLocality"));
        txt_LandMark.setText(mPropetyDetails.get("Landmark"));
        txt_Pin.setText(mPropetyDetails.get("Pincode"));
        txt_Town.setText(mPropetyDetails.get("TownCityDistrict"));
        txt_Rent.setText(mPropetyDetails.get("Rent"));
        txt_LeasDeposit.setText(mPropetyDetails.get("LeaseDeposit"));
        txt_Maintance.setText(mPropetyDetails.get("Maintenance"));
        txt_Utilities.setText(mPropetyDetails.get("Utilities"));
        txt_LeaseTerm.setText(mPropetyDetails.get("LeaseTerm"));
        txt_AvailableDate.setText(mPropetyDetails.get("AvailableOn"));
        txt_BuildUpArea.setText(mPropetyDetails.get("BuiltupArea"));
        txt_CarpetArea.setText(mPropetyDetails.get("CarpetArea"));
        //.setText(mPropetyDetails.get("Description"));

        txt_TypeId.setText(mPropetyDetails.get("TypeID"));
        txt_AvialableFor.setText(mPropetyDetails.get("AvailableFor"));
        //img_Furnished.setText(mPropetyDetails.get("Furniture"));
        //txt_FloorNo.setText(mPropetyDetails.get("Floor"));
        txt_Beds.setText(mPropetyDetails.get("Beds"));
        txt_Baths.setText(mPropetyDetails.get("Baths"));
        //txt_b.setText(mPropetyDetails.get("BikeParking"));
        txt_Balconi.setText(mPropetyDetails.get("Balconies"));
        txt_NoOfPackage.setText(mPropetyDetails.get("NoOfCarParkings"));
        txt_State.setText(mPropetyDetails.get("State"));
        txt_FloorNo.setText(mPropetyDetails.get("FloorNo"));
        txt_TotalFloor.setText(mPropetyDetails.get("TotalFloors"));
        txt_TotalBuilding.setText(mPropetyDetails.get("TotalBuildings"));
        txt_NoOfLift.setText(mPropetyDetails.get("NoOfLifts"));
        txt_AgeOfBuilding.setText(mPropetyDetails.get("AgeOfBuilding"));
        //txt_TotalBuilding.setText(mPropetyDetails.get("TotalBuildings"));
        
        setToggleButton(img_Furnished,Boolean.parseBoolean(mPropetyDetails.get("Furnished")));
        setToggleButton(img_GasLeakDetector,Boolean.parseBoolean(mPropetyDetails.get("GasLeakDetector")));
        setToggleButton(img_Intercom,Boolean.parseBoolean(mPropetyDetails.get("Intercom")));
        setToggleButton(img_Inverter,Boolean.parseBoolean(mPropetyDetails.get("Inverte")));
        setToggleButton(img_PanicButton,Boolean.parseBoolean(mPropetyDetails.get("PanicButton")));
        setToggleButton(img_Pets,Boolean.parseBoolean(mPropetyDetails.get("Pets")));
        setToggleButton(img_PipedGas,Boolean.parseBoolean(mPropetyDetails.get("PipedGas")));
        setToggleButton(img_VideoDoorphone,Boolean.parseBoolean(mPropetyDetails.get("VideoDoorphone")));
        setToggleButton(img_WaterStorage,Boolean.parseBoolean(mPropetyDetails.get("WaterStorage")));
        setToggleButton(img_Lift,Boolean.parseBoolean(mPropetyDetails.get("Lift")));
        setToggleButton(img_Gym,Boolean.parseBoolean(mPropetyDetails.get("Gym")));
        setToggleButton(img_GarbageChute,Boolean.parseBoolean(mPropetyDetails.get("GarbageChute")));
        setToggleButton(img_PowerBackup,Boolean.parseBoolean(mPropetyDetails.get("PowerBackup")));
        setToggleButton(img_VisitorParking,Boolean.parseBoolean(mPropetyDetails.get("VisitorParking")));
        setToggleButton(img_InternetWifi,Boolean.parseBoolean(mPropetyDetails.get("InternetWifi")));
        setToggleButton(img_ServiceLift,Boolean.parseBoolean(mPropetyDetails.get("ServiceLift")));
        setToggleButton(img_Clubhouse,Boolean.parseBoolean(mPropetyDetails.get("Clubhouse")));
        setToggleButton(img_SwimmingPool,Boolean.parseBoolean(mPropetyDetails.get("SwimmingPool")));
        setToggleButton(img_Garden,Boolean.parseBoolean(mPropetyDetails.get("Garden")));
        setToggleButton(img_Playarea,Boolean.parseBoolean(mPropetyDetails.get("Playarea")));
        setToggleButton(img_Security,Boolean.parseBoolean(mPropetyDetails.get("Security")));
        setToggleButton(img_WaterStorage,Boolean.parseBoolean(mPropetyDetails.get("WaterStorage")));
        setToggleButton(img_ReservedParking,Boolean.parseBoolean(mPropetyDetails.get("ReservedParking")));
        setToggleButton(img_MaintenanceStaff,Boolean.parseBoolean(mPropetyDetails.get("MaintenanceStaff")));
        setToggleButton(img_RWH,Boolean.parseBoolean(mPropetyDetails.get("RWH")));
        setToggleButton(img_STP,Boolean.parseBoolean(mPropetyDetails.get("STP")));
	}
    
	
	
	private void setToggleButton(ImageView tB,boolean isTrue){
		
		try{
			
			if(isTrue)
				tB.setImageResource(R.drawable.switch_to_map_view);
			else
				tB.setImageResource(R.drawable.delete);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
