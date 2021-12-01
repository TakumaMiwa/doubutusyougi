package calcValue;


import java.util.ArrayList;

import board.Kyokumen;
import board.Move;
import board.CalcKyokumenFoul;

public class Value {
	private int value;
	private boolean determine;
	
	public Value () {
		this.determine = false;
		this.value = 0;
	}
	
	// 評価値を計算
	public void calcValuePresent (Kyokumen kyokumen, ArrayList<Kyokumen> kyokumenList, Move beforeMove) {
		System.out.println("calcValuePresent: " + kyokumen.getTeban());
		if (kyokumenList != null) {
			// 千日手チェック
			int sennitite = CalcKyokumenFoul.checkSennitite(kyokumenList, kyokumen);
			if (sennitite ==1) {
				System.out.println("千日手です。");
				setDetermine(true);
				setValue(0);
				return;
			} else if (sennitite == 2) {
				// 手番負け
				setDetermine(true);
				if (kyokumen.getTeban() == 1) setValue(-100);
				else setValue(100);
				return;
			} else if (sennitite == 3) {
				// 手番勝ち
				setDetermine(true);
				if (kyokumen.getTeban() == 1) setValue(100);
				else setValue(-100);
				return;
			}
		}
			
		// 詰みチェック
		if (TumiCheck.checkTumiNTe(kyokumen, 1) != null) {
			// 勝ち
			setWin(kyokumen.getTeban());
			return;
		} else if (TumiCheck.checkTumiForLion(kyokumen, 2)) {
			// 詰みがある。負け
			setLose(kyokumen.getTeban());
			return;
		}
		
		// 駒の損得
		LossKomaValue lossKomaValue = new LossKomaValue(kyokumen);
		addValue(lossKomaValue.getValueInt());
		
		// 駒の利き
		MovePlaceValue movePlaceValue = new MovePlaceValue(kyokumen);
		addValue(movePlaceValue.getValueInt());
		
		// タダで取られる駒
		TadaValue tadaValue = new TadaValue(kyokumen);
		addValue(tadaValue.getValueInt());
	}
	
	private void addValue (int add) {
		value += add;
	}
	
	public int getValue () {
		return value;
	}
	
	public boolean getDetermine () {
		return determine;
	}
	
	public void setValue (int value) {
		this.value = value;
	}
	
	public void setDetermine (boolean determine) {
		this.determine = determine;
	}
	
	public void setValueAll (Value value) {
		this.value = value.getValue();
		this.determine = value.getDetermine();
	}
	
	public void setWin (int teban) {
		setDetermine(true);
		if (teban == 1) setValue(100);
		else if (teban == 2) setValue(-100);
	}
	
	public void setLose (int teban) {
		setDetermine(true);
		if (teban == 1) setValue(-100);
		else if (teban == 2) setValue(100);
	}
	
	public boolean getWin (int teban) {
		if (!determine) return false;
		else if (teban == 1 && value > 0) return true;
		else if (teban == 2 && value < 0) return true;
		else return false;
	}
}





