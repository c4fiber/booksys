package com.controller;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.domain.Customer;
import com.domain.Table;
import com.domain.User;
import com.storage.Database;

@SpringBootApplication
@RestController
public class MainController {

	public static void main(String[] args) {
		SpringApplication.run(MainController.class, args);
		Database.getInstance();
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
				int oid = rset.getInt(0);
				String id = rset.getString(1);
				String password = rset.getString(2);
				String name = rset.getString(3);
				String phoneNumber = rset.getString(4);
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
			@RequestParam(value = "time") String time,
			@RequestParam(value = "date") String date,
			@RequestParam(value = "covers") int covers,
			@RequestParam(value = "oid") int oid){
		String result = "done";
		try {
			Statement stmt
			  = Database.getConnection().createStatement() ;
			//이미 테이블 예약이 되어있는 시간안에 있으면 제거 필요 (제작중) 
			/*
			 * 	ResultSet rset = stmt.executeQuery("SELECT table_id from reservation WHERE arrivalTime=null ");
				while (rset.next()) {
				
				}
			 */
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
  			2.customer_id도 마찬가지로  id가 존재하는 지 확인해야하는데 이것도 역시 customer_id와 user_id가 동시에 있어 
  			  무엇으로 가야할지 회의 필요
  			3.나머지 경우는 그냥 넣으면 될 예정
  			4. PM님 시도대로 똑같은 코드 제작
  			  //TODO 
			  /* 정상적으로 작동하는지 확인하기 위해 return 값을 string 고정하였다. 차후 수정필요함.
		     * 아래는 예시 코드
		     * http://localhost:8080/reservation.do?customer_id=1234&table_id=01&time=09:00:00&date=1971-01-03&covers=5&oid=0
		     */
		return result;
	}	
	
	@GetMapping("/register.do")
	public String register(@RequestParam(value = "id") String id, 
			@RequestParam(value = "password") String password,
			@RequestParam(value = "name") String name,
			@RequestParam(value = "phoneNumber") String phoneNumber) {
		
		String result = "done";
		try {
			Statement stmt
			  = Database.getConnection().createStatement() ;
			int updateCount = stmt.executeUpdate(
					"INSERT INTO user (id, password, name, phoneNumber)" +
					       "VALUES ('" + id + "', '" + password + "', '" + name + "', '" + phoneNumber + "')");
			stmt.close() ;
		}catch (SQLException e) {
			e.printStackTrace() ;
			result = "fail";
		}
		
		//TODO 
		/* 정상적으로 작동하는지 확인하기 위해 return 값을 string 고정하였다. 차후 수정필요함.
		 * 아래는 예시 코드
		 * http://localhost:8080/register.do?id=test&password=1234&name=bb&phoneNumber=01012341234
		 */
		return result;
	}	
}
