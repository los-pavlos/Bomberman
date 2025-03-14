package cz.vsb;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Bomb implements Drawable {
    private final int x;  // cooridinates in blocks
    private final int y;
    private final int range; // range of explosion
    private boolean isActive; // active / inactive
    private final long placedTime; // bomb placed time
    private final Image bombImage;
    private final Image explosionImage;
    private GameMap map;
    private final long explosionDuration = 500;
    private long explosionStartTime;

    public Bomb(GameMap map, int x, int y, int range) {
        this.x = x;
        this.y = y;
        this.map = map;
        this.range = range;
        this.isActive = true;
        this.placedTime = System.currentTimeMillis(); // Uloží čas položení
        this.bombImage = new Image(getClass().getResourceAsStream("/Bomb/bomb.gif"));
        this.explosionImage = new Image(getClass().getResourceAsStream("/Bomb/explosion.gif"));
    }


    @Override
    public void draw(GraphicsContext gc) {
        if (isActive) {
            gc.drawImage(bombImage, x * 80, y * 80, 80, 80);
        } else {
            drawExplosion(gc);
        }
    }

    private void drawExplosion(GraphicsContext gc) {
        // explosion on bomb
        gc.drawImage(explosionImage, x * 80, y * 80, 80, 80);

        // explosion in range
        for (int i = 1; i <= range; i++) {
            // Check left
            if (map.getBlock(x - i, y) instanceof IndestructibleBlock) {
                break;
            }
            map.destroyBlock(x - i, y);
            gc.drawImage(explosionImage, (x - i) * 80, y * 80, 80, 80);
        }
        for (int i = 1; i <= range; i++) {
            // Check right
            if (map.getBlock(x + i, y) instanceof IndestructibleBlock) {
                break;
            }
            map.destroyBlock(x + i, y);
            gc.drawImage(explosionImage, (x + i) * 80, y * 80, 80, 80);
        }
        for (int i = 1; i <= range; i++) {
            // Check up
            if (map.getBlock(x, y - i) instanceof IndestructibleBlock) {
                break;
            }
            map.destroyBlock(x, y - i);
            gc.drawImage(explosionImage, x * 80, (y - i) * 80, 80, 80);
        }
        for (int i = 1; i <= range; i++) {
            // Check down
            if (map.getBlock(x, y + i) instanceof IndestructibleBlock) {
                break;
            }
            map.destroyBlock(x, y + i);
            gc.drawImage(explosionImage, x * 80, (y + i) * 80, 80, 80);
        }
    }

    public void activate() {

        SoundManager.playExplosionSound();
        isActive = false;
    }

    public boolean isActive() {
        return isActive;
    }

    public void checkExplosion() {
        if (isActive && (System.currentTimeMillis() - placedTime >= 2000)) { // 2 seconds
            activate();
            explosionStartTime = System.currentTimeMillis();
        }
    }

    public boolean hasExplosionEnded() {
        return (System.currentTimeMillis() - explosionStartTime >= explosionDuration);
    }


    //  if is player center in explosion
    public boolean isInRange(int playerX, int playerY) {
        int bombX = x;
        int bombY = y;
        playerX = (playerX+40) / 80;
        playerY = (playerY+40) / 80;
        // Check the bomb's position
        if (playerX == bombX && playerY == bombY) {
            return true;
        }

        // Check left
        for (int i = 1; i <= range; i++) {
            if (playerX == bombX - i && playerY == bombY) {
                return true;
            }
            if (map.getBlock(x - i, y) instanceof IndestructibleBlock) {
                break;
            }
        }

        // Check right
        for (int i = 1; i <= range; i++) {
            if (playerX == bombX + i && playerY == bombY) {
                return true;
            }
            if (map.getBlock(x + i, y) instanceof IndestructibleBlock) {
                break;
            }
        }

        // Check up
        for (int i = 1; i <= range; i++) {
            if (playerX == bombX && playerY == bombY - i) {
                return true;
            }
            if (map.getBlock(x, y - i) instanceof IndestructibleBlock) {
                break;
            }
        }

        // Check down
        for (int i = 1; i <= range; i++) {
            if (playerX == bombX && playerY == bombY + i) {
                return true;
            }
            if (map.getBlock(x, y + i) instanceof IndestructibleBlock) {
                break;
            }
        }

        return false;
    }

}
