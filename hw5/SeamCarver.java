import edu.princeton.cs.algs4.Picture;

import java.awt.*;

public class SeamCarver {
    Picture picture;
    int width;
    int height;
    boolean isVertical;
    public SeamCarver(Picture picture) {
        this.picture = picture;
        width = picture().width();
        height = picture.height();;
        isVertical = true;
    }
    private void transpose() {
        isVertical = !isVertical;
    }
    public Picture picture() {
        return picture;
    }
    public int width() {
        if (isVertical) {
            return width;
        }
        return height;
    }
    public int height() {
        if (isVertical) {
            return height;
        }
        return width;
    }
    public double energy(int x, int y) {
        if (isVertical) {
            return energyhelper(x, y);
        }
        return energyhelper(y, x);
    }
    private double energyhelper(int x, int y) {
        Color u, d, l ,r;
        u = y > 0 ? picture.get(x, y - 1) : picture.get(x, height - 1);
        d = y < height - 1 ? picture.get(x, y + 1) : picture.get(x, 0);
        l = x > 0 ? picture.get(x - 1, y) : picture.get(width - 1, y);
        r = x < width - 1 ? picture.get(x + 1, y) : picture.get(0, y);
        double rx = l.getRed() - r.getRed();
        double gx = l.getGreen() - r.getGreen();
        double bx = l.getBlue() - r.getBlue();
        double ry = u.getRed() - d.getRed();
        double gy = u.getGreen() - d.getGreen();
        double by = u.getBlue() - d.getBlue();
        return rx * rx + gx * gx + bx * bx + ry * ry + gy * gy + by * by;
    }
    public int[] findHorizontalSeam() {
        transpose();
        int[] res = findVerticalSeam();
        transpose();
        return res;
    }
    public int[] findVerticalSeam() {
        int[][] parent = new int[width()][height()];
        double[][] cost = new double[width()][height()];
        for (int i = 0; i < width(); i++) {
            cost[i][0] = energy(i, 0);
        }
        for (int j = 1; j < height(); j++) {
            for (int i = 0; i < width(); i++) {
                double t;
                cost[i][j] = Double.POSITIVE_INFINITY;
                for (int k = Math.max(i - 1, 0); k <= Math.min(i + 1, width() - 1); k++) {
                    t = cost[k][j - 1] + energy(i, j);
                    if (t < cost[i][j]) {
                        cost[i][j] = t;
                        parent[i][j] = k;
                    }
                }
            }
        }
        int[] res = new int[height()];
        double minCost = Double.POSITIVE_INFINITY;
        for (int i = 0; i < width(); i++) {
            if (cost[i][height() - 1] < minCost) {
                minCost = cost[i][height() - 1];
                res[height() - 1] = i;
            }
        }
        for (int j = height() - 1; j > 0; j--) {
            res[j - 1] = parent[res[j]][j];
        }
        return res;
    }
    public void removeHorizontalSeam(int[] seam) {
        if (seam.length != width || !validate(seam)) {
            throw new IllegalArgumentException();
        }
        SeamRemover.removeHorizontalSeam(picture, seam);
    }
    public void removeVerticalSeam(int[] seam) {
        if (seam.length != height || !validate(seam)) {
            throw new IllegalArgumentException();
        }
        SeamRemover.removeVerticalSeam(picture, seam);
    }
    private boolean validate(int[] seam) {
        for (int i = 0; i < seam.length - 1; i++) {
            if (Math.abs(seam[i + 1] - seam[i]) > 1) {
                return false;
            }
        }
        return true;
    }
}
