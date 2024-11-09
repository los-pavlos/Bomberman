package cz.vsb;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

class GameMap implements Drawable {
    private List<List<Block>> blocks;   // 2D kolekce bloků
    private final int rows = 11;
    private final int cols = 15;

    // list of bombs
    private List<Bomb> bombs = new ArrayList<>();

    public GameMap() {
        blocks = new ArrayList<>();
        for (int i = 0; i < rows; i++) {
            blocks.add(new ArrayList<>());
        }
        generateMap(false);
    }

    private void generateMap(boolean isMapRandom) {
        // Startivni prostor pro hráče
        int[][] playerSpawnPositions = {
                {1, 1}, {1, 2}, {2, 1},  // Bloky pro hráče 1
                {rows - 2, cols - 2}, {rows - 3, cols - 2}, {rows - 2, cols - 3}  // Bloky pro hráče 2
        };

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                // Okrajové bloky - neznicitelne
                if (row == 0 || row == rows - 1 || col == 0 || col == cols - 1) {
                    blocks.get(row).add(new IndestructibleBlock(col, row));
                }
                // Druhá vrstva za okrajem - zničitelné kromě spawn míst
                else if (row == 1 || row == rows - 2 || col == 1 || col == cols - 2) {
                    if (isPlayerSpawn(row, col, playerSpawnPositions)) {
                        blocks.get(row).add(new EmptyBlock(col, row)); // Prázdné místo pro spawn
                    } else {
                        blocks.get(row).add(new DestructibleBlock(col, row)); // Zničitelné bloky
                    }
                }
                // Zbytek mapy...
                else if(isMapRandom){
                    if (new Random().nextInt(100) < 60) {
                        blocks.get(row).add(new DestructibleBlock(col, row)); // Zničitelné bloky
                    } else {
                        blocks.get(row).add(new IndestructibleBlock(col, row)); // Nezničitelné bloky
                    }
                }
                else if (row % 2 != 0 || col % 2 != 0) {
                    blocks.get(row).add(new DestructibleBlock(col, row)); // Zničitelné bloky
                } else {
                    blocks.get(row).add(new IndestructibleBlock(col, row)); // Nezničitelné bloky
                }
            }
        }
    }

    private boolean isPlayerSpawn(int row, int col, int[][] spawnPositions) {
        for (int[] pos : spawnPositions) {
            if (pos[0] == row && pos[1] == col) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void draw(GraphicsContext gc) {
        for (int row = 0; row < blocks.size(); row++) {
            for (int col = 0; col < blocks.get(row).size(); col++) {
                blocks.get(row).get(col).draw(gc);
            }
        }
        // kvuli odstranovani behem iterace
        Iterator<Bomb> iterator = bombs.iterator();
        while (iterator.hasNext()) {
            Bomb bomb = iterator.next();

            if (!bomb.isActive() && bomb.hasExplosionEnded()) {
                iterator.remove(); // Bezpečné odstranění během iterace
            } else {
                bomb.checkExplosion(); // Check if the bomb should explode
                bomb.draw(gc);
            }
        }
    }

    public boolean isEmpty(int pixelX, int pixelY) {
        int blockSize = 80;

        // Vypočítání blokových souřadnic pro čtyři rohy
        int topLeftX = pixelX / blockSize;
        int topLeftY = pixelY / blockSize;

        int topRightX = (pixelX + blockSize - 1) / blockSize;
        int topRightY = pixelY / blockSize;

        int bottomLeftX = pixelX / blockSize;
        int bottomLeftY = (pixelY + blockSize - 1) / blockSize;

        int bottomRightX = (pixelX + blockSize - 1) / blockSize;
        int bottomRightY = (pixelY + blockSize - 1) / blockSize;

        // Kontrola, zda jsou všechny čtyři rohové bloky prázdné
        int[][] corners = {
                {topLeftX, topLeftY},
                {topRightX, topRightY},
                {bottomLeftX, bottomLeftY},
                {bottomRightX, bottomRightY}
        };

        for (int[] corner : corners) {
            int x = corner[0];
            int y = corner[1];

            if (x < 0 || y < 0 || x >= cols || y >= rows) {
                return false;
            }
            if (!(blocks.get(y).get(x) instanceof EmptyBlock)) {
                return false;
            }
        }

        return true;
    }

    public void destroyBlock(int col, int row) {
        if (blocks.get(row).get(col) instanceof DestructibleBlock) {
            blocks.get(row).set(col, new EmptyBlock(col, row));
        }
    }

    public int getCols() {
        return cols;
    }

    public int getRows() {
        return rows;
    }

    public Block getBlock(int col, int row) {
        if (col < 0 || col >= cols || row < 0 || row >= rows) {
            return null; // Return null for out-of-bounds
        }
        return blocks.get(row).get(col);
    }

    public void reset(boolean isMapRandom) {
        bombs.clear();
        blocks.clear();
        for (int i = 0; i < rows; i++) {
            blocks.add(new ArrayList<>());
        }
        generateMap(isMapRandom);
    }

    public void addBomb(Bomb bomb) {
        bombs.add(bomb);
    }

    // metoda která kontroluje zda je hráč v explozi, ale jen když zrovna exploze probíhá
    public boolean isPlayerInExplosion(Player player) {
        for (Bomb bomb : bombs) {
            if (!bomb.hasExplosionEnded() && bomb.isInRange(player.getX(), player.getY())) {
                player.hit();
                return true;
            }
        }
        return false;
    }
}