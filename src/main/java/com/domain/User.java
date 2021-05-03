/*
 * Restaurant Booking System: example code to accompany
 *
 * "Practical Object-oriented Design with UML"
 * Mark Priestley
 * McGraw-Hill (2004)
 */

package com.domain ;

public class User
{
  private String id;
  private String password;
  private String name ;
  private String phoneNumber ;

  public User(String i, String pw,String n, String p)
  {
	id = i;
	password = pw;
    name = n ;
    phoneNumber = p ;
  }

  public String getName()
  {
    return name ;
  }

  public String getPhoneNumber()
  {
    return phoneNumber ;
  }
  public String getId()
  {
    return id ;
  }
  public String getPassword()
  {
    return password ;
  }
}
