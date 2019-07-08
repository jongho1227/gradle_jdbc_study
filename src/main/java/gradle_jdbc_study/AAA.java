package gradle_jdbc_study;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AAA {

	public static void main(String[] args) {
		Date date = new Date();
		SimpleDateFormat sp = new SimpleDateFormat("yy");
		String a = sp.format(date);
		System.out.println(a);
		String b = "001";
		int c = Integer.parseInt(b);
		System.out.println(c);
	}

}
