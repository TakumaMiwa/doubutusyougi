package main;

import java.util.Scanner;


// 先手後手が人かCPかを確認して保存
public class Player {
	private boolean playerSente;
	private boolean playerGote;
	
	public Player  () {
		playerSente = false;
		playerGote = false;
	}
	
	// 先手後手を決める
	public void decidePlayer () {
		Scanner scan = new Scanner(System.in);
		String str;
		
		// 先手決める
		while (true) {
			System.out.println("先手番を入力してください。人:human コンピュータ:cp");
			str = scan.next();
			if (str.equals("human")) {
				playerSente = false;
				break;
			} else if (str.equals("cp")) {
				playerSente = true;
				break;
			} else {
				System.out.println("入力が正しくありません。");
			}
		}
		
		// 後手決める
		while (true) {
			System.out.println("後手番を入力してください。人:human コンピュータ:cp");
			str = scan.next();
			if (str.equals("human")) {
				playerSente = false;
				break;
			} else if (str.equals("cp")) {
				playerGote = true;
				break;
			} else {
				System.out.println("入力が正しくありません。");
			}
		}
		
		outputPlayer();
	}
	
	public void outputPlayer () {
		if (playerSente) System.out.println("先手番: cp");
		else System.out.println("先手番: 人間");
		if (playerGote) System.out.println("後手番: cp");
		else System.out.println("後手番: 人間");
	}
	
	public String getPlayerBoth () {
		return getPlayerSenteString() + getPlayerGoteString();
	}
	
	private String getPlayerSenteString () {
		if (playerSente) return "先手番: cp";
		else return "先手番: 人間";
	}
	
	private String getPlayerGoteString () {
		if (playerGote) return "後手番: cp";
		else return "後手番: 人間";
	}
	
	public boolean getPlayerSente () {
		return playerSente;
	}
	
	public boolean getPlayerGote () {
		return playerGote;
	}
	
	public boolean getPlayer (int teban) {
		if (teban == 1) return playerSente;
		else if (teban == 2) return playerGote;
		else {
			System.out.println("error: Player.getPlayer");
			return false;
		}
	}
	
	public void setPlayerSente (boolean playerSente) {
		this.playerSente = playerSente;
	}
	
	public void setPlayerGote (boolean playerGote) {
		this.playerGote = playerGote;
	}
	
	// cpが含まれているならtrue
	public boolean containCp () {
		return playerSente || playerGote;
	}
}





















