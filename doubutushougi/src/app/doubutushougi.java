package app;

import javafx.application.Application;
import javafx.stage.*;
import javafx.scene.*;

public class doubutushougi extends Application {
	@Override
	public void start (Stage pstage) {
		Frames frame = new Frames();
		frame.setFrames();
		Scene scene = new Scene(frame);
		scene.getStylesheets().add(getClass().getResource("stylesheet.css").toExternalForm());
		pstage.setScene(scene);
		pstage.sizeToScene();
		pstage.show();
	}
	public static void main (String[] args) {
		launch(args);
	}
}
