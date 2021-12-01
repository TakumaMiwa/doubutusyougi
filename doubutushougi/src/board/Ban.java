package board;


import koma.Hiyoko;
import koma.Kirin;
import koma.Koma;
import koma.Lion;
import koma.Zou;

public class Ban {
	private Koma banarray[][] = new Koma[4][5];
	public Ban() {
		setFirstBan();
	}
	public Ban(Koma[][] banarray) {
		this.banarray = banarray;
	}
	
	// 初期盤面生成
	public void setFirstBan() {
		// 盤面全てをnull
		for (int i=1; i<4; i++) {
			for (int j=1;j<5;j++) {
				setBanarray(i, j, null);
			}
		}
		
		// 駒配置
		// ひよこ
		Koma hiyoko1 = new Hiyoko(1);
		Koma hiyoko2 = new Hiyoko(2);
		setBanarray(2, 3, hiyoko1);
		setBanarray(2, 2, hiyoko2);
		
		// ぞう
		Koma zou1 = new Zou(1);
		Koma zou2 = new Zou(2);
		setBanarray(1, 4, zou1);
		setBanarray(3, 1, zou2);
		
		// キリン
		Koma kirin1 = new Kirin(1);
		Koma kirin2 = new Kirin(2);
		setBanarray(1, 1, kirin2);
		setBanarray(3, 4, kirin1);
		
		// ライオン
		Koma lion1 = new Lion(1);
		Koma lion2 = new Lion(2);
		setBanarray(2, 4, lion1);
		setBanarray(2, 1, lion2);
	}
	
	// 指定した座標に駒をセット
	public void setBanarray(int a, int b, Koma koma) {
		if (0<a && a<4 && 0<b && b<5) {
			banarray[a][b] = koma;
			if (koma!=null) koma.setPlace(a, b);
		}
	}
	
	// 指定した座標の駒を得る
	public Koma getBanarray(int a, int b) {
		if (0<a && a<4 && 0<b && b<5) {
			return banarray[a][b];
		}
		return null;
	}
	
	// クローン生成
	public Ban clone() {
		Koma array[][] = new Koma[4][5];
		for (int i=1; i<4; i++) {
			for (int j=1;j<5;j++) {
				array[i][j] = banarray[i][j];
			}
		}
		return new Ban(array);
	}
	
	// 同値判定
	public boolean equal(Ban ban) {
		for (int i=1; i<4; i++) {
			for (int j=1;j<5;j++) {
				if(banarray[i][j]==null) {
					if(ban.getBanarray(i, j)!=null) {
						return false;
					}
				} else {
					if(!banarray[i][j].equal(ban.getBanarray(i, j))) {
						return false;
					}
				}
			}
		}
		return true;
	}
	
	// 盤面出力
	public void outputBan() {
		System.out.println("盤面を出力します。");
		Koma koma = null;
		for (int i=1; i<4; i++) {
			for (int j=4;j>0;j--) {
				koma = getBanarray(i, j);
				if (koma==null) {
					System.out.print("　　　");
				} else {
					koma.outputKoma();
				}
			}
			System.out.println("");
		}
	}
	
	public void removeBanArray (int i, int j) {
		banarray[i][j] = null;
	}
	
//	// ファイルへの書き出し
//	public void saveFile(FileWriter fileWriter) {
//		try {
//			for (int i=0; i<4; i++) {
//				for (int j=0;j<5;j++) {
//					if (banarray[i][j]==null) {
//						fileWriter.write("0\n");
//					} else {
//						banarray[i][j].saveFile(fileWriter);
//					}
//				}
//			}
//		} catch (IOException e) {
//			// TODO 自動化された catch ブロック
//			e.printStackTrace();
//		}
//	}
	
	// ファイルの読み込み。正しければtrue
//	public boolean loadFile (BufferedReader br) {
//		try {
//			String str = null;
//			for (int i=0;i<4;i++) {
//				for (int j=0;j<5;j++) {
//					str = br.readLine();
//					if (str.equals("0")) {
//						banarray[i][j] = null;
//					} else {
//						Koma koma = CalcKoma.makeLoad_Koma(str);
//						if (koma==null) {
//							return false;
//						}
//						banarray[i][j] = koma;
//					}
//				}
//			}
//		} catch (IOException e) {
//			// TODO 自動化された catch ブロック
//			e.printStackTrace();
//			return false;
//		}
//		return true;
//	}
}
