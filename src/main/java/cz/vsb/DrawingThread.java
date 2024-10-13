package cz.vsb;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;

public class DrawingThread extends AnimationTimer {
    private final Canvas canvas;
    private final GraphicsContext gc;
    private final GameMap map;
    private final Player player1;
    private final Player player2;

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

    public DrawingThread(Canvas canvas) {
        this.canvas = canvas;
        this.gc = canvas.getGraphicsContext2D();
        this.map = new GameMap();
        this.player1 = new Player(this.map, 1, 1, "/Player1/");
        this.player2 = new Player(this.map, 13, 9, "/Player2/");
        setupControls();
    }

    private void setupControls() {
        canvas.getScene().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            switch (event.getCode()) {
                case W -> upPressed1 = true;
                case S -> downPressed1 = true;
                case A -> leftPressed1 = true;
                case D -> rightPressed1 = true;
                case UP -> upPressed2 = true;
                case DOWN -> downPressed2 = true;
                case LEFT -> leftPressed2 = true;
                case RIGHT -> rightPressed2 = true;
                case SPACE -> spacePressed = true; //  pro hráče 1
                case M -> mPressed = true; // pro hráče 2
            }
        });

        canvas.getScene().addEventFilter(KeyEvent.KEY_RELEASED, event -> {
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

    @Override
    public void handle(long now) {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        map.draw(gc);

        handlePlayerMovement(player1, upPressed1, downPressed1, leftPressed1, rightPressed1);
        handlePlayerMovement(player2, upPressed2, downPressed2, leftPressed2, rightPressed2);

        if (spacePressed) {
            player1.placeBomb();
        }
        if (mPressed) {
            player2.placeBomb();
        }


        updateAndDrawBombs(player1);
        updateAndDrawBombs(player2);
    }

    private void handlePlayerMovement(Player player, boolean up, boolean down, boolean left, boolean right) {
        if (up) {
            player.move(0, -1);
            player.changeImage(3);
        }
        if (down) {
            player.move(0, 1);
            player.changeImage(4);
        }
        if (left) {
            player.move(-1, 0);
            player.changeImage(2);
        }
        if (right) {
            player.move(1, 0);
            player.changeImage(1);
        }
        if (!up && !down && !left && !right) {
            player.changeImage(0);
        }
    }

    private void updateAndDrawBombs(Player player) {
        player.updateBombs();
        player.draw(gc);
    }


}
