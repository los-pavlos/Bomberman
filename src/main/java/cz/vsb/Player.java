package cz.vsb;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class Player {
    private int x;
    private int y;
    private Image activeImage;
    private Image standingImage;
    private Image leftImage;
    private Image rightImage;
    private Image upImage;
    private Image downImage;
    private final int size = 80;

    public Player(int startX, int startY, String imagePath) {
        this.x = startX;
        this.y = startY;
        this.standingImage = new Image(getClass().getResourceAsStream(imagePath + "standing.gif"));
        this.rightImage = new Image(getClass().getResourceAsStream(imagePath + "right.gif"));
        this.leftImage = new Image(getClass().getResourceAsStream(imagePath + "left.gif"));
        this.upImage = new Image(getClass().getResourceAsStream(imagePath + "up.gif"));
        this.downImage = new Image(getClass().getResourceAsStream(imagePath + "down.gif"));
        this.activeImage = standingImage;
    }

    public void move(int deltaX, int deltaY, GameMap map) {
        int newX = x + deltaX;
        int newY = y + deltaY;

        if (map.isEmpty(newX, newY)) {
            x = newX;
            y = newY;
        }
    }

    public void draw(GraphicsContext gc) {
        gc.drawImage(activeImage, x * size, y * size, size, size);
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
}
