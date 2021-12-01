package koma;

import board.Kyokumen;

public class Kirin extends Koma {
	
	public Kirin (int tebanN) {
		super(tebanN);
		setPoint(12000000);
	}
	
	public int getKomaNumber () {
		return 3;
	}
	
	
	public String getKomaName () {
		return " キ";
	}
	
	// 動ける場所を決める
	public void decideMovePlace (Kyokumen kyokumen) {
		Koma koma = null;
		int a = getPlaceA() * 10;
		int b = getPlaceB();
		CalcKoma calc = new CalcKoma();
		int[] array = {a+10+b, a-10+b, a+b+1, a+b-1};
		for (int i: array) {
			if (calc.inBan(i)) {
				koma = kyokumen.getBanarray(i);
				if (koma == null) addMovePlace(i);
				else if (koma.getTeban() != getTeban()) addMovePlace(i);
			}
		}
	}
}
