package calcValue;

import board.Kyokumen;
import koma.Koma;

public class MovePlaceValue extends ParentValue{
	public MovePlaceValue (Kyokumen kyokumen) {
		super(kyokumen);
	}
	
	// 評価値を決める
	public void calcValue () {
		// valueBooleanは変えない
		int value = 0;
		int movePlaceN = 0;
		for (Koma koma: getKyokumen().getKomaArray()) {
			movePlaceN = koma.getMovePlace().size();
			if (koma.getTeban() == 2) movePlaceN *= -1;
			value += movePlaceN;
		}
		setValueInt(value);
	}
}
