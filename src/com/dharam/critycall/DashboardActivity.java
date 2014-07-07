package com.dharam.critycall;

import com.actionbarsherlock.app.SherlockActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;

public class DashboardActivity extends SherlockActivity {

	private static int THEME = R.style.Theme_Styled;
	private static SharedPreferences sharedPreferences;
	private static Editor editor;

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
