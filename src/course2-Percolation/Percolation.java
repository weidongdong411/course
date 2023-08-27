import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int n;
    private boolean[] open; // open[i] = is open or not of i
    private int openNums;
    private WeightedQuickUnionUF weightedQuickUnionUF;

    private int vt, vb;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        this.n = n;
        open = new boolean[n * n + 2];
        openNums = 0;
        weightedQuickUnionUF = new WeightedQuickUnionUF(n * n + 2);

        vt = 0;
        vb = n * n + 1;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        int i = getI(row, col);
        int cur = weightedQuickUnionUF.find(i);
        // up
        if (row > 1) {
            int upI = getI(row - 1, col);
            int up = weightedQuickUnionUF.find(upI);
            if (cur != up && isOpen(row - 1, col)) {
                weightedQuickUnionUF.union(i, upI);
            }
        }

        // left
        if (col > 1) {
            int leftI = getI(row, col - 1);
            int left = weightedQuickUnionUF.find(leftI);
            if (cur != left && isOpen(row, col - 1)) {
                weightedQuickUnionUF.union(i, leftI);
            }
        }

        // down
        if (row < n) {
            int downI = getI(row + 1, col);
            int down = weightedQuickUnionUF.find(downI);
            if (cur != down && isOpen(row + 1, col)) {
                weightedQuickUnionUF.union(i, downI);
            }
        }

        // right
        if (col < n) {
            int rightI = getI(row, col + 1);
            int right = weightedQuickUnionUF.find(rightI);
            if (cur != right && isOpen(row, col + 1)) {
                weightedQuickUnionUF.union(i, rightI);
            }
        }

        open[i] = true;
        openNums++;

        if (i <= n) {
            weightedQuickUnionUF.union(i, vt);
        }

        if (i >= n * (n - 1) + 1 && n <= n * n) {
            weightedQuickUnionUF.union(i, vb);
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        int i = getI(row, col);
        return open[i];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        int i = getI(row, col);
        int cur = weightedQuickUnionUF.find(i);

        return cur == weightedQuickUnionUF.find(vt);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openNums;
    }

    // does the system percolate?
    public boolean percolates() {
        return weightedQuickUnionUF.find(vt) == weightedQuickUnionUF.find(vb);
    }

    private int getI(int row, int col) {
        if (row < 1 || col < 1) {
            throw new IllegalArgumentException("");
        }

        int i = (row - 1) * n + (col);
        return i;
    }

    // test client (optional)
    public static void main(String[] args) {
        Percolation p = new Percolation(4);
        p.open(1, 2);
        p.open(2, 2);
        p.open(3, 2);
        p.open(3, 3);
        p.open(4, 3);

        boolean b = p.percolates();
        System.out.println(b);
    }
}
