package app;

import javafx.geometry.Insets;
import javafx.scene.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import board.Ban;
import koma.Koma;

public class BanPanel extends GridPane {
	private String[] imageList = {"Kirin.png", "Zou.png", "Hiyoko.png", "Niwatori.png", "Lion.png"};
	private Ban ban;
	private Frames frame;
	
	public BanPanel (Frames frame) {
		this.frame = frame;
	}

	public void setBanPanel () {
		this.setStyle("-fx-background-color: dodgerblue;");
		this.setOpacity(0.7);
		this.setPrefSize(450, 460);
		this.setMaxSize(USE_PREF_SIZE, USE_PREF_SIZE);
		this.setMinSize(USE_PREF_SIZE, USE_PREF_SIZE);
		this.setPadding(new Insets(20, 30, 30, 0));
		for (int i=3;i>0;i--) {
			HBox label = new HBox();
			label.setPadding(new Insets(40, 30, 30, 30));
			label.setPrefSize(69, 69);
			label.setMaxSize(USE_PREF_SIZE, USE_PREF_SIZE);
			label.setMinSize(USE_PREF_SIZE, USE_PREF_SIZE);
			Text t = new Text(i+"");
			t.setTextAlignment(TextAlignment.CENTER);
		
			label.getChildren().add(t);
			this.add(label, 4 - i, 0);
		}
		String[] textDic = {"一", "二", "三", "四"};
		for (int i=1;i<5;i++) {
			HBox label = new HBox();
			label.setPadding(new Insets(30, 40, 40, 40));
			label.setPrefSize(69, 69);
			label.setMaxSize(USE_PREF_SIZE, USE_PREF_SIZE);
			label.setMinSize(USE_PREF_SIZE, USE_PREF_SIZE);
			Text t = new Text(textDic[i-1]);
			t.setTextAlignment(TextAlignment.CENTER);
			label.getChildren().add(t);
			this.add(label, 0, i);
		}
		for (int i=1;i<4;i++) {
			for (int j=1;j<5;j++) {
				Rectangle whiteIcon = new Rectangle();
				whiteIcon.setHeight(69);
				whiteIcon.setWidth(69);
				whiteIcon.setStroke(Color.BLACK);
				whiteIcon.setFill(Color.WHITE);
				this.add(whiteIcon, i, j);
			}
		}
	}

	public void makeMasuFromBan (Ban ban) {
		this.ban = ban;
		this.getChildren().clear();
//		setBanPanel();
		for (int i=3;i>0;i--) {
			HBox label = new HBox();
			label.setPadding(new Insets(40, 30, 30, 30));
			label.setPrefSize(69, 69);
			label.setMaxSize(USE_PREF_SIZE, USE_PREF_SIZE);
			label.setMinSize(USE_PREF_SIZE, USE_PREF_SIZE);
			Text t = new Text(i+"");
			t.setStyle("-fx-font-size: 50;");
			t.setStyle("-fx-font-weight: bold;");
			t.setTextAlignment(TextAlignment.CENTER);
		
			label.getChildren().add(t);
			this.add(label, 4 - i, 0);
		}
		String[] textDic = {"一", "二", "三", "四"};
		for (int i=1;i<5;i++) {
			HBox label = new HBox();
			label.setPadding(new Insets(30, 40, 40, 40));
			label.setPrefSize(69, 69);
			label.setMaxSize(USE_PREF_SIZE, USE_PREF_SIZE);
			label.setMinSize(USE_PREF_SIZE, USE_PREF_SIZE);
			Text t = new Text(textDic[i-1]);
			t.setStyle("-fx-font-size: 50;");
			t.setStyle("-fx-font-weight: bold;");
			t.setTextAlignment(TextAlignment.CENTER);
			label.getChildren().add(t);
			this.add(label, 0, i);
		}
		for (int i=1;i<4;i++) {
			for (int j=1;j<5;j++) {
				Koma koma = ban.getBanarray(i, j);
				if (koma != null) {
					int komaNumber = koma.getKomaNumber();
					int idx=0;
					switch (komaNumber) {
					case 1:
						idx = 2;
						break;
					case 2:
						idx = 1;
						break;
					case 3:
						idx = 0;
						break;
					case 4:
						idx = 4;
						break;
					case 11:
						idx = 3;
						break;
					}
					Image img = new Image(getClass().getResourceAsStream(imageList[idx]));
					ImageView komaImage = new ImageView(img);
					komaImage.setFitHeight(70);
					komaImage.setFitWidth(70);
					if (koma.getTeban() == 2) komaImage.setRotate(180);
					HBox pictureRegion = new HBox();
					pictureRegion.getChildren().add(komaImage);
					pictureRegion.setOnMouseClicked(this::clickedAction);
					this.add(pictureRegion, i, j);
				} else {
					Rectangle whiteIcon = new Rectangle();
					whiteIcon.setHeight(69);
					whiteIcon.setWidth(69);
					whiteIcon.setStroke(Color.BLACK);
					whiteIcon.setFill(Color.WHITE);
					whiteIcon.setOnMouseClicked(this::clickedAction);
					this.add(whiteIcon, i, j);
				}
			}
		}
	}
	
	private void clickedAction (MouseEvent e) {
		Node source = (Node)e.getSource();
		int i = GridPane.getColumnIndex(source);
		int j = GridPane.getRowIndex(source);
		Koma koma = ban.getBanarray(i, j);
		if (koma != null) koma.outputKoma();
		else System.out.println("blank");
		frame.cellClickedBan(i, j);
	}
}
