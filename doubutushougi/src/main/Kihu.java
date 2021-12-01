package main;

import java.io.IOException;
import java.util.ArrayList;

import board.Kyokumen;
import board.Move;

public class Kihu {
	private ArrayList<Move> kihuList;
	private Kyokumen firstKyokumen;
	private Kyokumen lastKyokumen;
	
	public Kihu () {
		this.kihuList = new ArrayList<Move>();
	}
	
	public Kihu (Kyokumen kyokumen) {
		this.kihuList = new ArrayList<Move>();
		this.firstKyokumen = kyokumen.clone();
		this.lastKyokumen = null;
	}
	
	public Kihu (ArrayList<Move> kihuList, Kyokumen firstKyokumen) {
		this.kihuList = kihuList;
		this.firstKyokumen = firstKyokumen;
		this.lastKyokumen = null;
	}
	
//	public Kihu cloneKihu () {
//		ArrayList<Move> kihuListClone = new ArrayList<Move>();
//		for (Move move: kihuList) kihuListClone.add(move);
//		return new Kihu(kihuListClone, firstKyokumen.clone());
//	}
	
	public void makeLastKyokumen () {
		Kyokumen lastK = firstKyokumen.clone();
		for (Move move: kihuList) lastK.moveNextKyokumen(move);
		lastKyokumen = lastK;
	}
	
	public void outputKihu () {
		System.out.println("棋譜を出力します。");
		firstKyokumen.outputKyokumen();
		for (Move move: kihuList) move.outputMoveTest();
	}
	
	public void addMove (Move move) {
		kihuList.add(move);
	}
	
	public ArrayList<Move> getKihuList () {
		return kihuList;
	}
	
	public Move getLastMove () {
		if (kihuList == null || kihuList.isEmpty()) return null;
		else return kihuList.get(kihuList.size() - 1);
	}
	
	public void removeLastMove () {
		kihuList.remove(kihuList.size() - 1);
	}
	
	public Kyokumen getFirstKyokumen () {
		return firstKyokumen;
	}
	
	public Kyokumen getLastKyokumen () {
		return lastKyokumen;
	}
}

















