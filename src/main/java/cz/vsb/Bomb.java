package cz.vsb;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Bomb implements Drawable {
    private final int x;  // v blocích...
    private final int y;
    private final int range; // Range výbuchu
    private boolean isActive; // Stav bomby (aktivní/neaktivní)
    private final long placedTime; // Čas, kdy byla bomba položena
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
        // vybuch na bombe
        gc.drawImage(explosionImage, x * 80, y * 80, 80, 80);

        // vykresleni vybuchu do daneho range do vsech smeru
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
        isActive = false;
    }

    public boolean isActive() {
        return isActive;
    }

    public void checkExplosion() {
        if (isActive && (System.currentTimeMillis() - placedTime >= 2000)) { // 2 sekundy
            activate();
            explosionStartTime = System.currentTimeMillis();
        }
    }

    public boolean hasExplosionEnded() {
        return (System.currentTimeMillis() - explosionStartTime >= explosionDuration);
    }



}
