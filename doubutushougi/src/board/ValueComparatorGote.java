package board;

import java.util.Comparator;

import calcValue.Value;

public class ValueComparatorGote implements Comparator<Board>{
	@Override
	
	// b2が良ければ1を返す
	public int compare (Board b1, Board b2) {
		Value v1 = b1.getValue();
		Value v2 = b2.getValue();
		
		// 勝ちが決まっているなら何方でもいい。千日手もあり
		if (v1.getDetermine() || v2.getDetermine()) {
			if (v1.getValue() < 0) return 1;
			else if (v1.getValue() > 0) return -1;
			else return 0;
		} else {
			if (v1.getValue() < v2.getValue()) return 1;
			else if (v1.getValue() == v2.getValue()) return 0;
			else return -1;
		}
	}
	
	// b1が小さければtrue
	public static boolean compareBoardGote (Board b1, Board b2) {
		Value v1 = b1.getValue();
		Value v2 = b2.getValue();
		
		// 勝ちが決まっているなら何方でもいい。千日手もあり
		if (v1.getDetermine() || v2.getDetermine()) {
			if (v1.getValue() < 0) return true;
			else return false;
		} else {
			if (v1.getValue() < v2.getValue()) return true;
			else return false;
		}
	}
}
