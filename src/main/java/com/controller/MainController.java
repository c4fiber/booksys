package com.controller;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.domain.Comment;
import com.domain.Customer;
import com.domain.Table;
import com.domain.User;
import com.storage.Database;

@SpringBootApplication
//@RestController
@Controller
public class MainController {

	public static void main(String[] args) {
		SpringApplication.run(MainController.class, args);
		Database.getInstance();
	}

	// 기본 페이지
	@RequestMapping("/")
	public String index(HttpSession session) {
		return "index";
	}
  
	// 회원가입
	@RequestMapping("/register")
	public String register() {
		return "register";
	}

	// 회원가입 로직 처리
	@RequestMapping("/register.do")
	public String register_do(@RequestParam("id") String id, @RequestParam("password") String password,
			@RequestParam("name") String name, @RequestParam("phoneNumber") String phoneNumber) {

		String result = "done";
		try {
			Statement stmt = Database.getConnection().createStatement();
			int updateCount = stmt.executeUpdate("INSERT INTO user (id, password, name, phoneNumber)" + "VALUES ('" + id
					+ "', '" + password + "', '" + name + "', '" + phoneNumber + "')");
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			result = "fail";
		}
  
		// TODO
		/*
		 * 정상적으로 작동하는지 확인하기 위해 return 값을 string 고정하였다. 차후 수정필요함. 아래는 예시 코드
		 * http://localhost:8080/register.do?id=test&password=1234&name=bb&phoneNumber=
		 * 01012341234
		 */
		return "index";
	}

	// 로그인
	@RequestMapping("/login")
	public String login() {
		return "login";
	}

	@RequestMapping("/login.do")
	public String login(HttpServletRequest request, @RequestParam("id") String id,
			@RequestParam("password") String password, Model model) {
		// 1. id키가 기본키이므로 id키로 Select문을 사용해 User객체를 받아옴
		User findUser = getUser("SELECT * FROM user WHERE id = '" + id + "'");
		// 2. 없으면 false리턴
		if (findUser == null)
			return "index";
		else {
			// 3.id와 패스워드 같으면
			if (id.equals(findUser.getId()) && password.equals(findUser.getPassword())) {
				// 트루

				HttpSession session = request.getSession();
				session.setAttribute("name", findUser.getName());
				model.addAttribute("id", id);
			}
		}
		/*
		 * 이줄은 필수가 아닙니다. 읽지 않으셔도 됩니다. /AND password = '" + password + "'"
		 */
		/*
		 * &&name.equals(findUser.getName())&&phoneNumber.equals(findUser.getPhoneNumber
		 * ()) 매개변수와 User객체 비교하는데 쓸 수 있는 보조문 (필수아님)
		 */

		return "index";
	}

	// 타임테이블 / 예약
	@RequestMapping("/timeTable")
	public String timeTable() {

		return "timeTable";
	}

	// TODO DAO 객체 만들어서 처리
	/*
	 * //column oid id password name phoneNumber => user 0 1 2 3 각 User의 column 내용을
	 * 데이터베이스에서 가져와서 User 객체를 돌려준다.
	 */
	private User getUser(String sql) {
		User c = null;
		try {
			Statement stmt = Database.getInstance().getConnection().createStatement();
			ResultSet rset = stmt.executeQuery(sql);
			while (rset.next()) {
				int oid = rset.getInt(1);
				String id = rset.getString(3);
				String password = rset.getString(4);
				String name = rset.getString(5);
				String phoneNumber = rset.getString(6);
				c = new User(oid, id, password, name, phoneNumber);
			}
			rset.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return c;
	}

	/* 예약 파트 */
	@RequestMapping("/reservation")
	public String reservation() {
		return "reservation";
	}

	@RequestMapping("/checkReservation")
	public String reservation(@RequestParam int tableNumber, @RequestParam float time, Model model) {
		// 날짜, 시간 기준으로 예약내역 조회
		try {
			Statement stmt = Database.getConnection().createStatement();
			ResultSet checkSet = stmt.executeQuery("SELECT oid from reservation where");
		} catch (Exception e) {

		}

		return "reservation";
	}

	// 예약하기
	@RequestMapping("/reservation.do")
	public String reservation(@RequestParam(value = "customer_id") int customer_id,
			@RequestParam(value = "table_id") String table_id, @RequestParam(value = "time") Time time,
			@RequestParam(value = "date") String date, @RequestParam(value = "covers") int covers,
			@RequestParam(value = "oid") int oid, Model model) {
		String result = "done";
		try {
			Statement stmt = Database.getConnection().createStatement();

			// 같은 날인지 확인
			ResultSet checkSet = stmt.executeQuery("SELECT oid from reservation WHERE date=" + "'" + date + "'"
					+ "AND time=" + "'" + time + "'" + "AND table_id=" + "'" + table_id + "'");
			while (checkSet.next()) {
				if (checkSet.getInt(1) > 0) {
					model.addAttribute("status", 0);
					return "timeTable";
				}
			}
			checkSet.close();
			// 중복된 예약 없을 시 반환
			int updateCount = stmt.executeUpdate(
					"INSERT INTO reservation ( customer_id, table_id, time,date,covers,oid)" + "VALUES ('" + customer_id
							+ "','" + table_id + "','" + time + "', '" + date + "', '" + covers + "', '" + oid + "')");
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			result = "fail";
		}
    
    
    
		/*
		 * public Reservation(int c, Date d, Time t, Table tab, Customer cust, Time arr)
		 * arrivalTime은 아직 없으므로 NULL 고려해야할 점 1.table_id 받아 왔을 때 이미 예약 내에 같은 table_id가
		 * 있으면 거부해 주는 작업 필요할 것 예상 (Select문) // 초안 구현 완료 2.user_id로 결정 4. PM님 시도대로 똑같은 코드 제작
		 * 
		 * //TODO Reservation 처리 /* 정상적으로 작동하는지 확인하기 위해 return 값을 string 고정하였다. 차후
		 * 수정필요함. 아래는 예시 코드
		 * http://localhost:8080/reservation.do?customer_id=1234&table_id=01&time=10:00:
		 * 00&date=1971-01-21&covers=5&oid=12
		 * 
		 * customer -> 후에 user_id로 변경될 예정
		 */
		return "timeTable";
	}

	/*
	 * DB로 부터 리뷰를 읽어 와서 VECTOR 객체로 반환합니다. 개수 COUNT개 매개변수로 넣어줄 것(number) 게시판 1번
	 * 페이지..2번 페이지 ..3번.. 예 number에 1 넣으면 최신 데이터 0~10번째 리뷰를 읽어옵니다.
	 * 
	 * COUNT 페이지 당 몇 개씩 리뷰를 올릴 건지 조절 가능
	 */
	final int COUNT = 10;

	@RequestMapping("/commentRead.do")
	Vector<Comment> getComment(@RequestParam(value = "number") int number) {
		// 전부 겹쳐있는 commentList
		Vector<Comment> commentList = new Vector<>();
		try {
			Statement stmt = Database.getConnection().createStatement();
			stmt = Database.getConnection().createStatement();
			// 가장 최신 부터 limit " +((페이지번호 1...2..3)*10번째부터)+", "+COUNT(10개씩));
			ResultSet checkSet = stmt.executeQuery(
					"SELECT * from comment order by oid DESC limit " + ((number - 1) * COUNT) + ", " + COUNT);
			while (checkSet.next()) {
				Comment temp = new Comment(checkSet.getInt(1), 
						checkSet.getString(2), 
						checkSet.getString(3),
						checkSet.getString(4));
				commentList.add(temp);
			}
			checkSet.close();
			stmt.close();

			return commentList;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	/*
	 * comment를 DB로 넘겨줍니다.
	 */
	@RequestMapping("/comment.do")
	public String putComment(@RequestParam(value = "id") int id, @RequestParam(value = "comment") String comment,
			@RequestParam(value = "date") String date, @RequestParam(value = "oid") int oid) {
		String result = "done";
		try {
			Statement stmt = Database.getConnection().createStatement();
			int updateCount = stmt.executeUpdate("INSERT INTO comment (id, date, comment)" + "VALUES ('" + id + "', '"
					+ date + "', '" + comment + "')");
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			result = "fail";
		}
		return "index";
	}

}
