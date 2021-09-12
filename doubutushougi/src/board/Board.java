package board;

import java.util.ArrayList;
import java.util.Collections;

import calcValue.Value;
import main.Kihu;
import main.Player;

public class Board {
	// 手番は局面から取る
	private Kyokumen kyokumen; // 局面
	private Player player; // プレイヤー 
	private Move beforeMove; // 直前のmove、棋譜を保存する時に使う
	private ArrayList<Board> boardList; // 候補のリスト、全ての手のリスト
	private Value value; // 評価値
	private static int maxDepth = 2; // 何手まで読めるようにするか
	private ArrayList<Kyokumen> kyokumenList; // 千日手チェックに必要
	private Kihu kihu; // 棋譜を保存する
	
	public Board (Kyokumen kyokumen, Player player, ArrayList<Kyokumen> kyokumenList, Kihu kihu) {
		this.kyokumen = kyokumen;
		this.player = player;
		this.kyokumenList = kyokumenList;
		boardList = new ArrayList<Board>();
		value = new Value();
		beforeMove = null;
		this.kihu = kihu;
	}
	
	// 上下結合出来ないか？
	public Board (Kyokumen kyokumen, Player player, ArrayList<Kyokumen> kyokumenList) {
		this.kyokumen = kyokumen;
		this.player = player;
		this.kyokumenList = kyokumenList;
		boardList = new ArrayList<Board>();
		value = new Value();
		beforeMove = null;
		this.kihu = new Kihu(kyokumen);
	}
}