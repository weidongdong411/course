import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private static final double CONFIDENCE_95 = 1.96;
    private int trials;
    private double[] thresholds;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("");
        }

        this.trials = trials;
        thresholds = new double[trials];

        int t = 0;
        while (t < trials) {
            Percolation percolation = new Percolation(n);
            while (!percolation.percolates()) {
                int rand = StdRandom.uniformInt(n * n);
                int r = rand / n + 1, c = rand % n + 1;
                if (!percolation.isOpen(r, c)) {
                    percolation.open(r, c);
                }
            }

            thresholds[t++] = (double) percolation.numberOfOpenSites() / (n * n);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(thresholds);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(thresholds);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {

        return mean() - (CONFIDENCE_95 * stddev() / Math.sqrt(trials));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + (CONFIDENCE_95 * stddev() / Math.sqrt(trials));
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);

        PercolationStats percolationStats = new PercolationStats(n, t);

        System.out.println("mean=" + percolationStats.mean());
        System.out.println("stddev=" + percolationStats.stddev());
        System.out.println("95% confidence interval=[" + percolationStats.confidenceLo() + "," + percolationStats.confidenceHi() + "]");
    }
}
