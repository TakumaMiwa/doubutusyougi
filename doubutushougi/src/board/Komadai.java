package board;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import koma.CalcKoma;
import koma.Koma;
import main.Calc;

public class Komadai {
	// 0:なし, 1:ひよこ, 2:ぞう, 3:キリン, 4:ライオン
	private int komadai[] = new int[5];
	private int teban;
	
	public Komadai (int[] komadai, int teban) {
		this.komadai = komadai;
		this.teban = teban;
	}
	
	public Komadai (int teban) {
		for (int i=0;i<5;i++) komadai[i] =0;
		this.teban = teban;
	}
	
	public Komadai clone () {
		int dai[] = new int[5];
		for (int i=0;i<5;i++) dai[i] = komadai[i];
		return new Komadai(dai, teban);
	}
	
	public boolean equal (Komadai komadaiN) {
		if (this.teban != komadaiN.getTeban()) return false;
		for (int i=0;i<5;i++) if (komadai[i] != komadaiN.getKomaNumber(i)) return false;
		return true;
	}
	
	 // ファイルを保存
	public void saveFile (FileWriter fileWriter) {
		try {
			fileWriter.write(getTebanS()+"\n");
			for (int i=0;i<5;i++) fileWriter.write(komadai[i] + ",");
			fileWriter.write("\n");
		} catch (IOException e) {
			// TODO 自動生成されたcatchブロック
			e.printStackTrace();
		}
	}
	
	// ファイルの読み込み
	public boolean loadFile (BufferedReader br) {
		try {
			// 手番があっているか確認
			String str = br.readLine();
			if (Calc.isNumber(str)) {
				int teban = Integer.parseInt(str);
				if (this.teban != teban) return false;
			} else {
				return false;
			}
			str = br.readLine();
			String[] komadaiS = str.split(",", 0);
			if (komadaiS.length != 5) {
				System.out.println("The length of komadai is incorrect!");
				return false;
			}
			for (int i=0;i<5;i++) {
				if (Calc.isNumber(komadaiS[i])) komadai[i] = Integer.parseInt(komadaiS[i]);
				else return false;
			}
		} catch (IOException e) {
			// TODO 自動生成されたcatchブロック
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	// 駒台にある駒のポイント化
	public int getAllPointTeban () {
		Koma koma = null;
		int allPoint = 0;
		for (int i=0;i<5;i++) {
			koma = getKomadai(i);
			if (koma != null) allPoint += koma.getPointTeban() * komadai[i];
		}
		return (int)(allPoint * 1.1); // 持ち駒は持っていた方が得
	}
	
	// 駒台にある駒をリストで返す
	public ArrayList<Koma> getKomadaiAllKoma () {
		ArrayList<Koma> komaList = new ArrayList<Koma>();
		Koma koma = null;
		for (int i=0;i<5;i++) {
			koma = getKomadai(i);
			if (koma != null) komaList.add(koma);
		}
		return komaList;
	}
	
	// 駒台の出力
	public void outputKomadai () {
		if (teban==1) System.out.println("先手の駒台");
		else System.out.println("後手の駒台");
		
		for (int i=0;i<5;i++) if (komadai[i] > 0) System.out.println(changeNS(i)+":"+"枚　");
		System.out.println("");
	}
	
	// 駒を打つ
	public void decreaseKomadai (Koma koma) {
		if (koma.getTeban() != teban) {
			System.out.println("error: Komadai decreaseKomadai teban");
			return;
		}
		
		int n = koma.getKomaNumber();
		if (komadai[n] < 1) {
			System.out.println("Komadai doesn't have it");
			return;
		}
		komadai[n] -= 1;
	}
	
	// 駒を打つ
	public void decreaseKomadaiKomaNumber (int komaNumber) {
		if (komaNumber < 1 || 4 < komaNumber) System.out.println("error: decreaseKomadaiKomaNumber");
		else if (komadai[komaNumber] < 1) System.out.println("Komadai doesn't have it");
		else komadai[komaNumber] -= 1;
	}
	
	// 駒を駒台に加える
	public void setKomadai (Koma koma) {
		int n = koma.getKomaNumber();
		if (n > 10) n -= 10;
		komadai[n] += 1;
	}
	
	// placeに持ち駒があればそれを返す
	public Koma getKomadai (int place) {
		if (komadai[place] < 1) return null;
		Koma koma = null;
		koma = CalcKoma.makeKoma(place, teban);
		koma.setMotigoma(true);
		koma.setPlace(teban*10, place);
		return koma;
	}
	
	// 駒の枚数を返す
	public int getKomaNumber (int place) {
		return komadai[place];
	}
	
	// 手番を返す
	public int getTeban () {
		return teban;
	}
	
	// 手番を文字列で返す
	private String getTebanS () {
		if (teban==1) return "1";
		else if (teban==2) return "2";
		else return "error";
	}
	
	// 駒番号を文字に変換
	private String changeNS (int n) {
		String st;
		switch (n) {
		case 0:
			st = "  ";
			break;
		case 1:
			st = "　ひ";
			break;
		case 2:
			st = "　象";
			break;
		case 3:
			st = "　キ";
			break;
		case 4:
			st = "　ラ";
			break;
		case 11:
			st = "　に";
			break;
		default:
			st = "error";
			break;
		}
		return st;
	}
}












