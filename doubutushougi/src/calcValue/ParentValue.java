package calcValue;

import board.Kyokumen;

public class ParentValue {
	private Kyokumen kyokumen;
	private boolean valueBoolean; // 勝敗が決まっている時はtrue
	private int valueInt;
	public ParentValue (Kyokumen kyokumen) {
		this.kyokumen = kyokumen;
		calcValue();
	}
	
	// 評価値を決める
	public void calcValue () {
		valueInt = 0;
	}
	
	public boolean getValueBoolean () {
		return valueBoolean;
	}
	
	public int getValueInt () {
		return valueInt;
	}
	
	public Kyokumen getKyokumen () {
		return kyokumen;
	}
	
	public int getTeban () {
		return kyokumen.getTeban();
	}
	
	public void setValueInt (int valueInt) {
		this.valueInt = valueInt;
	}
	
	public void setValueBoolean (boolean valueBoolean) {
		this.valueBoolean = valueBoolean;
	}
}
