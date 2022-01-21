import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private final int trials;
    private final double[] mean;
    private static final double index = 1.96;

    // perform independent trals on an n-by-n grid
    public PercolationStats(int n, int trials) {
        this.trials = trials;
        validate(n, trials);
        mean = new double[trials];
        Percolation per;
        for (int i = 0; i < trials; i++) {
            per = new Percolation(n);
            double count = 0;
            while (!per.percolates()) {
                per.open(StdRandom.uniform(1, n + 1), StdRandom.uniform(1, n + 1));
                count = per.numberOfOpenSites();
                if (per.percolates()) {
                    break;
                }
            }
            mean[i] = count / (n * n);
        }
    }

    private void validate(int n, int t) {
        if (n <= 0 || t <= 0) {
            throw new IllegalArgumentException("illegal value of n or trials");
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(mean);
    }
    //
    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(mean);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - (index * stddev()) / Math.sqrt(trials);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + (index * stddev()) / Math.sqrt(trials);
    }

    public static void main(String[] args) {
        PercolationStats test = new PercolationStats(2, 10000);
        System.out.println("mean = " + test.mean());
        System.out.println("stddev = " + test.stddev());
        System.out.println("95% confidence interval = [" + test.confidenceLo() + "," + test.confidenceHi() + "]");
    }
}
