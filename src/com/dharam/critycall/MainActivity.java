package com.dharam.critycall;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.dharam.critycall.database.City;
import com.dharam.critycall.database.DatabaseHandler;
import com.dharam.critycall.database.Location;
import com.dharam.critycall.database.Speciality;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.Toast;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;

public class MainActivity extends Activity {

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
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_flash);
		final DatabaseHandler db = new DatabaseHandler(getApplicationContext());

		doctor_location_cd_list = new ArrayList<String>();
		doctor_speciality_cd_list = new ArrayList<String>();
		hospital_location_cd_list = new ArrayList<String>();
		chemist_location_cd_list = new ArrayList<String>();
		lab_location_cd_list = new ArrayList<String>();
		ambulance_location_cd_list = new ArrayList<String>();

		request = new RequestParams();
		request.put("tag", "all_location_specialization");

		if (!isNetworkConnected()) {
			Toast.makeText(getApplicationContext(), "No Internet Connection Found!!!", Toast.LENGTH_LONG).show();
			Thread timer = new Thread() {
				public void run() {
					try {
						sleep(2000);
						finish();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			};
			timer.start();

		}

		CritycallRestClient.post("dashboard.php", request, new AsyncHttpResponseHandler() {
			public void onSuccess(String response) {
				try {
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

					db.addCity(new City("C0001", "Mumbai"));

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
					startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
				}
				super.onSuccess(response);
			}
		});
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
