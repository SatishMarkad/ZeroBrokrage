package com.android.zero.utils;

import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class BitmapConverter 
{
	// convert images from Assets folder to Bitmaps 
	public static Bitmap getBitmapFromAssets(AssetManager assetManager, String image_path, final int reqWidth, final int reqHeight)
	{
		InputStream inputStream = null;
		Bitmap bitmap = null;
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inScaled = true;
		options.inJustDecodeBounds = true;
		
		try
		{
			if(!image_path.equals(""))
				inputStream = assetManager.open(image_path);
			else
				inputStream = assetManager.open("img_not_available.jpeg");
		}
		catch(OutOfMemoryError eOfMemoryError) 
		{
			eOfMemoryError.printStackTrace();
		}
		catch(Exception exception) 
		{
			exception.printStackTrace();
			bitmap = BitmapConverter.getBitmapForCameraImages(image_path, 177, 128);
			return bitmap;
		}
		// First decode with inJustDecodeBounds=true to check dimensions
		bitmap = BitmapFactory.decodeStream(inputStream, null, options);
		
		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
		
		options.inJustDecodeBounds = false;
		
		// Decode bitmap with inSampleSize set
		bitmap = BitmapFactory.decodeStream(inputStream, null, options);
		try 
		{
			if(inputStream != null)
				inputStream.close();
		} 
		catch(IOException eIoException) 
		{
			eIoException.printStackTrace();
		}
		
		return bitmap;
	}
	
	// convert camera images stored in sd card to Bitmaps 
	public static Bitmap getBitmapForCameraImages(String image_path, final int reqWidth, final int reqHeight)
	{
		Bitmap bitmap = null;
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inScaled = true;
		options.inJustDecodeBounds = true;
	
		// First decode with inJustDecodeBounds=true to check dimensions
		bitmap = BitmapFactory.decodeFile(image_path, options);
		
		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
		
		options.inJustDecodeBounds = false;
		
		// Decode bitmap with inSampleSize set
		bitmap = BitmapFactory.decodeFile(image_path, options);
		
		return bitmap;
	}
	
	// calculate inSampleSize for each image in order to reduce memory consumption
	public static int calculateInSampleSize(BitmapFactory.Options options, final int reqWidth, final int reqHeight) 
	{
	    // Raw height and width of image
	    final int height = options.outHeight;
	    final int width = options.outWidth;
	    int inSampleSize = 1;
	    
	    if(height > reqHeight || width > reqWidth) 
	    {
	        if(width > height) 
	        {
	            inSampleSize = Math.round((float)height / (float)reqHeight);
	        } 
	        else 
	        {
	            inSampleSize = Math.round((float)width / (float)reqWidth);
	        }
	    }
	    return inSampleSize;
	}
	
	// don't down sample the images while showing in full screen as image in full screen view needs to be more clear
	public static Bitmap getBitmapFromAssetsForFullScreenView(Context context, String image_path)
	{
		AssetManager assetManager = context.getAssets();
		InputStream inputStream = null;
		Bitmap bitmap = null;
		try
		{
			inputStream = assetManager.open(image_path);
			bitmap = BitmapFactory.decodeStream(inputStream);
		}
		catch(Exception exception) 
		{
			exception.printStackTrace();
			bitmap = null;
		}
		
		return bitmap;
	}
}
