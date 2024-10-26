package cz.vsb;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.Random;
public abstract class Boost  implements Drawable{
    protected int fullDuration;
    protected int duration;
    private Image image;
    private int x;
    private int y;
    protected GameMap map;
    protected boolean isUsed = false;
    Random rand = new Random();
    protected Player player;
    protected Boost(int duration, String imagePath, GameMap map) {

        this.duration = duration;
        this.fullDuration = duration;
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
            this.x =  rand.nextInt(1,14);
            this.y = rand.nextInt(1,10);
        }while(!(map.getBlock(x, y) instanceof EmptyBlock));

    }

    public void draw(GraphicsContext gc) {
        if(map.getBlock(x, y) instanceof EmptyBlock && !isUsed){
            gc.drawImage(image, x * 80, y * 80, 80, 80);
        }else if(isUsed){
            duration = duration - 1;
        }

    }
    public int getDuration() {
        return duration;
    }

    public abstract void applyEffect(Player player);
    public abstract void removeEffect();

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    private void setUsed(boolean used){
        this.isUsed = used;
    }

    public boolean isUsed(){
        return isUsed;
    }

    public Player getPlayer(){
        return player;
    }

    public void renew(){

        do{
            this.x =  rand.nextInt(1,14);
            this.y = rand.nextInt(1,10);
        }while(!(map.getBlock(x, y) instanceof EmptyBlock));
        setUsed(false);
        duration = fullDuration;
    }
}