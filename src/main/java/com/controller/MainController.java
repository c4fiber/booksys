package com.controller;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

	@RequestMapping("/timeTable")
	public String timeTable() {
		return "timeTable";
	}
	
	@RequestMapping("/login")
	public String login() {
		return "login";
	}
	
	@RequestMapping("/register")
	public String register() {
		return "register";
	}
	
	@RequestMapping("/register.do")
	public String register_do() {
		
		
		return "timeTable";
	}
	
	@GetMapping("/hello")
	public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
		return String.format("Hello %s!", name);
	}

	@GetMapping("/test")
	public String test1(@RequestParam(value = "test", defaultValue = "TESTING!") String name) {
		return String.format("TESTING NOW!!!! =  %s", name);
	}

	@GetMapping("/error")
	public String error() {
		return String.format("ERROR OCCURED!!!!");
	}

	@GetMapping("/login.do")
	public boolean login(String id, String password, String name, String phoneNumber) {
		// 1. id키가 기본키이므로 id키로 Select문을 사용해 User객체를 받아옴
		User findUser = getUser("SELECT * FROM user WHERE id = '" + id + "'");
		// 2. 없으면 false리턴
		if (findUser == null)
			return false;
		else {
			// 3.id와 패스워드 같으면
			if (id.equals(findUser.getId()) && password.equals(findUser.getPassword())) {
				// 트루
				return true;
			} else
				return false;
		}
		/*
		 * 이줄은 필수가 아닙니다. 읽지 않으셔도 됩니다. /AND password = '" + password + "'"
		 */
		/*
		 * &&name.equals(findUser.getName())&&phoneNumber.equals(findUser.getPhoneNumber
		 * ()) 매개변수와 User객체 비교하는데 쓸 수 있는 보조문 (필수아님)
		 */
	}

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
				String id = rset.getString(2);
				String password = rset.getString(3);
				String name = rset.getString(4);
				String phoneNumber = rset.getString(5);
				c = new User(oid, id, password, name, phoneNumber);
			}
			rset.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return c;
	}

	@GetMapping("/reservation.do")
	public String reservation(
			@RequestParam(value = "customer_id") int customer_id,
			@RequestParam(value = "table_id") String table_id,
			@RequestParam(value = "time") Time time,
			@RequestParam(value = "date") String date,
			@RequestParam(value = "covers") int covers,
			@RequestParam(value = "oid") int oid){
		String result = "done";
		try {
			Statement stmt
			  = Database.getConnection().createStatement();
	
			//같은 날인지 확인
			ResultSet checkSet = stmt.executeQuery("SELECT oid from reservation WHERE date="+"'" +date+ "'"+"AND time="+"'" +time+ "'"+"AND table_id="+"'" +table_id+ "'");
			while(checkSet.next())
			{
				if(checkSet.getInt(1)>0)
				{
					return "이미 배정된 테이블";
				}
			}
			checkSet.close();
			//중복된 예약 없을 시 반환
			int updateCount = stmt.executeUpdate(
					"INSERT INTO reservation ( customer_id, table_id, time,date,covers,oid)" +
					       "VALUES ('" + customer_id + "','" + table_id + "','" + time + "', '" + date + "', '" + covers + "', '" + oid + "')");
			stmt.close() ;
		}catch (SQLException e) {
			e.printStackTrace() ;
			result = "fail";
		}
		/*
		 *  public Reservation(int c, Date d, Time t, Table tab, Customer cust, Time arr)
  			arrivalTime은 아직 없으므로 NULL
  			고려해야할 점 
  			1.table_id 받아 왔을 때 이미 예약 내에 같은 table_id가 있으면 거부해 주는 작업 필요할 것 예상 (Select문)
  			// 초안 구현 완료
  			2.user_id로 결정
  			4. PM님 시도대로 똑같은 코드 제작
  			  //TODO 
			  /* 정상적으로 작동하는지 확인하기 위해 return 값을 string 고정하였다. 차후 수정필요함.
		     * 아래는 예시 코드
		     * http://localhost:8080/reservation.do?customer_id=1234&table_id=01&time=10:00:00&date=1971-01-21&covers=5&oid=12
		     * 
		     * customer -> 후에 user_id로 변경될 예정 
		     */
		return result;
	}	
	
	@GetMapping("/register.do")
	public String register(@RequestParam(value = "id") String id, 
			@RequestParam(value = "password") String password,
			@RequestParam(value = "name") String name,
			@RequestParam(value = "phoneNumber") String phoneNumber) {
		try {
			Statement stmt
			  = Database.getConnection().createStatement() ;
			int updateCount = stmt.executeUpdate(
					"INSERT INTO user (id, password, name, phoneNumber)" +
					       "VALUES ('" + id + "', '" + password + "', '" + name + "', '" + phoneNumber + "')");
			stmt.close() ;
		}catch (SQLException e) {
			e.printStackTrace() ;
			return "register";
		}
		
		//TODO 
		/* 정상적으로 작동하는지 확인하기 위해 return 값을 string 고정하였다. 차후 수정필요함.
		 * 아래는 예시 코드
		 * http://localhost:8080/register.do?id=test&password=1234&name=bb&phoneNumber=01012341234
		 */
		return "timeTable";
	}	
}


// TODO
// add part