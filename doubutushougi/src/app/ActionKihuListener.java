package app;

import board.Board;
import board.Move;

public class ActionKihuListener {
	private Board board;
	private Frames  frame;
	private StateGame state;
	
	public  ActionKihuListener (Board board, Frames frame, StateGame state) {
		this.board = board;
		this.frame = frame;
		this.state = state;
	}
	
	@Override
	public void actionPerformedFirst () {
		if (state.checkReadingKihu()) {
			board.setKyokumen(state.getKihu().getFirstKyokumen().clone());
		}
	}
}
