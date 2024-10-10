package cz.vsb;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import java.io.IOException;
import java.io.InputStream;

abstract class Block {
    protected int x;
    protected int y;
    protected final int size = 80;
    protected Image image;

    protected Block(int x, int y, String imagePath) {
        this.x = x;
        this.y = y;

        try (InputStream input = getClass().getResourceAsStream(imagePath)) {
            if (input == null) {
                throw new IOException("Obrazek nenalezen: " + imagePath);
            }
            this.image = new Image(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(GraphicsContext gc) {
        gc.drawImage(image, x * size, y * size, size, size);
    }
}
