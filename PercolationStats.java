package assignment1;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
public class PercolationStats {
	private double mean;
	private double stddev;
	private double confidenceLo;
	private double confidenceHi;
	private double[] percolationThresholds;
	
	public PercolationStats(int n, int trials) {
		if (n <=0) {
			throw new IllegalArgumentException("the grid size must be larger than zero");
		}
		if (trials <= 0) {
			throw new IllegalArgumentException("the number of experiment should larger than zero");
		}
		
		/*double[] percolationThresholds = new double[trials];*/
		for (int i = 0; i < trials; i++) {
			Percolation percolation = new Percolation(n);
			
			int runs = 0;
			while (!percolation.percolates()) {
				int column;
				int row;
				
				do {
					column = 1 + StdRandom.uniform(n);
					row = 1 + StdRandom.uniform(n);
				} while (percolation.isOpen(row, column));
				
				percolation.open(row, column);
				runs++;
			}
			percolationThresholds[i] = runs/(double) (n * n);
		}
		
		double confidenceFraction = (1.96 * stddev()) / Math.sqrt(trials);
        confidenceLo = mean - confidenceFraction;
        confidenceHi = mean + confidenceFraction;
	}
	
	public double mean() {
		mean = StdStats.mean(percolationThresholds);
		return mean;
	}
	
	public double stddev() {
		stddev = StdStats.stddev(percolationThresholds);
		return stddev;
	}
	
	public double confidenceLo() {
		return confidenceLo;
	}
	
	public double confidenceHi() {
		return confidenceHi;
	}
	
	public static void main(String[] args) {
		int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);

        PercolationStats stats = new PercolationStats(n, trials);
        System.out.println("mean                    = " + stats.mean());
        System.out.println("stddev                  = " + stats.stddev());
        System.out.println("95% confidence interval = " + stats.confidenceLo() + ", " + stats.confidenceHi());
	}
}
