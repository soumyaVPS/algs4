import java.lang.Math;
import java.lang.IllegalArgumentException;

public class PercolationStats {
	private int x[];
	private int N, T;
    public double callsToOpen = 0;
	public PercolationStats(int N, int T) { // perform T independent
											// computational experiments on an
											// N-by-N grid
        if (N < 0 || T< 0) throw new IllegalArgumentException(  );
		x = new int[T];
		this.T = T;
		this.N = N;

		if (N <= 0 || T <= 0) {
			throw new java.lang.IllegalArgumentException();
		}

		for (int trials = 0; trials < T; trials++) {
			Percolation perc = new Percolation(N);
			int gridsOpen = 0;
			// int i =1;
			while (!perc.percolates()) {

				int i = StdRandom.uniform(1, N + 1);
				int j = StdRandom.uniform(1, N + 1);
				// int j = 1;
				if (!perc.isOpen(i, j)) {
					perc.open(i, j);
                    callsToOpen ++;
					gridsOpen++;
				}
				// i+=1;

			}
			x[trials] = gridsOpen;
		}

	}

	public double mean() { // sample mean of percolation threshold
		float mu = 0;
		for (int i = 0; i < T; i++) {
			mu += x[i];

		}
		mu = mu / T;
		return mu;

	}

	public double stddev() { // sample standard deviation of percolation
								// threshold
		double sigsquare = 0;
		double mu = mean();
		for (int i = 0; i < T; i++)
			sigsquare += Math.pow((x[i] - mu), 2);
		sigsquare = sigsquare / (T - 1);
		return Math.sqrt(sigsquare);

	}

	public double confidenceLo() { // returns lower bound of the 95% confidence
									// interval
		double mu = mean();
		double sigma = stddev();
		return mu - 1.96 * sigma / Math.sqrt(T);
	}

	public double confidenceHi() { // returns upper bound of the 95% confidence
									// interval
		double mu = mean();
		double sigma = stddev();
		return mu + 1.96 * sigma / Math.sqrt(T);
	}

	public static void main(String[] args) { // test client, described below
		if (args.length != 2)
			StdOut.println("Usage: java PercolationStats N T");

		int N = Integer.parseInt(args[0]);
		int T = Integer.parseInt(args[1]);
		PercolationStats ps = new PercolationStats(N, T);
		StdOut.println("mean                   =" + ps.mean());
		StdOut.println("stddev                 =" + ps.stddev());
		StdOut.println("95% confidence interval=" + ps.confidenceLo() + ","
				+ ps.confidenceHi());
        StdOut.println(ps.callsToOpen/ (N*N*T));
	}
}