package hw2;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    double mean;
    double stddev;
    int T;
    private double experiment(Percolation percolation) {
        int n = percolation.grid.length;
        while (!percolation.percolates()) {
            percolation.open(StdRandom.uniform(n), StdRandom.uniform(n));
        }
        return  (percolation.openNum * 1.0) / (n * n);
    }
    public PercolationStats(int N, int T, PercolationFactory pf) {
        double[] data = new double[T];
        for (int i = 0; i < T; i++) {
            Percolation percolation = pf.make(N);
            data[i] = experiment(percolation);
        }
        mean = StdStats.mean(data);
        stddev = StdStats.stddev(data);
        this.T = T;
    }
    public double mean() {
        return mean;
    }
    public double stddev() {
        return stddev;
    }
    public double confidenceLow() {
        return mean - 1.96 * stddev / Math.sqrt(T);
    }
    public double confidenceHigh() {
        return mean + 1.96 * stddev / Math.sqrt(T);
    }
    public static void main(String[] args) {
        PercolationFactory pf = new PercolationFactory();
        PercolationStats test = new PercolationStats(400, 100, pf);
        System.out.println(test.mean());
        System.out.println(test.stddev());
        System.out.println(test.confidenceLow() + "," + test.confidenceHigh());
    }
}
