package main;

public class Calc {
	// 文字列が数字か判断
	public static boolean isNumber (String str) {
		try {
			Integer.parseInt(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
	public static int changeTeban (int teban) {
		if (teban == 1) return 2;
		else if (teban == 2) return 1;
		else {
			System.out.println("error: Calc.changeteban");
			return 0;
		}
	}
}
