package board;

import koma.CalcKoma;
import koma.Koma;

// 局面を自由に編集できるクラス
public class FreeKyokumen extends Kyokumen{
	public FreeKyokumen (Kyokumen kyokumen) {
		setBanKomadai(kyokumen.getBan().clone(), kyokumen.getSenteKomadai().clone(), kyokumen.getGoteKomadai().clone());
	}
	
	public Kyokumen getKyokumen (int teban) {
		Kyokumen kyokumen = new Kyokumen(getBan(), getSenteKomadai(), getGoteKomadai(), teban);
		kyokumen.setKomaArray();
		kyokumen.decideAllKomaMovePlace();
		return kyokumen;
	}
	
	// 駒を動かす　取った駒をreturn
	public Koma move (int beforePlace, int afterPlace) {
		Koma koma = getKomaFromPlace(beforePlace);
		if (koma == null) {
			System.out.println("error at free kyokumen move");
			return null;
		}
		
		// 駒が盤上にある場合
		if (beforePlace/10 < 10) setKomaFromPlace(null, beforePlace/10, beforePlace%10);
		else decreaseMotigoma(koma);
		
		// 同じ場所なら成る
		if (beforePlace == afterPlace) {
			if (koma.ableToNaru() || koma.getKomaNumber() > 10) {
				int komaNumber = koma.getKomaNumber();
				if (komaNumber < 10) komaNumber += 10;
				else komaNumber -= 10;
				Koma komaN = CalcKoma.makeKoma(komaNumber, koma.getTeban());
				setKomaFromPlace(komaN, afterPlace/10, afterPlace%10);
			} else {
				// 成れない駒の時
				setKomaFromPlace(koma, afterPlace/10, afterPlace%10);
			}
			return null;
		} else {
			// 同じ場所以外
			setKomaFromPlace(koma, afterPlace/10, afterPlace%10);
			//盤上に移動
			if (afterPlace/10 < 10) return null;
			else return koma;
		}
	}
}
