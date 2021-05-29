package com.dao;

import java.sql.Date;
import java.sql.Time;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.domain.User;

@Repository
public class BooksysDAO {
	@Autowired
	JdbcTemplate jt;

	/*
	 * 로그인, 회원가입
	 */
	// 로그인: match하는 User Object(id, name 갱신) 반환. 없으면 null
	public User login(String id, String password) {

		// queryForObject를 사용하면 결과가 0개일때 exception 발생
		return DataAccessUtils
				.singleResult(jt.query("select id, name, password FROM user where id = ? and password = ?",
						(rs, rowNum) -> new User(rs.getString("id"), rs.getString("name")), id, password));
	}

	// 회원가입: return 1(성공), 0(실패)
	public int register(String id, String password, String name, String phoneNumber) {
		// insert문은 update method를 사용한다.
		String sql = "INSERT INTO user (id, password, name, phoneNumber) VALUES (?,?,?,?)";
		int result = jt.update(sql, id, password, name, phoneNumber);

		return result;
	}

	/*
	 * 예약
	 */
	// 예약 추가
	public int addReservation(int covers, Date date, Time time, int table_id, int customer_id) {
		String sql = "INSERT INTO reservation (covers, date, time, table_id, customer_id) VALUES (?,?,?,?,?)";
		int result = jt.update(sql, covers, date, time, table_id, customer_id);

		return result;
	}

	// 예약 전체 조회
	public List<Map<String, ?>> selectAllReservations() {
		return jt.query("select * from reservation", (rs, rowNum) -> {
			Map<String, Object> mss = new HashMap<>();
			mss.put("covers", rs.getInt(2));
			mss.put("date", rs.getDate(3));
			mss.put("time", rs.getTime(4));
			mss.put("table_id", rs.getInt(5));
			mss.put("customer_id", rs.getInt(6));
			mss.put("arrivalTime", rs.getTime(7));
			return mss;
		});
	}
  
  	// 날짜에 따른 예약 조회
	public List<Map<String, ?>> takeAllReservationsUseDate(Date date) {
		return jt.query("select * from reservation where date='" + date.toString() + "'", (rs, rowNum) -> {
			Map<String, Object> mss = new HashMap<>();
			mss.put("covers", rs.getInt(2));
			mss.put("date", rs.getDate(3));
			mss.put("time", rs.getTime(4));
			mss.put("table_id", rs.getInt(5));
			mss.put("customer_id", rs.getInt(6));
			mss.put("arrivalTime", rs.getTime(7));
			return mss;
		});
	}
 	
	// 예약 추가
	public int addReservation(int covers, String date, String time, int table_id, int customer_id) {
		String sql = "INSERT INTO reservation (covers, date, time, table_id, customer_id) VALUES (?,?,?,?,?)";
		int result = jt.update(sql, covers, date, time, table_id, customer_id);
		
		return result;		
	}

	// 해당 날짜 해당 테이블에 중복된 예약이 있는지 확인
	public boolean nowTableReservationAvailable(Date date,Time time,int table_id)
	{	
		//해당 날짜 시간 테이블에 맞는 테이블을 찾아 있으면 false없으면 true (예약가능 반환한다.)
		String SQL = "SELECT count(*) from reservation WHERE date=" + "'" + date + "'"
				+ " AND time=" + "'" + time + "'" + " AND table_id=" + "'" + table_id + "'";
		int rowCount = jt.queryForObject(SQL, Integer.class);
		if(rowCount>=1)
		{
			return false;
		}
		return true;
	}

	/*
	 * 리뷰
	 */
	// mss->리뷰를 가져온다
	public List<Map<String, ?>> selectAllReviews() {
		return jt.query("select * from review", (rs, rowNum) -> {
			Map<String, Object> mss = new HashMap<>();
			mss.put("review_num", rs.getInt(1));
			mss.put("user_id", rs.getString(2));
			mss.put("date", rs.getDate(3));
			mss.put("comment", rs.getString(4));

			return mss;
		});
	}

	// 리뷰 추가
	public int addReview(String id, Date date, String comment) {
		String sql = "INSERT INTO review (id, date, comment) VALUES (?,?,?)";
		int result = jt.update(sql, id, date, comment);

		return result;
	}

	
	/*
	 * TEST CODE
	 */
	// TEST: table 전체 불러오기, dbTableSelect.jsp와 연동
	public List<Map<String, ?>> selectAllTables() {

		return jt.query("select * from `table`", (rs, rowNum) -> {
			Map<String, Object> mss = new HashMap<>();
			mss.put("oid", rs.getInt(1));
			mss.put("number", rs.getInt(2));
			mss.put("places", rs.getInt(3));
			return mss;
		});
	}
}
