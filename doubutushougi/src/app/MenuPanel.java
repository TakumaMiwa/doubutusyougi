package app;


import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class MenuPanel extends FlowPane {
	private Button b1;
	private Button b3;
	private Frames frame;

	public MenuPanel (Frames frame) {
		this.setHgap(10);
		this.frame = frame;
	}
	
	// 対局中の状態にする
	public void setGaming () {
		b1.setVisible(false);
		b3.setVisible(true);
	}
	
	public void setNotGaming () {
		b1.setVisible(true);
		b3.setVisible(false);
	}
	
	public void setMenuPanel () {
		this.setStyle("-fx-background-color: gray;");
		this.setPrefSize(950, 50);
		this.setMaxSize(USE_PREF_SIZE, USE_PREF_SIZE);
		this.setMinSize(USE_PREF_SIZE, USE_PREF_SIZE);
		this.setPadding(new Insets(10, 10, 10, 10));
		b1 = new Button("新規");
		b1.setOnAction((event) -> {
			frame.setFirstKyokumen();
		});
		this.getChildren().add(b1);
		b3 = new Button("投了");
		b3.setOnAction((event) -> {
			frame.setTouryou();
		});
		this.getChildren().add(b3);
	}
	
}













