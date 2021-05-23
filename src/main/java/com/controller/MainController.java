package com.controller;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dao.BooksysDAO;
import com.domain.Review;
import com.domain.Customer;
import com.domain.Table;
import com.domain.User;
import com.storage.Database;

@SpringBootApplication
@ComponentScan(basePackages= {"com"})

@Controller
public class MainController {
	@Autowired BooksysDAO booksysDAO;
	
	// session확보용 객체
	User user = new User("","anonymous"); 

	public static void main(String[] args) {
		SpringApplication.run(MainController.class, args);
		Database.getInstance();
	}

	/* 메인 페이지 */
	@RequestMapping("/")
	public String index(Model model) {
		model.addAttribute("id", user.getId());
		model.addAttribute("name", user.getName());
		return "index";
	}
  
	/* 회원가입 파트 */
	@RequestMapping("/register")
	public String register() {
		return "register";
	}

	
	@RequestMapping("/review")
	public String review(Model model) {
		
		// model.addAttributes(review_bean,null);
		
		return "review";
	}

	// 회원가입
	@RequestMapping("/register.do")
	public String register_do(
			@RequestParam("id") String id,
			@RequestParam("password") String password,
			@RequestParam("name") String name,
			@RequestParam("phoneNumber") String phoneNumber,
			Model model) {

		model.addAttribute("result", booksysDAO.register(id,password,name,phoneNumber));
		
		return "redirect:/";
	}

	// 로그인
	@RequestMapping("/login")
	public String login() {
		return "login";
	}
	
	@RequestMapping("/logout")
	protected String logout(HttpSession session)
	{
	       	session.invalidate();
	        return "redirect:/";
	}
	
	@RequestMapping("/login.do")
	public String login(
			@RequestParam("id") String id,
			@RequestParam("password") String password, 
			Model model) {
		
		User result = booksysDAO.login(id, password);
		
		if (result != null) {
			user.setId(result.getId());
			user.setName(result.getName());
		}
		
		return "redirect:/";
	}

	// 타임테이블 / 예약
	@RequestMapping("/timeTable")
	public String timeTable() {

		return "timeTable";
	}


	/* 예약 파트 */
	@RequestMapping("/reservation")
	public String reservation() {
		return "reservation";
	}

	// 예약되어있는지 점검
	@RequestMapping("/checkReservation")
	public String reservation(@RequestParam int tableNumber, @RequestParam float time, Model model) {
		model.addAttribute("reservations", booksysDAO.selectAllReservations());

		return "reservation";
	}

	// 예약하기
	@RequestMapping("/reservation.do")
	public synchronized String reservation(
			@RequestParam(value = "customer_id") int customer_id,
			@RequestParam(value = "table_id") String table_id,
			@RequestParam(value = "time") Time time,
			@RequestParam(value = "date") String date,
			@RequestParam(value = "covers") int covers,
			@RequestParam(value = "oid") int oid,
			Model model){
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

	/* 리뷰 파트 */
	@RequestMapping("/commentRead.do")
	Vector<Review> getComment(@RequestParam(value = "number") int number) {
		// 전부 겹쳐있는 commentList
		Vector<Review> commentList = new Vector<>();
		try {
			Statement stmt = Database.getConnection().createStatement();
			stmt = Database.getConnection().createStatement();
			// 가장 최신 부터 limit " +((페이지번호 1...2..3)*10번째부터)+", "+COUNT(10개씩));
			ResultSet checkSet = stmt.executeQuery(
					"SELECT * from comment order by oid DESC limit " + ((number - 1) * COUNT) + ", " + COUNT);
			while (checkSet.next()) {
				Review temp = new Review(checkSet.getInt(1), 
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
	 * review 작성. comment를 DB로 넘겨줍니다.
	 */
	@RequestMapping("/review.do")
	public String putComment(
			@RequestParam(value = "user_id") String id, 
			@RequestParam(value = "review_content") String comment,
			@RequestParam(value = "date", defaultValue="") String date,
			Model model) {

		if (booksysDAO.addReview(id, comment, date) != 1) {
			model.addAttribute("result", "fail");
		}
		return "review";
	}
	
	// table 모두 출력
	@RequestMapping("/select.do")
	public String selectAllTable(Model model) {
		model.addAttribute("results",booksysDAO.selectAll());
		
		return "dbTableSelect";
	}

}
