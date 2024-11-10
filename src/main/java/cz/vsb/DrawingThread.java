package cz.vsb;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.List;

public class DrawingThread extends AnimationTimer {
    private final Canvas canvas;
    private final GraphicsContext gc;
    private final GameMap map;
    private final Player player1;
    private final Player player2;
    private final GameController controller;
    private Boost bombRangeBoost;
    private Boost speedBoost;
    public static boolean randomMap = false;

    private List<Drawable> drawables;
    private long lastUpdate = 0;
    private long startTime;
    public DrawingThread(Canvas canvas, GameController controller, String player1Name, String player2Name) {
        this.canvas = canvas;
        this.gc = canvas.getGraphicsContext2D();
        this.map = new GameMap();
        this.player1 = new Player(this.map, 1, 1, "/Player1/", player1Name, controller);
        this.player2 = new Player(this.map, 13, 9, "/Player2/", player2Name, controller);
        player1.add(controller.getDeadListener());
        player2.add(controller.getDeadListener());
        // Instantiate boosts
        this.speedBoost = new SpeedBoost(600, map);
        this.bombRangeBoost = new BombRangeBoost(600, map);

        this.drawables = new ArrayList<>();
        this.drawables.add(map);
        this.drawables.add(player1);
        this.drawables.add(player2);
        this.drawables.add(speedBoost);
        this.drawables.add(bombRangeBoost);

        this.controller = controller;
        this.startTime = System.currentTimeMillis();

        // Reset to generate a new map if it should be random
        resetGame();
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
        player1.setBombRange(2);
        player2.setBombRange(2);
        player1.setSpeed(4);
        player2.setSpeed(4);
        bombRangeBoost.renew();
        speedBoost.renew();
    }

    public void update() {
        // Check if the game is over
         if (map.isPlayerInExplosion(player1)) {
            player1.hit();
            stop();
        }
        else if (map.isPlayerInExplosion(player2)) {
             player2.hit();
             stop();
        }
        // Check and apply boosts for each player
        for (Drawable drawable : drawables) {
            if (drawable instanceof Boost) {
                Boost boost = (Boost) drawable;

                player1.checkAndApplyBoost(boost);
                player2.checkAndApplyBoost(boost);

                if (boost.getDuration() <= 0) {
                    boost.removeEffect();
                    boost.renew();
                }
            }
        }
        checkBoostCoordinates();

    }



    private void checkBoostCoordinates(){
        if (speedBoost.getX() == bombRangeBoost.getX() && speedBoost.getY() == bombRangeBoost.getY()) {
            speedBoost.renew();
        }
    }




}