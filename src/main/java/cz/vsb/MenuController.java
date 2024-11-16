package cz.vsb;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    private GridPane leaderboard;

    @FXML
    private ScrollPane leaderboardScrollPane;

    @FXML
    private void initialize() {
        btnPlay.setOnAction(event -> switchToGameScene());

        loadTopPlayers();

        leaderboardScrollPane.viewportBoundsProperty().addListener((observable, oldValue, newValue) -> {
            leaderboard.setPrefWidth(newValue.getWidth());
        });
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

    private void loadTopPlayers() {
        Map<String, Integer> winCounts = ScoreManager.getWinCounts();
        List<Map.Entry<String, Integer>> sortedWinCounts = winCounts.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toList());

        leaderboard.getChildren().clear();

        for (int i = 0; i < sortedWinCounts.size(); i++) {
            Map.Entry<String, Integer> entry = sortedWinCounts.get(i);
            Label rankLabel = new Label((i + 1) + ".");
            Label nameLabel = new Label(entry.getKey());
            Label winCountLabel = new Label(String.valueOf(entry.getValue()));

            rankLabel.getStyleClass().add("cell");
            nameLabel.getStyleClass().add("cell");
            winCountLabel.getStyleClass().add("third-column");

            leaderboard.add(rankLabel, 0, i);
            leaderboard.add(nameLabel, 1, i);
            leaderboard.add(winCountLabel, 2, i);
        }
    }

}