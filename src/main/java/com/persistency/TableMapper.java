/*
 * Restaurant Booking System: example code to accompany
 *
 * "Practical Object-oriented Design with UML"
 * Mark Priestley
 * McGraw-Hill (2004)
 */

package com.persistency;

import com.domain.Table;
import com.storage.Database;
import java.sql.*;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

public class TableMapper {
	// Implementation of hidden cache

	private Hashtable cache;

	private PersistentTable getFromCache(int oid) {
		Integer key = new Integer(oid);
		return (PersistentTable) cache.get(key);
	}

	private PersistentTable getFromCacheByNumber(int tno) {
		PersistentTable t = null;
		Enumeration enums = cache.elements();
		while (t == null & enums.hasMoreElements()) {
			PersistentTable tmp = (PersistentTable) enums.nextElement();
			if (tmp.getNumber() == tno) {
				t = tmp;
			}
		}
		return t;
	}

	private void addToCache(PersistentTable t) {
		Integer key = new Integer(t.getId());
		cache.put(key, t);
	}

	// Constructor:

	private TableMapper() {
		cache = new Hashtable();
	}

	// Singleton:

	private static TableMapper uniqueInstance;

	public static TableMapper getInstance() {
		if (uniqueInstance == null) {
			uniqueInstance = new TableMapper();
		}
		return uniqueInstance;
	}

	public PersistentTable getTable(int tno) {
		PersistentTable t = getFromCacheByNumber(tno);
		if (t == null) {
			t = getTable("SELECT * FROM `Table` WHERE number='" + tno + "'");
			if (t != null) {
				addToCache(t);
			}
		}
		return t;
	}

	/*
	 * 
	 */
	public PersistentTable appropriateTable(int a,Time time,Date date) {
		try
		{
			//같은날 같은시간에 테이블들을 확인해
			Statement stmt = Database.getConnection().createStatement();
			//있으면 빼주고 남는 테이블 객체 반환 
			ResultSet checkSet = stmt.executeQuery("SELECT * from reservation WHERE time='"+time+"'");
			while (checkSet.next()) {
				if (checkSet.getInt(1) > 0) {
					return null;
				}
			}
			checkSet.close();
			PersistentTable t = getTable("Select * from table where number=" + a);
			return t;
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	PersistentTable getTableForOid(int oid) {
		PersistentTable t = getFromCache(oid);
		if (t == null) {
			t = getTable("SELECT * FROM `Table` WHERE oid ='" + oid + "'");
			if (t != null) {
				addToCache(t);
			}
		}
		return t;
	}

	private PersistentTable getTable(String sql) {
		PersistentTable t = null;
		try {
			Statement stmt = Database.getInstance().getConnection().createStatement();
			ResultSet rset = stmt.executeQuery(sql);
			while (rset.next()) {
				int oid = rset.getInt(1);
				int number = rset.getInt(2);
				int places = rset.getInt(3);
				t = new PersistentTable(oid, number, places);
			}
			rset.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return t;
	}

	public Vector getTableNumbers() {
		Vector v = new Vector();
		try {
			Statement stmt = Database.getInstance().getConnection().createStatement();
			ResultSet rset = stmt.executeQuery("SELECT * FROM `Table` ORDER BY number");
			while (rset.next()) {
				v.addElement(new Integer(rset.getInt(2)));
			}
			rset.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return v;
	}
	
	public PersistentTable appropriateTable(Time time,Date date) {
		try
		{
			/*
			 * nowReservationTable
			 * 이미 date일 time에 예약된 테이블 번호 모아져 있는 벡터 
			 */
			Vector<Integer> nowReservationTable = new Vector<>();
			//같은날 같은시간에 테이블들을 확인해
			Statement stmt = Database.getConnection().createStatement(); 
			ResultSet checkSet = stmt.executeQuery("SELECT table_id from reservation WHERE time='"+time+"'"+"AND date='"+date+"'");
			while (checkSet.next()) {
				nowReservationTable.add(checkSet.getInt(1));
			}
			checkSet.close();
			stmt.close();
			
			
			//빈 테이블 검색 
			/*
			 * findTableSQLsyntax=> SQL구문 생성
			 * SELECT * from `table` WHERE '위에서 찾았던 이미 예약된 테이블이 아닌것'
			 */
			stmt = Database.getConnection().createStatement(); 
			StringBuffer findTableSQLsyntax = new StringBuffer();
			findTableSQLsyntax.append("SELECT * from `table`");
			if (!nowReservationTable.isEmpty())
			{
				findTableSQLsyntax.append(" WHERE");
				for(int i=0;i<nowReservationTable.size();i++)
				{
					findTableSQLsyntax.append(" (not number="+nowReservationTable.get(i)+")");	
					if(i!=(nowReservationTable.size()-1))
						findTableSQLsyntax.append("AND");
				}	
			}		
			PersistentTable table=null;
			checkSet= stmt.executeQuery(findTableSQLsyntax.toString());
			while (checkSet.next()) {
				//그 테이블 중 가장 먼저 찾은 테이블 객체 반환
				table = getTable("Select * from `table` where number=" + checkSet.getInt(1));
				return table;
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		/*
		 * 없을 시 위에서 SQLException 일어날 것이므로 일단 NULL 처리
		 */
		return null;
	}

}
