package com.android.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.android.ZerobrokerageHomes.R;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class LazyAdapter extends BaseAdapter {
    
    private Activity activity;
    private static LayoutInflater inflater=null;
    private int mChecker;
    @SuppressWarnings("unused")
	private int[] colors = new int[] {60012,0x30FFFFFF};
    private ArrayList<ArrayList<String>>mAddDetailsList=new ArrayList<ArrayList<String>>();
    private ArrayList<Map<String,String>> mSearchDetailsList=new ArrayList<Map<String,String>>();
    private ArrayList<Bitmap>mImageBitMap=new ArrayList<Bitmap>();	
    public LazyAdapter(Activity a,ArrayList<ArrayList<String>> mDetails,ArrayList<Bitmap> mImage,int ch) {
		// TODO Auto-generated constructor stub
       	 activity = a;
    	mAddDetailsList=mDetails;
    	mImageBitMap=mImage;
    	mChecker=ch;
	}

    public LazyAdapter(Activity a,ArrayList<Map<String,String>> mDetails,ArrayList<Bitmap> mImage,int ch,boolean b) {
		// TODO Auto-generated constructor stub
       	 activity = a;
    	mSearchDetailsList=mDetails;
    	mImageBitMap=mImage;
    	mChecker=ch;
	}
	public int getCount() {
		if(mChecker==0)
        return mAddDetailsList.size();
		else 
		return mSearchDetailsList.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }
    
    public static class ViewHolder{
        public TextView text1,text2,text_time,text4;
        public ImageView image,icon;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        ViewHolder holder;
        if(convertView==null){
           inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        	vi = inflater.inflate(R.layout.custome_listview_screen, null);

            holder=new ViewHolder();
            holder.text1 = (TextView)vi.findViewById(R.id.layout_custome_TextView);
			holder.text2 = (TextView)vi.findViewById(R.id.layout_custome_TextView_Day);	
			holder.icon = (ImageView)vi.findViewById(R.id.layout_custome_ImageView);
            vi.setTag(holder);
        }
        else
            holder=(ViewHolder)vi.getTag();
        	if(mChecker==0)
        	 holder.text1.setText(mAddDetailsList.get(position).get(0)+"\n"+mAddDetailsList.get(position).get(1)+"\n"+mAddDetailsList.get(position).get(2));
        	else if(mChecker==1){
        		holder.text1.setText(mSearchDetailsList.get(position).get("Beds")+"BHK  ("+mSearchDetailsList.get(position).get("Distance")+" Kms)"+"\n"+"Rent :"+mSearchDetailsList.get(position).get("Rent")+" k/month"+"\n"+"Available on :"+mSearchDetailsList.get(position).get("AvailableOn"));
        	}else if(mChecker==2){
        		holder.text1.setText("AvailableFor :"+mSearchDetailsList.get(position).get("AvailableFor")+"\n"+"Rent :"+mSearchDetailsList.get(position).get("Rent")+" k/month"+"\n"+"HomeType :"+mSearchDetailsList.get(position).get("HomeType")+"\n"+"Available on :"+mSearchDetailsList.get(position).get("AvailableOn"));
        	}
        	holder.text2.setText("day ago");
        	holder.icon.setImageBitmap(mImageBitMap.get(position));
        	return vi;
        }
	}