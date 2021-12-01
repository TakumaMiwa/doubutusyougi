package calcValue;

import java.util.ArrayList;
import board.Kyokumen;
import board.Move;
import koma.Koma;

public class LikelyToBeTokenValue extends ParentValue {
	public LikelyToBeTokenValue (Kyokumen kyokumen) {
		super(kyokumen);
	}
	
	// 評価値を決める
	public void calcValueL () {
		setValueInt(0);
		setValueBoolean(true);
		// 自分の駒のリストを作る
		ArrayList<Koma> myKomaList = getKyokumen().getTebanKomaListBan(getTeban());
		// 利きを見る
		Koma komaEnemy = null;
		Move move = null;
		Kyokumen kyokumenEnemy = null;
		ArrayList<Integer> nextValueList = new ArrayList<Integer>(); // 次の局面の評価値のリスト
		
		for (Koma koma: myKomaList) {
			for (int place: koma.getMovePlace()) {
				komaEnemy = getKyokumen().getKomaFromPlace(place); // 駒が動ける場所に相手の駒があるか
				if (komaEnemy != null) {
					// 確認
					// 駒を取れる。moveを使ってその局面の評価値を得る。
					move = new Move(koma.getPlaceA(), koma.getPlaceB(), place/10, place%10);
					kyokumenEnemy = getKyokumen().clone();
					kyokumenEnemy.moveNextKyokumen(move);
					// 次の評価値をnextvaluelistに加える
					int value = 0;
					nextValueList.add(value);
				}
			}
		}
		
		if (nextValueList.isEmpty()) {
			setValueBoolean(false);
			return;
		}
		
		if (getTeban() == 1) {
			int max = nextValueList.get(0);
			for (int v: nextValueList) if (max < v) max = v;
			setValueInt(max);
		} else {
			int min = nextValueList.get(0);
			for (int v: nextValueList) if (min > v) min = v;
			setValueInt(min);
		}
		
	}
}
