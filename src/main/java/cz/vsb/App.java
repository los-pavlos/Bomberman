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

	private Stage primaryStage;

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		switchToMenuScene();
		primaryStage.setTitle("Bomberman Game");
		primaryStage.resizableProperty().set(false);
		primaryStage.show();
		primaryStage.setOnCloseRequest(this::exitProgram);
	}

	public void switchToMenuScene() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/menu.fxml"));
			Parent root = loader.load();
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	@Override
	public void stop() throws Exception {
		super.stop();
	}

	private void exitProgram(WindowEvent evt) {
		System.exit(0);
	}
}