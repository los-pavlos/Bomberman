package cz.vsb;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
    private TableView<PlayerScore> leaderboard;

    @FXML
    private TableColumn<PlayerScore, String> leaderboardName;

    @FXML
    private TableColumn<PlayerScore, Integer> leaderboardWins;

    private ObservableList<PlayerScore> playerScores = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        // Bind columns to properties
        leaderboardName.setCellValueFactory(data -> data.getValue().nameProperty());
        leaderboardWins.setCellValueFactory(data -> data.getValue().winsProperty().asObject());

        // Load top players into the leaderboard
        loadTopPlayers();

        // Set Play button action
        btnPlay.setOnAction(event -> switchToGameScene());
        leaderboard.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY); // Automatická velikost sloupců
    }

    public void setPlayerNames(String player1Name, String player2Name) {
        tfPlayer1.setText(player1Name);
        tfPlayer2.setText(player2Name);
    }

    private void setCheckbxRandMap() {
        DrawingThread.randomMap = checkbxRandMap.isSelected();
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
        // Clear the existing data
        playerScores.clear();

        // Fetch and sort the win counts
        Map<String, Integer> winCounts = ScoreManager.getWinCounts();
        List<Map.Entry<String, Integer>> sortedWinCounts = winCounts.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toList());

        // Add the data to the observable list
        for (Map.Entry<String, Integer> entry : sortedWinCounts) {
            playerScores.add(new PlayerScore(entry.getKey(), entry.getValue()));
        }

        // Update the TableView
        leaderboard.setItems(playerScores);
    }
}
