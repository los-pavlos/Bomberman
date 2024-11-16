package cz.vsb;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
// src/main/java/cz/vsb/GameController.java

public class GameController {

    private DrawingThread timer;

    private boolean upPressed1 = false;
    private boolean downPressed1 = false;
    private boolean leftPressed1 = false;
    private boolean rightPressed1 = false;

    private boolean upPressed2 = false;
    private boolean downPressed2 = false;
    private boolean leftPressed2 = false;
    private boolean rightPressed2 = false;

    private boolean spacePressed = false;
    private boolean mPressed = false;

    ScoreManager scoreManager = new ScoreManager();
    private boolean gameEnded = false;

    @FXML
    private Button btnReset;

    @FXML
    private Button btnMenu;

    @FXML
    private Canvas canvas;

    @FXML
    private Label topLabel;

    private String player1Name;
    private String player2Name;

    public void setPlayerNames(String player1Name, String player2Name) {
        this.player1Name = player1Name;
        this.player2Name = player2Name;
        if (timer == null) {
            initializeTimer();
        }
    }

    @FXML
    void menu(ActionEvent event) {
        timer.stop();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/menu.fxml"));
            Parent root = loader.load();
            MenuController menuController = loader.getController();
            menuController.setPlayerNames(player1Name, player2Name); // Pass the player names back

            Scene scene = new Scene(root);
            URL cssUrl = getClass().getResource("/application.css");
            scene.getStylesheets().add(cssUrl.toString());
            Stage stage = (Stage) btnMenu.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void reset(ActionEvent event) {
        timer.resetGame();
        topLabel.setText("Game is running...");
        timer.start();
        gameEnded = false;
    }

    @FXML
    void initialize() {

        if (player1Name != null && player2Name != null) {
            initializeTimer();
        }
        // Block space key for reset and menu buttons
        btnReset.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == javafx.scene.input.KeyCode.SPACE) {
                event.consume();  // Stop default action of space key
            }
        });
        btnMenu.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == javafx.scene.input.KeyCode.SPACE) {
                event.consume();  // Stop default action of space key
            }
        });

        setupControls();
    }

    private void initializeTimer() {
        timer = new DrawingThread(canvas, this, player1Name, player2Name);
        timer.start();
        topLabel.setText("Game is running...");
    }

    public void stop() {
        timer.stop();
    }

    public void setupControls() {
        Scene scene = canvas.getScene();
        if (scene != null) {
            scene.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
                switch (event.getCode()) {
                    case W -> upPressed1 = true;
                    case S -> downPressed1 = true;
                    case A -> leftPressed1 = true;
                    case D -> rightPressed1 = true;
                    case UP -> upPressed2 = true;
                    case DOWN -> downPressed2 = true;
                    case LEFT -> leftPressed2 = true;
                    case RIGHT -> rightPressed2 = true;
                    case SPACE -> spacePressed = true;
                    case M -> mPressed = true;
                }
            });

            scene.addEventFilter(KeyEvent.KEY_RELEASED, event -> {
                switch (event.getCode()) {
                    case W -> upPressed1 = false;
                    case S -> downPressed1 = false;
                    case A -> leftPressed1 = false;
                    case D -> rightPressed1 = false;
                    case UP -> upPressed2 = false;
                    case DOWN -> downPressed2 = false;
                    case LEFT -> leftPressed2 = false;
                    case RIGHT -> rightPressed2 = false;
                    case SPACE -> spacePressed = false;
                    case M -> mPressed = false;
                }
            });
        }
    }

    public boolean isUpPressed1() { return upPressed1; }
    public boolean isDownPressed1() { return downPressed1; }
    public boolean isLeftPressed1() { return leftPressed1; }
    public boolean isRightPressed1() { return rightPressed1; }
    public boolean isUpPressed2() { return upPressed2; }
    public boolean isDownPressed2() { return downPressed2; }
    public boolean isLeftPressed2() { return leftPressed2; }
    public boolean isRightPressed2() { return rightPressed2; }
    public boolean isSpacePressed() { return spacePressed; }
    public boolean ismPressed() { return mPressed; }

    public Label getTopLabel() {
        return topLabel;
    }

    public void setTopLabel(String text) {
        topLabel.setText(text);
    }

    private class Dead implements DeadListener {
        @Override
        public void playerDead(Player player) {
            setTopLabel(player.getName() + " is dead!!!!!!!!");
            if(!gameEnded){
                if(player.getName().equals(player1Name)){
                    ScoreManager.saveWin(player2Name);
                    gameEnded = true;
                    stop();
                }

                if(player.getName().equals(player2Name)){
                    ScoreManager.saveWin(player1Name);
                    gameEnded = true;
                    stop();
                }
            }

        }
    }

    public DeadListener getDeadListener() {
        return new Dead();
    }

}