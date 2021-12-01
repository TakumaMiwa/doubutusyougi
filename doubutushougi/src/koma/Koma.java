package koma;

import java.util.ArrayList;

import board.Kyokumen;


public class Koma {
	private int teban; 
	private int placeA;
	private int placeB;
	private int point; // 駒の価値
	private int pointSpecial;
	private ArrayList<Integer> movePlace;
	private boolean motigoma;
	
	public Koma (int tebanN) {
		this.teban = tebanN;
		this.movePlace = new ArrayList<Integer>();
		this.motigoma = false;
		this.placeA = 0;
		this.placeB = 0;
	}
	
	public Koma clone () {
		Koma komaClone = CalcKoma.makeKoma(getKomaNumber(), getTeban());
		komaClone.setPlace(getPlaceA(), getPlaceB());
		komaClone.setPoint(getPoint());
		komaClone.setPointSpecial(getPointSpecial());
		komaClone.setMovePlace(getMovePlaceCloneDeep());
		komaClone.setMotigoma(getMotigoma());
		return komaClone;
	}
	
	public boolean equal (Koma koma) {
		if (koma == null) return false;
		if (this.teban != koma.getTeban()) return false;
		if (this.placeA != koma.getPlaceA()) return false;
		if (this.placeB != koma.getPlaceB()) return false;
		if (this.motigoma != koma.getMotigoma()) return false;
		return true;
	}
	
	// 動けるかどうか判断
	public boolean ableToMove () {
		return false;
	}
	
	// 不成の駒を返す
	public Koma getNarazukoma () {
		if (getKomaNumber() > 10) {
			Koma narazuKoma = CalcKoma.makeKoma(getKomaNumber() - 10, getTeban());
			narazuKoma.setPlace(getPlaceA(), getPlaceB());
			return narazuKoma;
		} else {
			System.out.println("error: Koma getNarazukoma not narigoma");
			return this;
		}
	}
	
	// 成駒を返す
	public Koma getNarigoma () {
		int n = getKomaNumber();
		Koma koma = CalcKoma.makeKoma(n+10,  getTeban());
		koma.setPlace(getPlaceA(), getPlaceB());
		return koma;
	}
	
	public boolean ableToNaru () {
		return false;
	}
	
	public String getKomaName () {
		return "　駒";
	}
	
	private String getTebanS () {
		if (teban == 1) return "↑";
		else return "↓";
	}
	
	public void outputKoma () {
		System.out.print(getKomaName() + getTebanS() + " ");
	}
	
	// テスト用
	public void outputTest () {
		System.out.print(getKomaName() + getTebanS() + " " + getPlace());
	}
	
	public void movePlaceClear () {
		movePlace.clear();
	}
	
	// その場所に移動できるか
	public boolean containMovePlace (int place) {
		return movePlace.contains(place);
	}
	
	// 自分のいる場所等を出力
	public void outputMovePlace () {
		System.out.print(getKomaName() + getPlaceA() + "" +getPlaceB() + "movePlace:");
		for (int i: movePlace) System.out.print(i + ",");
		System.out.println("");
	}
	
	public void setTeban (int tebanN) {
		this.teban = tebanN;
	}
	
	public int getTeban () {
		return teban;
	}
	
	public void setPlace (int a, int b) {
		this.placeA = a;
		this.placeB = b;
	}
	
	public void setPoint (int pointN) {
		point = pointN;
	}
	
	public int getPoint () {
		return point;
	}
	
	public void setPointSpecial (int pointSpecialN) {
		this.pointSpecial = pointSpecialN;
	}
	
	public int getPointSpecial () {
		return pointSpecial;
	}
	
	public void setMovePlace (ArrayList<Integer> movePlace) {
		this.movePlace = movePlace;
	}
	
	public ArrayList<Integer> getMovePlace () {
		return movePlace;
	}
	
	public ArrayList<Integer> getMovePlaceCloneDeep () {
		ArrayList<Integer> movePlaceClone = new ArrayList<Integer>();
		for (Integer i: movePlace) movePlaceClone.add(i);
		return movePlaceClone;
	}
	
	public void addMovePlace (int place) {
		movePlace.add(place);
	}
	
	public int getPlaceA () {
		return placeA;
	}
	
	public int getPlaceB () {
		return placeB;
	}
	
	public int getKomaNumber () {
		return 0;
	}
	
	public void setMotigoma (boolean bool) {
		this.motigoma = bool;
	}
	
	public boolean getMotigoma () {
		return motigoma;
	}
	
	public int getPlace () {
		return placeA * 10 + placeB;
	}
	
	public int getPointTeban () {
		if (teban == 1) return getPoint();
		else if (teban == 2) return -1 * getPoint();
		else {
			System.out.println("error: koma.getpointteban");
			return 0;
		}
	}
	
	public void changeTeban () {
		if (teban == 1) this.teban = 2;
		else if (teban == 2) this.teban = 1;
		else System.out.println("error: koma.changeTeban");
	}
	
	public void  decideMovePlace (Kyokumen kyokumen) {
		// 中身は空 他の駒に継承されて利用される
	}
}




















