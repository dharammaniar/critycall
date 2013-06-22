package com.dharam.critycall.database;

public class City {

	String city_cd;
	String city_name;

	public City() {

	}

	public City(String city_cd, String city_name) {
		this.city_cd = city_cd;
		this.city_name = city_name;
	}

	public String getCity_cd() {
		return city_cd;
	}

	public void setCity_cd(String city_cd) {
		this.city_cd = city_cd;
	}

	public String getCity_name() {
		return city_name;
	}

	public void setCity_name(String city_name) {
		this.city_name = city_name;
	}

}
