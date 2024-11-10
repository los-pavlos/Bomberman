package cz.vsb;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuController {

    @FXML
    private Button btnPlay;

    @FXML
    private CheckBox checkbxRandMap;

    @FXML
    private TextField tfPlayer1;

    @FXML
    private TextField tfPlayer2;

    @FXML
    private void initialize() {
        btnPlay.setOnAction(event -> switchToGameScene());
    }

    public void setPlayerNames(String player1Name, String player2Name) {
        tfPlayer1.setText(player1Name);
        tfPlayer2.setText(player2Name);
    }

    private void setCheckbxRandMap() {
        if (checkbxRandMap.isSelected()) {
            DrawingThread.randomMap = true;
        } else {
            DrawingThread.randomMap = false;
        }
    }

    private void switchToGameScene() {
        try {
            setCheckbxRandMap(); // Ensure this is called before switching scenes
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/game.fxml"));
            Parent root = loader.load();

            GameController gameController = loader.getController();
            gameController.setPlayerNames(tfPlayer1.getText(), tfPlayer2.getText()); // Pass the player names

            Scene scene = new Scene(root);
            Stage stage = (Stage) btnPlay.getScene().getWindow();
            stage.setScene(scene);

            gameController.setupControls();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}