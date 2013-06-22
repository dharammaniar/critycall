package com.dharam.critycall;

import java.util.ArrayList;
import java.util.List;


import com.dharam.critycall.database.City;
import com.dharam.critycall.database.DatabaseHandler;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.Toast;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;

public class MainActivity extends Activity {

	public static List<String> city_list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_flash);
		final DatabaseHandler db = new DatabaseHandler(getApplicationContext());
 
		city_list = new ArrayList<String>();

		if (!isNetworkConnected()) {
			Toast.makeText(getApplicationContext(), "No Internet Connection Found!!!", Toast.LENGTH_LONG).show();

		}

		db.addCity(new City("C0001", "Mumbai"));
		db.addCity(new City("C0002", "Thane"));
		db.addCity(new City("C0003", "New Mumbai"));

		city_list.add("Mumbai");
		city_list.add("Thane");
		city_list.add("New Mumbai");

		Thread timer = new Thread() {
			public void run() {
				try {
					sleep(2000);
					finish();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
				}
			}
		};
		timer.start();

	}

	private boolean isNetworkConnected() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getActiveNetworkInfo();
		if (ni == null) {
			return false;
		} else
			return true;
	}

}
