package booksys;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

import com.dao.BooksysDAO;

class MainControllerTest {
	BooksysDAO booksysDAO;
	
	@Test
	void testWrongInputRegister1() {
		try {
			final String id = "test";
			final String passowrd = "test";
			final String name = "홍길동";
			final String phoneNumber = "12312341234123";
			booksysDAO.register(id, passowrd, name, phoneNumber);
			
		} catch (Exception success) {
			System.out.println("휴대폰 번호는 11자리까지만 입력 가능합니다.");
		}
	}
	
	@Test
	void testWrongInputRegister2() {
		try {
			final String id = "test";
			final String passowrd = "testtestestestsettsetsetsetsetestsetes";
			final String name = "홍길동";
			final String phoneNumber = "12312341234";
			booksysDAO.register(id, passowrd, name, phoneNumber);
			
		} catch (Exception success) {
			System.out.println("비밀번호는 최대 20자리까지만 입력 가능합니다.");
		}
	}
	
	@Test
	void testWrongInputLogin1() {
		try {
			final String id = "";
			final String passowrd = "test";
			booksysDAO.login(id, passowrd);
			
		} catch (Exception success) {
			System.out.println("아이디가 입력되지 않았습니다.");
		}
	}
	
	@Test
	void testWrongInputLogin2() {
		try {
			final String id = "test";
			final String passowrd = "";
			booksysDAO.login(id, passowrd);
			
		} catch (Exception success) {
			System.out.println("비밀번호가 틀렸거나 입력되지 않았습니다.");
		}
	}
	
	@Test
	void testWrongInputReservation1() {
		try {
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			String dateStr = dtf.format(LocalDateTime.now());
			final Date date = Date.valueOf(dateStr);
	        
			final int covers = 4;
			final String passowrd = "";
			final Time time = new Time(23, 0, 0);
			final int temptable_id = 99999999;
			final int user_oid = 1;
			
			booksysDAO.addReservation(covers, date, time, covers, covers);
			
		} catch (Exception success) {
			System.out.println("테이블 번호가 올바르지 않습니다.");
		}
	}
	
	@Test
	void testWrongInputReservation2() {
		try {
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			String dateStr = dtf.format(LocalDateTime.now());
			final Date date = Date.valueOf(dateStr);
	        
			final int covers = 4;
			final String passowrd = "";
			final Time time = new Time(23, 0, 0);
			final int temptable_id = 1;
			final int user_oid = 999999999;
			
			booksysDAO.addReservation(covers, date, time, covers, covers);
			
		} catch (Exception success) {
			System.out.println("예약자의 oid가 올바르지 않습니다.");
		}
	}
	
	@Test
	void testWrongInputReservation3() {
		try {
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			String dateStr = dtf.format(LocalDateTime.now());
			final Date date = Date.valueOf(dateStr);
	        
			final int covers = 0;
			final String passowrd = "";
			final Time time = new Time(18, 0, 0);
			final int temptable_id = 1;
			final int user_oid = 1;
			
			booksysDAO.addReservation(covers, date, time, covers, covers);
			
		} catch (Exception success) {
			System.out.println("예약인원은 최소 1명 입니다.");
		}
	}
	
	@Test
	void testWrongInputReservation4() {
		try {
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			String dateStr = dtf.format(LocalDateTime.now());
			final Date date = Date.valueOf(dateStr);
	        
			final int covers = 4;
			final String passowrd = "";
			final Time time = new Time(23, 0, 0);
			final int temptable_id = 1;
			final int user_oid = 1;
			
			booksysDAO.addReservation(covers, date, time, covers, covers);
			booksysDAO.addReservation(covers, date, time, covers, covers);
			
		} catch (Exception success) {
			System.out.println("이미 같은시간, 테이블에 예약이 존재합니다.");
		}
	}
	
	@Test
	void testWrongInputReviewCheckSession1() {
		try {
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			String dateStr = dtf.format(LocalDateTime.now());
			final Date date = Date.valueOf(dateStr);
			
			final String user_id = "";
			final String comment = "잘 먹고 갑니다.";
			
			booksysDAO.addReview(user_id, date, comment);
			
		} catch (Exception success) {
			System.out.println("로그인이 필요합니다.");
		}
	}
	
	@Test
	void testWrongInputReviewCheckSession2() {
		try {
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			String dateStr = dtf.format(LocalDateTime.now());
			final Date date = Date.valueOf(dateStr);
			
			final String user_id = "guest12";
			final String comment = "";
			
			booksysDAO.addReview(user_id, date, comment);
			
		} catch (Exception success) {
			System.out.println("리뷰 내용이 없습니다.");
		}
	}

}
