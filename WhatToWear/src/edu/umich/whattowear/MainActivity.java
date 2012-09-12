package edu.umich.whattowear;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

//Test
//Vincent's branch

public class MainActivity extends Activity {
	
	private TextView display;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);;
        display = (TextView) findViewById(R.id.text);
        new readWeatherTask().execute("http://api.wunderground.com/api/352f5fa78aee6c34/conditions/q/CA/San_Francisco.json");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    private class readWeatherTask extends AsyncTask<String, Integer, String> {

		@Override
		protected String doInBackground(String... urls) {
			// TODO Auto-generated method stub
			InputStream is = null;
			String result = null;
			try {
				URL url = new URL((String) urls[0]);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setReadTimeout(10000);
				conn.setConnectTimeout(15000);
				conn.setRequestMethod("GET");
				conn.setDoInput(true);
				conn.connect();
				int response = conn.getResponseCode();
				Log.d("WhatToWear", "The response is " + response);
				is = conn.getInputStream();
				Reader reader = new InputStreamReader(is, "UTF-8");
				char[] buffer = new char[10000];
				reader.read(buffer);
				WeatherReport report = new WeatherReport(new String(buffer));
				result = String.valueOf(report.getFeelLikeCelcius());
				Log.d("WhatToWear", result);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if (is != null) {
					try {
						is.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			return result;
		}
    	
		@Override
		protected void onPostExecute(String result) {
			display.setText(result);
		}
    }
    
}
