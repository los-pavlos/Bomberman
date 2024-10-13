package cz.vsb;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Player {
    private int x;
    private int y;
    private int speed;
    private Image activeImage;
    private Image standingImage;
    private Image leftImage;
    private Image rightImage;
    private Image upImage;
    private Image downImage;
    private final int size = 80;
    private GameMap map;

    public Player(GameMap map, int startX, int startY, String imagePath) {
        this.x = startX * 80;
        this.y = startY * 80;
        this.speed = 1; // Výchozí rychlost hráče, bude 2 po sebrání boostu
        this.map = map;
        this.standingImage = new Image(getClass().getResourceAsStream(imagePath + "standing.gif"));
        this.rightImage = new Image(getClass().getResourceAsStream(imagePath + "right.gif"));
        this.leftImage = new Image(getClass().getResourceAsStream(imagePath + "left.gif"));
        this.upImage = new Image(getClass().getResourceAsStream(imagePath + "up.gif"));
        this.downImage = new Image(getClass().getResourceAsStream(imagePath + "down.gif"));
        this.activeImage = standingImage;
    }

    public void move(int deltaX, int deltaY) {
        int newX = x + deltaX * speed;
        int newY = y + deltaY * speed;

        // Kontrola, zda je nová pozice volná
        if (map.isEmpty(newX, newY)) {
            x = newX;
            y = newY;
        }
    }

    public void draw(GraphicsContext gc) {
        gc.drawImage(activeImage, x, y, size, size);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void changeImage(int direction) {
        switch (direction) {
            case 0: this.activeImage = this.standingImage; break;
            case 1: this.activeImage = this.rightImage; break;
            case 2: this.activeImage = this.leftImage; break;
            case 3: this.activeImage = this.upImage; break;
            case 4: this.activeImage = this.downImage; break;
        }
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    private boolean canMove(int newX, int newY) {
        return map.isEmpty(newX, newY);
    }
}
