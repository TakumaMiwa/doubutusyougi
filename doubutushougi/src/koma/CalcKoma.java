package koma;


// 駒を作るクラス
public class CalcKoma {
	public static Koma makeKoma (int n, int teban) {
		Koma koma = null;
		switch (n) {
			case 1:
				koma = new Hiyoko(teban);
				break;
			case 2:
				koma = new Zou(teban);
				break;
			case 3:
				koma = new Kirin(teban);
				break;
			case 4:
				koma = new Lion(teban);
				break;
			case 11:
				koma = new Niwatori(teban);
				break;
		}
		return koma;
	}
	
	// 盤上にあるか判定
	public static boolean inBan (int a, int b) {
		if (0 < a && a < 4 && 0 < b && b < 5) return true;
		else return false;
	}
	
	public boolean inBan(int place) {
		return inBan(place / 10, place % 10);
	}
}
