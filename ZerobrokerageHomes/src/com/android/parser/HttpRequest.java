package com.android.parser;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.Log;

import com.android.ZerobrokerageHomes.R;


public class HttpRequest{
	
	 InputStream mInput = null;	
  public String HttpRequestGet(String mUrl1){
	    
	  String result = "";
	    try{
	    
	    	DefaultHttpClient httpClient = new DefaultHttpClient();;
            HttpGet httpGet = new HttpGet(mUrl1);
            HttpResponse httpResponse = httpClient.execute(httpGet);
            HttpEntity httpEntity = httpResponse.getEntity();
             mInput = httpEntity.getContent();
			 result =BufferReaderMethod(mInput);
	    }catch(Exception e){
	    	
	    	e.printStackTrace();
	    }
	    
	    return result;
  }
  
  public int HttpRequestDeleteStatusCode(String mUrl1){
	    
	  int result = 0 ;
	    try{Log.e("URL",""+mUrl1);
	    	
	    	HttpClient httpclient = new DefaultHttpClient();
		    HttpDelete mHttpDelete = new HttpDelete(mUrl1);
		    HttpResponse response = httpclient.execute(mHttpDelete);
            result=response.getStatusLine().getStatusCode();
	    }catch(Exception e){
	    	
	    	e.printStackTrace();
	    }
	    
	    return result;
  }
  public String HttpRequestPost(String mUrl,ArrayList<NameValuePair> nameValuePairs){
	   
	   
	    String result = "";
	  try
		{  
		    HttpClient httpclient = new DefaultHttpClient();
		    HttpPost mHttpPost = new HttpPost(mUrl);
	        mHttpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		    HttpResponse response = httpclient.execute(mHttpPost);
		    HttpEntity entity = response.getEntity();
		    mInput = entity.getContent();
            result= BufferReaderMethod(mInput);
	    }catch(Exception e){
	        Log.e("HttpBlock", "Error in http connection "+e.toString());
	    }
  
	    return result;
  }
  
 public String HttpRequestPost(String url,String json){
	 String result = "";
	  try {
          HttpPost post = new HttpPost(url);
          HttpClient httpclient = new DefaultHttpClient();
          StringEntity se = new StringEntity( json.toString());  
          se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
          post.setEntity(se);
          HttpResponse response  = httpclient.execute(post);
          /*Checking response */
          if(response!=null){
               mInput = response.getEntity().getContent(); //Get the data in the entity
               result= BufferReaderMethod(mInput);
          }

      } catch(Exception e) {
          e.printStackTrace();
         
      }
	   return result;
  }
 public int HttpRequestPostStatusCode(String url,String json){
	 int result = 0 ;
	  try {
          HttpPost post = new HttpPost(url);
          HttpClient httpclient = new DefaultHttpClient();
          StringEntity se = new StringEntity( json.toString());  
          se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
          post.setEntity(se);
          HttpResponse response  = httpclient.execute(post);
          result=response.getStatusLine().getStatusCode();

      } catch(Exception e) {
          e.printStackTrace();
         
      }
	   return result;
  }
 
  public String HttpRequestPut(String mUrl,ArrayList<NameValuePair> nameValuePairs){
	   
	   
	    String result = "";
	  try
		{//Log.e("Url", ""+mUrl);
		    HttpClient httpclient = new DefaultHttpClient();
		    HttpPut mHttpPut = new HttpPut(mUrl);
	        mHttpPut.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		    HttpResponse response = httpclient.execute(mHttpPut);
		    result=""+response.getStatusLine();
	    }catch(Exception e){
	        Log.e("HttpBlock", "Error in httpput connection "+e.toString());
	    }

	    return result;
}
  
  public String HttpRequestPut(String mUrl,String json){
	   
	   
	    String result = "";
	  try
		{   Log.e("Url", ""+mUrl);
		    
		    HttpClient httpclient = new DefaultHttpClient();
		    HttpPut mHttpPut = new HttpPut(mUrl);
		    StringEntity se = new StringEntity( json.toString());  
	        se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
	        mHttpPut.setEntity(se); 
	        HttpResponse response = httpclient.execute(mHttpPut);
		    result=""+response.getStatusLine();
	    }catch(Exception e){
	        Log.e("HttpBlock", "Error in httpput connection "+e.toString());
	    }

	    return result;
} 
  public Bitmap ImageGet(Context mContext,String mUrl){
	    
	  String result = "";
	    try{
	    	//Log.e("Url", ""+mUrl1);
	    	
             URL u=new URL(mUrl);
			    InputStream ins = null;
			    BufferedInputStream bis = null;
			    Bitmap bmp = null;
			    URLConnection conn = u.openConnection();
			    conn.connect();
			    ins = conn.getInputStream();
			    bis = new BufferedInputStream(ins);
			    bmp = BitmapFactory.decodeStream(bis);
	            int width = bmp.getWidth();
	            int height = bmp.getHeight();
	            int newWidth=150;
	            int newHeight=100;
	            float scaleWidth = ((float) newWidth) / width;
	            float scaleHeight = ((float) newHeight) / height;      

		        Matrix  matrix = new Matrix();
		        matrix.postScale(scaleWidth, scaleHeight);
		        Bitmap resizedBitmap = Bitmap.createBitmap(bmp, 0, 0,width, height, matrix, true);

               return resizedBitmap;
	    }catch(Exception e){
	    	
	    	Bitmap bmp=BitmapFactory.decodeResource(mContext.getResources(),R.drawable.app_logo);
		    int width = bmp.getWidth();
            int height = bmp.getHeight();
            int newWidth=150;
            int newHeight=100;
            float scaleWidth = ((float) newWidth) / width;
            float scaleHeight = ((float) newHeight) / height;      
	        Matrix  matrix = new Matrix();
	        matrix.postScale(scaleWidth, scaleHeight);
	        Bitmap resizedBitmap = Bitmap.createBitmap(bmp, 0, 0,width, height, matrix, true);
	        return resizedBitmap;
	    }
  }
  
  public String BufferReaderMethod(InputStream is){
	  
	  String result = "";
	  try
	  	 {
		        BufferedReader reader = new BufferedReader(new InputStreamReader(is,"UTF-8"),8);
		        StringBuilder sb = new StringBuilder();
		        String line = null;
	  	        while ((line = reader.readLine()) != null)
	  	        {
	  	            sb.append(line + "\n");
	  	        }
		        result=sb.toString();
		     
	  	    }catch(Exception e){
	  	        Log.e("BufferReader", "Error converting result "+e.toString());
	  	    }
	  
	   return result;
		    
  }


}