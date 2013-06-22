package com.dharam.critycall;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockListActivity;
import com.actionbarsherlock.view.MenuItem;
import com.dharam.critycall.database.DatabaseHandler;

public class SearchResultActivity extends SherlockListActivity {

	private static int THEME = R.style.Theme_Styled;
	private static SharedPreferences sharedPreferences;
	private static String selection;

	private static DatabaseHandler db;

	public static String id[];
	public static int selectedIDPosition;
	public static String name[];
	public static String address[];
	public static String location_cd[];
	public static List<String> location;
	public static String city_cd[];
	public static String pincode[];
	public static String phone[];
	public static String mobile[];
	public static String qualification[];
	public static String speciality_cd[];
	public static List<String> speciality;
	public static String days[];
	public static String timing[];
	public static String website[];

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setTheme(THEME);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_result);
		BitmapDrawable bg = (BitmapDrawable) getResources().getDrawable(R.drawable.bg);
		bg.setTileModeXY(TileMode.REPEAT, TileMode.REPEAT);
		getSupportActionBar().setBackgroundDrawable(bg);

		sharedPreferences = getApplicationContext().getSharedPreferences("Critycall", 0);

		selection = sharedPreferences.getString("Selection", "null");
		db = new DatabaseHandler(getApplicationContext());

		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		getSupportActionBar().setTitle(selection);

		location = new ArrayList<String>();
		speciality = new ArrayList<String>();

		populateDetails();
	}

	private void populateDetails() {

		try {
			id = SearchActivity.passData.getString("ID").split("`");
			name = SearchActivity.passData.getString("NAME").split("`");
			address = SearchActivity.passData.getString("ADDRESS").split("`");
			location_cd = SearchActivity.passData.getString("LOCATION_CD").split("`");
			for (int i = 0; i < location_cd.length; i++) {
				location.add(db.getLocation(location_cd[i]).getLocation_name());
			}
			city_cd = SearchActivity.passData.getString("CITY_CD").split("`");
			pincode = SearchActivity.passData.getString("PINCODE").split("`");
			phone = SearchActivity.passData.getString("PHONE").split("`");

			if (selection.equals("Doctor")) {
				mobile = SearchActivity.passData.getString("MOBILE").split("`");
				qualification = SearchActivity.passData.getString("QUALIFICATION").split("`");
				speciality_cd = SearchActivity.passData.getString("SPECIALITY_CD").split("`");
				for (int i = 0; i < speciality_cd.length; i++) {
					speciality.add(db.getSpeciality(speciality_cd[i]).getSpeciality_name());
				}
				days = SearchActivity.passData.getString("DAYS").split("`");
			}

			if (!selection.equals("Hospital")) {
				timing = SearchActivity.passData.getString("TIMING").split("`");
			}

			if (selection.equals("Hospital")) {
				website = SearchActivity.passData.getString("WEBSITE").split("`");
			}

			setListAdapter(new SearchResultListAdapter(getApplicationContext(), R.layout.list_search_result, id));

		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			SearchActivity.Area = "";
			SearchActivity.Specialization = "";
			finish();
		}
		return false;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			SearchActivity.Area = "";
			SearchActivity.Specialization = "";
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		selectedIDPosition = position;
		startActivity(new Intent(getApplicationContext(), DetailActivity.class));
	}

}
