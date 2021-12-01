package main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import board.Kyokumen;
import board.Board;
import board.Move;

public class Main {
	
	public static void main (String[] args) throws IOException {
		
		System.out.println("対局を始めます。");
		Kyokumen kyokumen = new Kyokumen();
		
		// 先手後手のプレイヤーを決める
		Player player = new Player();
		player.decidePlayer();
		ArrayList<Kyokumen> kyokumenList = new ArrayList<Kyokumen>();
		Board board = new Board(kyokumen, player, kyokumenList);
		Kihu kihu = new Kihu();
		
		kyokumenList.add(board.getKyokumen().clone());
		for (Move move: kihu.getKihuList()) {
			board.getMoveNextBoard(move);
			kyokumenList.add(board.getKyokumen().clone());
		}
		kyokumenList.remove(kyokumenList.size() - 1);
		
		// 対局中。
		int i = 1;
		while (i < 1000) {
			System.out.println(i+"手目の入力をしてください。"+board.getTebanS());
			// 全ての駒のmovePlaceを決める
			 board.decideAllKomaMovePlace();
			 board.outputKyokumen();
			 kyokumenList.add(board.getKyokumen().clone());
			 
			 // 対局週力、中断、投了、待った
			 int checkInt = checkInterruption(); // 1:対局継続 2:待った 3:投了
			 if (checkInt == 2) {
				 if (i < 2) {
					 System.out.println("error: Main back 1");
					 break;
				 }
				 
				 // 待った、二手戻る
				 Move backMove = kihu.getLastMove();
				 kihu.removeLastMove();
				 board.moveBackKyokumen(backMove);
				 backMove = kihu.getLastMove();
				 kihu.removeLastMove();
				 board.moveBackKyokumen(backMove);
				 i -= 2;
			 } else if (checkInt == 1) {
				 // 継続
				 board = board.goNextBoard();
				 if (board == null) {
					 System.out.println("負けました。");
					 break;
				 }
				 
				 // 棋譜を保存
				 kihu.addMove(board.getBeforeMove());
				 System.out.println("指し手:");
				 board.getBeforeMove().outputMoveTest();
				 i++;
			 } else if (checkInt == 3) {
				 // 投了

				 System.out.println("負けました。");
				 break;
			 }
		}
		
		System.out.println("プログラムを終了します。");
	}
	
	// 対局を続けるか確認
	private static int checkInterruption () {
		Scanner scan = new Scanner(System.in);
		String str;
		
		while (true) {
			System.out.println("対局を続けますか？ n:次の手, back:待った, loss:負けました。 save:局面保存");
			str = scan.next();
			if (str.equals("n")) return 1;
			else if (str.equals("back")) return 2;
			else if (str.equals("loss")) return 3;
			else if (str.equals("save")) return 4;
			else System.out.println("入力が違います。");
		}
	}
}

















