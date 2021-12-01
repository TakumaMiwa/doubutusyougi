package board;

import java.util.ArrayList;

import koma.Koma;
import main.Calc;

public class CalcKyokumenFoul {
	// 千日手かどうか調べる
	// @return 0: 千日手でない 1: 千日手 2: 千日手番がライオン手 3: 2の逆
	public static int checkSennitite (ArrayList<Kyokumen> kyokumenList, Kyokumen kyokumen) {
		// kyokumenListに同じ局面があるか調べる
		if (kyokumenList==null || kyokumenList.size() < 10) return 0;
		int sameKyokumenCount = 0;
		for (Kyokumen k: kyokumenList) if (k.equal(kyokumen)) sameKyokumenCount++;
		if (sameKyokumenCount<4) return 0;
		for (Kyokumen k: kyokumenList) k.outputKyokumen();
		
		// 連続ライオン手が無いか
		if (isLionte(kyokumen, kyokumen.getTeban())) {
			int number = kyokumenList.size() - 2;
			Kyokumen kyokumenS = null;
			while (true) {
				kyokumenS = kyokumenList.get(number);
				if (kyokumenS.equal(kyokumen)) return 3;
				if (!isLionte(kyokumen, kyokumen.getTeban())) break;
				number -= 2;
			}
		}
		return 1;
	}
	
	// 手番にライオン手がかかっているか確認
	public static boolean isLionte (Kyokumen kyokumen, int teban) {
		int placeLion = kyokumen.getPlaceLion(teban);
		int tebanR = Calc.changeTeban(teban);
		ArrayList<Koma> myKomaList = kyokumen.getTebanKomaListAll(tebanR);
		for (Koma koma: myKomaList) for (int place: koma.getMovePlace()) if (place==placeLion) return true;
		return false;
	}
}
