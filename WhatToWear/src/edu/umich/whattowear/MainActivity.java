package edu.umich.whattowear;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;


public class MainActivity extends Activity
{
	  private static final String DEBUG_TAG = "WeatherApp";
	
	private TextView display;
	private ImageView image;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);;
		  display = (TextView) findViewById(R.id.edit_message);
		// image = (ImageView) findViewById(R.id.imageView);
		//Cant get these to work
		new getWeatherInfo().execute("http://api.wunderground.com/api/14faba995dd5152f/conditions/q/48109.json");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	private class getWeatherInfo extends AsyncTask {


		protected String doInBackground(String myurl) throws IOException {
			// TODO Auto-generated method stub
			InputStream is = null;
			int len = 50000;
			String contentAsString = null;
			try {
				URL url = new URL(myurl);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setReadTimeout(10000 /* milliseconds */);
		        conn.setConnectTimeout(15000 /* milliseconds */);
				conn.setRequestMethod("GET");
				conn.setDoInput(true);
				conn.connect();
				int response = conn.getResponseCode();
				Log.d(DEBUG_TAG, "The response is " + response);
				is = conn.getInputStream();
				
				Reader reader=null;
				reader = new InputStreamReader(is, "UTF-8");
				char[] buffer = new char[len];
				reader.read(buffer);
				contentAsString="";
			} catch (Exception e) {
				System.out.println("Error");
			} finally {
				if (is != null) {
						is.close();
				}
			}
			return contentAsString;
		}

		@Override
		protected Object doInBackground(Object... params) {
			// TODO Auto-generated method stub
			return null;
		}

	}

}
