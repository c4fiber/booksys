/*
 * Restaurant Booking System: example code to accompany
 *
 * "Practical Object-oriented Design with UML"
 * Mark Priestley
 * McGraw-Hill (2004)
 */

package com.domain;

public class User extends Object{
	private int oid;
	private String id;
	private String password;
	private String name;
	private String phoneNumber;

	public User(int aOid, String aID, String aPassword, String aName, String aPhoneNumber) {
		oid = aOid;
		id = aID;
		password = aPassword;
		name = aName;
		phoneNumber = aPhoneNumber;
	}

	public User(String aID,String aName) {
		id = aID;
		name = aName;
	}

	public int getOid() {
		return oid;
	}

	public String get() {
		return name;
	}

	public String getName() {
		return name;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public String getId() {
		return id;
	}

	public String getPassword() {
		return password;
	}
	
	public void setId(String aID) {
		id = aID;
	}
	
	public void setName(String aName) {
		name = aName;
	}
}
