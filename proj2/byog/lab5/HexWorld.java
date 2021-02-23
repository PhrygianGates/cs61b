package byog.lab5;
import org.junit.Test;
import static org.junit.Assert.*;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {

    public void drawline (TETile[][] tiles, TETile tile, int length, int x, int y) {
        for (int i = 0; i < length; i++) {
            tiles[x + i][y] = tile;
        }
    }
    public void addHexagon (TETile[][] tiles, TETile tile, int size, int x, int y) {
        for (int i = 0; i < size; i++) {
            drawline(tiles, tile, size + 2 * i, x + size - 1 - i, y + i);
            drawline(tiles, tile, size + 2 * i, x + size - 1 - i, y + 2 * size - 1 - i);
        }
    }
}
