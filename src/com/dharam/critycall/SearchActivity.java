package com.dharam.critycall;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.dharam.critycall.database.DatabaseHandler;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class SearchActivity extends SherlockActivity {

	private static int THEME = R.style.Theme_Styled;
	private static SharedPreferences sharedPreferences;
	private static DatabaseHandler db;

	private Button search_button_specialization;
	private Button search_button_area;
	private Button search_button_area_clear;
	private Button search_button_specialization_clear;

	public static String selection;

	private static RequestParams request;
	public static JSONObject passData;

	public static String Area;
	public static String Specialization;

	private ProgressBar progressBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setTheme(THEME);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		BitmapDrawable bg = (BitmapDrawable) getResources().getDrawable(R.drawable.bg);
		bg.setTileModeXY(TileMode.REPEAT, TileMode.REPEAT);
		getSupportActionBar().setBackgroundDrawable(bg);

		sharedPreferences = getApplicationContext().getSharedPreferences("Critycall", 0);
		db = new DatabaseHandler(getApplicationContext());

		selection = sharedPreferences.getString("Selection", "null");

		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		getSupportActionBar().setTitle(selection);

		search_button_specialization = (Button) findViewById(R.id.search_button_specialization);
		search_button_area = (Button) findViewById(R.id.search_button_area);
		search_button_area_clear = (Button) findViewById(R.id.search_button_area_clear);
		search_button_specialization_clear = (Button) findViewById(R.id.search_button_specialization_clear);

		progressBar = (ProgressBar) findViewById(R.id.search_progressbar);

		if (selection.equals("Doctor")) {
			search_button_specialization.setVisibility(0);
			if (Specialization != null) {
				if (!(Specialization.length() == 0)) {
					search_button_specialization.setText(Specialization);
					search_button_specialization_clear.setVisibility(0);
				}
			}
		} else {
			// To avoid Specialization to be saved after coming from a
			// non-doctor screen
			Specialization = null;
		}

		if (Area != null) {
			if (!(Area.length() == 0)) {
				search_button_area.setText(Area);
				search_button_area_clear.setVisibility(0);
			}
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			Area = "";
			Specialization = "";
			finish();
		}
		return false;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Area = "";
			Specialization = "";
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}

	public void onClickArea(View v) {
		startActivity(new Intent(getApplicationContext(), SearchAreaActivity.class));
	}

	public void onClickSpecialization(View v) {
		startActivity(new Intent(getApplicationContext(), SearchSpecializationActivity.class));
	}

	public void onClickAreaClear(View v) {
		Area = "";
		search_button_area.setText("Select Area");
		search_button_area_clear.setVisibility(4);
	}

	public void onClickSpecializationClear(View v) {
		Specialization = "";
		search_button_specialization.setText("Select Specialization");
		search_button_specialization_clear.setVisibility(4);
	}

	public void onClickSearch(View v) {

		String location = "";
		String speciality = "";
		if (selection.equals("Doctor")) {
			request = new RequestParams();
			request.put("tag", "doctors");
			if (Area != null) {
				if (!(Area.length() == 0)) {
					location = db.getLocationByName(Area).getLocation_cd();
				}
			}
			if (Specialization != null) {
				if (!(Specialization.length() == 0)) {
					speciality = db.getSpecialityByName(Specialization).getSpeciality_cd();
				}
			}

			if (location.equals("") && speciality.equals("")) {
				Toast.makeText(getApplicationContext(), "Please Select Area or Specialization", Toast.LENGTH_SHORT).show();
			} else {
				request.put("location_cd", location);
				request.put("speciality_cd", speciality);
				progressBar.setVisibility(0);
				CritycallRestClient.post("search.php", request, new AsyncHttpResponseHandler() {
					public void onSuccess(String response) {
						try {
							System.out.println(response);
							passData = new JSONObject(response);
						} catch (JSONException e) {
							e.printStackTrace();
						} finally {
							startActivity(new Intent(getApplicationContext(), SearchResultActivity.class));
						}
						super.onSuccess(response);
					}
				});
			}
		}
		if (selection.equals("Hospital")) {
			request = new RequestParams();
			request.put("tag", "hospitals");
			if (Area != null) {
				if (!(Area.length() == 0)) {
					location = db.getLocationByName(Area).getLocation_cd();
				}
			}

			if (location.equals("")) {
				Toast.makeText(getApplicationContext(), "Please Select Area", Toast.LENGTH_SHORT).show();
			} else {
				request.put("location_cd", location);
				progressBar.setVisibility(0);
				CritycallRestClient.post("search.php", request, new AsyncHttpResponseHandler() {
					public void onSuccess(String response) {
						try {
							System.out.println(response);
							passData = new JSONObject(response);
						} catch (JSONException e) {
							e.printStackTrace();
						} finally {
							startActivity(new Intent(getApplicationContext(), SearchResultActivity.class));
						}
						super.onSuccess(response);
					}
				});
			}
		}
		if (selection.equals("Ambulance")) {
			request = new RequestParams();
			request.put("tag", "ambulances");
			if (Area != null) {
				if (!(Area.length() == 0)) {
					location = db.getLocationByName(Area).getLocation_cd();
				}
			}

			if (location.equals("")) {
				Toast.makeText(getApplicationContext(), "Please Select Area", Toast.LENGTH_SHORT).show();
			} else {
				request.put("location_cd", location);
				progressBar.setVisibility(0);
				CritycallRestClient.post("search.php", request, new AsyncHttpResponseHandler() {
					public void onSuccess(String response) {
						try {
							System.out.println(response);
							passData = new JSONObject(response);
						} catch (JSONException e) {
							e.printStackTrace();
						} finally {
							startActivity(new Intent(getApplicationContext(), SearchResultActivity.class));
						}
						super.onSuccess(response);
					}
				});
			}
		}
		if (selection.equals("Chemist")) {
			request = new RequestParams();
			request.put("tag", "chemists");
			if (Area != null) {
				if (!(Area.length() == 0)) {
					location = db.getLocationByName(Area).getLocation_cd();
				}
			}

			if (location.equals("")) {
				Toast.makeText(getApplicationContext(), "Please Select Area", Toast.LENGTH_SHORT).show();
			} else {
				request.put("location_cd", location);
				progressBar.setVisibility(0);
				CritycallRestClient.post("search.php", request, new AsyncHttpResponseHandler() {
					public void onSuccess(String response) {
						try {
							System.out.println(response);
							passData = new JSONObject(response);
						} catch (JSONException e) {
							e.printStackTrace();
						} finally {
							startActivity(new Intent(getApplicationContext(), SearchResultActivity.class));
						}
						super.onSuccess(response);
					}
				});
			}
		}
		if (selection.equals("Lab")) {
			request = new RequestParams();
			request.put("tag", "labs");
			if (Area != null) {
				if (!(Area.length() == 0)) {
					location = db.getLocationByName(Area).getLocation_cd();
				}
			}

			if (location.equals("")) {
				Toast.makeText(getApplicationContext(), "Please Select Area", Toast.LENGTH_SHORT).show();
			} else {
				request.put("location_cd", location);
				progressBar.setVisibility(0);
				CritycallRestClient.post("search.php", request, new AsyncHttpResponseHandler() {
					public void onSuccess(String response) {
						try {
							System.out.println(response);
							passData = new JSONObject(response);
						} catch (JSONException e) {
							e.printStackTrace();
						} finally {
							startActivity(new Intent(getApplicationContext(), SearchResultActivity.class));
						}
						super.onSuccess(response);
					}
				});
			}
		}
	}

}
