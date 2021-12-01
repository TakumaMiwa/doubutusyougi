package app;
import koma.Koma;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import board.Board;
import board.Kyokumen;
import board.Move;

public class Frames extends FlowPane {
	private MenuPanel menupanel;
	private BanPanel banpanel;
	private KomadaiPanel sentepanel;
	private KomadaiPanel gotepanel;
	private Board board;
	private StateGame state;
	private Koma waitKoma;
	private ScrollPane textpanel;
	
	public Frames () {
		board = new Board(new Kyokumen());
		state = new StateGame(board, this);
	}
	
	public void setFrames () {
		this.setPrefSize(1000, 1000);
		this.setMaxSize(USE_PREF_SIZE, USE_PREF_SIZE);
		this.setMinSize(USE_PREF_SIZE, USE_PREF_SIZE);
		this.setPadding(new Insets(20, 20, 20, 20));
		this.setStyle("-fx-background-color: cornsilk;");
		menupanel = new MenuPanel(this);
		menupanel.setMenuPanel();
		menupanel.setAlignment(Pos.CENTER);
		this.getChildren().add(menupanel);
		gotepanel = new KomadaiPanel(this);
		gotepanel.setKomadaiPanel();
		gotepanel.setAlignment(Pos.TOP_CENTER);
		this.getChildren().add(gotepanel);
		banpanel = new BanPanel(this);
		banpanel.setBanPanel();
		banpanel.setAlignment(Pos.TOP_CENTER);
		this.getChildren().add(banpanel);
		sentepanel = new KomadaiPanel(this);
		sentepanel.setKomadaiPanel();
		sentepanel.setAlignment(Pos.BOTTOM_CENTER);
		this.getChildren().add(sentepanel);
		textpanel = new ScrollPane();
		textpanel.setVvalue(1.0);
		textpanel.setPrefSize(950, 200);
		textpanel.setMaxSize(USE_PREF_SIZE, USE_PREF_SIZE);
		textpanel.setMinSize(USE_PREF_SIZE, USE_PREF_SIZE);
		textpanel.setPadding(new Insets(20, 20, 20, 20));
		this.getChildren().add(textpanel);
		
	}
	
	public void startGame () {
		board.setPlayerSente(false);
		board.setPlayerGote(true);
		menupanel.setGaming();
//		kihupanel.setNotVisible();
	}
	
	
	public void endGame () {
		menupanel.setNotGaming();
	}
	
	public void setKyokumen (Kyokumen kyokumen) {
		banpanel.makeMasuFromBan(kyokumen.getBan());
		sentepanel.makeKomadaiPanel(kyokumen.getSenteKomadai());
		gotepanel.makeKomadaiPanel(kyokumen.getGoteKomadai());
		this.setVisible(true);
	}

	public void changePanel (int placeA, int placeB, Kyokumen kyokumen) {
		if (0 < placeA && placeA < 10) banpanel.makeMasuFromBan(kyokumen.getBan());
		else if (placeA == 10) sentepanel.makeKomadaiPanel(kyokumen.getSenteKomadai());
		else if (placeA == 20) gotepanel.makeKomadaiPanel(kyokumen.getGoteKomadai());
		this.setVisible(true);
	}

	// 新規対局時，盤面を初期状態にする
	public void setFirstKyokumen () {
		Text t = new Text(">対局を開始します。");
		t.setFill(Color.WHITE);
		textpanel.setContent(t);
		board = new Board(new Kyokumen()); 
		board.setFirst();
		startGame();
		state.setGaming(board.getTebanPlayer());
		banpanel.makeMasuFromBan(board.getKyokumen().getBan());
		sentepanel.makeKomadaiPanel(board.getKyokumen().getSenteKomadai());
		gotepanel.makeKomadaiPanel(board.getKyokumen().getGoteKomadai());
		return;
	}

	public void cellClickedBan (int i, int j) {
		switch (state.getState()) {
		case 1:
		case 3:
			System.out.println("state: "+state.getState());
			System.out.println("location: "+i+j);
			waitKoma = board.getKyokumen().getBanarray(i, j);
			if (waitKoma == null) return;
			if (waitKoma.getTeban() != board.getTeban()) return;
			state.setStateValue(4);
			System.out.println("state: "+state.getState());
			break;
		case 4:
			Koma koma = board.getKyokumen().getBanarray(i, j);
			if (koma == null) System.out.println("blank");
			if (10 * i + j == waitKoma.getPlace()) {
				state.setStateValue(3);
				break;
			}
			Move move = makeMove(waitKoma.getPlace(), 10 * i + j, waitKoma);
			move.outputMoveTest();
			if (checkGoNextKyokumen(move, waitKoma)) {
				board.moveNextKyokumen(move);
				System.out.println("teban: " + board.getTeban());
				banpanel.makeMasuFromBan(board.getKyokumen().getBan());
				sentepanel.makeKomadaiPanel(board.getKyokumen().getSenteKomadai());
				gotepanel.makeKomadaiPanel(board.getKyokumen().getGoteKomadai());
				if (move.getGetKoma() == 4) setTouryou();
				else if (waitKoma.getKomaNumber() == 4) {
					if (board.getTeban() == 1 && move.getAfterPlaceB() == 4) setTouryou();
					else if (board.getTeban() == 2 && move.getAfterPlaceB() == 1) setTouryou();
				}
			} 
			state.setStateValue(3);
			System.out.println("state: " + state.getState());
			break;
		}
	}
	
	public void cellClickedKomadai (Koma koma) {
		switch (state.getState()) {
		case 1:
		case 3:
			int l = 10;
			if (koma.getTeban() == 1) l = 10;
			else l = 20;
			System.out.println("state: "+state.getState());
			System.out.println("location: " + l);
			waitKoma = koma;
			if (waitKoma == null) return;
			if (waitKoma.getTeban() != board.getTeban()) return;
			state.setStateValue(4);
			System.out.println("state: "+state.getState());
			break;
		}
	}
	
	private Move makeMove (int bp, int ap, Koma koma) {
		Move move = new Move(bp / 10, bp % 10, ap / 10, ap % 10);
		if (move.checkAbleNaru(koma)) move.setNaru(true);
		return move;
	}

	// 次の局面にいけるか確認
	private boolean checkGoNextKyokumen (Move move, Koma koma) {
		
		// afterplaceが正しいか確認 KomaクラスのmovePlaceを見る
		int a = move.getAfterPlaceA();
		int b = move.getAfterPlaceB();
		
		// 持ち駒の時
		if (koma.getMotigoma()) {
			if (a==0 || b==0) return false;
			Koma afterKoma = board.getKyokumen().getBanarray(a, b);
			if (afterKoma==null) return true;
			return false;
		}
		if (koma.containMovePlace(a*10+b)) return true;
		return false;
	}
	
	public void setTouryou () {
		int teban = board.getTeban();
		Text t = new Text();
		if (teban == 1) t.setText(">後手番の勝利です。お疲れ様でした。");
		else if(teban == 2) t.setText(">先手番の勝利です。お疲れ様でした。");
		t.setFill(Color.WHITE);
		textpanel.setContent(t);
		endGame();
		return;
	}
	
	private void cpTeban () {
		board = board.decideNextBoardNoParent(1);
		banpanel.makeMasuFromBan(board.getKyokumen().getBan());
		sentepanel.makeKomadaiPanel(board.getKyokumen().getSenteKomadai());
		gotepanel.makeKomadaiPanel(board.getKyokumen().getGoteKomadai());
	}
}




























