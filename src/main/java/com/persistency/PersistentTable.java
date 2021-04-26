/*
 * Restaurant Booking System: example code to accompany
 *
 * "Practical Object-oriented Design with UML"
 * Mark Priestley
 * McGraw-Hill (2004)
 */

package com.persistency ;

import com.domain.Table ;

class PersistentTable extends Table
{
  private int oid ;

  PersistentTable(int id, int n, int c)
  {
    super(n, c) ;
    oid = id ;
  }

  int getId() {
    return oid ;
  }
}
