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
		this.boardList = new ArrayList<Board>();
		this.value = new Value();
		this.beforeMove = null;
		this.kihu = kihu;
	}
	
	// 上下結合出来ないか？
	public Board (Kyokumen kyokumen, Player player, ArrayList<Kyokumen> kyokumenList) {
		this.kyokumen = kyokumen;
		this.player = player;
		this.kyokumenList = kyokumenList;
		this.boardList = new ArrayList<Board>();
		this.value = new Value();
		this.beforeMove = null;
		this.kihu = new Kihu(kyokumen);
	}
	
	public Board (Kyokumen kyokumen) {
		this.kyokumen = kyokumen;
		this.player = new Player();
		this.kyokumenList = new ArrayList<Kyokumen>();
		this.boardList = null;
		this.value = new Value();
		this.beforeMove = null;
		this.kihu = new Kihu(kyokumen);
	}
	
//	// 棋譜読み込み
//	public void readKihu (String loadFile) {
//		setFirst();
//		kihu.readKihuFromFirstKyokumen(loadFile);
//		kyokumen = kihu.getLirstKyokumen().clone();
//	}
//	
//	// 局面読み込み
//	public void readKyokumen (String loadFile) {
//		setFirst();
//		kyokumen.loadKyokumen(loadFile);
//	}
//	
//	// 局面を保存
//	public void saveKyokumen(String saveFile) {
//		kyokumen.saveKyokumen(saveFile);
//	}
	
	// 初期盤面にする
	public void setFirst () {
		this.kyokumen = new Kyokumen();
		this.player = new Player();
		this.beforeMove = null;
		this.boardList = new ArrayList<Board>();
		this.value = new Value();
		this.kyokumenList = new ArrayList<Kyokumen>();
		this.kihu = new Kihu();
	}
	
	// 局面以外リセット
	public void resetExceptKyokumen () {
		decideAllKomaMovePlace();
		this.player = new Player();
		this.beforeMove = null;
		this.boardList = new ArrayList<Board>();
		this.value = new Value();
		this.kyokumenList = new ArrayList<Kyokumen>();
		this.kihu = new Kihu(kyokumen);
	}
	
	// 全ての駒のmovePlaceを決める
	public void decideAllKomaMovePlace() {
		kyokumen.decideAllKomaMovePlace();
	}
	
	// 引数のboardに変える
	private void setBoard (Board board) {
		this.kyokumen = board.getKyokumen();
		this.beforeMove = board.getBeforeMove();
		this.boardList = board.getBoardList();
		this.value = board.getValue();
		this.kyokumenList = board.getKyokumenList();
		this.kihu = board.getKihu();
	}
	
	// 千日手か確認
	public int checkSennitite (ArrayList<Kyokumen> kyokumenList) {
		return CalcKyokumenFoul.checkSennitite(kyokumenList, kyokumen);
	}
	
	// 千日手か確認
	public int checkSennitite () {
		return CalcKyokumenFoul.checkSennitite(kyokumenList, kyokumen);
	}
	
	// 詰んでいるか確認
	public boolean checkTumi () {
		Board bb = new Board(kyokumen.clone());
		Board nextBoard = bb.decideNextBoardNoParent(maxDepth);
		if (nextBoard==null) return true; //　行く場所が無い、詰み
		else return false;
	}
	
	// 局面を出力
	public void outputKyokumen () {
		System.out.println("value: "+value.getValue());
		kyokumen.outputKyokumen();
	}
	
	// Moveをもらって見る
	public void moveBackKyokumen (Move move) {
		kyokumen.moveBackKyokumen(move);
	}
	
	// moveから次の局面になるboardを返す
	public Board getMoveNextBoard (Move move) {
		kyokumen.clone().moveNextKyokumen(move);
		if (boardList!=null && !boardList.isEmpty()) {
			for (Board b:boardList) if (move.equalsMove(b.getBeforeMove())) return b;
		}
		
		ArrayList<Kyokumen> kyokumenListN = new ArrayList<Kyokumen>();
		for (Kyokumen k:kyokumenList) kyokumenListN.add(k);
		Board board = new Board(kyokumen.clone(), player, kyokumenListN);
		board.moveNextKyokumen(move);
		return board;
	}
	
	// moveをもらって局面を進める
	public void moveNextKyokumen (Move move) {
		kyokumen.moveNextKyokumen(move); // 最終的なmoveを決定
		if (boardList!=null && !boardList.isEmpty()) {
			for (Board b:boardList) {
				if (move.equalsMove(b.getBeforeMove())) {
					setBoard(b);
					return;
				}
			}
		}
		kyokumenList.add(kyokumen.clone());
		kihu.addMove(move.clone());
		beforeMove = move;
	}
	
	// 次の局面を行く
//	public Board goNextBoard () {
//		Board board = null;
//		Move move = null;
//		if (player.getPlayer(getTeban())) {
//			board = decideNextBoardNoParent(1); //コンピュータの場合、候補手の数だけ探索
//		} else {
//			// moveを受け取る、反則をはじかない
//			CalcMoveHuman calcMoveHuman = new CalcMoveHuman(kyokumen);
//			move = calcMoveHuman.getMove();
//			kyokumenList.add(kyokumen.clone());
//			board = new Board(kyokumen, player, kyokumenList);
//			board.moveNextKyokumen(move);
//		}
//		return board;
//	}
	
	// 次の局面が無ければ0を返す
	public int goNextBoardFromSwing () {
		System.out.println("calc next te!");
		Board board = decideNextBoardNoParent(1);
		if (board==null) return 0;
		setBoard(board);
		return 1;
	}
	
	//boardListを先手なら評価値の高い順に並び変える (後手なら低い順)
	private void sortBoardList () {
		if (getTeban()==1) Collections.sort(boardList, new ValueComparatorGote());
		else Collections.sort(boardList, new ValueComparatorSente());
	}
	
	// boardListを作り、次の局面を返す
	public Board decideNextBoardNoParent (int depth) {
		// boardListは一回しか作らない
		System.out.println("decideNextBoardNoParent: " + getTeban());
		if (boardList==null || boardList.isEmpty()) makeBoardList();
		selectBoardList();
		if (boardList==null || boardList.isEmpty()) {
			// 負けている
			value.setDetermine(true);
			if (getTeban()==1) value.setValue(-100);
			else value.setValue(100);
			
			return null;
		}
		sortBoardList();
		if (depth==1) System.out.println("sorted board list size: "+boardList.size());
		if (depth>=maxDepth) {
			Board board = boardList.get(0); // 1手先で最善
			// ここでvalueの値も一つ下の値と同じに更新
			value.setValueAll(board.getValue());
			return board;
		}
		// a-b法を用いる
		Board nextBoard = null; // 次の局面でその選択肢はあるか
		Board boardExpect = null;
		
		int n = 1;
		for (Board board: boardList) {
			if (depth==1) System.out.println("count "+n++);
			if (boardExpect == null) {
				// 最初
				if (!board.getValue().getDetermine()) {
					// 評価値が未確定なら探索
					nextBoard = board.decideNextBoardNoParent(depth+1);
					if (nextBoard==null) {
						// 勝っている。boardを選べば次相手は負ける。
						value.setWin(getTeban());
						return board;
					}
				}
				// 勝敗が決まっているなら探索を終了すべき
				// 親の評価値より自分の評価値がよければ終了
				boardExpect = board;
			} else {
				// boardExpectがあるとき
				if (!board.getValue().getDetermine()) {
					// 評価値が未確定なら探索
					nextBoard = board.decideNextBoardNoParent(depth+1, boardExpect);
					if (nextBoard==null) {
						// 勝っている。boardを選べば次相手は負ける。
						value.setWin(getTeban());
						return board;
					}
				}
				
				if (compareBoard(board, boardExpect)) {
					// boardの方が良い時
					boardExpect = board;
				}
			}
		}
		value.setValueAll(boardExpect.getValue());
		return boardExpect;
	}
	
	// boardListを作り、次の局面を返す
	private Board decideNextBoardNoParent (int depth, Board boardParent) {
		// boardListは一回しか作らない
		if (boardList==null || boardList.isEmpty()) makeBoardList();
		selectBoardList();
		if (boardList==null || boardList.isEmpty()) {
			// 負けている
			value.setDetermine(true);
			if (getTeban()==1) value.setValue(-100);
			else value.setValue(100);
			
			return null;
		}
		sortBoardList();
		if (depth==1) System.out.println("sorted board list size: "+boardList.size());
		if (depth>=maxDepth) {
			Board board = boardList.get(0); // 1手先で最善
			// ここでvalueの値も一つ下の値と同じに更新
			value.setValueAll(board.getValue());
			return board;
		}
		// a-b法を用いる
		Board nextBoard = null; // 次の局面でその選択肢はあるか
		Board boardExpect = null;
		
		int n = 1;
		for (Board board: boardList) {
			if (depth==1) System.out.println("count "+n++);
			if (boardExpect == null) {
				// 最初
				if (!board.getValue().getDetermine()) {
					// 評価値が未確定なら探索
					nextBoard = board.decideNextBoardNoParent(depth+1);
					if (nextBoard==null) {
						// 勝っている。boardを選べば次相手は負ける。
						value.setWin(getTeban());
						return board;
					}
				}
				
				// 勝敗が決まっているなら探索を終了すべき
				// 親の評価値より自分の評価値がよければ終了
				if (compareBoard(board, boardParent)) {
					value.setValueAll(board.getValue());
					return board;
				}
				boardExpect = board;
			} else {
				// boardExpectがあるとき
				if (!board.getValue().getDetermine()) {
					// 評価値が未確定なら探索
					nextBoard = board.decideNextBoardNoParent(depth+1, boardExpect);
					if (nextBoard==null) {
						// 勝っている。boardを選べば次相手は負ける。
						value.setWin(getTeban());
						return board;
					}
				}
				
				if (compareBoard(board, boardExpect)) {
					// boardの方が良い時
					boardExpect = board;
					
					// 親の評価値より自分の評価値がよければ終了
					if(compareBoard(board, boardParent)) {
						value.setValueAll(board.getValue());
						return board;
					}
				}
			}
		}
		value.setValueAll(boardExpect.getValue());
		return boardExpect;
	}
	
	// b1の評価値が高ければtrue
	private boolean compareBoard (Board b1, Board b2) {
		if (getTeban()==1) return ValueComparatorSente.compareBoardSente(b1, b2);
		else if (getTeban()==2) return ValueComparatorGote.compareBoardGote(b1, b2);
		System.out.println("error:teban in Board class compareBoard method");
		return true;
	}
	
	// 勝っているboardを選択
	private void selectBoardList () {
		ArrayList<Board> removeList = new ArrayList<Board>();
		Value value = null;
		for (Board board: boardList) {
			value = board.getValue();
			if (value.getDetermine() && value.getValue()!=0) {
				// 勝敗が決まっている
				// 引き分けは除かない
				if (value.getWin(getTeban())) {
					// 勝ち
					ArrayList<Board> winList = new ArrayList<Board>();
					winList.add(board);
					boardList = winList;
					return;
				} else {
					// 負け
					removeList.add(board);
				}
			}
		}
		for (Board board:removeList) boardList.remove(board);
	}
	
	// 全ての手をリストに収納
	private void makeBoardList () {
		boardList = MakeBoardList.getNextBoardList(this);
		// それぞれのboardで評価値を算出
		for (Board board: boardList) board.setValueThis();
	}
	
	// 評価値を読む
	public void setValueThis () {
		value.calcValuePresent(kyokumen, kyokumenList, beforeMove);
	}
	
	// 評価値を出力
	public void outputValueTest () {
		value.calcValuePresent(kyokumen, kyokumenList, beforeMove);
		System.out.println("評価値は: "+value.getValue());
	}
	
	// 手番を返す
	public String getTebanS () {
		if (getTeban()==1) return "先手";
		else if (getTeban()==2) return "後手";
		return "error";
	}
	
	//　相手の手番を返す
	public String getTebanOppositeS () {
		if (getTeban()==2) return "先手";
		else if (getTeban()==1) return "後手";
		return "error";
	}
	
	// 一手前のmoveを返す
	public Move getBeforeMove () {
		return beforeMove;
	}
	
	// 評価値を返す
	public Value getValue () {
		return value;
	}
	
	// 一手前のmoveを保存
	public void setBeforeMove (Move move) {
		this.beforeMove = move;
	}
	
	// 局面を返す
	public Kyokumen getKyokumen () {
		return kyokumen;
	}
	
	// 盤面のリストを返す
	public ArrayList<Board> getBoardList () {
		return boardList;
	}
	
	// 手番を返す
	public int getTeban () {
		return kyokumen.getTeban();
	}
	
	// 先手プレイヤーを読む
	public void setPlayerSente (boolean sente) {
		this.player.setPlayerSente(sente);
	}
	
	// 後手プレイヤーを読む
	public void setPlayerGote (boolean gote) {
		this.player.setPlayerGote(gote);
	}
	
	// 両方のプレイヤーを返す
	public String getPlayerBoth () {
		return this.player.getPlayerBoth();
	}
	
	// 手番のプレイヤーを返す
	public boolean getTebanPlayer () {
		if (getTeban()==1) return this.player.getPlayerSente();
		else return this.player.getPlayerGote();
	}
	
	//  局面のリストを返す
	public ArrayList<Kyokumen> getKyokumenList () {
		return kyokumenList;
	}
	
	// 棋譜を返す
	public Kihu getKihu () {
		return kihu;
	}
	
	// 棋譜を保存
//	public void saveKihu (String fileName) {
//		kihu.saveKihuFromFirstKyokumen(fileName);
//	}
	
	// 最後に保存した局面を削除
	public void deleteLastKyokumenList () {
		kyokumenList.remove(kyokumenList.size()-1);
	}
	
	// プレイヤーにcpが含まれていればtrue
	public boolean containCp () {
		return player.containCp();
	}
	
	// 局面を読み込む
	public void setKyokumen(Kyokumen kyokumen) {
		this.kyokumen = kyokumen;
	}
	
	// 棋譜を読み込む
	public void setKihu (Kihu kihu) {
		this.kihu = kihu;
	}
}


















