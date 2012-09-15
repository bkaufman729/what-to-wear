package edu.umich.whattowear;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v4.app.NavUtils;



public class MainActivity extends Activity {
	private Button submitButton;
	private EditText postalInput;
	private TextView cityText;
	private TextView tempText;
	private TextView clothesText;
	private ImageView clothesImage;
	private double latval;
	private double lonval;
	private Button geoButton;
	
	LocationManager locationManager;
	Location location;
	String provider;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        //Get location through either GPS or Network
        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, false);
        location = locationManager.getLastKnownLocation(provider);

        latval = location.getLatitude();
        lonval = location.getLongitude();
        
        submitButton = (Button) findViewById(R.id.submit_button);
        postalInput = (EditText) findViewById(R.id.postal_input);
        cityText = (TextView) findViewById(R.id.city_text);
        tempText = (TextView) findViewById(R.id.temp_text);
        clothesText = (TextView) findViewById(R.id.clothes_text);
        clothesImage = (ImageView) findViewById(R.id.clothes_image);
        geoButton = (Button) findViewById(R.id.geo_button);
        
        submitButton.setOnClickListener(new View.OnClickListener() {
		
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String postal = postalInput.getText().toString();
				String url = "http://api.wunderground.com/api/352f5fa78aee6c34/conditions/q/" + postal + ".json";
				new readWeatherTask().execute(url);
			}
		});
        
        geoButton.setOnClickListener(new View.OnClickListener() {
    		
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String postal = postalInput.getText().toString();
				String url = "http://api.wunderground.com/api/352f5fa78aee6c34/conditions/q/" + latval + "," + lonval + ".json";
				new readWeatherTask().execute(url);
			}
		});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    private class readWeatherTask extends AsyncTask<String, Integer, WeatherReport> {
    	
    	private String getResponse(String urlString) {
    		String response = "";
    		try {
				URL url = new URL(urlString);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setReadTimeout(10000);
				conn.setConnectTimeout(15000);
				conn.setRequestMethod("GET");
				conn.setDoInput(true);
				conn.connect();
				InputStream is = conn.getInputStream();
				Reader reader = new InputStreamReader(is, "UTF-8");
				char[] buffer = new char[10000];
				reader.read(buffer);
				response = new String(buffer);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		return response;
    	}
		@Override
		protected WeatherReport doInBackground(String... urls) {
			// TODO Auto-generated method stub

			String response = getResponse(urls[0]);
			WeatherReport report = new WeatherReport(response);
			return report;
		}
    	
		@Override
		protected void onPostExecute(WeatherReport report) {
			Recommender recommender = new Recommender(report);
			cityText.setText("Your city is " + report.getCity());
			String tempString = "Current temperature: " + report.getTempString() + "\nFeels like: " + report.getFeelslikeString();
			//tempText.setText(tempString);
			tempText.setText(tempString + "\nlat is: " + latval + "\nlon is: " + lonval);
			
			int result = recommender.getClothesToWear();
			clothesText.setText("Your should wear ");
			if (result == 1) {
				clothesText.append("T-shirt");
				clothesImage.setImageResource(R.drawable.tshirt);
			} else if (result == 2) {
				clothesText.append("long sleeves");
				clothesImage.setImageResource(R.drawable.long_sleeve);
			} else if (result == 3) {
				clothesText.append("jacket");
				clothesImage.setImageResource(R.drawable.jacket);
 			} else if (result == 4) {
 				clothesText.append("winter jacket");
 				clothesImage.setImageResource(R.drawable.winter_jacket);
 			}
			
		}
    }

    
}
