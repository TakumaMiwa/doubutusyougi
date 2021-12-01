package app;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import koma.Koma;;

public class KomaIcon extends HBox {
	private Koma koma;
	private KomadaiPanel komadai;

	public KomaIcon (Image img, Koma koma, KomadaiPanel komadai) {
		this.koma = koma;
		this.komadai = komadai;
		ImageView komaImage = new ImageView(img);
		komaImage.setFitHeight(70);
		komaImage.setFitWidth(70);
		if (koma.getTeban() == 2) komaImage.setRotate(180);
		this.getChildren().add(komaImage);
		this.setOnMouseClicked(this::clickedAction);
	}
	
	private void clickedAction (MouseEvent e) {
		komadai.cellClickedKomadai(koma);
	}
}
