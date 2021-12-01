package board;
import java.util.ArrayList;

import koma.CalcKoma;
import koma.Koma;

// 局面を保存する。
// 0:なし 1:ひよこ 2:ぞう 3:キリン 4:ライオン
// 10の位　0:普通 1:成駒
// 100の位　1:先手の駒 2:後手の駒
public class Kyokumen {
	private Komadai senteKomadai;
	private Komadai goteKomadai;
	private Ban ban;
	private int teban; // 1:先手 2:後手
	private ArrayList<Koma> komaArray; // 全ての駒のリスト。駒を探しやすいように、盤上にある駒のみ
	
	public Kyokumen () {
		// 入力無しなら初期化
		this.ban = new Ban();
		this.senteKomadai = new Komadai(1);
		this.goteKomadai = new Komadai(2);
		this.teban = 1;
		this.komaArray = new ArrayList<Koma>();
		setKomaArray();
		decideAllKomaMovePlace();
	}
	
	public Kyokumen (Ban ban, Komadai senteKomadai, Komadai goteKomadai, int teban) {
		// 入力ありのとき
		this.ban = ban;
		this.senteKomadai = senteKomadai;
		this.goteKomadai = goteKomadai;
		this.teban = teban;
		this.komaArray = new ArrayList<Koma>();
		setKomaArray();
		decideAllKomaMovePlace();
	}
	
	// クローン作成
	public Kyokumen clone () {
		return new Kyokumen(ban.clone(), senteKomadai.clone(), goteKomadai.clone(), teban);
	}
	
	// 局面の同値性を判別
	public boolean equal (Kyokumen kyokumen) {
		if (this.teban != kyokumen.getTeban()) return false;
		else if (!this.senteKomadai.equal(kyokumen.getSenteKomadai())) return false;
		else if (!this.goteKomadai.equal(kyokumen.getGoteKomadai())) return false;
		else if (!this.ban.equal(kyokumen.getBan())) return false;
		else return true;
	}
	
	// 全ての駒のクローンを作って置き換える
	public void cloneAllKoma () {
		Koma newKoma = null;
		ArrayList<Koma> komaArrayNew = new ArrayList<Koma>();
		for (Koma koma: komaArray) {
			newKoma = koma.clone();
			komaArrayNew.add(newKoma);
			setKomaFromPlace(newKoma, newKoma.getPlaceA(), newKoma.getPlaceB());
		}
		komaArray = komaArrayNew;
	}
	
	// 盤上の自駒を返す
	public ArrayList<Koma> getTebanKomaListBan (int teban) {
		ArrayList<Koma> myKomaList = new ArrayList<Koma>();
		for (Koma koma: komaArray) if (koma.getTeban() == teban) myKomaList.add(koma);
		return myKomaList;
	}
	
	// 自駒を全て返す
	public ArrayList<Koma> getTebanKomaListAll (int teban) {
		ArrayList<Koma> myKomaList = getTebanKomaListBan(teban);
		for (Koma koma: getKomadai(teban).getKomadaiAllKoma()) myKomaList.add(koma);
		return myKomaList;
	}
	
	// ライオンの場所を返す
	public int getPlaceLion (int teban) {
		for (Koma koma: komaArray) if (koma.getKomaNumber() == 4 && koma.getTeban() == teban) return koma.getPlace();
		return 0;
	}
	
	// 打った駒を駒台から消す
	public void decreaseMotigoma(Koma koma) {
		if (koma.getTeban() == 1) senteKomadai.decreaseKomadai(koma);
		else goteKomadai.decreaseKomadai(koma);
	}
	
	// 前の局面へ移動
	public void moveBackKyokumen (Move move) {
		// 動いた駒を元に戻す
		Koma moveKoma = getKomaFromPlace(move.getAfterPlaceA(), move.getAfterPlaceB());
		removeKomaArray(moveKoma);
		if (move.getNaru()) moveKoma.getNarazukoma();
		if (move.getBeforePlaceA() < 10) {
			// 盤上移動の時
			ban.setBanarray(move.getBeforePlaceA(), move.getBeforePlaceB(), moveKoma);
			addKomaArray(moveKoma);
		} else {
			// 駒を打った時
			moveKoma.changeTeban();
			setKomaFromPlace(moveKoma, move.getAfterPlaceA(), move.getAfterPlaceB());
		}
		
		// 取られた駒を復元
		if (move.getGetKoma() == 0) {
			changeTeban();
			ban.setBanarray(move.getAfterPlaceA(), move.getAfterPlaceB(), null);
		} else {
			Koma koma = CalcKoma.makeKoma(move.getGetKoma(), teban);
			ban.setBanarray(move.getAfterPlaceA(), move.getAfterPlaceB(), koma);
			int komaNumber = move.getGetKoma();
			if (komaNumber > 9) komaNumber -= 10;
			
			// 駒台から無くす
			if (teban == 1) goteKomadai.decreaseKomadaiKomaNumber(komaNumber);
			else senteKomadai.decreaseKomadaiKomaNumber(komaNumber);
			changeTeban();
			addKomaArray(koma);
		}
		decideAllKomaMovePlace();
	}
	
	// moveをもらって次の局面へ
	public void moveNextKyokumen (Move move) {
		int a = move.getBeforePlaceA();
		int b = move.getBeforePlaceB();
		
		Koma koma = getKomaFromPlace(a, b);
		if (a != 10 && a != 20) ban.removeBanArray(a, b);
		a = move.getAfterPlaceA();
		b = move.getAfterPlaceB();
		Koma getKoma = getBanarray(a, b);
		if (koma.getMotigoma()) {
			koma.setMotigoma(false);
			decreaseMotigoma(koma);
			a = move.getAfterPlaceA();
			b = move.getAfterPlaceB();
			ban.setBanarray(a, b, koma);
			addKomaArray(koma);
		} else {
			ban.setBanarray(a, b, null);
			if (getKoma != null) {
				// 駒を取っていた場合
				if (teban == 1) senteKomadai.setKomadai(getKoma);
				else goteKomadai.setKomadai(getKoma);
				
				// 取った駒は盤から消す
				komaArray.remove(getKoma);
				move.setGetKoma(getKoma.getKomaNumber());
			}
			
			// 成れるか確認
			if (move.getNaru()) {
				removeKomaArray(koma);
				koma = koma.getNarigoma();
				addKomaArray(koma);
			}
			ban.setBanarray(a, b, koma);
		}
		changeTeban();
		decideAllKomaMovePlace();
	}
	
	private void changeTeban () {
		if (teban == 1) teban = 2;
		else teban = 1;
	}
	
	// 全ての駒の移動場所を決める
	public void decideAllKomaMovePlace () {
		for (Koma koma: komaArray) {
			koma.movePlaceClear();
			koma.decideMovePlace(this);
		}
	}
	
	// 駒を追加する
	public void addKomaArray (Koma koma) {
		if (koma.getPlace() > 34) System.out.println("errlr: addKomaArray");
		else komaArray.add(koma);
	}
	
	// 駒を削除する
	public void removeKomaArray (Koma koma) {
		komaArray.remove(koma);
	}
	
	// komaArrayを作る
	public void setKomaArray () {
		komaArray = new ArrayList<Koma>();
		Koma koma = null;
		
		// 盤
		for (int i=1;i<4;i++) {
			for(int j=1;j<5;j++) {
				koma = ban.getBanarray(i, j);
				if (koma != null) {
					komaArray.add(koma);
					if (koma.getPlace() > 34) System.out.println("error: setKomaArray");
				}
			}
		}
	}
	
	// 局面を出力する
	public void outputKyokumen () {
		// 出力
		System.out.println("局面を出力");
		goteKomadai.outputKomadai();
		ban.outputBan();
		senteKomadai.outputKomadai();
	}
	
	// komaArrayを出力
	public void outputKomaArray () {
		System.out.println("komaArrayを出力");
		for (Koma koma: komaArray) koma.outputMovePlace();
	}
	
	// 任意の場所の駒を返す
	public Koma getKomaFromPlace (int placeA, int placeB) {
		
		Koma koma;
		if (placeA == 10) koma = senteKomadai.getKomadai(placeB);
		else if (placeA == 20) koma = goteKomadai.getKomadai(placeB);
		else koma = ban.getBanarray(placeA, placeB);

		return koma;
	}
	
	// 駒の場所を探す
	public Koma getKomaFromPlace (int place) {
		return getKomaFromPlace(place/10, place%10);
	}
	
	// 場所に駒をセットする
	public void setKomaFromPlace (Koma koma, int placeA, int placeB) {
		if (placeA < 10) ban.setBanarray(placeA,  placeB, koma);
		else if (placeA == 10) senteKomadai.setKomadai(koma);
		else if (placeA == 20) goteKomadai.setKomadai(koma);
	}

	// 先手の駒台の駒を得る
	public Koma getSenteKomadaiKoma (int placeB) {
		return senteKomadai.getKomadai(placeB);
	}
	
	// 後手の駒台の駒を得る
	public Koma getGoteKomadaiKoma (int placeB) {
		return goteKomadai.getKomadai(placeB);
	}
	
	// 先手の駒台を得る
	public Komadai getSenteKomadai () {
		return senteKomadai;
	}
	
	// 後手の駒台を得る
	public Komadai getGoteKomadai () {
		return goteKomadai;
	}
	
	// 選んだ手番の駒台を得る
	public Komadai getKomadai (int teban) {
		if (teban == 1) return getSenteKomadai();
		else if (teban == 2) return getGoteKomadai();
		else {
			System.out.println("error: getkomadai.");
			return null;
		}
	}
	
	// 指定した座標の駒を得る
	public Koma getBanarray (int a, int b) {
		if (0 < a && a < 4 && 0 < b && b < 5) return ban.getBanarray(a,  b);
		else return null;
	}
	
	// 指定した座標の駒を得る
	public Koma getBanarray (int place) {
		int a = place / 10;
		int b = place % 10;
		return getBanarray(a, b);
	}
	
	// 手番を得る
	public int getTeban () {
		return teban;
	}
	
	// komaArrayを得る
	public ArrayList<Koma> getKomaArray () {
		return komaArray;
	}
	
	// 盤の情報を得る
	public Ban getBan () {
		return ban;
	}
	
	public String getTebanString () {
		if (teban == 1) return "先手";
		else if (teban == 2) return "後手";
		else return "error: getTebanString()";
	}
	
	public void setBanKomadai (Ban ban, Komadai sente, Komadai gote) {
		this.ban = ban;
		this.senteKomadai = sente;
		this.goteKomadai = gote;
	}
	
	
	
 }

















