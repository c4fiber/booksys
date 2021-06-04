package booksys;

import static org.junit.jupiter.api.Assertions.*;

import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;

import com.domain.User;

class UserTest {
	User u = new User(0, "khs", "1234", "kwon", "01012341234");
	User u2 = new User(1, "khs2", "12345", "kim", "01012341111");

	@Test
	void testName() {
		Boolean t = false;
		Boolean t2 = false;
		for (int x = 0; x < 10; x++) {
			t = u.getName().contains("" + x);
			t2 = u2.getName().contains("" + x);
			if (t || t2)
				break;
		}
		assertFalse(t);
	}

	@Test
	void testNormalPhoneNumber() {
		Boolean t = false;
		if (u.getPhoneNumber().matches(".*[0-9].*") && u2.getPhoneNumber().matches(".*[0-9].*")) {
			t = true;
		}
		assertTrue(t);
	}

	@Test
	void testPhoneNumberLength() {
		Boolean t = false;
		if (11 >= u.getPhoneNumber().length() && 11 >= u2.getPhoneNumber().length()) {
			t = true;
		}
		assertTrue(t);
	}

	@Test
	void testNormalId() {
		Boolean t = false;
		String pattern = "^[ㄱ-ㅎ가-힣a-zA-Z0-9]*$";
		if (Pattern.matches(pattern, u.getId()) && Pattern.matches(pattern, u.getId())) {
			t = true;
		}
		assertTrue(t);
	}

	@Test
	void testSameId() {
		Boolean t = true;
		if (u.getId() == u2.getId()) {
			t = false;
		}
		assertTrue(t);
	}
}
