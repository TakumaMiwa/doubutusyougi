package board;

import koma.Koma;

public class CalcMoveHuman {
	private Kyokumen kyokumen;
	public CalcMoveHuman(Kyokumen kyokumen) {
		this.kyokumen = kyokumen;
	}
	
//	 正しい入力のmoveを返す
//	public Move getMove () {
//		Move move = null;
//		while (true) {
//			move = makeMove();
//			// moveが正しいか判定　正しければreturn
//			if (checkGoNextKyokumen(move)) {
//				System.out.println("next kyokumen");
//				break;
//			}
//			System.out.println("Input Error happened.");
//		}
//		
//		// 成るかどうかの確認
//		Koma koma = kyokumen.getKomaFromPlace(move.getBeforePlaceA(), move.getBeforePlaceB());
//		if (move.isNaru(koma)) move.setNaru(true);
//		return move;
//	}
	
	// beforeA, beforeBに駒があるか確認
	private boolean checkGoBeforePlace (int beforeA, int beforeB) {
		if (beforeA > 4) {
			// 駒台の時
			if ((beforeA/10) == getTeban()) {
				// 手番の駒台に駒があるか確認
				if (kyokumen.getKomaFromPlace(beforeA, beforeB) != null) return true;
				else {
					System.out.println("error: beforeplace is not true in CalcMove class checGoBeforePlace method");
					return false;
				}
			} else {
				// 手番でない駒台を指している
				System.out.println("error: input is not true in CalcMove class checGoBeforePlace method");
				return false;
			}
		} else {
			// 盤面の駒を動かす時
			Koma koma = kyokumen.getBanarray(beforeA, beforeB);
			if (koma == null) {
				System.out.println("error: input is not true in CalcMove class checGoBeforePlace method");
				return false;
			}
			if (koma.getTeban() == getTeban()) return true;
			System.out.println("error: input is not true in CalcMove class checGoBeforePlace method");
			return false;
		}
	}
	
	// moveを作る
	private Move makeMove (int i, int j) {
		if (getTeban() == 1) {
			System.out.println("先手番です。");
		} else if (getTeban() == 2) {
			System.out.println("後手番です。");
		}
		Move move = new Move();
		move.inputMove(i, j);
		return move;
	}
	
	// 手番を返す
	private int getTeban () {
		return kyokumen.getTeban();
	}
}







