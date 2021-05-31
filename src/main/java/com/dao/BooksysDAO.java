package com.dao;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.Collection;
import java.util.ArrayList;
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

	/**
	 * 로그인을 체크하는 메소드
	 * @param id
	 * @param password
	 * @return 성공(User), 실패(null)
	 */
	public User login(String id, String password) {
		// queryForObject를 사용하면 결과가 0개일때 exception 발생
		return DataAccessUtils
				.singleResult(jt.query("select id, name, password FROM user where id = ? and password = ?",
						(rs, rowNum) -> new User(rs.getString("id"), rs.getString("name")), id, password));
	}

	/**
	 * 회원가입
	 * @param id
	 * @param password
	 * @param name
	 * @param phoneNumber
	 * @return 1(성공), 0(실패)
	 */
	public int register(String id, String password, String name, String phoneNumber) {
		// insert문은 update method를 사용한다.
		String sql = "INSERT INTO user (id, password, name, phoneNumber) VALUES (?,?,?,?)";
		int result = jt.update(sql, id, password, name, phoneNumber);

		return result;
	}

	/**
	 * 새로운 예약을 추가하는 메소드
	 * @param covers 
	 * @param date
	 * @param time
	 * @param table_id
	 * @param customer_id
	 * @return 성공(1), 실패(0)
	 */
	public int addReservation(int covers, Date date, Time time, int table_id, int customer_id) {
		String sql = "INSERT INTO reservation (covers, date, time, table_id, customer_id) VALUES (?,?,?,?,?)";
		int result = jt.update(sql, covers, date, time, table_id, customer_id);

		return result;
	}

	/**
	 * 예약 전체 조회
	 * covers, date, time, table_id, customer_id, arrivalTime
	 * @return 전체 예약 리스트(hashMap)
	 */
	public List<String> reservationStatus(Date date) {
		return jt.query("select * from reservation where date=?", (rs, rowNum) -> {
			
			return rs.getDate(3).toString() + "/" + rs.getTime(4).toString().substring(0,2) + "/" + rs.getInt(5);
		}, date);
	}
  
	/**
	 * 날짜에 따른 예약 조회
	 * @param date
	 * @return 모든 예약을 hashMap형태로 포함한 list
	 */
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

	/** 
	 * 예약을 추가하는 메소드
	 * @return 성공(1) 실패(0)
	 */
	public int addReservation(int covers, String date, String time, int table_id, int customer_id) {
		String sql = "INSERT INTO reservation (covers, date, time, table_id, customer_id) VALUES (?,?,?,?,?)";
		int result = jt.update(sql, covers, date, time, table_id, customer_id);
		
		return result;		
	}

	/**
	 *  해당 날짜 해당 테이블에 중복된 예약이 있는지 확인
	 * @param date
	 * @param time
	 * @param table_id
	 * @return 성공(true), 실패(false)
	 */
	public boolean nowTableReservationAvailable(Date date,Time time,int table_id)
	{	
		//해당 날짜 시간 테이블에 맞는 테이블을 찾아 있으면 false없으면 true (예약가능 반환한다.)
		String SQL = "SELECT count(*) from reservation WHERE date=" + "'" + date + "'"
				+ " AND time=" + "'" + time + "'" + " AND table_id=" + "'" + table_id + "'";
		int rowCount = jt.queryForObject(SQL, Integer.class);
		if(rowCount==0)
		{
			return true;
		}
		return false;
	}

	/**
	 *  유저 id로 oid찾기
	 * @param user_id
	 * @return 성공(User객체) 없으면 null
	 */
	public List<Map<String,Integer>> findUserOiduseUser_id(String user_id) throws SQLException{	
		//해당 날짜 시간 테이블에 맞는 테이블을 찾아 있으면 false없으면 true (예약가능 반환한다.)
		String SQL = "SELECT oid from user WHERE id=" + "'" + user_id + "'";
		return jt.query(SQL, (rs, rowNum) -> {
				Map<String, Integer> mss = new HashMap<>();
				mss.put("oid", rs.getInt(1));
				return mss;
		});
	}
	
	
	/**
	 * 전체 리뷰를 조회하는 메소드
	 * review_num, user_id, date, comment
	 * @return 위의값을 key로 하는 해시맵 형태를 가진 List
	 */
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

	/**
	 * 리뷰 추가
	 * @param id
	 * @param date
	 * @param comment
	 * @return 성공(1) 실패(0)
	 */
	public int addReview(String id, Date date, String comment) {
		String sql = "INSERT INTO review (id, date, comment) VALUES (?,?,?)";
		int result = jt.update(sql, id, date, comment);

		return result;
	}

	/**
	 * 모든 테이블 정보를 불러오는 함수.
	 * dbTestTable.jsp와 연동
	 * @return
	 */
	public List<Map<String, ?>> selectAllTables() {

		return jt.query("select * from `table`", (rs, rowNum) -> {
			Map<String, Object> mss = new HashMap<>();
			mss.put("oid", rs.getInt(1));
			mss.put("number", rs.getInt(2));
			mss.put("places", rs.getInt(3));
			return mss;
		});
	}
	
	/**
	 * DB에 등록된 Table의 개수를 돌려주는 메소드
	 * @return
	 */
	public int selectNumOfTables() {
		String sql = "SELECT COUNT(number) FROM `TABLE`";

		int numOfTables = jt.queryForObject(sql, Integer.class);
		
		return numOfTables;
	}
}
