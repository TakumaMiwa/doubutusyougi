package koma;

import board.Kyokumen;

public class Zou extends Koma {
	public Zou (int tebanN) {
		super(tebanN);
		setPoint(10000000);
	}
	
	public int getKomaNumber () {
		return 2;
	}
	
	public String getKomaName () {
		return " 象";
	}
	
	// 動ける場所を決める
	public void decideMovePlace (Kyokumen kyokumen) {
		Koma koma = null;
		int a = getPlaceA() * 10;
		int b = getPlaceB();
		CalcKoma calc = new CalcKoma();
		int[] array = {a+10+b+1, a+10+b-1, a-10+b+1, a-10+b-1};
		for (int i: array) {
			if (calc.inBan(i)) {
				koma = kyokumen.getBanarray(i);
				if (koma == null) addMovePlace(i);
				else if (koma.getTeban() != getTeban()) addMovePlace(i);
			}
		}
	}
}
