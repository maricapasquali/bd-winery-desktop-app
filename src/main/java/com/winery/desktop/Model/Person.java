package com.winery.desktop.Model;

import com.winery.desktop.Utility.Utility;

public class Person {

	private long ID;
	private String name;
	private String lastName;
	private String street;
	private int streetNumber;
	private String streetCity;
	private String phoneNumber;

	public Person() {
	}

	public long getID() {
		return ID;
	}

	public void setID(final Long iD) {
		ID = iD;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		Utility.checkLength(name);
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(final String lastName) {
		Utility.checkLength(lastName);
		this.lastName = lastName;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(final String street) {
		Utility.checkLength(street);
		this.street = street;
	}

	public int getStreetNumber() {
		return streetNumber;
	}

	public void setStreetNumber(final int streetNumber) {
		Utility.checkNumber(streetNumber);
		this.streetNumber = streetNumber;
	}

	public String getStreetCity() {
		return streetCity;
	}

	public void setStreetCity(final String streetCity) {
		Utility.checkLength(streetCity);
		this.streetCity = streetCity;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(final String phoneNumber){
		Utility.checkLength(phoneNumber);
		this.phoneNumber = phoneNumber;
	}

	@Override
	public String toString() {
		return "Person [ID =" + ID + ", name =" + name + ", lastName =" + lastName + ", street =" + street
				+ ", streetNumber =" + streetNumber + ", streetCity =" + streetCity + ", phoneNumber =" + phoneNumber
				+ "]";
	}
	
	public String string() {
		return getID() + Utility.getSplit() + getName() + Utility.getSplit() + getLastName();
	}
}
