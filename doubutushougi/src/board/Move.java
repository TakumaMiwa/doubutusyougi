package board;

import koma.Koma;
import main.Calc;

public class Move {
	private int beforePlaceA; // 23の2, 駒台なら10, 20
	private int beforePlaceB; // 23の3, 駒台なら10, 20
	private int afterPlaceA;
	private int afterPlaceB;
	private boolean naru; // trueで成った
	private int getKoma;
	
	public Move () {
		this.beforePlaceA = 0;
		this.beforePlaceB = 0;
		this.afterPlaceA = 0;
		this.afterPlaceB = 0;
		this.naru = false;
		this.getKoma = 0;
	}
	
	public Move (Move move) {
		this.beforePlaceA = move.getBeforePlaceA();
		this.beforePlaceB = move.getBeforePlaceB();
		this.afterPlaceA = move.getAfterPlaceA();
		this.afterPlaceB = move.getAfterPlaceB();
		this.naru = move.getNaru();
		this.getKoma = move.getGetKoma();
	}
	
	public Move (int beforeA, int beforeB, int afterA, int afterB) {
		setBeforePlaceA(beforeA);
		setBeforePlaceB(beforeB);
		setAfterPlaceA(afterA);
		setAfterPlaceB(afterB);
		this.naru = false;
		this.getKoma = 0;
	}
	
	public Move clone () {
		return new Move(this);
	}
	
	// moveの同値性を判定
	public boolean equalsMove (Move move) {
		if (this.beforePlaceA == move.getBeforePlaceA() && this.beforePlaceB == move.getBeforePlaceB() &&
				this.afterPlaceA == move.getAfterPlaceA() && this.afterPlaceB == move.getAfterPlaceB() &&
				this.getKoma == move.getGetKoma()) {
			if ((this.naru && move.getNaru()) || (!this.naru && !move.getNaru())) return true;
			else return false;
		} else return false;
	}
	
	// 駒が成れるか判別
	public boolean isNaru (Koma koma) {
		if (!koma.getMotigoma() && koma.ableToNaru()) return true;
		else return false;
	}
	
	// 駒が成れるか判別
	public boolean ableNuru (int teban) {
		if (teban == 1 && (beforePlaceB < 2 || afterPlaceB < 2)) return true;
		else if (teban == 2 && (beforePlaceB > 3 || afterPlaceB > 3)) return true;
		else return false;
	}
	
	// 駒が成れるか判別
	public boolean checkAbleNaru (Koma koma) {
		// 持ち駒からは成れない
		if (koma.getMotigoma()) return false;
		
		if (koma.ableToNaru() && koma.getTeban() == 1 && (beforePlaceB < 2 || afterPlaceB < 2)) return true;
		else if (koma.ableToNaru() && koma.getTeban() == 2 && (beforePlaceB > 3 || afterPlaceB > 3)) return true;
		else return false;
	}
	
	// moveの出力
	public void outputMoveTest () {
		int n;
		if (naru) n = 1;
		else n = 0;
		
		System.out.println(beforePlaceA+","+beforePlaceB+","+afterPlaceA+","+afterPlaceB+","+n+","+getKoma);
	}
	
	// 棋譜を保存するときの改行を含む文字列を返す
	public String getMoveKihu () {
		int n;
		if (naru) n = 1;
		else n = 0;
		String ans = beforePlaceA+","+beforePlaceB+","+afterPlaceA+","+afterPlaceB+","+n+","+getKoma+"\n";
		return ans;
	}
	
	// moveの入力
	public void inputMove (int i, int j) {
		beforePlaceA = i;
		beforePlaceB = j;
	}
	
	// ファイルから読み込む
	public Move getInputMove (String str) {
		String[] input = str.split(",", 0);
		if (input.length != 6) {
			System.out.println("error: Move getInputMove"+str);
			return null;
		}
		
		int[] setNumber = new int[6];
		for (int i=0;i<6;i++) {
			if (Calc.isNumber(input[i])) setNumber[i] = Integer.parseInt(input[i]);
			else {
				System.out.println("error: Move getInputMove"+input[i]);
				return null;
			}
		}
		
		Move move = new Move();
		move.setBeforePlaceA(setNumber[0]);
		move.setBeforePlaceB(setNumber[1]);
		move.setAfterPlaceA(setNumber[2]);
		move.setAfterPlaceB(setNumber[3]);
		
		if (setNumber[4]==0) move.setNaru(false);
		else if (setNumber[4]==1) move.setNaru(true);
		else {
			System.out.println("error: Move getinputMove naru"+setNumber[4]);
			return null;
		}
		
		move.setGetKoma(setNumber[5]);
		return move;
	}
	
	// 入力と同じ場所がafterPlaceか
	public boolean matchAfterPlace (int a, int b) {
		if (a == afterPlaceA && b == afterPlaceB) return true;
		else return false;
	}
	
	public boolean matchAfterPlace (int place) {
		int a = place / 10;
		int b = place % 10;
		return matchAfterPlace(a, b);
	}
	
	public int getBeforePlaceA () {
		return beforePlaceA;
	}
	
	public int getBeforePlaceB () {
		return beforePlaceB;
	}
	
	public int getAfterPlaceA () {
		return afterPlaceA;
	}
	
	public int getAfterPlaceB () {
		return afterPlaceB;
	}
	
	public void setBeforePlaceA (int beforeA) {
		if (beforeA == 10 || beforeA == 20 || (0 < beforeA && beforeA < 4)) this.beforePlaceA = beforeA;
		else System.out.println("error: setBeforePlaceA");
	}
	
	public void setBeforePlaceB (int beforeB) {
		if (beforeB == 10 || beforeB == 20 || (0 < beforeB && beforeB < 5)) this.beforePlaceB = beforeB;
		else System.out.println("error: setBeforePlaceB");
	}
	
	public void setPlace (int beforeA, int beforeB, int after) {
		setBeforePlaceA(beforeA);
		setBeforePlaceB(beforeB);
		setAfterPlace(after);
	}
	
	public void setAfterPlaceA (int afterA) {
		if (0 < afterA && afterA < 4) this.afterPlaceA = afterA;
	}
	
	public void setAfterPlaceB (int afterB) {
		if (0 < afterB && afterB < 5) this.afterPlaceB = afterB;
	}
	
	public void setAfterPlace (int afterPlace) {
		int a = afterPlace / 10;
		int b = afterPlace % 10;
		setAfterPlaceA(a);
		setAfterPlaceB(b);
	}
	
	public boolean getNaru () {
		return naru;
	}
	
	public int getGetKoma () {
		return getKoma;
	}
	
	public void setNaru (boolean naru) {
		this.naru = naru;
	}
	
	public void setGetKoma(int koma) {
		this.getKoma = koma;
	}
	
	public int getAfterPlaceMix () {
		return afterPlaceA * 10 + afterPlaceB;
	}
	
	
}








