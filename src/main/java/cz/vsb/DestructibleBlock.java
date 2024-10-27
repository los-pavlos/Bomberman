package cz.vsb;

import java.util.Random;
class DestructibleBlock extends Block {

    public DestructibleBlock(int x, int y) {
            super (x, y, "/Blocks/DestructibleBlock.png");
        //super(x, y, "/Blocks/DestructibleBlock" + new Random().nextInt(5) + ".png");
    }
}