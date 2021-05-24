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
	@Autowired JdbcTemplate jt;

	// table 전체 불러오기, dbTableSelect.jsp와 연동
	public List<Map<String, ?>> selectAll() {
		
		return jt.query("select * from `table`", (rs, rowNum) -> {
			Map<String, Object> mss = new HashMap<>();
			mss.put("oid", rs.getInt(1));
			mss.put("number", rs.getInt(2));
			mss.put("places", rs.getInt(3));
			return mss;
		});
	}
	
	// 로그인: match하는 User Object(id, name 갱신) 반환. 없으면 null
	public User login(String id, String password){
		
		// queryForObject를 사용하면 결과가 0개일때 exception 발생
		return DataAccessUtils.singleResult(jt.query("select id, name, password FROM user where id = ? and password = ?", 
				(rs, rowNum) -> new User(rs.getString("id"),rs.getString("name")),
					id, password)
				);
	}
	
	// 회원가입 데이터 삽입: return 1(성공), 0(실패)
	public int register(String id, String password, String name, String phoneNumber) {
		// insert문은 update method를 사용한다.
		String sql = "INSERT INTO user (id, password, name, phoneNumber) VALUES (?,?,?,?)";
		int result = jt.update(sql, id, password, name, phoneNumber);
		
		return result;
	}
	//
	
	//date인자를 받아서 입력될 날짜에 해당하는 예약 리스트로 뽑아올수있게
	public List<Map<String,?>> takeAllReservationsUseDate(Date date){
		return jt.query("select * from reservation where date='"+date.toString()+"'", (rs,rowNum)->{
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
	
	//mss->리뷰를 가져올께요
	
	// 예약 조회
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
	
	//해당 날짜 해당 테이블에 중복된 예약이 있는지 확인
	public boolean nowTableReservationAvailable(Date date,Time time,int table_id)
	{
		jt.query("SELECT oid from reservation WHERE date=" + "'" + date + "'"
					+ "AND time=" + "'" + time + "'" + "AND table_id=" + "'" + table_id + "'",(rs,rowNum)->{
						Map<String, Object> mss = new HashMap<>();
						mss.put("customer_id", rs.getInt(6));
						if(mss.isEmpty()) 
							return true;
						return false;
						});
		return false;
	}
	
	// 예약 추가
		public void addReservation(int covers,Date date,Time time,int table_id,int customer_id) {
				String sql ="INSERT INTO reservation ( customer_id, table_id, time,date,covers)" + "VALUES ("+ customer_id
						+ "," + table_id + ",'" + time + "', '" + date + "',"  + covers+")";
				jt.update(sql);
		}
	
	// 리뷰 작성 
	public int addReview(String id, String comment, String date) {
		String sql = "INSERT INTO review (id, date, comment) VALUES (?,?,?)";
		int result = jt.update(sql, id, date, comment);
		
		return result;
	}
}
