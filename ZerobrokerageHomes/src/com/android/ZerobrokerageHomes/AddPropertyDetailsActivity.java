package com.android.ZerobrokerageHomes;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.JSONValue;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.ToggleButton;

import com.android.parser.HttpRequest;
import com.android.zero.utils.AlertDialogs;
import com.android.zero.utils.Constants;
public class AddPropertyDetailsActivity extends Activity {

  private RelativeLayout mLayout_1,mLayout_2,mLayout_3,mLayout_4,mLayout_PropertyAddress;
  
  private ToggleButton Furnished_tbtn,GasLeakDetector_tbtn,Intercom_tbtn,Inverte_tbtn,PanicButton_tbtn,Pets_tbtn,PipedGas_tbtn,VideoDoorphone_tbtn,WaterStorage_tbtn,
                    Lift_tbtn,Gym_tbtn,GarbageChute_tbtn,PowerBackup_tbtn,VisitorParking_tbtn,ServiceLift_tbtn,InternetWifi_tbtn,Clubhouse_tbtn,SwimmingPool_tbtn,Garden_tbtn,
                    Playarea_tbtn,Security_tbtn,ReservedParking_tbtn,MaintenanceStaff_tbtn,RWH_tbtn,STP_tbtn;
  
  private Spinner spinner_Type,spinner_Availableon,spinner_Furniture,spinner_Floor,spinner_beds,spinner_baths,spinner_Balconies,
                 spinner_NoOfCarParkings,spinner_BikeParking,spinner_States,spinner_FloorNo,spinner_TotalFloors,spinner_TotalBuildings,spinner_NoOfLifts,spinner_AgeOfBuilding;
 
  private EditText 
                   //screen Fristt
                   FlatHouseNumber_edt,BuildingSocietyName_edt,StreetRoadName_edt,AreaLocality_edt,Landmark_edt,Pincode_edt,TownCity_edt,
                   //Screen second
                   Rent_edt,LeaseDeposite_edt,Maintance_edt,Utilities_edt,LeaseTerm_edt,calender_edt,
                   //Screen Thrid
                   BuiltupArea_edt,CarpetArea_edt,PropertyDescrption_edt;
  
  private ArrayList<String> State_ArrayList,State_IDArrayList, TypeID_ArrayList,TypeID_IDArrayList;
  private boolean isEditProperty;
  
  private String[] Floor_str;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_adddetails_screen);
		
		initializtion();
		
	}

	private void initializtion() {
		// TODO Auto-generated method stub
		isEditProperty=getIntent().getBooleanExtra("Edit",false);
		Constants.PROPERTYID="";
		mLayout_PropertyAddress=(RelativeLayout) findViewById(R.id.layout_propertyaddress);
		mLayout_1=(RelativeLayout) findViewById(R.id.layout_Detail_Page1);
		mLayout_2=(RelativeLayout) findViewById(R.id.layout_Detail_Page2);
		mLayout_3=(RelativeLayout) findViewById(R.id.layout_Detail_Page3);
		mLayout_4=(RelativeLayout) findViewById(R.id.layout_Detail_Page4);
		
		//Screen Frist
		
		 FlatHouseNumber_edt=(EditText) findViewById(R.id.edt_flathousenumber);
		 BuildingSocietyName_edt=(EditText) findViewById(R.id.edt_buildsocityname);
		 StreetRoadName_edt=(EditText) findViewById(R.id.edt_streetroadname);
		 AreaLocality_edt=(EditText) findViewById(R.id.edt_arealocality);
		 Landmark_edt=(EditText) findViewById(R.id.edt_landmark);
		 Pincode_edt=(EditText) findViewById(R.id.edt_pincode);
		 TownCity_edt=(EditText) findViewById(R.id.edt_towncitydistric);
		 spinner_States=(Spinner) findViewById(R.id.spinner_states);
		 
				 
		//new BackgroundTask().execute();
		 new BackgroundTaskStateTypeId().execute();
		
		 
		//Screen 2 Declaration
		spinner_Type=(Spinner) findViewById(R.id.spinner1);
		spinner_Availableon=(Spinner) findViewById(R.id.spinner2);
		spinner_Furniture=(Spinner) findViewById(R.id.spinner3);
		spinner_Floor=(Spinner) findViewById(R.id.spinner4);
		calender_edt=(EditText) findViewById(R.id.btn_calender);
        Rent_edt=(EditText) findViewById(R.id.edt_rental);
        LeaseDeposite_edt=(EditText) findViewById(R.id.edt_deposit);
        Maintance_edt=(EditText) findViewById(R.id.edt_maintanance);
        Utilities_edt=(EditText) findViewById(R.id.edt_Utilities);
        LeaseTerm_edt=(EditText) findViewById(R.id.edt_LeaseTerm);
        onclickEventScreen2();
        
		//Screen3 Declaration
        spinner_beds=(Spinner) findViewById(R.id.property_Infospinner1);
        spinner_baths=(Spinner) findViewById(R.id.property_Infospinner2);
        spinner_Balconies=(Spinner) findViewById(R.id.property_Infospinner3);
        spinner_FloorNo=(Spinner) findViewById(R.id.spinner_FloorNo);
        spinner_TotalFloors=(Spinner) findViewById(R.id.spinner_TotalFloors);
        spinner_TotalBuildings=(Spinner) findViewById(R.id.spinner_TotalBuildings);
        spinner_NoOfCarParkings=(Spinner) findViewById(R.id.btn_carparking);
        spinner_NoOfLifts=(Spinner) findViewById(R.id.sinner_NoOfLifts);
        spinner_AgeOfBuilding=(Spinner) findViewById(R.id.sinner_AgeOfBuilding);
		
        BuiltupArea_edt=(EditText) findViewById(R.id.edt_area);
        CarpetArea_edt=(EditText) findViewById(R.id.edt_lease);
		PropertyDescrption_edt=(EditText) findViewById(R.id.edt_property_Des);
		onclickEventScreen3();
		
		//Screen 4 Declaration
		Furnished_tbtn=(ToggleButton) findViewById(R.id.btn_Furnished);
		GasLeakDetector_tbtn=(ToggleButton) findViewById(R.id.btn_GasLeakDetector);
		Intercom_tbtn=(ToggleButton) findViewById(R.id.btn_Intercom);
		Inverte_tbtn=(ToggleButton) findViewById(R.id.btn_Inverter);
		PanicButton_tbtn=(ToggleButton) findViewById(R.id.btn_PanicButton);
		Pets_tbtn=(ToggleButton) findViewById(R.id.btn_Pets);
		PipedGas_tbtn=(ToggleButton) findViewById(R.id.btn_PipedGas);
		VideoDoorphone_tbtn=(ToggleButton) findViewById(R.id.btn_VideoDoorphone);
		WaterStorage_tbtn=(ToggleButton) findViewById(R.id.btn_WaterStorage);
		onclickEventScreen4();
		
			//Screen 5 Declaration
		Lift_tbtn=(ToggleButton) findViewById(R.id.btn_Lift);
		Gym_tbtn=(ToggleButton) findViewById(R.id.btn_Gym);
		GarbageChute_tbtn=(ToggleButton) findViewById(R.id.btn_GarbageChute);
		PowerBackup_tbtn=(ToggleButton) findViewById(R.id.btn_PowerBackup);
		VisitorParking_tbtn=(ToggleButton) findViewById(R.id.btn_VisitorParking);
		ServiceLift_tbtn=(ToggleButton) findViewById(R.id.btn_ServiceLift);
		InternetWifi_tbtn=(ToggleButton) findViewById(R.id.btn_InternetWifi);
		Clubhouse_tbtn=(ToggleButton) findViewById(R.id.btn_Clubhouse);
		SwimmingPool_tbtn=(ToggleButton) findViewById(R.id.btn_SwimmingPool);
		Garden_tbtn=(ToggleButton) findViewById(R.id.btn_Garden);
        Playarea_tbtn=(ToggleButton) findViewById(R.id.btn_PlayareaGarden);
        Security_tbtn=(ToggleButton) findViewById(R.id.btn_Security);
        ReservedParking_tbtn=(ToggleButton) findViewById(R.id.btn_ReservedParking);
        MaintenanceStaff_tbtn=(ToggleButton) findViewById(R.id.btn_MaintenanceStaff);
        RWH_tbtn=(ToggleButton) findViewById(R.id.btn_RWH);
        STP_tbtn=(ToggleButton) findViewById(R.id.btn_STP);
		onclickEventScreen5();
		
		if(isEditProperty){
			 setEditPropertyDetails();
		}
			
	}

	private void setEditPropertyDetails() {
		// TODO Auto-generated method stub
		HashMap<String,String> mPropetyDetails=(HashMap<String, String>) getIntent().getSerializableExtra("AllDetails");
		
        FlatHouseNumber_edt.setText(mPropetyDetails.get("FlatHouseNumber"));
        BuildingSocietyName_edt.setText(mPropetyDetails.get("BuildingSocietyName"));
        StreetRoadName_edt.setText(mPropetyDetails.get("StreetRoadName"));
        AreaLocality_edt.setText(mPropetyDetails.get("AreaLocality"));
        Landmark_edt.setText(mPropetyDetails.get("Landmark"));
        Pincode_edt.setText(mPropetyDetails.get("Pincode"));
        TownCity_edt.setText(mPropetyDetails.get("TownCityDistrict"));
        Rent_edt.setText(mPropetyDetails.get("Rent"));
        LeaseDeposite_edt.setText(mPropetyDetails.get("LeaseDeposit"));
        Maintance_edt.setText(mPropetyDetails.get("Maintenance"));
        Utilities_edt.setText(mPropetyDetails.get("Utilities"));
        LeaseTerm_edt.setText(mPropetyDetails.get("LeaseTerm"));
        calender_edt.setText(mPropetyDetails.get("AvailableOn"));
        BuiltupArea_edt.setText(mPropetyDetails.get("BuiltupArea"));
        CarpetArea_edt.setText(mPropetyDetails.get("CarpetArea"));
        PropertyDescrption_edt.setText(mPropetyDetails.get("Description"));

        setSpinnerValue(spinner_Type,""+mPropetyDetails.get("TypeID"));
        setSpinnerValue(spinner_Availableon,""+mPropetyDetails.get("AvailableFor"));
        setSpinnerValue(spinner_Furniture,""+mPropetyDetails.get("Furniture"));
        setSpinnerValue(spinner_Floor,""+mPropetyDetails.get("Floor"));
        setSpinnerValue(spinner_beds,""+mPropetyDetails.get("Beds"));
        setSpinnerValue(spinner_baths,""+mPropetyDetails.get("Baths"));
        setSpinnerValue(spinner_BikeParking,""+mPropetyDetails.get("BikeParking"));
        setSpinnerValue(spinner_Balconies,""+mPropetyDetails.get("Balconies"));
        setSpinnerValue(spinner_NoOfCarParkings,""+mPropetyDetails.get("NoOfCarParkings"));
        setSpinnerValue(spinner_States,""+mPropetyDetails.get("States"));
        setSpinnerValue(spinner_FloorNo,""+mPropetyDetails.get("FloorNo"));
        setSpinnerValue(spinner_TotalFloors,""+mPropetyDetails.get("TotalFloors"));
        setSpinnerValue(spinner_TotalBuildings,""+mPropetyDetails.get("TotalBuildings"));
        setSpinnerValue(spinner_NoOfLifts,""+mPropetyDetails.get("NoOfLifts"));
        setSpinnerValue(spinner_AgeOfBuilding,""+mPropetyDetails.get("AgeOfBuilding"));
        setSpinnerValue(spinner_TotalBuildings,""+mPropetyDetails.get("TotalBuildings"));
        
        setToggleButton(Furnished_tbtn,Boolean.parseBoolean(mPropetyDetails.get("Furnished")));
        setToggleButton(GasLeakDetector_tbtn,Boolean.parseBoolean(mPropetyDetails.get("GasLeakDetector")));
        setToggleButton(Intercom_tbtn,Boolean.parseBoolean(mPropetyDetails.get("Intercom")));
        setToggleButton(Inverte_tbtn,Boolean.parseBoolean(mPropetyDetails.get("Inverte")));
        setToggleButton(PanicButton_tbtn,Boolean.parseBoolean(mPropetyDetails.get("PanicButton")));
        setToggleButton(Pets_tbtn,Boolean.parseBoolean(mPropetyDetails.get("Pets")));
        setToggleButton(PipedGas_tbtn,Boolean.parseBoolean(mPropetyDetails.get("PipedGas")));
        setToggleButton(VideoDoorphone_tbtn,Boolean.parseBoolean(mPropetyDetails.get("VideoDoorphone")));
        setToggleButton(WaterStorage_tbtn,Boolean.parseBoolean(mPropetyDetails.get("WaterStorage")));
        setToggleButton(Lift_tbtn,Boolean.parseBoolean(mPropetyDetails.get("Lift")));
        setToggleButton(Gym_tbtn,Boolean.parseBoolean(mPropetyDetails.get("Gym")));
        setToggleButton(GarbageChute_tbtn,Boolean.parseBoolean(mPropetyDetails.get("GarbageChute")));
        setToggleButton(PowerBackup_tbtn,Boolean.parseBoolean(mPropetyDetails.get("PowerBackup")));
        setToggleButton(VisitorParking_tbtn,Boolean.parseBoolean(mPropetyDetails.get("VisitorParking")));
        setToggleButton(InternetWifi_tbtn,Boolean.parseBoolean(mPropetyDetails.get("InternetWifi")));
        setToggleButton(ServiceLift_tbtn,Boolean.parseBoolean(mPropetyDetails.get("ServiceLift")));
        setToggleButton(Clubhouse_tbtn,Boolean.parseBoolean(mPropetyDetails.get("Clubhouse")));
        setToggleButton(SwimmingPool_tbtn,Boolean.parseBoolean(mPropetyDetails.get("SwimmingPool")));
        setToggleButton(Garden_tbtn,Boolean.parseBoolean(mPropetyDetails.get("Garden")));
        setToggleButton(Playarea_tbtn,Boolean.parseBoolean(mPropetyDetails.get("Playarea")));
        setToggleButton(Security_tbtn,Boolean.parseBoolean(mPropetyDetails.get("Security")));
        setToggleButton(ReservedParking_tbtn,Boolean.parseBoolean(mPropetyDetails.get("ReservedParking")));
        setToggleButton(MaintenanceStaff_tbtn,Boolean.parseBoolean(mPropetyDetails.get("MaintenanceStaff")));
        setToggleButton(RWH_tbtn,Boolean.parseBoolean(mPropetyDetails.get("RWH")));
        setToggleButton(STP_tbtn,Boolean.parseBoolean(mPropetyDetails.get("STP")));
        Constants.PROPERTYID=mPropetyDetails.get("mPropertyId");
	}
    
	private void setSpinnerValue(Spinner sp,String value){
		try{
			@SuppressWarnings("rawtypes")
			ArrayAdapter adapter=(ArrayAdapter) sp.getAdapter();
			@SuppressWarnings("unchecked")
			int pos=adapter.getPosition(value);
			sp.setSelection(pos);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private void setToggleButton(ToggleButton tB,boolean isTrue){
		
		try{
			tB.setChecked(isTrue);
			if(isTrue)
				tB.setBackgroundResource(R.drawable.on);
			else
				tB.setBackgroundResource(R.drawable.off);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	private void onclickEventScreen2() {
		// TODO Auto-generated method stub
		
		ArrayAdapter<CharSequence> mAdapter=ArrayAdapter.createFromResource(AddPropertyDetailsActivity.this,R.array.furnish_array,android.R.layout.simple_spinner_item);
		mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner_Furniture.setAdapter(mAdapter);
		
		mAdapter=ArrayAdapter.createFromResource(AddPropertyDetailsActivity.this,R.array.availableon_array,android.R.layout.simple_spinner_item);
		mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner_Availableon.setAdapter(mAdapter);
		
		 Floor_str =new String[100];
			for(int f=0;f<100;f++){
				Floor_str[f]=""+f;
			}
			List<String> list = new ArrayList<String>(Arrays.asList(Floor_str));
			list.remove(0);
		ArrayAdapter<String> mAdapterArray=new ArrayAdapter<String>(AddPropertyDetailsActivity.this,android.R.layout.simple_spinner_item,list);
		mAdapterArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner_Floor.setAdapter(mAdapterArray);
		
		
		 //Data picker dialog Recent Data
		   final DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {

            // when dialog box is closed, below method will be called.
            public void onDateSet(DatePicker view, int selectedYear,
                            int selectedMonth, int selectedDay) {
                   String year =""+ selectedYear,month = ""+selectedMonth+1,
                    day =""+selectedDay;
                    calender_edt.setText(""+year+"-"+month+"-"+day);
            }
        };
        
		  
	
		   calender_edt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Calendar calendar = Calendar.getInstance();
				DatePickerDialog mDialog = new DatePickerDialog(AddPropertyDetailsActivity.this,datePickerListener , calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
				mDialog.show();
			}
		});
	}
	private void onclickEventScreen3() {
		// TODO Auto-generated method stub
		
		ArrayAdapter<CharSequence> mAdapter=ArrayAdapter.createFromResource(AddPropertyDetailsActivity.this,R.array.beds_array,android.R.layout.simple_spinner_item);
		mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner_beds.setAdapter(mAdapter);
		spinner_NoOfCarParkings.setAdapter(mAdapter);
		spinner_NoOfLifts.setAdapter(mAdapter);
		
		mAdapter=ArrayAdapter.createFromResource(AddPropertyDetailsActivity.this,R.array.baths_array,android.R.layout.simple_spinner_item);
		mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner_baths.setAdapter(mAdapter);
		
		mAdapter=ArrayAdapter.createFromResource(AddPropertyDetailsActivity.this,R.array.balcanies_array,android.R.layout.simple_spinner_item);
		mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner_Balconies.setAdapter(mAdapter);
		
		Floor_str =new String[26];
		for(int f=0;f<26;f++){
			
			Floor_str[f]=""+f;
		}
		List<String> list = new ArrayList<String>(Arrays.asList(Floor_str));
		list.remove(0);
		Floor_str = list.toArray(Floor_str);
		ArrayAdapter<String> mAdapterArray=new ArrayAdapter<String>(AddPropertyDetailsActivity.this,android.R.layout.simple_spinner_item,list);
		mAdapterArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner_FloorNo.setAdapter(mAdapterArray);
		spinner_TotalFloors.setAdapter(mAdapterArray);
	    mAdapterArray=new ArrayAdapter<String>(AddPropertyDetailsActivity.this,android.R.layout.simple_spinner_item,list);
		mAdapterArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner_TotalBuildings.setAdapter(mAdapterArray);
		
		Floor_str =new String[51];
		for(int f=0;f<51;f++){
			
			Floor_str[f]=""+f;
		}
		list = new ArrayList<String>(Arrays.asList(Floor_str));
		list.remove(0);
		Floor_str = list.toArray(Floor_str);
	    mAdapterArray=new ArrayAdapter<String>(AddPropertyDetailsActivity.this,android.R.layout.simple_spinner_item,list);
		mAdapterArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner_AgeOfBuilding.setAdapter(mAdapterArray);
	}
	
	private void onclickEventScreen4() {
		// TODO Auto-generated method stub
		
		Furnished_tbtn.setText(null);
		Furnished_tbtn.setTextOn(null);
		Furnished_tbtn.setTextOff(null);
		Furnished_tbtn.setBackgroundResource(R.drawable.toggle_on_of);
		Furnished_tbtn.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
						if(isChecked){
							Furnished_tbtn.setBackgroundResource(R.drawable.on);
						}else{
							
							Furnished_tbtn.setBackgroundResource(R.drawable.off);
						}
				
			}
		});
		
		GasLeakDetector_tbtn.setText(null);
		GasLeakDetector_tbtn.setTextOn(null);
		GasLeakDetector_tbtn.setTextOff(null);
		GasLeakDetector_tbtn.setBackgroundResource(R.drawable.toggle_on_of);
		GasLeakDetector_tbtn.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
						if(isChecked){
							GasLeakDetector_tbtn.setBackgroundResource(R.drawable.on);
						}else{
							
							GasLeakDetector_tbtn.setBackgroundResource(R.drawable.off);
						}
				
			}
		});
		
		Intercom_tbtn.setText(null);
		Intercom_tbtn.setTextOn(null);
		Intercom_tbtn.setTextOff(null);
		Intercom_tbtn.setBackgroundResource(R.drawable.toggle_on_of);
		Intercom_tbtn.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
						if(isChecked){
							Intercom_tbtn.setBackgroundResource(R.drawable.on);
						}else{
							
							Intercom_tbtn.setBackgroundResource(R.drawable.off);
						}
				
			}
		});
		
		Inverte_tbtn.setText(null);
		Inverte_tbtn.setTextOn(null);
		Inverte_tbtn.setTextOff(null);
		Inverte_tbtn.setBackgroundResource(R.drawable.toggle_on_of);
		Inverte_tbtn.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
						if(isChecked){
							Inverte_tbtn.setBackgroundResource(R.drawable.on);
						}else{
							
							Inverte_tbtn.setBackgroundResource(R.drawable.off);
						}
				
			}
		});
		
		PanicButton_tbtn.setText(null);
		PanicButton_tbtn.setTextOn(null);
		PanicButton_tbtn.setTextOff(null);
		PanicButton_tbtn.setBackgroundResource(R.drawable.toggle_on_of);
		PanicButton_tbtn.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
						if(isChecked){
							PanicButton_tbtn.setBackgroundResource(R.drawable.on);
						}else{
							
							PanicButton_tbtn.setBackgroundResource(R.drawable.off);
						}
				
			}
		});
		
		Pets_tbtn.setText(null);
		Pets_tbtn.setTextOn(null);
		Pets_tbtn.setTextOff(null);
		Pets_tbtn.setBackgroundResource(R.drawable.toggle_on_of);
		Pets_tbtn.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
						if(isChecked){
							Pets_tbtn.setBackgroundResource(R.drawable.on);
						}else{
							
							Pets_tbtn.setBackgroundResource(R.drawable.off);
						}
				
			}
		});
		
		PipedGas_tbtn.setText(null);
		PipedGas_tbtn.setTextOn(null);
		PipedGas_tbtn.setTextOff(null);
		PipedGas_tbtn.setBackgroundResource(R.drawable.toggle_on_of);
		PipedGas_tbtn.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
						if(isChecked){
							PipedGas_tbtn.setBackgroundResource(R.drawable.on);
						}else{
							
							PipedGas_tbtn.setBackgroundResource(R.drawable.off);
						}
				
			}
		});
		
		VideoDoorphone_tbtn.setText(null);
		VideoDoorphone_tbtn.setTextOn(null);
		VideoDoorphone_tbtn.setTextOff(null);
		VideoDoorphone_tbtn.setBackgroundResource(R.drawable.toggle_on_of);
		VideoDoorphone_tbtn.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
						if(isChecked){
							VideoDoorphone_tbtn.setBackgroundResource(R.drawable.on);
						}else{
							
							VideoDoorphone_tbtn.setBackgroundResource(R.drawable.off);
						}
				
			}
		});
		
		WaterStorage_tbtn.setText(null);
		WaterStorage_tbtn.setTextOn(null);
		WaterStorage_tbtn.setTextOff(null);
		WaterStorage_tbtn.setBackgroundResource(R.drawable.toggle_on_of);
		WaterStorage_tbtn.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
						if(isChecked){
							WaterStorage_tbtn.setBackgroundResource(R.drawable.on);
						}else{
							
							WaterStorage_tbtn.setBackgroundResource(R.drawable.off);
						}
				
			}
		});
	}
	
	private void onclickEventScreen5() {
		// TODO Auto-generated method stub
		
		Lift_tbtn.setText(null);
		Lift_tbtn.setTextOn(null);
		Lift_tbtn.setTextOff(null);
		Lift_tbtn.setBackgroundResource(R.drawable.toggle_on_of);
		Lift_tbtn.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
						if(isChecked){
							Lift_tbtn.setBackgroundResource(R.drawable.on);
						}else{
							
							Lift_tbtn.setBackgroundResource(R.drawable.off);
						}
				
			}
		});
		
		ServiceLift_tbtn.setText(null);
		ServiceLift_tbtn.setTextOn(null);
		ServiceLift_tbtn.setTextOff(null);
		ServiceLift_tbtn.setBackgroundResource(R.drawable.toggle_on_of);
		ServiceLift_tbtn.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
						if(isChecked){
							ServiceLift_tbtn.setBackgroundResource(R.drawable.on);
						}else{
							
							ServiceLift_tbtn.setBackgroundResource(R.drawable.off);
						}
				
			}
		});
		
		VisitorParking_tbtn.setText(null);
		VisitorParking_tbtn.setTextOn(null);
		VisitorParking_tbtn.setTextOff(null);
		VisitorParking_tbtn.setBackgroundResource(R.drawable.toggle_on_of);
		VisitorParking_tbtn.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
						if(isChecked){
							VisitorParking_tbtn.setBackgroundResource(R.drawable.on);
						}else{
							
							VisitorParking_tbtn.setBackgroundResource(R.drawable.off);
						}
				
			}
		});
		
		ServiceLift_tbtn.setText(null);
		ServiceLift_tbtn.setTextOn(null);
		ServiceLift_tbtn.setTextOff(null);
		ServiceLift_tbtn.setBackgroundResource(R.drawable.toggle_on_of);
		ServiceLift_tbtn.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
						if(isChecked){
							ServiceLift_tbtn.setBackgroundResource(R.drawable.on);
						}else{
							
							ServiceLift_tbtn.setBackgroundResource(R.drawable.off);
						}
				
			}
		});
		
		InternetWifi_tbtn.setText(null);
		InternetWifi_tbtn.setTextOn(null);
		InternetWifi_tbtn.setTextOff(null);
		InternetWifi_tbtn.setBackgroundResource(R.drawable.toggle_on_of);
		InternetWifi_tbtn.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
						if(isChecked){
							InternetWifi_tbtn.setBackgroundResource(R.drawable.on);
						}else{
							
							InternetWifi_tbtn.setBackgroundResource(R.drawable.off);
						}
				
			}
		});
		
		Gym_tbtn.setText(null);
		Gym_tbtn.setTextOn(null);
		Gym_tbtn.setTextOff(null);
		Gym_tbtn.setBackgroundResource(R.drawable.toggle_on_of);
		Gym_tbtn.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
						if(isChecked){
							Gym_tbtn.setBackgroundResource(R.drawable.on);
						}else{
							
							Gym_tbtn.setBackgroundResource(R.drawable.off);
						}
				
			}
		});
		
		Clubhouse_tbtn.setText(null);
		Clubhouse_tbtn.setTextOn(null);
		Clubhouse_tbtn.setTextOff(null);
		Clubhouse_tbtn.setBackgroundResource(R.drawable.toggle_on_of);
		Clubhouse_tbtn.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
						if(isChecked){
							Clubhouse_tbtn.setBackgroundResource(R.drawable.on);
						}else{
							
							Clubhouse_tbtn.setBackgroundResource(R.drawable.off);
						}
				
			}
		});
		
		SwimmingPool_tbtn.setText(null);
		SwimmingPool_tbtn.setTextOn(null);
		SwimmingPool_tbtn.setTextOff(null);
		SwimmingPool_tbtn.setBackgroundResource(R.drawable.toggle_on_of);
		SwimmingPool_tbtn.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
						if(isChecked){
							SwimmingPool_tbtn.setBackgroundResource(R.drawable.on);
						}else{
							
							SwimmingPool_tbtn.setBackgroundResource(R.drawable.off);
						}
				
			}
		});
		
		GarbageChute_tbtn.setText(null);
		GarbageChute_tbtn.setTextOn(null);
		GarbageChute_tbtn.setTextOff(null);
		GarbageChute_tbtn.setBackgroundResource(R.drawable.toggle_on_of);
		GarbageChute_tbtn.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
						if(isChecked){
							GarbageChute_tbtn.setBackgroundResource(R.drawable.on);
						}else{
							
							GarbageChute_tbtn.setBackgroundResource(R.drawable.off);
						}
				
			}
		});
		
		Garden_tbtn.setText(null);
		Garden_tbtn.setTextOn(null);
		Garden_tbtn.setTextOff(null);
		Garden_tbtn.setBackgroundResource(R.drawable.toggle_on_of);
		Garden_tbtn.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
						if(isChecked){
							Garden_tbtn.setBackgroundResource(R.drawable.on);
						}else{
							
							Garden_tbtn.setBackgroundResource(R.drawable.off);
						}
				
			}
		});

		Playarea_tbtn.setText(null);
		Playarea_tbtn.setTextOn(null);
		Playarea_tbtn.setTextOff(null);
		Playarea_tbtn.setBackgroundResource(R.drawable.toggle_on_of);
		Playarea_tbtn.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
						if(isChecked){
							Playarea_tbtn.setBackgroundResource(R.drawable.on);
						}else{
							
							Playarea_tbtn.setBackgroundResource(R.drawable.off);
						}
				
			}
		});
		
		PowerBackup_tbtn.setText(null);
		PowerBackup_tbtn.setTextOn(null);
		PowerBackup_tbtn.setTextOff(null);
		PowerBackup_tbtn.setBackgroundResource(R.drawable.toggle_on_of);
		PowerBackup_tbtn.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
						if(isChecked){
							PowerBackup_tbtn.setBackgroundResource(R.drawable.on);
						}else{
							
							PowerBackup_tbtn.setBackgroundResource(R.drawable.off);
						}
				
			}
		});

		Security_tbtn.setText(null);
		Security_tbtn.setTextOn(null);
		Security_tbtn.setTextOff(null);
		Security_tbtn.setBackgroundResource(R.drawable.toggle_on_of);
		Security_tbtn.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
						if(isChecked){
							Security_tbtn.setBackgroundResource(R.drawable.on);
						}else{
							
							Security_tbtn.setBackgroundResource(R.drawable.off);
						}
				
			}
		});
		
		ReservedParking_tbtn.setText(null);
		ReservedParking_tbtn.setTextOn(null);
		ReservedParking_tbtn.setTextOff(null);
		ReservedParking_tbtn.setBackgroundResource(R.drawable.toggle_on_of);
		ReservedParking_tbtn.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
						if(isChecked){
							ReservedParking_tbtn.setBackgroundResource(R.drawable.on);
						}else{
							
							ReservedParking_tbtn.setBackgroundResource(R.drawable.off);
						}
				
			}
		});

		VisitorParking_tbtn.setText(null);
		VisitorParking_tbtn.setTextOn(null);
		VisitorParking_tbtn.setTextOff(null);
		VisitorParking_tbtn.setBackgroundResource(R.drawable.toggle_on_of);
		VisitorParking_tbtn.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
						if(isChecked){
							VisitorParking_tbtn.setBackgroundResource(R.drawable.on);
						}else{
							
							VisitorParking_tbtn.setBackgroundResource(R.drawable.off);
						}
				
			}
		});
		
		MaintenanceStaff_tbtn.setText(null);
		MaintenanceStaff_tbtn.setTextOn(null);
		MaintenanceStaff_tbtn.setTextOff(null);
		MaintenanceStaff_tbtn.setBackgroundResource(R.drawable.toggle_on_of);
		MaintenanceStaff_tbtn.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
						if(isChecked){
							MaintenanceStaff_tbtn.setBackgroundResource(R.drawable.on);
						}else{
							
							MaintenanceStaff_tbtn.setBackgroundResource(R.drawable.off);
						}
				
			}
		});

		RWH_tbtn.setText(null);
		RWH_tbtn.setTextOn(null);
		RWH_tbtn.setTextOff(null);
		RWH_tbtn.setBackgroundResource(R.drawable.toggle_on_of);
		RWH_tbtn.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
						if(isChecked){
							RWH_tbtn.setBackgroundResource(R.drawable.on);
						}else{
							
							RWH_tbtn.setBackgroundResource(R.drawable.off);
						}
				
			}
		});
		
		STP_tbtn.setText(null);
		STP_tbtn.setTextOn(null);
		STP_tbtn.setTextOff(null);
		STP_tbtn.setBackgroundResource(R.drawable.toggle_on_of);
		STP_tbtn.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
						if(isChecked){
							STP_tbtn.setBackgroundResource(R.drawable.on);
						}else{
							
							STP_tbtn.setBackgroundResource(R.drawable.off);
						}
				
			}
		});



	}

	

	

	public void onBack(View v){
		
		finish();
	}
	
	public void onPost(View v){
		new BackgroundTask().execute();
	}
	
  public void onLocationDetails(View v){
		
		startActivity(new Intent(AddPropertyDetailsActivity.this,MapDetailsActivity.class));
		finish();
	}
	public void onBackPage1(View v){
		
		mLayout_1.setVisibility(View.GONE);
		mLayout_PropertyAddress.setVisibility(View.VISIBLE);
	}
	
	public void onNextPropertyAddress(View v){
		//TODO Next to propertyAddress
		//if(FlatHouseNumber_edt.getText().length()>0&& BuildingSocietyName_edt.getText().length()>0){
		  mLayout_1.setVisibility(View.VISIBLE);
		  mLayout_PropertyAddress.setVisibility(View.GONE);
		//}else{
		  
		//	new AlertDialogs(this, "",getResources().getString(R.string.txt_DialogValidation));
		//}
	}
	public void onNextPage1(View v){
		
		mLayout_1.setVisibility(View.GONE);
		mLayout_2.setVisibility(View.VISIBLE);
		
	}
	
	public void onBackPage2(View v){
		
		mLayout_2.setVisibility(View.GONE);
		mLayout_1.setVisibility(View.VISIBLE);
	}
	
	public void onNextPage2(View v){
		mLayout_2.setVisibility(View.GONE);
		mLayout_3.setVisibility(View.VISIBLE);
		
	}
	
	
	public void onBackPage3(View v){
		
		mLayout_3.setVisibility(View.GONE);
		mLayout_2.setVisibility(View.VISIBLE);
	}
	
	public void onNextPage3(View v){
		
		mLayout_3.setVisibility(View.GONE);
		mLayout_4.setVisibility(View.VISIBLE);
	}
	
	public void onBackPage4(View v){
		
		mLayout_4.setVisibility(View.GONE);
		mLayout_3.setVisibility(View.VISIBLE);
	}
	
	public void onNextPage4(View v){
		
		
	}
	
	public void onPostDetails(View v){
		
	}
	
	public void onPicture(View v){
		if(Constants.PROPERTYID.length()>0)
		 startActivity(new Intent(AddPropertyDetailsActivity.this,UploadImageActivity.class));
		else
		 new AlertDialogs(AddPropertyDetailsActivity.this,"" ,getResources().getString(R.string.alert_postdetails));
					
	}
	
	private class BackgroundTaskStateTypeId extends AsyncTask<String,String,String>{

		ProgressDialog mDialog;
		String mMessage;
		
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			mDialog=ProgressDialog.show(AddPropertyDetailsActivity.this, "", getResources().getString(R.string.msg_dialog), true);
			State_ArrayList=new ArrayList<String>();
			State_IDArrayList=new ArrayList<String>();
			TypeID_ArrayList=new ArrayList<String>();
			TypeID_IDArrayList=new ArrayList<String>();
		}
		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub			
			try{
				
			    String result=new HttpRequest().HttpRequestGet(getResources().getString(R.string.url_getstate));
			    JSONArray jArray=new JSONArray(result);
			    JSONObject jsonObject;
			   for(int i=0;i<jArray.length();i++){
				   jsonObject =jArray.getJSONObject(i);
				   /*State_ArrayList.add(jsonObject.getString("Description"));
				   State_IDArrayList.add(jsonObject.getString("TypeID"));*/
				   State_ArrayList.add(jsonObject.getString("State"));
				   State_IDArrayList.add(jsonObject.getString("ID"));
				  }
			   
			     result=new HttpRequest().HttpRequestGet(getResources().getString(R.string.url_gettypeid));
			     jArray=new JSONArray(result);
			     for(int i=0;i<jArray.length();i++){
				   jsonObject =jArray.getJSONObject(i);
				   TypeID_ArrayList.add(jsonObject.getString("Description"));
				   TypeID_IDArrayList.add(jsonObject.getString("TypeID"));
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
			
			ArrayAdapter<String> mAdapterArray=new ArrayAdapter<String>(AddPropertyDetailsActivity.this,android.R.layout.simple_spinner_item,State_ArrayList);
			mAdapterArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spinner_States.setAdapter(mAdapterArray);
			
			mAdapterArray=new ArrayAdapter<String>(AddPropertyDetailsActivity.this,android.R.layout.simple_spinner_item,TypeID_ArrayList);
			mAdapterArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		    spinner_Type.setAdapter(mAdapterArray);
		}
	}

	
	
	private class BackgroundTask extends AsyncTask<String,String,String>{

		ProgressDialog mDialog;
		
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			mDialog=ProgressDialog.show(AddPropertyDetailsActivity.this, "", getResources().getString(R.string.msg_dialog), true);
		}
		@SuppressWarnings({ "rawtypes", "unchecked" })
		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub	
			String result = null;
			try{
				Map<String, Object> nameValuePairs = new  LinkedHashMap<String, Object>();
				if(isEditProperty)
					nameValuePairs.put("PropertyID",Constants.PROPERTYID);
				else
				nameValuePairs.put("PropertyID",0);
				
				nameValuePairs.put("UserID",Integer.parseInt(Constants.USERID));
			    nameValuePairs.put("Description",PropertyDescrption_edt.getText().toString());
			    nameValuePairs.put("AvailableFor",spinner_Availableon.getSelectedItem());
			    nameValuePairs.put("TypeID",Integer.parseInt(TypeID_IDArrayList.get(spinner_Type.getSelectedItemPosition())));
			    nameValuePairs.put("Beds",Float.parseFloat(""+spinner_beds.getSelectedItem().toString()));
			    nameValuePairs.put("Baths",Integer.parseInt(""+spinner_baths.getSelectedItem()));
			    nameValuePairs.put("Balconies",Integer.parseInt(""+spinner_Balconies.getSelectedItem()));
			    nameValuePairs.put("BuiltupArea",Float.parseFloat(""+BuiltupArea_edt.getText().toString()));
			    nameValuePairs.put("CarpetArea",Float.parseFloat(""+CarpetArea_edt.getText().toString()));
			    nameValuePairs.put("FloorNo",Integer.parseInt(""+spinner_FloorNo.getSelectedItem()));
			    nameValuePairs.put("TotalFloors",Integer.parseInt(""+spinner_TotalFloors.getSelectedItem()));
			    nameValuePairs.put("TotalBuildings",Integer.parseInt(""+spinner_TotalBuildings.getSelectedItem()));
			    nameValuePairs.put("LeaseTerm",Integer.parseInt(""+LeaseTerm_edt.getText().toString()));
			    nameValuePairs.put("LeaseDeposit",Float.parseFloat(""+LeaseDeposite_edt.getText().toString()));
			    nameValuePairs.put("Rent",Float.parseFloat(""+Rent_edt.getText().toString()));
			   
			    nameValuePairs.put("Maintenance",Float.parseFloat("0.0"));
			    nameValuePairs.put("Utilities",Float.parseFloat("0.0"));
			    
			   /* nameValuePairs.put("Maintenance",Integer.parseInt(""+Maintance_edt.getText().toString()));
			    nameValuePairs.put("Utilities",Integer.parseInt(""+Utilities_edt.getText().toString()));
			    */nameValuePairs.put("AgeOfBuilding",Integer.parseInt(""+spinner_AgeOfBuilding.getSelectedItem()));
			    nameValuePairs.put("NoOfCarParkings",Float.parseFloat(""+spinner_NoOfCarParkings.getSelectedItem()));
			    nameValuePairs.put("NoOfLifts",Float.parseFloat(""+spinner_NoOfLifts.getSelectedItem()));
			    nameValuePairs.put("AvailableOn",""+calender_edt.getText().toString());
			    nameValuePairs.put("Cost",Float.parseFloat(Rent_edt.getText().toString()));
			    nameValuePairs.put("UnderConstruction",null);
			    nameValuePairs.put("PossessionDate",null);
			    nameValuePairs.put("ReadyPossesion",null);
			    nameValuePairs.put("PGAccomodationFor",null);
			    Map<String, Object> nameValuePairs1 = new  LinkedHashMap<String, Object>();
			    if(isEditProperty)
					nameValuePairs1.put("PropertyID",Constants.PROPERTYID);
				else
				nameValuePairs1.put("PropertyID",0);
				
			    nameValuePairs1.put("Lift",Lift_tbtn.isChecked());
			    nameValuePairs1.put("ServiceLift",ServiceLift_tbtn.isChecked());
			    nameValuePairs1.put("InternetWifi",InternetWifi_tbtn.isChecked());
			    nameValuePairs1.put("Gym",Gym_tbtn.isChecked());
			    nameValuePairs1.put("Clubhouse",Clubhouse_tbtn.isChecked());
			    nameValuePairs1.put("SwimmingPool",SwimmingPool_tbtn.isChecked());
			    nameValuePairs1.put("GarbageChute",GarbageChute_tbtn.isChecked());
			    nameValuePairs1.put("Garden",Garden_tbtn.isChecked());
			    nameValuePairs1.put("Playarea",Playarea_tbtn.isChecked());
			    nameValuePairs1.put("PowerBackup",PowerBackup_tbtn.isChecked());
			    nameValuePairs1.put("Security",Security_tbtn.isChecked());
			    nameValuePairs1.put("ReservedParking",ReservedParking_tbtn.isChecked());
			    nameValuePairs1.put("VisitorParking",VisitorParking_tbtn.isChecked());
			    nameValuePairs1.put("MaintenanceStaff",MaintenanceStaff_tbtn.isChecked());
			    nameValuePairs1.put("RWH",RWH_tbtn.isChecked());
			    nameValuePairs1.put("STP",STP_tbtn.isChecked());
			    
			    Map<String, Object> nameValuePairs2 = new LinkedHashMap<String, Object>();
			    if(isEditProperty)
					nameValuePairs2.put("PropertyID",Constants.PROPERTYID);
				else
				nameValuePairs2.put("PropertyID",0);
				
				nameValuePairs2.put("Intercom",Intercom_tbtn.isChecked());
			    nameValuePairs2.put("VideoDoorphone",VideoDoorphone_tbtn.isChecked());
			    nameValuePairs2.put("PipedGas",PipedGas_tbtn.isChecked());
			    nameValuePairs2.put("GasLeakDetector",GasLeakDetector_tbtn.isChecked());
			    nameValuePairs2.put("PanicButton",PanicButton_tbtn.isChecked());
			    nameValuePairs2.put("Inverter",Inverte_tbtn.isChecked());
			    nameValuePairs2.put("WaterStorage",WaterStorage_tbtn.isChecked());
			    nameValuePairs2.put("Pets",Pets_tbtn.isChecked());
			    nameValuePairs2.put("Furnished",Furnished_tbtn.isChecked());
			    Map<String, Object> nameValuePairs3 = new LinkedHashMap<String, Object>();
			    if(isEditProperty)
					nameValuePairs3.put("PropertyID",Constants.PROPERTYID);
				else
				nameValuePairs3.put("PropertyID",0);
				
			    nameValuePairs3.put("Lat",Float.parseFloat(Constants.USERHOUSELATI));
			    nameValuePairs3.put("Long",Float.parseFloat(Constants.USERHOUSELONGI));
			    nameValuePairs3.put("FlatHouseNumber",FlatHouseNumber_edt.getText().toString());
			    nameValuePairs3.put("BuildingSocietyName",BuildingSocietyName_edt.getText().toString());
			    nameValuePairs3.put("StreetRoadName",StreetRoadName_edt.getText().toString());
			    nameValuePairs3.put("AreaLocality",AreaLocality_edt.getText().toString());
			    nameValuePairs3.put("TownCityDistrict",TownCity_edt.getText().toString());
			    nameValuePairs3.put("State",State_IDArrayList.get(spinner_States.getSelectedItemPosition()));
			    nameValuePairs3.put("Pincode",Pincode_edt.getText().toString());
			    nameValuePairs3.put("Landmark",Landmark_edt.getText().toString());
			    Map<String, Object> nameValuePairs4 = new LinkedHashMap<String, Object>();
			    if(isEditProperty)
					nameValuePairs4.put("PropertyID",Constants.PROPERTYID);
				else
				nameValuePairs4.put("PropertyID",0);
				
				nameValuePairs4.put("TypeID",TypeID_IDArrayList.get(spinner_Type.getSelectedItemPosition()));
			    nameValuePairs4.put("Description","Apartment");
			    
			   
			    nameValuePairs.put("ExternalAmenity", nameValuePairs1);
			    nameValuePairs.put("InternalAmenity",nameValuePairs2);
			    nameValuePairs.put("Location",nameValuePairs3);
			    nameValuePairs.put("Pictures",null);
			    nameValuePairs.put("PropertyType",nameValuePairs4);

			    String mConvert=JSONValue.toJSONString(nameValuePairs);
			   
			    if(isEditProperty)
			      result=new HttpRequest().HttpRequestPut(getResources().getString(R.string.url)+"Homes/"+Constants.PROPERTYID,mConvert);
			    else  
			     result=new HttpRequest().HttpRequestPost(getResources().getString(R.string.url)+"Homes/PostProperty",mConvert);
			   
			    Log.e("Request",""+mConvert);
			    Log.e("Result",""+result);
			    JSONObject jArray=new JSONObject(result);
			    /*
			    Log.e("Result",""+jArray.getString("Email"));
			    Log.e("Result",""+jArray.getString("MobileNumber"));
			    Log.e("Result",""+jArray.getBoolean("FirstName"));*/
			    Constants.PROPERTYID=jArray.getString("PropertyID");
			    
				}catch(Exception e){
					e.printStackTrace();
					Log.e("Result",""+e);
				}
			///Log.e("Property id@@@@",""+ Constants.PROPERTYID);
			return result;
		}
		
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			
			mDialog.dismiss();
			 if(Constants.PROPERTYID.length()>0)
				 new AlertDialogs(AddPropertyDetailsActivity.this,"" ,getResources().getString(R.string.alert_uploaddetailsucessfull));
			 else
				 new AlertDialogs(AddPropertyDetailsActivity.this,"" ,getResources().getString(R.string.alert_internetconnectionslow));
			
		}
	}

	/*public String DecimalFormat(String de){
		String result = null;
try{
		NumberFormat df = DecimalFormat.getInstance();
		df.setMinimumFractionDigits(1);
		df.setMaximumFractionDigits(1);
		df.setRoundingMode(RoundingMode.DOWN);
		result = df.format(de);
		Log.e("FractionREsult",""+result);
}catch (Exception e) {
	// TODO: handle exception
	e.printStackTrace();
}
		return result;
	}
*/
}
