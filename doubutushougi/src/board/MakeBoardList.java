package board;

import java.util.ArrayList;

import koma.Koma;

public class MakeBoardList {
	// 次の手でライオン手の局面を作る
	public static ArrayList<Move> getNextMoveListOnlyLionte (Kyokumen kyokumen) {
		int teban = kyokumen.getTeban();
		System.out.println("teban: " + teban);
		ArrayList<Move> moveListAll = makeMoveList(kyokumen, teban);
		if (moveListAll == null || moveListAll.isEmpty()) return null;
		else return moveListAll;
	}
	
	// 次に以降できるmoveクラスのリストを返す
	public static ArrayList<Move> getNextMoveListNoFoul (Kyokumen kyokumen) {
		int teban = kyokumen.getTeban();
		ArrayList<Move> moveList = makeMoveList(kyokumen, teban);
		if (moveList == null || moveList.isEmpty()) return null;
		else return moveList;
	}
	
	// 次手のboardリストを返す
//	正しい
	public static ArrayList<Board> getNextBoardList (Board board) {
		Kyokumen kyokumen = board.getKyokumen();
		int teban = kyokumen.getTeban();
		// moveのリストを作る
		ArrayList<Move> moveList = makeMoveList(kyokumen, teban);
		if (moveList == null || moveList.isEmpty()) return null;
		// moveListに評価値を加えていく
		ArrayList<Board> boardList = new ArrayList<Board>();
		Board b = null;
		for (Move move: moveList) {
			b = board.getMoveNextBoard(move);
			boardList.add(b);
		}
		return boardList;
	}
	
	// kyokumenからmoveListを作る
	private static ArrayList<Move> makeMoveList (Kyokumen kyokumen, int teban) {
		ArrayList<Koma> myKomaList = kyokumen.getTebanKomaListAll(teban);
		System.out.println("makeMoveList: " + teban);
		ArrayList<Move> moveList = new ArrayList<Move>();
		Move move = null;
		Koma banKoma = null;
		for (Koma koma: myKomaList) {
			if (koma.getMotigoma()) {
				// 駒が持ち駒の時は盤上の全てのnull
				for (int i=1; i<4; i++) {
					for (int j=1; j<5; j++) {
						banKoma = kyokumen.getKomaFromPlace(i, j);
						if (banKoma == null) {
							move = new Move(koma.getPlaceA(), koma.getPlaceB(), i, j);
							moveList.add(move);
						}
					}
				}
			} else {
				// 盤上の駒の時
				for (int place: koma.getMovePlace()) {
					move = new Move();
					banKoma = kyokumen.getKomaFromPlace(place);
					if (banKoma == null || banKoma.getTeban() != kyokumen.getTeban()) {
						move.setPlace(koma.getPlaceA(), koma.getPlaceB(), place);
						moveList.add(move);
						
						// 成れる場合はそれも考慮
						if (koma.ableToNaru()) {
							if (move.ableNuru(koma.getTeban())) {
								move = move.clone();
								move.setNaru(true);
								moveList.add(move);
							}
						}
					}
				}
			}
		}
		System.out.println("局面");
		kyokumen.outputKyokumen();
		for (Move movet: moveList) movet.outputMoveTest();
		return moveList;
	}
}

















