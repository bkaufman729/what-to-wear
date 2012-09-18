package edu.umich.whattowear;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.Dialog;
import android.app.FragmentTransaction;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.app.ActionBar;


public class MainActivity extends FragmentActivity implements ActionBar.TabListener {
	public final static String TAG = "WhatToWear";
	private final static String API_KEY = "352f5fa78aee6c34";
	//Splash stuff
	protected Dialog mSplashDialog;
	private SectionsPagerAdapter mSectionsPagerAdapter;
	private ViewPager mViewPager;
	private static LocationManager locationManager;
	private static InputMethodManager imm;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        ////////////
        MyStateSaver data = (MyStateSaver) getLastCustomNonConfigurationInstance();
        if (data != null) {
            // Show splash screen if still loading
            if (data.showSplashScreen) {
                showSplashScreen();
            }
            initUI();        
     
            // Rebuild your UI with your saved state here
        } else {
            showSplashScreen();
            initUI();
            // Do your heavy loading here on a background thread
        }
    
        ////////////
        
        
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        imm = (InputMethodManager) MainActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
        
        
    }
    
    //helper function to initialize the UI of the three tabs
    private void initUI() {
    	mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        
        final ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
        	@Override
        	public void onPageSelected(int position) {
        		actionBar.setSelectedNavigationItem(position);
        	}
        });
        
        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
        	actionBar.addTab(actionBar.newTab().setText(mSectionsPagerAdapter.getPageTitle(i)).setTabListener(this));
        }
    }

    public Object onRetainCustomNonConfigurationInstance() {
        MyStateSaver data = new MyStateSaver();
        // Save your important data here
     
        if (mSplashDialog != null) {
            data.showSplashScreen = true;
            removeSplashScreen();
        }
        return data;
    }
    
    protected void removeSplashScreen() {
        if (mSplashDialog != null) {
            mSplashDialog.dismiss();
            mSplashDialog = null;
        }
    }
    
    protected void showSplashScreen() {
        mSplashDialog = new Dialog(this, R.style.SplashScreen);
        mSplashDialog.setContentView(R.layout.splash);
        mSplashDialog.setCancelable(false);
        mSplashDialog.show();
         
        // Set Runnable to remove splash screen just in case
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
         // @Override
          public void run() {
            removeSplashScreen();
          }
        }, 5000);   //Edit this number for Splash length
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
    		case R.id.menu_refresh:
    			Log.d(TAG, String.valueOf(getActionBar().getSelectedNavigationIndex()));
    			return true;
    		default:
    			return super.onOptionsItemSelected(item);
    	}
    }
    
    private class MyStateSaver {
        public boolean showSplashScreen = false;
        // Your other important fields here
    }
    

	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		mViewPager.setCurrentItem(tab.getPosition());
	}

	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}
	
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
			// TODO Auto-generated constructor stub
		}

		@Override
		public Fragment getItem(int i) {
			// TODO Auto-generated method stub
			Fragment fragment = new RecommendationFragment();
			Bundle args = new Bundle();
			args.putInt(RecommendationFragment.FRAGMENT_TYPE, i);
			fragment.setArguments(args);
			return fragment;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 3;
		}
		
		@Override
		public CharSequence getPageTitle(int position) {
			switch (position) {
				case 0: return "Current";
				case 1: return "3 Hours Later";
				case 2: return "Tomorrow";
			}
			return null;
		}
		
	}
	
	public static class RecommendationFragment extends Fragment {
		public static final String FRAGMENT_TYPE = "TYPE";
		private Button submitButton;
		private EditText postalInput;
		private TextView cityText;
		private TextView tempText;
		private TextView clothesText;
		private TextView conditionText;
		private ImageView clothesImage;
		private double latval;
		private double lonval;
		Location location;
		String provider;
		private int hoursLater;
		
		public RecommendationFragment() {
	        
	        //Get location through either GPS or Network
	        Criteria criteria = new Criteria();
	        provider = locationManager.getBestProvider(criteria, false);
	        location = locationManager.getLastKnownLocation(provider);

	        latval = location.getLatitude();
	        lonval = location.getLongitude();
	        
		}
		
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.recommendation, container, false);
			submitButton = (Button) rootView.findViewById(R.id.submit_button);
	        postalInput = (EditText) rootView.findViewById(R.id.postal_input);
	        cityText = (TextView) rootView.findViewById(R.id.city_text);
	        tempText = (TextView) rootView.findViewById(R.id.temp_text);
	        clothesText = (TextView) rootView.findViewById(R.id.clothes_text);
	        conditionText = (TextView) rootView.findViewById(R.id.condition_text);
	        clothesImage = (ImageView) rootView.findViewById(R.id.clothes_image);
	        Bundle args = getArguments();
	        
	        submitButton.setOnClickListener(new View.OnClickListener() {
	    		
				public void onClick(View v) {
					// TODO Auto-generated method stub
					String postal = postalInput.getText().toString().trim();
					getRecommendation(postal);
				}
			});
	        
	        //set the hoursLater for each tab
	        switch (args.getInt((FRAGMENT_TYPE))) {
		        case 0: hoursLater = 0; break;
		        case 1: hoursLater = 3; break;
		        case 2: hoursLater = 24; break;
	        }
	        refreshRecommendation();
	        return rootView;
	        
		}
		
		public void refreshRecommendation() {
			cityText.setText("");
			tempText.setText("");
			clothesText.setText("");
			getRecommendation(String.valueOf(latval) + "," + String.valueOf(lonval));
		}
		
		public void getRecommendation(String query) {
			String url = "http://api.wunderground.com/api/" + API_KEY + "/hourly/q/" + query + ".json";
			new readWeatherTask().execute(url);
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
					char[] buffer = new char[100000];
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
				//get the city name from latlong or zip code
				String locationResponse = getResponse(urls[0].replace("hourly", "geolookup"));
				WeatherReport report = null;
				try {
					JSONObject locationJSON = new JSONObject(locationResponse);
					String response = getResponse(urls[0]);
					report = new WeatherReport(response, locationJSON.getJSONObject("location").getString("city"));
				} catch (JSONException e) {
					e.printStackTrace();
				}
				return report;
			}
	    	
			@Override
			protected void onPostExecute(WeatherReport report) {
				
				imm.hideSoftInputFromWindow(postalInput.getWindowToken(), 0);
				//Makes the keyboard go away after submit is pressed
				
				Recommender recommender = new Recommender(report);
				cityText.setText("Your city is " + report.getCity());
				String tempString = report.getTempString(hoursLater);
				tempText.setText(tempString);
				//tempText.setText(tempString + "\nlat is: " + latval + "\nlon is: " + lonval);
				conditionText.setText("Condition: ");
				conditionText.append(report.getCondition(hoursLater));
				
				int result = recommender.getClothesToWear(hoursLater);
				clothesText.setText("Your should wear ");
				Log.d(TAG, String.valueOf(result));
				if (result == 1) {
					clothesText.append("a T-shirt and shorts");
					clothesImage.setImageResource(R.drawable.tshirtshorts);
				} else if (result == 2) {
					clothesText.append("long sleeves and pants");
					clothesImage.setImageResource(R.drawable.longsleevepants);
				} else if (result == 3) {
					clothesText.append("a sweatshirt and pants");
					clothesImage.setImageResource(R.drawable.sweatpants);
	 			} else if (result == 4) {
	 				clothesText.append("a jacket and pants");
	 				clothesImage.setImageResource(R.drawable.jacketpantsshoes);
	 			} else if (result ==5) {
	 				clothesText.append("a jacket, pants, and a winter hat");
	 				clothesImage.setImageResource(R.drawable.jacketpantshatshoes);
	 			} else if (result == 6) {
					clothesText.append("a T-shirt and shorts with an umbrealla");
					clothesImage.setImageResource(R.drawable.raintshirtshorts);
				} else if (result == 7) {
					clothesText.append("long sleeves and pants with an umbrella");
					clothesImage.setImageResource(R.drawable.rainlongsleevepants);
	 			} else if (result == 8) {
	 				clothesText.append("a jacket and pants with an umbrella");
	 				clothesImage.setImageResource(R.drawable.rainjacketpantsshoes);
	 			} else if (result ==9) {
	 				clothesText.append("a jacket, pants, a winter hat, and boots");
	 				clothesImage.setImageResource(R.drawable.jacketpantshatboots);
	 			}
				
			}
	    }
	}

    
}
