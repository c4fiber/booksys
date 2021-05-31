package com.controller;

import java.sql.*;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
@ComponentScan(basePackages = { "com" })

@Controller
public class MainController {
	@Autowired
	BooksysDAO booksysDAO;
	List<Map<String, ?>> cacheR = new ArrayList<Map<String, ?>>();

	/*
	 * session확보용 객체
	 */
	User user = new User("", "anonymous");

	/**
	 * 식당 운영시간 (default = 16 ~ 24)
	 */
	final int startTime = 16;
	final int endTime = 24;

	public static void main(String[] args) {
		SpringApplication.run(MainController.class, args);
	}

	final static Date today() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String dateStr = dtf.format(LocalDateTime.now());
		Date date = Date.valueOf(dateStr);

		return date;
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

	/**
	 * 회원가입
	 * 
	 * @param id
	 * @param password
	 * @param name
	 * @param phoneNumber
	 * @param model
	 * @return
	 */
	@RequestMapping("/register.do")
	public String register_do(@RequestParam("id") String id, @RequestParam("password") String password,
			@RequestParam("name") String name, @RequestParam("phoneNumber") String phoneNumber, Model model) {

		model.addAttribute("result", booksysDAO.register(id, password, name, phoneNumber));

		return "redirect:/";
	}

	/*
	 * 로그인 & 로그아웃
	 */
	@RequestMapping("/login")
	public String login() {
		return "login";
	}

	@RequestMapping("/logout")
	protected String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}

	/**
	 * 로그인 체크
	 * 
	 * @param id
	 * @param password
	 * @param model
	 * @return
	 */
	@RequestMapping("/login.do")
	public String login(@RequestParam(value = "id", defaultValue = "") String id,
			@RequestParam(value = "password", defaultValue = "") String password, Model model) {

		System.out.println("id&password" + id + password);
		User result = booksysDAO.login(id, password);
		if (result != null) {
			user.setId(result.getId());
			user.setName(result.getName());

			return "redirect:/";
		} else {
			model.addAttribute("failed", "yes");

			return "/login";
		}
	}

	/*
	 * 타임 테이블 페이지
	 */

	public String timeTable(@RequestParam(value = "date", required = false) Date date, Model model) {
		model.addAttribute("id", user.getId());
		System.out.println(user.getId());
  
    // 입력 날짜가 없으면 오늘날짜로 입력(처음 timetable 열람 시)
		if (date == null) {
			System.out.println("debug: ok");
			date = today();
		}


		// table 개수, 입력 날짜, 영업 시작시간, 영업 종료시간
		int numOfTables = booksysDAO.selectNumOfTables();
		model.addAttribute("numOfTables", numOfTables);
		model.addAttribute("date", date.toString());
		model.addAttribute("startTime", this.startTime);
		model.addAttribute("endTime", this.endTime);
    
		return "timeTable";
	}

	/*
	 * 예약 페이지
	 */
	@RequestMapping("/reservation")
	public String reservation() {
		return "reservation";
	}

	@RequestMapping(value="/check.do",method = RequestMethod.GET)
	public synchronized String goHome()
	{ 
		return "index";	

    	/**
	 * 예약 가능한지 불가능한지 체크
	 * 
	 * @param model
	 * @param date
	 * @return
	 */
	@RequestMapping("/reservation.do")
	public synchronized String reservation(@RequestParam(value = "customer_id") int customer_id,
			@RequestParam(value = "table_id") int table_id, @RequestParam(value = "time") Time time,
			@RequestParam(value = "date") Date date, @RequestParam(value = "covers") int covers) {
		boolean bookAvailable = booksysDAO.nowTableReservationAvailable(date, time, table_id);
		if (bookAvailable) {
			booksysDAO.addReservation(covers, date, time, table_id, customer_id);
		}

		return "redirect:/timetable";
	}

	@RequestMapping(value="/check.do",method = RequestMethod.POST)
	public synchronized String check(Model model,Date date)
	{ 
		int tableNum = booksysDAO.selectNumOfTables(); // 전체 테이블 개수
  
		model.addAttribute("id", user.getId());
		model.addAttribute("superDate",date);
  
		//((int)((i*100)+16+j)값의 경우 예를 들어 2번테이블 18시일경우 218이 key가 됩니다.
		// 시간은  00~ 18 20 22 24~이고 테이블은 무한정 늘어날 수 있기 때문에 로직을 그러하게 작성하였습니다.
		for (int i = 1; i <= tableNum; i++) {
			for (int j = startTime; j < endTime; j = j + 2) {
				Time time = new Time((int) (startTime + j), 0, 0);
				model.addAttribute((i * 100 + j) + "", booksysDAO.nowTableReservationAvailable(date, time, i) + "");
			}
		}

		// List형태로 예약상황 조회 다루기
		List<String> rStatus = booksysDAO.reservationStatus(date);
		for (String bean : rStatus) {
			System.out.println(bean); // TODO string이여야 하는데 int로 받아짐. 수정필요

		System.out.println(rStatus);

		return "timeTable";
	}

	/**
	 * 리뷰 페이지 로딩
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/review")
	public String review(Model model) {
		final int COUNT = 10; // 페이지 당 리뷰 개수

		System.out.println(booksysDAO.selectAllReviews());
		model.addAttribute("reviews", booksysDAO.selectAllReviews());
		model.addAttribute("user_id", user.getId());

		return "review";
	}

	/**
	 * review 추가. comment를 DB로 넘겨줍니다.
	 * 
	 * @param user_id
	 * @param comment
	 * @param model
	 */
	@RequestMapping("/review.do")
	public String putComment(@RequestParam(value = "user_id", defaultValue = "") String user_id,
			@RequestParam(value = "review_content") String comment, Model model) throws SQLException {

		if (user_id.equals("")) {
			model.addAttribute("result", "id fail");

		} else {

			// string -> date 형변환 -> 데이터 타입 맞추기
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			String dateStr = dtf.format(LocalDateTime.now());
			Date date = Date.valueOf(dateStr);

			if (booksysDAO.addReview(user_id, date, comment) != 1) {
				model.addAttribute("result", "sql fail");
			}
		}

		return "redirect:/review";
	}

	/**
	 * table 모두 출력
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/select.do")
	public String selectAllTable(Model model) {
		model.addAttribute("results", booksysDAO.selectAllTables());

		return "dbTableSelect";
	}
	
	
	
	/**
	 * 예약 추가
	 * @param table_id
	 * @param time
	 * @param date
	 * @param covers
	 * @return DB에 입력 
	 */
		@RequestMapping("/reservation.do")
		public String reservation1(
				@RequestParam(value = "table_id") String[] table_id, @RequestParam(value = "time") String[] time,
				@RequestParam(value = "date") String[] date, @RequestParam(value = "covers") String[] covers,Model model,@RequestParam(value = "id") String[] id) throws SQLException {
			int user_oid=-500;
			String resultMessage = "";
			try {
				List<Map<String,Integer>> tempUserList = booksysDAO.findUserOiduseUser_id(id[0]);
				Map<String,Integer> tempMap = tempUserList.get(0);
				user_oid = tempMap.get("oid");
			}
			catch(ArrayIndexOutOfBoundsException e)
			{
				resultMessage = "잘못된 접근입니다.";
				model.addAttribute("Message", resultMessage);
				return "reservationResult";
			}
			
			
			/*
			 * 타임테이블에 입력한 모든값 넣기
			 * */
			for(int i=0;i<date.length;i++)
			{
				/*
				 * 값이 빈거 체크
				 * */
				if(covers[i].equals("")||time[i].equals("")||date[i].equals("")||table_id[i].equals(""))
				{
					resultMessage = resultMessage+(i+1)+"번째 줄 입력값에 빈칸이 있는 항목은 적용되지 않았습니다.<br>";
					continue;
				}
				Time tempTime = Time.valueOf(time[i]);
				Date tempDate = Date.valueOf(date[i]);
				int tempCovers = -500;
				int temptable_id = -500;
				/*
				 * 굳이 없어도 되기는 하는데 숫자값 입력 체크
				 * */
				try {
					tempCovers = Integer.valueOf(covers[i]);
					temptable_id = Integer.valueOf(table_id[i]);
				}
				catch(NumberFormatException e)
				{
					resultMessage = resultMessage+(i+1)+"번째 줄 테이블 입력값이 잘못 되었습니다.<br>";
				}
				/*
				 * 성공과 실패 구분
				 * */
				int success = booksysDAO.addReservation(tempCovers, tempDate, tempTime, temptable_id, user_oid);
				if(success==1)
				{
					resultMessage += date[i]+"일자 " +time[i]+"분 "+table_id[i]+"번 테이블 예약되었습니다.<br>";
				}
				else
				{
					resultMessage += date[i]+"일자 " +time[i]+"분 "+table_id[i]+" 번 테이블 예약하였습니다.<br>";
				}
			}
			
			/*
			 * resultPage로 메시지 넘기고 종료
			 */
			model.addAttribute("Message", resultMessage);
			return "reservationResult";
		}
	

	/**
	 * 예약 추가
	 * 
	 * @param table_id
	 * @param time
	 * @param date
	 * @param covers
	 * @return DB에 입력
	 */
	@RequestMapping("/reservation.do")
	public String reservation1(@RequestParam(value = "table_id") String[] table_id,
			@RequestParam(value = "time") String[] time, @RequestParam(value = "date") String[] date,
			@RequestParam(value = "covers") String[] covers, Model model, @RequestParam(value = "id") String[] id)
			throws SQLException {

		String resultMessage = null;

		List<Map<String, Integer>> tempUserList = booksysDAO.findUserOiduseUser_id(id[0]);
		Map<String, Integer> tempMap = tempUserList.get(0);
		int user_oid = tempMap.get("oid");

		/*
		 * 타임테이블에 입력한 모든값 넣기
		 */
		for (int i = 0; i < date.length; i++) {
			/*
			 * 값이 빈거 체크
			 */
			if (covers[i].equals("") || time[i].equals("") || date[i].equals("") || table_id[i].equals("")) {
				resultMessage = resultMessage + i + "번째 줄 입력값에 빈칸이 있는 항목은 적용되지 않았습니다.\n";
				continue;
			}
			Time tempTime = Time.valueOf(time[i]);
			Date tempDate = Date.valueOf(date[i]);
			int tempCovers = -500;
			int temptable_id = -500;
			/*
			 * 굳이 없어도 되기는 하는데 숫자값 입력 체크
			 */
			try {
				tempCovers = Integer.valueOf(covers[i]);
				temptable_id = Integer.valueOf(table_id[i]);
			} catch (NumberFormatException e) {
				resultMessage = resultMessage + i + "번째 줄 테이블 입력값이 잘못 되었습니다.\n";
			}
			/*
			 * 성공과 실패 구분
			 */
			int success = booksysDAO.addReservation(tempCovers, tempDate, tempTime, temptable_id, user_oid);
			if (success == 1) {
				resultMessage += date[i] + "일자" + time[i] + "분" + table_id[i] + "번 테이블 예약되었습니다.\n";
			} else {
				resultMessage += date[i] + "일자" + time[i] + "분" + table_id[i] + "번 테이블 예약하였습니다.\n";
			}
		}

		/*
		 * resultPage로 메시지 넘기고 종료
		 */
		model.addAttribute("Message", resultMessage);
		return "reservationResult";
	}

}
