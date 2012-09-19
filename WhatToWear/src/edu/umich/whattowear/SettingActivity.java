package edu.umich.whattowear;

import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.app.NavUtils;

public class SettingActivity extends PreferenceActivity implements OnSharedPreferenceChangeListener {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.layout.activity_setting);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        prefs.registerOnSharedPreferenceChangeListener(this);
    }
    
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_setting, menu);
        return true;
    }
 
 
    
    public static void Write(Context context, final String key, final String value) {
          SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
          SharedPreferences.Editor editor = settings.edit();
          editor.putString(key, value);
          editor.commit();        
    }


	public void onSharedPreferenceChanged(SharedPreferences prefs,
			String key) {
		// TODO Auto-generated method stub
		setResult(1, null);
		
	}
}
