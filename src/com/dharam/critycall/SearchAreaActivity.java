package com.dharam.critycall;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SectionIndexer;
import android.widget.AdapterView.OnItemClickListener;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.widget.SearchView;
import com.dharam.critycall.database.DatabaseHandler;
import com.dharam.critycall.widget.IndexableListView;
import com.dharam.critycall.widget.StringMatcher;

public class SearchAreaActivity extends SherlockActivity implements SearchView.OnQueryTextListener {

	private static int THEME = R.style.Theme_Styled;
	private static SharedPreferences sharedPreferences;
	private static String selection;
	private static DatabaseHandler db;
	private IndexableListView mListView;
	private static List<String> list;
	private static List<String> areaList;
	private static String specialityCd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setTheme(THEME);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_area);
		BitmapDrawable bg = (BitmapDrawable) getResources().getDrawable(R.drawable.bg);
		bg.setTileModeXY(TileMode.REPEAT, TileMode.REPEAT);
		getSupportActionBar().setBackgroundDrawable(bg);

		db = new DatabaseHandler(getApplicationContext());
		sharedPreferences = getApplicationContext().getSharedPreferences("Critycall", 0);

		selection = sharedPreferences.getString("Selection", "null");

		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		getSupportActionBar().setTitle("Select Area");

		if (selection.equals("Doctor")) {
			if (SearchActivity.Specialization == null) {

				list = new ArrayList<String>(new HashSet<String>(DashboardActivity.doctor_location_cd_list));

			} else if (SearchActivity.Specialization.length() == 0) {
				list = new ArrayList<String>(new HashSet<String>(DashboardActivity.doctor_location_cd_list));
			} else {
				areaList = new ArrayList<String>();
				specialityCd = db.getSpecialityByName(SearchActivity.Specialization).getSpeciality_cd();
				for (int i = 0; i < DashboardActivity.doctor_speciality_cd_list.size(); i++) {
					if (specialityCd.equals(DashboardActivity.doctor_speciality_cd_list.get(i))) {
						areaList.add(DashboardActivity.doctor_location_cd_list.get(i));
					}
				}
				list = new ArrayList<String>(new HashSet<String>(areaList));
			}
		}

		if (selection.equals("Hospital")) {
			list = new ArrayList<String>(new HashSet<String>(DashboardActivity.hospital_location_cd_list));
		}

		if (selection.equals("Chemist")) {
			list = new ArrayList<String>(new HashSet<String>(DashboardActivity.chemist_location_cd_list));
		}

		if (selection.equals("Lab")) {
			list = new ArrayList<String>(new HashSet<String>(DashboardActivity.lab_location_cd_list));
		}

		if (selection.equals("Ambulance")) {
			list = new ArrayList<String>(new HashSet<String>(DashboardActivity.ambulance_location_cd_list));
		}

		for (int i = 0; i < list.size(); i++) {
			list.set(i, db.getLocation(list.get(i)).getLocation_name());
		}

		setList(list);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		SearchView searchView = new SearchView(getSupportActionBar().getThemedContext());
		searchView.setQueryHint("Search for location");
		searchView.setOnQueryTextListener(this);

		menu.add("Search").setIcon(R.drawable.ic_search_inverse).setActionView(searchView).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
		}
		return false;
	}

	@Override
	public boolean onQueryTextSubmit(String query) {
		return true;
	}

	@Override
	public boolean onQueryTextChange(String newText) {
		List<String> locations = db.getSimilarLocations(newText.toString());
		setList(locations);
		return false;
	}

	public void setList(List<String> locations) {
		Collections.sort(locations);
		ContentAdapter adapter = new ContentAdapter(this, R.layout.list_item_text, locations);
		mListView = (IndexableListView) findViewById(R.id.search_area_lv);
		mListView.setAdapter(adapter);
		mListView.setFastScrollEnabled(true);
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				SearchActivity.Area = (String) mListView.getItemAtPosition(arg2);
				db.close();
				startActivity(new Intent(getApplicationContext(), SearchActivity.class));
			}
		});
	}

	private class ContentAdapter extends ArrayAdapter<String> implements SectionIndexer {

		private String mSections = "#ABCDEFGHIJKLMNOPQRSTUVWXYZ";

		public ContentAdapter(Context context, int textViewResourceId, List<String> objects) {
			super(context, textViewResourceId, objects);
		}

		@Override
		public int getPositionForSection(int section) {
			// If there is no item for current section, previous section will be
			// selected
			for (int i = section; i >= 0; i--) {
				for (int j = 0; j < getCount(); j++) {
					if (i == 0) {
						// For numeric section
						for (int k = 0; k <= 9; k++) {
							if (StringMatcher.match(String.valueOf(getItem(j).charAt(0)), String.valueOf(k)))
								return j;
						}
					} else {
						if (StringMatcher.match(String.valueOf(getItem(j).charAt(0)), String.valueOf(mSections.charAt(i))))
							return j;
					}
				}
			}
			return 0;
		}

		@Override
		public int getSectionForPosition(int position) {
			return 0;
		}

		@Override
		public Object[] getSections() {
			String[] sections = new String[mSections.length()];
			for (int i = 0; i < mSections.length(); i++)
				sections[i] = String.valueOf(mSections.charAt(i));
			return sections;
		}
	}
}
