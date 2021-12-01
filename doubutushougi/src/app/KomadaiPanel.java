package app;

import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

import board.Komadai;
import koma.Koma;

public class KomadaiPanel extends HBox {
	private String[] imageList = {"Kirin.png", "Zou.png", "Hiyoko.png", "Niwatori.png", "Lion.png"};
	private HBox komaCase;
	private Frames frame;

	public KomadaiPanel (Frames frame) {
		this.setSpacing(10);
		this.frame = frame;
	}

	public void setKomadaiPanel () {

		this.setStyle("-fx-background-color: darkturquoise;");
		this.setPrefSize(250, 460);
		this.setMaxSize(USE_PREF_SIZE, USE_PREF_SIZE);
		this.setMinSize(USE_PREF_SIZE, USE_PREF_SIZE);
		komaCase = new HBox(10);
		komaCase.setStyle("-fx-background-color: burlywood;");
		komaCase.setPrefSize(250, 120);
		komaCase.setMaxSize(USE_PREF_SIZE, USE_PREF_SIZE);
		komaCase.setMinSize(USE_PREF_SIZE, USE_PREF_SIZE);
		komaCase.setPadding(new Insets(20, 10, 20, 10));
		
		this.getChildren().add(komaCase);
	}
	
	public void makeKomadaiPanel (Komadai komadai) {
		komaCase.getChildren().clear();
		for (Koma koma: komadai.getKomadaiAllKoma()) {
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
			KomaIcon Icon = new KomaIcon(img, koma, this);
//			ImageView motigoma = new ImageView(img);
//			motigoma.setFitHeight(70);
//			motigoma.setFitWidth(70);
//			if (koma.getTeban() == 2) motigoma.setRotate(180);
//			HBox pictureRegion = new HBox();
//			pictureRegion.getChildren().add(motigoma);
//			pictureRegion.setOnMouseClicked(this::clickedAction);
			komaCase.getChildren().add(Icon);
		}
		
	}
	
	public void cellClickedKomadai (Koma koma) {
		if (koma != null) koma.outputKoma();
		else System.out.println("blank");
		frame.cellClickedKomadai(koma);
	}
	
}
























