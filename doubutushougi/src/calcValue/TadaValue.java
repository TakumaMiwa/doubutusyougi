package calcValue;

import java.util.ArrayList;

import board.Kyokumen;
import board.Move;
import koma.Koma;
import main.Calc;

public class TadaValue extends ParentValue {
	public TadaValue (Kyokumen kyokumen) {
		super(kyokumen);
	}
	
	public void calcValue () {
		// タダで取れる駒のリストを作る
		ArrayList<Koma> myKomaList = getKyokumen().getTebanKomaListBan(getTeban());
		// 相手の駒を取れるか確認。取れるなら、それを相手が取れるか確認
		Koma komaEnemy = null;
		Move move = null;
		int value = 0;
		for (Koma koma: myKomaList) {
			for (int place: koma.getMovePlace()) {
				komaEnemy = getKyokumen().getKomaFromPlace(place);
				if (komaEnemy == null) continue;
				move = new Move(koma.getPlaceA(), koma.getPlaceB(), place / 10, place % 10);
				if (checkEnemyKomaMove(move)) value += komaEnemy.getPointTeban();
			}
		}
		value *= -1;
		setValueInt(value);
	}
	
	// 相手の駒がmoveの移動先に利いているか
	private boolean checkEnemyKomaMove (Move move) {
		Kyokumen kyokumenEnemy = getKyokumen().clone();
		kyokumenEnemy.moveNextKyokumen(move);
		// 相手の駒のリスト
		ArrayList<Koma> komaListEnemy = kyokumenEnemy.getTebanKomaListBan(Calc.changeTeban(getTeban()));
		// 相手の動けるところにその場所があるか
		for (Koma koma: komaListEnemy) for (int place: koma.getMovePlace()) if (place == move.getAfterPlaceMix()) return false;
		return true;
	}
}
