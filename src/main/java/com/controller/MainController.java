package com.controller;

import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
	public void login() {
		
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
		}
		
		//TODO 
		/* 정상적으로 작동하는지 확인하기 위해 return 값을 string 고정하였다. 차후 수정필요함.
		 * 아래는 예시 코드
		 * http://localhost:8080/register.do?id=test&password=1234&name=himan&phoneNumber=01012341234
		 */
		return "done";
	}

	
}
