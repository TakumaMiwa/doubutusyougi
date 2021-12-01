package calcValue;

import java.util.ArrayList;

import board.CalcKyokumenFoul;
import board.Kyokumen;
import board.MakeBoardList;
import board.Move;

public class TumiCheck {
	// 手番の玉が詰んでいるか確認
	public static boolean checkTumiTeban (Kyokumen kyokumen) {
		if (CalcKyokumenFoul.isLionte(kyokumen, kyokumen.getTeban()));
		// 回避できるか調べる
		ArrayList<Move> moveList = MakeBoardList.getNextMoveListNoFoul(kyokumen);
		if (moveList == null || moveList.isEmpty()) return true;
		else return false;
	}
	
	// 局面でn手以下の詰みがあるか調べる
	public static Move checkTumiNTe (Kyokumen kyokumen, int n) {
		if (n < 1) return null;
		
		// 次の手でライオン手のリスト
		ArrayList<Move> moveListLion = MakeBoardList.getNextMoveListOnlyLionte(kyokumen);
		// どれか一つでも詰んでいるものがあればそのmoveを返す
		Kyokumen kyokumenNext = null;
		for (Move move: moveListLion) {
			move.outputMoveTest();
			kyokumenNext = kyokumen.clone();
			kyokumenNext.moveNextKyokumen(move);
			// 次の局面で玉が詰まされているか調べる
			if (checkTumiForLion(kyokumenNext, n-1)) return move;
		}
		return null;
	}
	
	public static boolean checkTumiForLion (Kyokumen kyokumen, int n) {
		if (n < 0) return false;
		kyokumen.outputKyokumen();
		ArrayList<Move> moveListEscape = MakeBoardList.getNextMoveListNoFoul(kyokumen);
//		System.out.println("局面");
//		kyokumen.outputKyokumen();
//		System.out.println("逃げる手");
//		for (Move move: moveListEscape) move.outputMoveTest();
		if (moveListEscape == null || moveListEscape.isEmpty()) return true;
		
		//　全ての逃げ方で詰むか確認
		Kyokumen kyokumenNext = null;
		for (Move move: moveListEscape) {
			kyokumenNext = kyokumen.clone();
			kyokumenNext.moveNextKyokumen(move);
			if (checkTumiNTe (kyokumenNext, n-1) == null) return false;
		}
		return true;
	}
}
