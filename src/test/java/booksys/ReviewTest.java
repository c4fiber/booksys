package booksys;

import static org.junit.jupiter.api.Assertions.*;
import java.util.Calendar;
import org.junit.jupiter.api.Test;
import com.domain.Review;

class ReviewTest {

	Review r = new Review(0, "khs", "2021-04-10", "잘 먹고 갑니다.");
	Review r2 = new Review(0, "khs", "2021-04-12", "분위기가 좋아요.");
	
	@Test
	void testDate() {
		Calendar cal = Calendar.getInstance();
		String year = Integer.toString(cal.get(cal.YEAR));
		String month = Integer.toString(cal.get(cal.MONTH) + 1);
		String date = Integer.toString(cal.get(cal.DATE));

		String[] nowDate = new String[5];
		nowDate[0] = year;
		if (month.length() == 1) {
			nowDate[1] = "0";
			nowDate[2] = month;
			if (date.length() == 1) {
				nowDate[3] = "0";
				nowDate[4] = date;
			} else {
				nowDate[3] = date;
			}
		} else {
			nowDate[1] = month;
			if (date.length() == 1) {
				nowDate[2] = "0";
				nowDate[3] = date;
			} else {
				nowDate[2] = date;
			}
		}
		String day[] = r.getDate().split("-");

		assertTrue(translate(nowDate) >= translate(day));
	}
	
	@Test
	void testComentOnce() {
		Boolean t = true;
		if (r.getId() == r2.getId() && 
				r.getDate() == r2.getDate()) {
			t = false;
		}
		assertTrue(t);
	}
	
	@Test
	void testComment() {
		Boolean t = true;
		if (0 == r.getComment().length() || 
				r.getComment().length() > 300)
			t = false;
		assertTrue(t);
	}

	int translate(String[] D) {
		StringBuilder sb = new StringBuilder();
		for (String arr : D) {
			sb.append(arr);
		}
		return Integer.parseInt(sb.toString());

	}
}
