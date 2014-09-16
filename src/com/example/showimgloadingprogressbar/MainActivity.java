package com.example.showimgloadingprogressbar;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;

public class MainActivity extends ActionBarActivity 
{
	private WebView loadingImg;
	private ImageView img;
	private Bitmap bitmap;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		loadingImg=(WebView)findViewById(R.id.imageView1);
		img=(ImageView)findViewById(R.id.imageView2);
		
		loadingImg.setVisibility(View.VISIBLE);
		img.setVisibility(View.GONE);
		
		ImageLoading imgLoading=new ImageLoading();
		imgLoading.execute();
	}

	private class ImageLoading extends AsyncTask<Void, Void, Boolean>
	{
		@Override
		protected void onPreExecute() 
		{
			super.onPreExecute();
			loadingImg.getSettings().setJavaScriptEnabled(true);
			loadingImg.loadUrl("http://10.0.2.2/imgloading/showLoading.php");
			Log.i("img","pre");
		}

		@Override
		protected Boolean doInBackground(Void... params) 
		{
			Log.i("img","background");
			try
			{
				
				HttpClient client=new DefaultHttpClient();
				HttpGet request=new HttpGet("http://10.0.2.2/imgloading/getImage.php?id=2");
				HttpResponse response=client.execute(request);
				Log.i("status code", ""+response.getStatusLine().getStatusCode());
				HttpEntity responseEntity=response.getEntity();
				final byte[] imgByteArray=EntityUtils.toByteArray(responseEntity);
				bitmap=BitmapFactory.decodeByteArray(imgByteArray, 0, imgByteArray.length);
				
				responseEntity.consumeContent();
				Log.i("img","inside try");

				return true;
			}
			catch(Exception e)
			{
				e.printStackTrace();
				Log.i("img","inside catch");
				Log.i("img", e.toString());
				return false;
			}
		}
		
		@Override
		protected void onPostExecute(Boolean result) 
		{
			super.onPostExecute(result);

			Log.i("img","post");
			if(result==true)
			{
				img.setVisibility(View.GONE);img.setVisibility(View.VISIBLE);
				Log.i("img","result true");
				img.setImageBitmap(bitmap);
			}
			else
			{
				img.setVisibility(View.GONE);img.setVisibility(View.GONE);
				Log.i("img","result false");
			}
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
