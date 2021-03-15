package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    boolean[][] grid;
    WeightedQuickUnionUF dSet;
    int top, bottom, openNum;

    public Percolation(int N) {              // create N-by-N grid, with all sites initially blocked
        if (N <= 0) {
            throw new IllegalArgumentException();
        }
        grid = new boolean[N][N];
        dSet = new WeightedQuickUnionUF(N * N + 2);
        top = N * N;
        bottom = N * N + 1;
        openNum = 0;
    }
    public void open(int row, int col) {      // open the site (row, col) if it is not open already
        if (isOpen(row, col)) {
            return;
        }
        grid[row][col] = true;
        openNum += 1;
        if (row == 0) {
            dSet.union(top, toInt(row, col));
        }
        if (row == grid.length - 1) {
            dSet.union(bottom, toInt(row, col));
        }
        //for this one's neighbors
        if (col != 0 && isOpen(row, col - 1)) {
            dSet.union(toInt(row, col), toInt(row, col - 1));
        }
        if (col != grid.length - 1 && isOpen(row, col + 1)) {
            dSet.union(toInt(row, col), toInt(row, col + 1));
        }
        if (row != 0 && isOpen(row - 1, col)) {
            dSet.union(toInt(row, col), toInt(row - 1, col));
        }
        if (row != grid.length - 1 && isOpen(row + 1, col)) {
            dSet.union(toInt(row, col), toInt(row + 1, col));
        }
    }
    public boolean isOpen(int row, int col) { // is the site (row, col) open?
        return grid[row][col];
    }
    //convert a xy coordinate to an integer
    private int toInt(int row, int col) {
        int N = grid.length;
        return row * N + col;
    }
    public boolean isFull(int row, int col) {  // is the site (row, col) full?
        int num = toInt(row, col);
        return dSet.connected(num, top);
    }
    public int numberOfOpenSites() {          // number of open sites
        return openNum;
    }
    public boolean percolates() {             // does the system percolate?
        return dSet.connected(top, bottom);
    }
    public static void main(String[] args) {  // use for unit testing (not required)
        Percolation percolation = new Percolation(4);
        percolation.open(0, 1);
        percolation.open(1, 1);
        percolation.open(1, 2);
        percolation.open(2, 2);
        //percolation.open(3, 2);
        System.out.println(percolation.isFull(1, 2));
        System.out.println(percolation.openNum);
        System.out.println(percolation.percolates());
    }

}
