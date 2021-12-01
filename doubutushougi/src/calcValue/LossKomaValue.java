package calcValue;

import board.Kyokumen;
import koma.Koma;

public class LossKomaValue extends ParentValue{
	public LossKomaValue (Kyokumen kyokumen) {
		super(kyokumen);
	}
	
	// 評価値を決める
	public void calcValue () {
		int value = 0;
		// 盤上の駒
		for (Koma koma: getKyokumen().getKomaArray()) value += koma.getPointTeban();
		// 駒台の駒
		value += getKyokumen().getSenteKomadai().getAllPointTeban();
		value += getKyokumen().getGoteKomadai().getAllPointTeban();
		setValueInt(value);
	}
}
