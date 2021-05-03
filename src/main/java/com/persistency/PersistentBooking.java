/*
 * Restaurant Booking System: example code to accompany
 *
 * "Practical Object-oriented Design with UML"
 * Mark Priestley
 * McGraw-Hill (2004)
 */

package com.persistency ;

import com.domain.* ;

interface PersistentBooking extends Booking
{
  int getId() ;
}
