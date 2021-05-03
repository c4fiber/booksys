package com.controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
/*
 * dfdsfesfse
 * f
 * sef
 * se
 * fse
 * f
 * sef
 * sf
 * s
 * fs
 * f
 * e
 * sf
 * se
 */
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
