package com.dharam.critycall.database;

public class Location {

	String location_cd;
	String location_name;
	String city_cd;

	public Location() {

	}

	public Location(String location_cd, String location_name, String city_cd) {
		this.location_cd = location_cd;
		this.location_name = location_name;
		this.city_cd = city_cd;
	}

	public String getLocation_cd() {
		return location_cd;
	}

	public void setLocation_cd(String location_cd) {
		this.location_cd = location_cd;
	}

	public String getLocation_name() {
		return location_name;
	}

	public void setLocation_name(String location_name) {
		this.location_name = location_name;
	}

	public String getCity_cd() {
		return city_cd;
	}

	public void setCity_cd(String city_cd) {
		this.city_cd = city_cd;
	}

}
