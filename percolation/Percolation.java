import java.lang.IllegalArgumentException;
public class Percolation {

	private boolean grid[][];
	private int N;
	// QuickFindUF uf;
	private WeightedQuickUnionUF uf;
	private WeightedQuickUnionUF uffull;
	public Percolation(int N) { // create N-by-N grid, with all sites blocked
        if (N < 0) throw new IllegalArgumentException();
		grid = new boolean[N + 2][N];
		this.N = N;
		// StdOut.println("N is " + N);
		uf = new WeightedQuickUnionUF((N + 2) * N);
		uffull = new WeightedQuickUnionUF((N + 1) * N);
		// uf = new QuickFindUF((N+2) * N);
		for (int i = 0; i < N; i++) {
			uf.union(0, i);
			uf.union(N * (N + 1), N * (N + 1) + i);
			uffull.union(0,i);

			grid[0][i] = true;
			grid[N + 1][i] = true;
		}

		for (int i = 1; i < N + 1; i++)
			for (int j = 0; j < N; j++) {
				grid[i][j] = false;

			}
	}

	public void open(int i, int j) { // open site (row i, column j) if it is not
										// already

		int gridi = i;
		int gridj = j - 1;
		// StdOut.println("open " +i+ " "+ j);
		if (i < 1 || i > N || j < 1 || j > N)
			throw new java.lang.IndexOutOfBoundsException();
		grid[gridi][gridj] = true;
		if (gridj > 0 && grid[gridi][gridj - 1]) {
			uf.union(gridi * N + gridj, gridi * N + gridj - 1);
			uffull.union(gridi * N + gridj, gridi * N + gridj - 1);
		}
		if (gridj < N - 1 && grid[gridi][gridj + 1]) {
			uf.union(gridi * N + gridj, gridi * N + gridj + 1);
			uffull.union(gridi * N + gridj, gridi * N + gridj + 1);
		}

		if (grid[gridi - 1][gridj]) {
			uf.union(gridi * N + gridj, (gridi - 1) * N + gridj);
			uffull.union(gridi * N + gridj, (gridi - 1) * N + gridj);
		}

		if (grid[gridi + 1][gridj]) {
			uf.union(gridi * N + gridj, (gridi + 1) * N + gridj);
			if (gridi<N) {
				uffull.union(gridi * N + gridj, (gridi + 1) * N + gridj);
			}
		}
		
	}

	public boolean isOpen(int i, int j) { // is site (row i, column j) open?
		// StdOut.println("is " +i+ " "+ j+ " open?");
		if (i < 1 || i > N || j < 1 || j > N)
			throw new java.lang.IndexOutOfBoundsException();
		return grid[i][j - 1];
	}

	public boolean isFull(int i, int j) { // is site (row i, column j) full?
		// StdOut.println("is " +i+ " "+ j+"Full? Array index" + i*(j-1) );
		if (i < 1 || i > N || j < 1 || j > N)
			throw new java.lang.IndexOutOfBoundsException();
		
		return uf.connected(0, N * i + j - 1) & uffull.connected(0, N * i + j - 1);
	}

	public boolean percolates() { // does the system percolate?
		return uf.connected(0, N * (N + 1));
	}
}