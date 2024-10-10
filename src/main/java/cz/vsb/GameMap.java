package cz.vsb;
import javafx.scene.canvas.GraphicsContext;

class GameMap {
    private Block[][] blocks;   // 2D pole bloků
    private final int rows = 11;
    private final int cols = 15;

    public GameMap() {
        blocks = new Block[rows][cols];
        generateMap();
    }

    private void generateMap() {
        // Startivni prostor pro hráče
        int[][] playerSpawnPositions = {
                {1, 1}, {1, 2}, {2, 1},  // Bloky pro hráče 1
                {rows - 2, cols - 2}, {rows - 3, cols - 2}, {rows - 2, cols - 3}  // Bloky pro hráče 2
        };

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                // Okrajové bloky - neznicitelne
                if (row == 0 || row == rows - 1 || col == 0 || col == cols - 1) {
                    blocks[row][col] = new IndestructibleBlock(col, row);
                }
                // Druhá vrstva za okrajem - zničitelné kromě spawn míst
                else if (row == 1 || row == rows - 2 || col == 1 || col == cols - 2) {
                    if (isPlayerSpawn(row, col, playerSpawnPositions)) {
                        blocks[row][col] = new EmptyBlock(col, row); // Prázdné místo pro spawn
                    } else {
                        blocks[row][col] = new DestructibleBlock(col, row); // Zničitelné bloky
                    }
                }
                // Zbytek mapy...
                else if (row % 2 != 0 || col % 2 != 0) {
                    blocks[row][col] = new DestructibleBlock(col, row); // Zničitelné bloky
                } else {
                    blocks[row][col] = new IndestructibleBlock(col, row); // Nezničitelné bloky
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

    public void draw(GraphicsContext gc) {
        for (int row = 0; row < blocks.length; row++) {
            for (int col = 0; col < blocks[row].length; col++) {
                blocks[row][col].draw(gc);
            }
        }
    }

    public boolean isEmpty(int x, int y) {
        // Mimo hranice mapy
        if (x < 0 || y < 0 || x >= cols || y >= rows) {
            return false;
        }
        return (blocks[y][x] instanceof EmptyBlock);
    }

    // Funkce pro destrukci bloku
    public void destroyBlock(int x, int y) {
        // Kontrola, jestli jsou souřadnice platné a jestli se jedná o zničitelný blok
        if (x < 0 || y < 0 || x >= cols || y >= rows) {
            return;
        }

        if (blocks[y][x] instanceof DestructibleBlock) {
            // Nahrazení zničitelného bloku instancí EmptyBlock
            blocks[y][x] = new EmptyBlock(x, y);
        }
    }
}
