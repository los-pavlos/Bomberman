package cz.vsb;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.Random;
public abstract class Boost  implements Drawable{

    protected int duration; // Duration of the boost effect in seconds
    private Image image;
    private int x;
    private int y;
    private GameMap map;
    private boolean isUsed = false;
    protected Boost(int duration, String imagePath, GameMap map) {
        Random rand = new Random();
        this.duration = duration;
        try {
            this.image = new Image(getClass().getResourceAsStream(imagePath));
            if (this.image.isError()) {
                throw new IllegalArgumentException("Image not found or invalid: " + imagePath);
            }
        } catch (Exception e) {
            System.err.println("Error loading image: " + e.getMessage());
            this.image = null; // Set to null or a default image
        }

        this.map = map;
        do{
            this.x =  rand.nextInt(1,13);
            this.y = rand.nextInt(1,9);
        }while(!(map.getBlock(x, y) instanceof EmptyBlock));

    }

    public void draw(GraphicsContext gc) {
        if(map.getBlock(x, y) instanceof EmptyBlock){
            gc.drawImage(image, x * 80, y * 80, 80, 80);
        }

    }
    public int getDuration() {
        return duration;
    }

    public abstract void applyEffect(Player player);
    public abstract void removeEffect(Player player);

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    private void setUsed(boolean used){
        this.isUsed = used;
    }

    private boolean isUsed(){
        return isUsed;
    }
}