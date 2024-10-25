package cz.vsb;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;

public class DrawingThread extends AnimationTimer {
    private final Canvas canvas;
    private final GraphicsContext gc;
    private final GameMap map;
    private final Player player1;
    private final Player player2;
    private final GameController controller;

    private boolean randomMap;

    private Drawable[] drawables;
    private long lastUpdate = 0;

    public DrawingThread(Canvas canvas, GameController controller) {
        this.canvas = canvas;
        this.gc = canvas.getGraphicsContext2D();
        this.map = new GameMap();
        this.player1 = new Player(this.map, 1, 1, "/Player1/");
        this.player2 = new Player(this.map, 13, 9, "/Player2/");
        this.drawables = new Drawable[]{map, player1, player2};
        this.controller = controller;
        this.randomMap = false;
    }



    @Override
    public void handle(long now) {
        if (now - lastUpdate >= 16_000_000) { // 16 milliseconds (approx. 60fps)
            gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

            for (Drawable drawable : drawables) {
                drawable.draw(gc);
            }
            update();
            handlePlayerMovement(player1, controller.isUpPressed1(), controller.isDownPressed1(), controller.isLeftPressed1(), controller.isRightPressed1(), controller.isSpacePressed());
            handlePlayerMovement(player2, controller.isUpPressed2(), controller.isDownPressed2(), controller.isLeftPressed2(), controller.isRightPressed2(), controller.ismPressed());

            lastUpdate = now;
        }
    }

    private void handlePlayerMovement(Player player, boolean up, boolean down, boolean left, boolean right, boolean placingBomb) {
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
        if (placingBomb) {
            player.placeBomb();
        }
    }

    public void resetGame() {
        map.reset(randomMap);
        player1.setCoordinates(1, 1);
        player2.setCoordinates(13, 9);
    }

    public void update() {
        // Check if player is in explosion
        if(map.isPlayerInExplosion(player1)&&map.isPlayerInExplosion(player2)){
            controller.getTopLabel().setText("Draw!");
            stop();
        }
        else if (map.isPlayerInExplosion(player1)) {
            controller.getTopLabel().setText("Player 2 wins!");
            stop();
        }
        else if (map.isPlayerInExplosion(player2)) {
            controller.getTopLabel().setText("Player 1 wins!");
            stop();
        }
    }

    public void setRandomMap(boolean randomMap) {
        this.randomMap = randomMap;
    }


}