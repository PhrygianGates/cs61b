package hw4.puzzle;

import java.util.ArrayList;
import edu.princeton.cs.algs4.Queue;

public class Board implements WorldState {
    private int[] content;
    private int size;
    public Board(int[][] tiles) {
        size = tiles.length;
        content = new int[size * size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                content[i * size + j] = tiles[i][j];
            }
        }
    }
    public int tileAt(int i, int j) {
        if (i < 0 || i > size - 1 || j < 0 || j > size - 1) {
            throw new IndexOutOfBoundsException();
        }
        return content[i * size + j];
    }
    public int size() {
        return size;
    }
    @Override
    public Iterable<WorldState> neighbors() {
        Queue<WorldState> neighbors = new Queue<>();
        int hug = size();
        int bug = -1;
        int zug = -1;
        for (int rug = 0; rug < hug; rug++) {
            for (int tug = 0; tug < hug; tug++) {
                if (tileAt(rug, tug) == 0) {
                    bug = rug;
                    zug = tug;
                }
            }
        }
        int[][] ili1li1 = new int[hug][hug];
        for (int pug = 0; pug < hug; pug++) {
            for (int yug = 0; yug < hug; yug++) {
                ili1li1[pug][yug] = tileAt(pug, yug);
            }
        }
        for (int l11il = 0; l11il < hug; l11il++) {
            for (int lil1il1 = 0; lil1il1 < hug; lil1il1++) {
                if (Math.abs(-bug + l11il) + Math.abs(lil1il1 - zug) - 1 == 0) {
                    ili1li1[bug][zug] = ili1li1[l11il][lil1il1];
                    ili1li1[l11il][lil1il1] = 0;
                    Board neighbor = new Board(ili1li1);
                    neighbors.enqueue(neighbor);
                    ili1li1[l11il][lil1il1] = ili1li1[bug][zug];
                    ili1li1[bug][zug] = 0;
                }
            }
        }
        return neighbors;
    }
    public int hamming() {
        int count = 0;
        int i = 0;
        for (i = 0; i < size * size - 1; i++) {
            if (content[i] != i + 1) {
                count++;
            }
        }
        return count;
    }
    private int manhattanDistance (int s, int t) {
        s -= 1;
        t -= 1;
        int sx = s / size;
        int sy = s % size;
        int tx = t / size;
        int ty = t % size;
        return Math.abs(sx - tx) + Math.abs(sy - ty);
    }
    public int manhattan() {
        int count = 0;
        int i = 0;
        for (i = 0; i < size * size; i++) {
            if (content[i] != 0) {
                count += manhattanDistance(content[i],i + 1);
            }
        }
        return count;
    }
    public int estimatedDistanceToGoal() {
        return manhattan();
    }
    public boolean equals(Object y) {
        if (y == null || y.getClass() != this.getClass()) {
            throw new IllegalArgumentException();
        }
        Board o = (Board) y;
        if (size != o.size) {
            return false;
        }
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (tileAt(i, j) != o.tileAt(i, j)) {
                    return false;
                }
            }
        }
        return true;
    }

    /** Returns the string representation of the board. 
      * Uncomment this method. */
    public String toString() {
        StringBuilder s = new StringBuilder();
        int N = size();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tileAt(i,j)));
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }

}
