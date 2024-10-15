package cz.vsb;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


public class App extends Application {
	private Canvas canvas;
	private DrawingThread timer;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		try {
			Group root = new Group();
			canvas = new Canvas(1200, 880);
			root.getChildren().add(canvas);
			Scene scene = new Scene(root, 1200, 880);
			primaryStage.setScene(scene);
			primaryStage.resizableProperty().set(false);
			primaryStage.setTitle("Bomberman Game");
			primaryStage.show();

			primaryStage.setOnCloseRequest(this::exitProgram);
			timer = new DrawingThread(canvas);
			timer.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void stop() throws Exception {
		if (timer != null) {
			timer.stop();
		}
		super.stop();
	}

	private void exitProgram(WindowEvent evt) {
		System.exit(0);
	}
}
