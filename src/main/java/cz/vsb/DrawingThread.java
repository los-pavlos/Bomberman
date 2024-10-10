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

    // ovladani hracu, navim zda ho resit timto zpusobem, ale zatím to funguje.
    // ale jeste bude urcite timer na rychlost pohybu, která se bude zvětšovat po tom co hráč najde nějaký boost

    private boolean upPressed1 = false;
    private boolean downPressed1 = false;
    private boolean leftPressed1 = false;
    private boolean rightPressed1 = false;

    private boolean upPressed2 = false;
    private boolean downPressed2 = false;
    private boolean leftPressed2 = false;
    private boolean rightPressed2 = false;

    public DrawingThread(Canvas canvas) {
        this.canvas = canvas;
        this.gc = canvas.getGraphicsContext2D();
        this.map = new GameMap();
        this.player1 = new Player(1, 1, "/Player1/");
        this.player2 = new Player(13, 9, "/Player1/"); //   nemam jeste obrazky pro hrace 2, budu muset upravit.
        setupControls();
    }

    private void setupControls() {
        canvas.getScene().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            switch (event.getCode()) {
                case UP -> upPressed1 = true;
                case DOWN -> downPressed1 = true;
                case LEFT -> leftPressed1 = true;
                case RIGHT -> rightPressed1 = true;
                case W -> upPressed2 = true;
                case S -> downPressed2 = true;
                case A -> leftPressed2 = true;
                case D -> rightPressed2 = true;
            }
        });

        canvas.getScene().addEventFilter(KeyEvent.KEY_RELEASED, event -> {
            switch (event.getCode()) {
                case UP -> upPressed1 = false;
                case DOWN -> downPressed1 = false;
                case LEFT -> leftPressed1 = false;
                case RIGHT -> rightPressed1 = false;
                case W -> upPressed2 = false;
                case S -> downPressed2 = false;
                case A -> leftPressed2 = false;
                case D -> rightPressed2 = false;
            }
        });
    }

    @Override
    public void handle(long now) {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        map.draw(gc);

        //  hrac 1 pohyb
        if (upPressed1) {
            player1.move(0, -1, map);
            player1.changeImage(3);
        }
        if (downPressed1) {
            player1.move(0, 1, map);
            player1.changeImage(4);
        }
        if (leftPressed1) {
            player1.move(-1, 0, map);
            player1.changeImage(2);
        }
        if (rightPressed1) {
            player1.move(1, 0, map);
            player1.changeImage(1);
        }
        if(!upPressed1 && !downPressed1 && !leftPressed1 && !rightPressed1) {
            player1.changeImage(0);
        }

        //  hrac 2 pohyb
        if (upPressed2) {
            player2.move(0, -1, map);
            player2.changeImage(3);
        }
        if (downPressed2) {
            player2.move(0, 1, map);
            player2.changeImage(4);
        }
        if (leftPressed2) {
            player2.move(-1, 0, map);
            player2.changeImage(2);
        }
        if (rightPressed2) {
            player2.move(1, 0, map);
            player2.changeImage(1);
        }
        if(!upPressed2&&!downPressed2&&!leftPressed2&&!rightPressed2){
            player2.changeImage(0);
        }

        player1.draw(gc);
        player2.draw(gc);
    }
}
