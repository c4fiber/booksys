package com.controller;

import java.sql.*;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

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
	List<Map<String, ?>> cacheR = new ArrayList<Map<String, ?>>();
	
	// session확보용 객체
	User user = new User("","anonymous"); 

	public static void main(String[] args) {
		SpringApplication.run(MainController.class, args);
		Database.getInstance();
	}

	/* 
	 * 메인 페이지
	 */
	@RequestMapping("/")
	public String index(Model model) {
		model.addAttribute("id", user.getId());
		model.addAttribute("name", user.getName());
		return "index";
	}
  
	/* 
	 * 회원가입 파트 
	 */
	@RequestMapping("/register")
	public String register() {
		return "register";
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

	/*
	 *  로그인 & 로그아웃
	 */
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
			@RequestParam(value = "id", defaultValue = "") String id,
			@RequestParam(value = "password", defaultValue = "") String password, 
			Model model) {
		
		User result = booksysDAO.login(id, password);
		
		if (result != null) {
			user.setId(result.getId());
			user.setName(result.getName());
			
			return "redirect:/";
		}else {
			model.addAttribute("alert", "yes");
			
			return "login";
		}
	}

	/*
	 *  타임테이블
	 */
	@RequestMapping("/timeTable")
	public String timeTable() {

		return "timeTable";
	}

	/*
	 *  예약 파트 
	 */
	@RequestMapping("/reservation")
	public String reservation() {
		return "reservation";
	}

	// 모든 예약 받기 (TEST)
	@RequestMapping("/AllReservations.do")
	public String reservation(@RequestParam int tableNumber, @RequestParam float time, Model model) {
		model.addAttribute("reservations", booksysDAO.selectAllReservations());

		return "reservation";
	}

	// 예약하기
	@RequestMapping("/reservation.do")
	public synchronized String reservation(
			@RequestParam(value = "customer_id") int customer_id,
			@RequestParam(value = "table_id") int table_id,
			@RequestParam(value = "time") Time time,
			@RequestParam(value = "date") Date date,
			@RequestParam(value = "covers") int covers){
		boolean bookAvailable = booksysDAO.nowTableReservationAvailable(date, time, table_id);
		if(bookAvailable)
		{
			booksysDAO.addReservation(covers, date, time, table_id, customer_id);
		}

		return "timetable";

	}

	
	//예약 가능한지 불가능한지 테이블에 따라 판단해주는 메소드 
	//테이블의 개수는 i의 5를 변수로 만들면 될 것 같습니다.
	//시간대도 2시간 단위이기때문에 초기설정을 해주었고 만약 변경이 필요하다면 16을변수로 만들면 될 것 같습니다.
	@RequestMapping(value="/check.do",method = RequestMethod.POST)
	public synchronized String check(Model model,Date date)
	{ 
		//((int)((i*100)+16+j)값의 경우 예를 들어 2번테이블 18시일경우 218이 key가 됩니다.
		// 시간은  00~ 18 20 22 24~이고 테이블은 무한정 늘어날 수 있기 때문에 로직을 그러하게 작성하였습니다.
		for (int i = 1; i <= 5; i++) {
				for (float j = 0; j <= 6; j=j+2) {
					Time time =new Time((int) (16+j), 0, 0);					
					model.addAttribute((int)((i*100)+16+j)+"", booksysDAO.nowTableReservationAvailable(date, time, i)+"");
				}
		}
		return "timeTable";	
	}
	/*
	 * DB로 부터 리뷰를 읽어 와서 VECTOR 객체로 반환합니다. 개수 COUNT개 매개변수로 넣어줄 것(number) 게시판 1번
	 * 페이지..2번 페이지 ..3번.. 예 number에 1 넣으면 최신 데이터 0~10번째 리뷰를 읽어옵니다.
	 */
	
	

	/* 
	 * 리뷰 파트
	 */
	final int COUNT = 10; // 페이지 당 리뷰 개수
	
	@RequestMapping("/review")
	public String review(Model model) {
		System.out.println(booksysDAO.selectAllReviews());
		model.addAttribute("reviews", booksysDAO.selectAllReviews() );
		
		return "review";
	}

	// review 추가. comment를 DB로 넘겨줍니다.
	@RequestMapping("/review.do")
	public String putComment(
			@RequestParam(value = "user_id") String user_id, 
			@RequestParam(value = "review_content") String comment,
			@RequestParam(value = "date", defaultValue="") Date date,
			Model model) {

		if (booksysDAO.addReview(user_id, date, comment) != 1) {
			model.addAttribute("result", "fail");
		}
		return "review";
	}
	
	// table 모두 출력
	@RequestMapping("/select.do")
	public String selectAllTable(Model model) {
		model.addAttribute("results",booksysDAO.selectAllTables());
		model.addAttribute("reservations", booksysDAO.selectAllReservations());
		
		return "dbTableSelect";
	}

}
