package app;

import board.Board;
import board.Move;
import main.Kihu;

public class StateGame {
	// 1:何もしてない，2:対局中cp手番，3:対局中手番選択無し，4:対局中人間手番駒の選択をしている
	// 5:棋譜を読み込んでいる，6:フリー自由に動かせる，選択していない，7:選択されている
	private int state;
	private int beforePlace;
	private Board board;
	private Frames frame;
	
	public StateGame (Board board, Frames frame) {
		this.state = 1;
		this.board = board;
		this.frame = frame;
	}
	
	public void setStateValue (int i) {
		this.state = i;
	}
	
	public void setNotGaming () {
		beforePlace = 0;
		state = 1;
	}
	
	public void setBeforePlace (int beforeplace) {
		this.beforePlace = beforeplace;
		this.state = 4;
	}
	
	// 人間の手番の時true
	public boolean checkGamingPlayer () {
		if (this.state == 3 || this.state == 4) return true;
		else return false;
	}
	
	public boolean checkChoseAfrerPlace () {
		if (this.state == 4) return true;
		else return false;
	}
	
	public boolean checkNotGaming () {
		if (this.state == 1 || this.state == 5) return true;
		else return false;
	}
	
	public boolean checkNothing () {
		if (this.state == 1) return true;
		else return false;
	}
	
	public boolean checkChoseBeforeKoma () {
		if (this.state == 3) return true;
		else return false;
	}
	
	public boolean checkFreeBefore () {
		if (this.state == 6) return true;
		else return false;
	}
	
	public boolean checkFreeAfter () {
		if (this.state == 7) return true;
		else return false;
	}
	
	public int getBeforePlace () {
		return beforePlace;
	}
	
	public int getState () {
		return state;
	}
	
	public void setGaming (boolean player) {
		// 対局開始の状態にする
		if (player) {
			this.state = 2;
			// 次の一手を計算
			String teban = board.getTebanS();
			if (board.decideNextBoardNoParent(3) == null) {
				// 投了
//				frame.outputTouryou(teban);
				state = 1;
				frame.setTouryou();
				return;
			}
			Move move = board.getBeforeMove();
			frame.changePanel(move.getAfterPlaceA(), move.getAfterPlaceB(), board.getKyokumen());
			frame.changePanel(move.getBeforePlaceA(), move.getBeforePlaceB(), board.getKyokumen());
			move.outputMoveTest();
		}
	}
}



















