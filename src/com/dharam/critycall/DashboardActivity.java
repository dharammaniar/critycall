package com.dharam.critycall;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.dharam.critycall.database.DatabaseHandler;
import com.dharam.critycall.database.Location;
import com.dharam.critycall.database.Speciality;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class DashboardActivity extends SherlockActivity {

	private static int THEME = R.style.Theme_Styled;
	private static SharedPreferences sharedPreferences;
	private static Editor editor;

	private static DatabaseHandler db;

	private static RequestParams request;
	private static JSONObject passData;

	private String location_cd[];
	private String location_name[];
	private String speciality_cd[];
	private String speciality_name[];
	public static String doctor_location_cd[];
	public static String doctor_speciality_cd[];
	public static String hospital_location_cd[];
	public static String chemist_location_cd[];
	public static String lab_location_cd[];
	public static String ambulance_location_cd[];
	public static List<String> doctor_location_cd_list;
	public static List<String> doctor_speciality_cd_list;
	public static List<String> hospital_location_cd_list;
	public static List<String> chemist_location_cd_list;
	public static List<String> lab_location_cd_list;
	public static List<String> ambulance_location_cd_list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setTheme(THEME);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dashboard);
		BitmapDrawable bg = (BitmapDrawable) getResources().getDrawable(R.drawable.bg);
		bg.setTileModeXY(TileMode.REPEAT, TileMode.REPEAT);
		getSupportActionBar().setBackgroundDrawable(bg);
		sharedPreferences = getApplicationContext().getSharedPreferences("Critycall", 0);
		editor = sharedPreferences.edit();
		db = new DatabaseHandler(getApplicationContext());

		doctor_location_cd_list = new ArrayList<String>();
		doctor_speciality_cd_list = new ArrayList<String>();
		hospital_location_cd_list = new ArrayList<String>();
		chemist_location_cd_list = new ArrayList<String>();
		lab_location_cd_list = new ArrayList<String>();
		ambulance_location_cd_list = new ArrayList<String>();

		if (sharedPreferences.contains("city")) {
			request = new RequestParams();
			System.out.println(db.getCityCdByName(sharedPreferences.getString("city", "")));
			request.put("city_cd", db.getCityCdByName(sharedPreferences.getString("city", "")));

			request = new RequestParams();
			request.put("tag", "all_location_specialization");
			CritycallRestClient.post("dashboard.php", request, new AsyncHttpResponseHandler() {
				public void onSuccess(String response) {
					try {
						System.out.println("Got Response");
						passData = new JSONObject(response);
						location_cd = passData.getString("LOCATION_CD").split("`");
						location_name = passData.getString("LOCATION_NAME").split("`");
						speciality_cd = passData.getString("SPECIALITY_CD").split("`");
						speciality_name = passData.getString("SPECIALITY_NAME").split("`");
						doctor_location_cd = passData.getString("DOCTOR_LOCATION_CD").split("`");
						doctor_speciality_cd = passData.getString("DOCTOR_SPECIALITY_CD").split("`");
						hospital_location_cd = passData.getString("HOSPITAL_LOCATION_CD").split("`");
						chemist_location_cd = passData.getString("CHEMIST_LOCATION_CD").split("`");
						lab_location_cd = passData.getString("LAB_LOCATION_CD").split("`");
						ambulance_location_cd = passData.getString("AMBULANCE_LOCATION_CD").split("`");

						for (int i = 0; i < location_cd.length; i++) {
							db.addLocation(new Location(location_cd[i], location_name[i], "C0001"));
						}

						for (int i = 0; i < speciality_cd.length; i++) {
							db.addSpeciality(new Speciality(speciality_cd[i], speciality_name[i]));
						}

						for (int i = 0; i < doctor_location_cd.length; i++) {
							doctor_location_cd_list.add(doctor_location_cd[i]);
						}
						for (int i = 0; i < doctor_speciality_cd.length; i++) {
							doctor_speciality_cd_list.add(doctor_speciality_cd[i]);
						}
						for (int i = 0; i < hospital_location_cd.length; i++) {
							hospital_location_cd_list.add(hospital_location_cd[i]);
						}
						for (int i = 0; i < chemist_location_cd.length; i++) {
							chemist_location_cd_list.add(chemist_location_cd[i]);
						}
						for (int i = 0; i < lab_location_cd.length; i++) {
							lab_location_cd_list.add(lab_location_cd[i]);
						}
						for (int i = 0; i < ambulance_location_cd.length; i++) {
							ambulance_location_cd_list.add(ambulance_location_cd[i]);
						}

					} catch (JSONException e) {
						e.printStackTrace();
						Toast.makeText(getApplicationContext(), "Please try again...", Toast.LENGTH_SHORT).show();
						finish();
					} finally {
					}
					super.onSuccess(response);
				}
			});
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (sharedPreferences.contains("city")) {
			menu.add(sharedPreferences.getString("city", "Select City")).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
		} else {
			menu.add("Select City").setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
		}
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case 0:
			System.out.println("Selected Item ID = 0");
			startActivity(new Intent(getApplicationContext(), SearchCityActivity.class));
		}
		return false;
	}

	public void onClickDoctor(View v) {
		editor.putString("Selection", "Doctor");
		editor.commit();
		startActivity(new Intent(getApplicationContext(), SearchActivity.class));
	}

	public void onClickHospital(View v) {
		editor.putString("Selection", "Hospital");
		editor.commit();
		startActivity(new Intent(getApplicationContext(), SearchActivity.class));
	}

	public void onClickAmbulance(View v) {
		editor.putString("Selection", "Ambulance");
		editor.commit();
		startActivity(new Intent(getApplicationContext(), SearchActivity.class));
	}

	public void onClickChemist(View v) {
		editor.putString("Selection", "Chemist");
		editor.commit();
		startActivity(new Intent(getApplicationContext(), SearchActivity.class));
	}

	public void onClickLab(View v) {
		editor.putString("Selection", "Lab");
		editor.commit();
		startActivity(new Intent(getApplicationContext(), SearchActivity.class));
	}

	public void onClickInfo(View v) {
		startActivity(new Intent(getApplicationContext(), InfoActivity.class));
	}
}
