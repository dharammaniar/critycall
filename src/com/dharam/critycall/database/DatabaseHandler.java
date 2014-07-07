package com.dharam.critycall.database;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {

	// All Static variables
	// Database Version
	private static final int version = 1;

	// Database Name
	private static final String name = "critycall";

	public DatabaseHandler(Context context) {
		super(context, name, null, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		System.out.println("Inside DB onCreate");

		// Create Static Tables
		db.execSQL("CREATE TABLE location (location_cd text primary key,location_name text,city_cd text)");
		db.execSQL("CREATE TABLE city (city_cd text primary key,city_name text)");
		db.execSQL("CREATE TABLE speciality (speciality_cd text primary key,speciality_name text)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		System.out.println("Inside DB onUpgrade");
		db.execSQL("DROP TABLE IF EXISTS location");
		db.execSQL("DROP TABLE IF EXISTS city");
		db.execSQL("DROP TABLE IF EXISTS speciality");
		onCreate(db);
	}

	public void initializeDB() {
		SQLiteDatabase db = this.getWritableDatabase();
		db.close();
	}

	public void addLocation(Location location) {

		if (getLocation(location.getLocation_cd()) == null) {
			SQLiteDatabase db = this.getWritableDatabase();
			ContentValues values = new ContentValues();
			values.put("location_cd", location.getLocation_cd());
			values.put("location_name", location.getLocation_name());
			values.put("city_cd", location.getCity_cd());
			// Inserting Row
			db.insert("location", null, values);
			db.close(); // Closing database connection
		}

	}

	public void addCity(City city) {

		if (getCity(city.getCity_cd()) == null) {
			SQLiteDatabase db = this.getWritableDatabase();
			ContentValues values = new ContentValues();
			values.put("city_cd", city.getCity_cd());
			values.put("city_name", city.getCity_name());
			// Inserting Row
			db.insert("city", null, values);
			db.close(); // Closing database connection
		}

	}

	public void addSpeciality(Speciality speciality) {

		if (getSpeciality(speciality.getSpeciality_cd()) == null) {
			SQLiteDatabase db = this.getWritableDatabase();
			ContentValues values = new ContentValues();
			values.put("speciality_cd", speciality.getSpeciality_cd());
			values.put("speciality_name", speciality.getSpeciality_name());
			// Inserting Row
			db.insert("speciality", null, values);
			db.close(); // Closing database connection
		}

	}

	public List<String> getAllLocations() {
		List<String> locationList = new ArrayList<String>();
		// Select All Query
		String selectQuery = "SELECT  * FROM location";

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				locationList.add(cursor.getString(1));
			} while (cursor.moveToNext());
		}
		db.close();
		return locationList;
	}

	public List<String> getAllCities() {
		List<String> cityList = new ArrayList<String>();
		// Select All Query
		String selectQuery = "SELECT  * FROM city";

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				cityList.add(cursor.getString(1));
			} while (cursor.moveToNext());
		}
		db.close();
		return cityList;
	}

	public List<String> getAllSpecialities() {
		List<String> specialityList = new ArrayList<String>();
		// Select All Query
		String selectQuery = "SELECT  * FROM speciality";

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				specialityList.add(cursor.getString(1));
			} while (cursor.moveToNext());
		}
		// return contact list
		db.close();
		return specialityList;
	}

	public Location getLocation(String location_cd) {
		Location location = null;
		// Select All Query
		String selectQuery = "SELECT * FROM location where location_cd = '" + location_cd + "'";

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			location = new Location();
			location.setLocation_cd(cursor.getString(0));
			location.setLocation_name(cursor.getString(1));
			location.setCity_cd(cursor.getString(2));
		}
		// return contact list
		db.close();
		return location;
	}

	public City getCity(String city_cd) {
		City city = null;
		// Select All Query
		String selectQuery = "SELECT * FROM city where city_cd = '" + city_cd + "'";

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			city = new City();
			city.setCity_cd(cursor.getString(0));
			city.setCity_name(cursor.getString(1));
		}
		// return contact list
		db.close();
		return city;
	}

	public Speciality getSpeciality(String speciality_cd) {
		Speciality speciality = null;
		// Select All Query
		String selectQuery = "SELECT * FROM speciality where speciality_cd = '" + speciality_cd + "'";

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			speciality = new Speciality();
			speciality.setSpeciality_cd(cursor.getString(0));
			speciality.setSpeciality_name(cursor.getString(1));
		}
		// return contact list
		db.close();
		return speciality;
	}

	public List<String> getSimilarLocations(String pattern) {
		List<String> locationList = new ArrayList<String>();
		// Select All Query
		String selectQuery = "SELECT  * FROM location where location_name like '%" + pattern + "%'";

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				locationList.add(cursor.getString(1));
			} while (cursor.moveToNext());
		}
		// return contact list
		db.close();
		return locationList;
	}

	public List<String> getSimilarSpecialities(String pattern) {
		List<String> specialityList = new ArrayList<String>();
		// Select All Query
		String selectQuery = "SELECT  * FROM speciality where speciality_name like '%" + pattern + "%'";

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				specialityList.add(cursor.getString(1));
			} while (cursor.moveToNext());
		}
		// return contact list
		db.close();
		return specialityList;
	}

	public Location getLocationByName(String location_name) {
		Location location = null;
		// Select All Query
		String selectQuery = "SELECT * FROM location where location_name = '" + location_name + "'";

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			location = new Location();
			location.setLocation_cd(cursor.getString(0));
			location.setLocation_name(cursor.getString(1));
			location.setCity_cd(cursor.getString(2));
		}
		// return contact list
		db.close();
		return location;
	}

	public Speciality getSpecialityByName(String doctorSpecialization) {
		Speciality speciality = null;
		// Select All Query
		String selectQuery = "SELECT * FROM speciality where speciality_name = '" + doctorSpecialization + "'";

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			speciality = new Speciality();
			speciality.setSpeciality_cd(cursor.getString(0));
			speciality.setSpeciality_name(cursor.getString(1));
		}
		// return contact list
		db.close();
		return speciality;
	}

}
