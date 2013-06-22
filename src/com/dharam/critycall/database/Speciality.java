package com.dharam.critycall.database;

public class Speciality {

	String speciality_cd;
	String speciality_name;

	public Speciality() {

	}

	public Speciality(String speciality_cd, String speciality_name) {
		this.speciality_cd = speciality_cd;
		this.speciality_name = speciality_name;
	}

	public String getSpeciality_cd() {
		return speciality_cd;
	}

	public void setSpeciality_cd(String speciality_cd) {
		this.speciality_cd = speciality_cd;
	}

	public String getSpeciality_name() {
		return speciality_name;
	}

	public void setSpeciality_name(String speciality_name) {
		this.speciality_name = speciality_name;
	}

}
