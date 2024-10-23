package cz.vsb;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class App extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	private GameController gameController;

	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader gameLoader = new FXMLLoader(getClass().getResource("/game.fxml"));
			Parent root = gameLoader.load();
			gameController = gameLoader.getController();
			Scene scene = new Scene(root);

			primaryStage.setScene(scene);
			primaryStage.resizableProperty().set(false);
			primaryStage.setTitle("Java 1 - 1th laboratory");
			primaryStage.show();

			// Call setupControls after the stage is shown
			gameController.setupControls();

			// Exit program when main window is closed
			primaryStage.setOnCloseRequest(this::exitProgram);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void stop() throws Exception {
		gameController.stop();
		super.stop();
	}

	private void exitProgram(WindowEvent evt) {
		System.exit(0);
	}
}