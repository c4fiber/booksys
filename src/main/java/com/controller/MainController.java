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

import booksys.application.persistency.PersistentCustomer;
import booksys.storage.Database;

@SpringBootApplication
@RestController
public class MainController {

	public static void main(String[] args) {
		SpringApplication.run(MainController.class, args);
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
	public boolean login(String id,String password,String name, String phoneNumber ) {		
		//1. id키가 기본키이므로 id키로 Select문을 사용해 User객체를 받아옴
		User findUser = getUser("SELECT * FROM user WHERE id = '" + id + "'" );
		//2. 없으면 false리턴
		if(findUser==null)
			return false;
		else
		{
			//3.id와 패스워드 같으면
			if(id.equals(findUser.getId())&&password.equals(findUser.getPassword()))
			{
				//트루
				return true;
			}
			else return false;
		}
		/*
		 * 이줄은 필수가 아닙니다. 읽지 않으셔도 됩니다.
		 * /AND password = '" + password + "'"
		 */
		/*&&name.equals(findUser.getName())&&phoneNumber.equals(findUser.getPhoneNumber())
		 * 매개변수와 User객체 비교하는데 쓸 수 있는 보조문 (필수아님) 
		 */
	}
	
	private User getUser(String sql) {
		/*
		 * //column id password name phoneNumber  => user
		 * 			0	1		2		3	
		 *  각 User의 column 내용을 데이터베이스에서 가져와서 
		 *  User 객체를 돌려준다.
		 */	
		User c = null;
		try {
			Statement stmt = Database.getInstance().getConnection().createStatement();
			ResultSet rset = stmt.executeQuery(sql);
			while (rset.next()) {
				String id = rset.getString(0);
				String password = rset.getString(1);
				String name  = rset.getString(2);
				String phoneNumber = rset.getString(3);
				c = new User(id,password,name,phoneNumber);
			}
			rset.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return c;
	}
}
