package koma;

import board.Kyokumen;

public class Niwatori extends Koma {
	public Niwatori (int tebanN) {
		super(tebanN);
		setPoint(15000000);
	}
	
	public int getKomaNumber () {
		return 11;
	}
	
	public String getKomaName () {
		return " 鶏";
	}
	
	// 動ける場所を決める
	public void decideMovePlace (Kyokumen kyokumen) {
		Koma koma = null;
		int a = getPlaceA() * 10;
		int b = getPlaceB();
		CalcKoma calc = new CalcKoma();
		int difference;
		if (getTeban() == 1) difference = -1;
		else difference = 1;
		int b1 = b + difference;
		
		int[] array = {a+10+b1, a-10+b1, a+10+b, a+b-10, a+b+1, a+b-1};
		for (int i: array) {
			if (calc.inBan(i)) {
				koma = kyokumen.getBanarray(i);
				if (koma == null) addMovePlace(i);
				else if (koma.getTeban() != getTeban()) addMovePlace(i);
			}
		}
	}
}
