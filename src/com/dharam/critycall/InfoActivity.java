package com.dharam.critycall;

import android.content.Intent;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;

public class InfoActivity extends SherlockActivity {

	private static int THEME = R.style.Theme_Styled;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setTheme(THEME);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_info);
		BitmapDrawable bg = (BitmapDrawable) getResources().getDrawable(R.drawable.bg);
		bg.setTileModeXY(TileMode.REPEAT, TileMode.REPEAT);
		getSupportActionBar().setBackgroundDrawable(bg);

		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setTitle("Info");

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
		}
		return false;
	}

	public void onClickTerms(View v) {
		startActivity(new Intent(getApplicationContext(), TermsActivity.class));
	}

	public void onClickShare(View v) {

		Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
		sharingIntent.setType("text/plain");
		sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Critycall");
		sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Critycall is a mobile application providing information about doctors, hospitals, chemists, labs and ambulance services in your city. Download it now at https://play.google.com/store/apps/details?id=com.dharam.critycall");
		startActivity(Intent.createChooser(sharingIntent, "Share via"));
	}

}
