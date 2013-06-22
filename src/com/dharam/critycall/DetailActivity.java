package com.dharam.critycall;

import java.util.ArrayList;
import java.util.List;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockListActivity;
import com.actionbarsherlock.view.MenuItem;

public class DetailActivity extends SherlockListActivity {

	private static int THEME = R.style.Theme_Styled;
	private static SharedPreferences sharedPreferences;
	public static String selection;

	public List<String> title;
	public List<String> details;

	private TextView name;
	private TextView degree;

	private int position;
	private String shareText = "";

	public static List<String> callNumbers;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setTheme(THEME);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_details);
		BitmapDrawable bg = (BitmapDrawable) getResources().getDrawable(R.drawable.bg);
		bg.setTileModeXY(TileMode.REPEAT, TileMode.REPEAT);
		getSupportActionBar().setBackgroundDrawable(bg);

		sharedPreferences = getApplicationContext().getSharedPreferences("Critycall", 0);

		selection = sharedPreferences.getString("Selection", "null");

		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setTitle(selection);

		name = (TextView) findViewById(R.id.details_tv_name);
		degree = (TextView) findViewById(R.id.details_tv_degree);

		position = SearchResultActivity.selectedIDPosition;

		name.setText(SearchResultActivity.name[position]);
		shareText = shareText + SearchResultActivity.name[position] + "\n";
		if (selection.equals("Doctor")) {
			degree.setText(SearchResultActivity.qualification[position]);
			shareText = shareText + "Qualification - " + SearchResultActivity.qualification[position] + "\n";
		} else {
			degree.setHeight(0);
		}

		title = new ArrayList<String>();
		details = new ArrayList<String>();
		title.add("Address");
		details.add(SearchResultActivity.address[position] + ", " + SearchResultActivity.location.get(position) + ", Mumbai " + SearchResultActivity.pincode[position]);
		shareText = shareText + "Address - " + SearchResultActivity.address[position] + ", " + SearchResultActivity.location.get(position) + ", Mumbai " + SearchResultActivity.pincode[position] + "\n";
		title.add("Phone");
		details.add(SearchResultActivity.phone[position]);
		shareText = shareText + "Phone - " + SearchResultActivity.phone[position] + "\n";
		if (selection.equals("Doctor")) {

			title.add("Mobile");
			details.add(SearchResultActivity.mobile[position]);
			shareText = shareText + "Mobile - " + SearchResultActivity.mobile[position] + "\n";
			title.add("Speciality");
			details.add(SearchResultActivity.speciality.get(position));
			shareText = shareText + "Speciality - " + SearchResultActivity.speciality.get(position) + "\n";
		}
		if (!selection.equals("Hospital")) {
			title.add("Timings");
			details.add(SearchResultActivity.timing[position]);
			shareText = shareText + "Timings - " + SearchResultActivity.timing[position] + "\n";
		} else {
			title.add("Website");
			details.add(SearchResultActivity.website[position]);
			shareText = shareText + "Website - " + SearchResultActivity.website[position] + "\n";
		}

		setListAdapter(new DetailListAdapter(getApplicationContext(), R.layout.list_result, title, details));
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
		}
		return false;
	}

	public void onClickCall(View v) {

		try {
			Intent callIntent = new Intent(Intent.ACTION_CALL);
			if (selection.equals("Doctor")) {
				if (!SearchResultActivity.phone[position].equals("-") && !SearchResultActivity.mobile[position].equals("-")) {
					callNumbers = new ArrayList<String>();
					callNumbers.add(SearchResultActivity.phone[position]);
					callNumbers.add(SearchResultActivity.mobile[position]);
					startActivityForResult(new Intent(getApplicationContext(), CallActivity.class), 1);
				} else if (!SearchResultActivity.phone[position].equals("-") && SearchResultActivity.mobile[position].equals("-")) {
					callIntent.setData(Uri.parse("tel:" + SearchResultActivity.phone[position]));
					startActivity(callIntent);
				} else if (SearchResultActivity.phone[position].equals("-") && !SearchResultActivity.mobile[position].equals("-")) {
					callIntent.setData(Uri.parse("tel:" + SearchResultActivity.mobile[position]));
					startActivity(callIntent);
				}
			} else if (!SearchResultActivity.phone[position].equals("-")) {
				callIntent.setData(Uri.parse("tel:" + SearchResultActivity.phone[position]));
				startActivity(callIntent);
			} else {
				Toast.makeText(getApplicationContext(), "No Phone Number Available", Toast.LENGTH_SHORT).show();
			}

		} catch (ActivityNotFoundException e) {
		}

	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode == 1) {

			if (resultCode == RESULT_OK) {
				Intent callIntent = new Intent(Intent.ACTION_CALL);
				String result = data.getStringExtra("result");
				callIntent.setData(Uri.parse("tel:" + result));
				startActivity(callIntent);
			}
			if (resultCode == RESULT_CANCELED) {
			}
		}
	}

	public void onClickShare(View v) {
		shareText = shareText + "www.critycall.com" + "\n";
		Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
		sharingIntent.setType("text/plain");
		sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Critycall Information");
		sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareText);
		startActivity(Intent.createChooser(sharingIntent, "Share via"));
	}
}
