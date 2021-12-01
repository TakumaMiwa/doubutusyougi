package calcValue;

import java.util.ArrayList;

import board.Kyokumen;
import koma.Koma;
import main.Calc;


// ライオンの安全度を評価
public class SafetyLionValue extends ParentValue {
	public SafetyLionValue (Kyokumen kyokumen) {
		super(kyokumen);
	}
	
	public void calcValue () {
		setValueBoolean(false);
		int value = 0; //最初は危険な駒のポイントの合計
		value += makeValue(getTeban());
		value += makeValue(Calc.changeTeban(getTeban()));
		setValueInt((int)(value * 0.3));
	}
	
	// tebanの玉の危険度を返す
	private int makeValue (int teban) {
		int value= 0;
		ArrayList<Koma> komaEnemyList = getKyokumen().getTebanKomaListBan(Calc.changeTeban(teban));
		int placeLion = getKyokumen().getPlaceLion(teban);
		int placeLionA = placeLion / 10;
		int placeLionB = placeLion % 10;
		//自分のライオンから1マス以内に敵の駒があれば危険
		int placeEnemyKomaA = 0;
		int placeEnemyKomaB = 0;
		int diffA = 0;
		int diffB = 0;
		for (Koma koma: komaEnemyList) {
			placeEnemyKomaA = koma.getPlaceA();
			placeEnemyKomaB = koma.getPlaceB();
			
			diffA = placeLionA - placeEnemyKomaA;
			diffB = placeLionB - placeEnemyKomaB;
			
			if (checkDiff(diffA) && checkDiff(diffB)) value += koma.getPointTeban();
			else {
				// 自陣に駒があるか
				if (teban == 1 && koma.getPlaceB() > 2) value += (int)(koma.getPointTeban() * 0.6);
				else if (teban == 2 && koma.getPlaceB() < 3) value += (int)(koma.getPointTeban() * 0.6);
			}
		}
		return value;
	}
	
	private boolean checkDiff (int diff) {
		if (-1 < diff && diff < 1) return true;
		else return false;
	}
}

















