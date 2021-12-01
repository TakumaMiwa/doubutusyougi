package board;

import java.util.Comparator;

import calcValue.Value;

public class ValueComparatorSente implements Comparator<Board>{
	@Override
	
	// b1が良ければ1を返す
	public int compare (Board b1, Board b2) {
		Value v1 = b1.getValue();
		Value v2 = b2.getValue();
		
		// 勝ちが決まっているなら何方でもいい。千日手もあり
		if (v1.getDetermine() || v2.getDetermine()) {
			if (v1.getValue() < 0) return -1;
			else if (v1.getValue() > 0) return 1;
			else return 0;
		} else {
			if (v1.getValue() < v2.getValue()) return -1;
			else if (v1.getValue() == v2.getValue()) return 0;
			else return 1;
		}
	}
	
	// b1が小さければtrue
	public static boolean compareBoardSente (Board b1, Board b2) {
		Value v1 = b1.getValue();
		Value v2 = b2.getValue();
		
		// 勝ちが決まっているなら何方でもいい。千日手もあり
		if (v1.getDetermine() || v2.getDetermine()) {
			if (v1.getValue() < 0) return false;
			else return true;
		} else {
			if (v1.getValue() < v2.getValue()) return false;
			else return false;
		}
	}
}
