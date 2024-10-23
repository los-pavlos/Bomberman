package cz.vsb;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;

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

    @FXML
    private CheckBox randomMapCheckBox;



    @FXML
    private Button btnReset;

    @FXML
    private Canvas canvas;

    @FXML
    private Label topLabel;

    @FXML
    void randomMapActivate(ActionEvent event) {
        if (randomMapCheckBox.isSelected()) {
            timer.setRandomMap(true);
        } else {
            timer.setRandomMap(false);
        }
    }


    //  start timer
    @FXML
    void reset(ActionEvent event) {
        timer.resetGame();
        topLabel.setText("Game is running");
        timer.start();
    }


    @FXML
        void initialize() {
            timer = new DrawingThread(canvas, this);
            timer.start();
            topLabel.setText("Game is running");
            // Zablokování mezerníku pro tlačítko reset
            btnReset.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
                if (event.getCode() == javafx.scene.input.KeyCode.SPACE) {
                    event.consume();  // Zastaví výchozí akci tlačítka na mezerník
                }
            });
            randomMapCheckBox.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
                if (event.getCode() == javafx.scene.input.KeyCode.SPACE) {
                    event.consume();  // Zastaví výchozí akci tlačítka na mezerník
                }
            });;

            setupControls();
        }

        public void stop(){
                timer.stop();
            }

    public void win(String winner) {
        topLabel.setText(winner + " wins!");
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
}
