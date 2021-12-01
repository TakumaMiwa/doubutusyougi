package koma;

import board.Kyokumen;

public class Hiyoko extends Koma {
	
	public Hiyoko (int tebanN) {
		super(tebanN);
		setPoint(1000000);
	}
	
	public boolean ableToMove () {
		if (getTeban() == 1 && getPlaceB() == 1) return true;
		else if (getTeban() == 2 && getPlaceB() == 4) return true;
		else return false;
	}

	
	public boolean ableToNaru () {
		return true;
	}

	
	public int getKomaNumber () {
		return 1;
	}
	
	
	public String getKomaName () {
		return " ひ";
	}
	
	// 動ける場所を決める
	public void decideMovePlace (Kyokumen kyokumen) {
		Koma koma = null;
		int a = getPlaceA();
		int b = getPlaceB();
		int difference;
		
		if (getTeban() == 1) difference = -1;
		else difference = 1;
		
		b += difference;
		koma = kyokumen.getBanarray(a, b);
		if (koma != null && koma.getTeban() == getTeban()) return;
		else addMovePlace(a * 10 + b);
	}
}
