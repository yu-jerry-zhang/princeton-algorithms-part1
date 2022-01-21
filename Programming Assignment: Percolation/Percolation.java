import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final WeightedQuickUnionUF uf1;
    private final WeightedQuickUnionUF uf2;
    private final boolean[][] id;
    private final int virtualTop;
    private final int virtualBot;
    private int count;
    private final int n;

    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("illegal value of n");
        }
        id = new boolean[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                id[i][j] = false;
            }
        }
        this.n = n;
        virtualTop = 0;
        virtualBot = n * n + 1;
        uf1 = new WeightedQuickUnionUF(n * n + 2);
        uf2 = new WeightedQuickUnionUF(n * n + 1);
    }

    // open the site(row,col) if it is not open already
    public void open(int row, int col) {
        validate(row, col);
        if (!id[row - 1][col - 1]) {
            id[row - 1][col - 1] = true;
            // connect point at row1 to the virtual top
            if (row == 1) {
                uf1.union(trans(row, col), virtualTop);
                uf2.union(trans(row, col), virtualTop);
            }
            // connect point at row n to the virtual bottom
            if (row == n) {
                uf1.union(trans(row, col), virtualBot);
            }
            count++;
            // up site
            if (row > 1 && isOpen(row - 1, col)) {
                uf1.union(trans(row, col), trans(row - 1, col));
                uf2.union(trans(row, col), trans(row - 1, col));
            }
            // down site
            if (row < n && isOpen(row + 1, col)) {
                uf1.union(trans(row, col), trans(row + 1, col));
                uf2.union(trans(row, col), trans(row + 1, col));
            }
            // left site
            if (col > 1 && isOpen(row, col - 1)) {
                uf1.union(trans(row, col), trans(row, col - 1));
                uf2.union(trans(row, col), trans(row, col - 1));
            }
            // right site
            if (col < n && isOpen(row, col + 1)) {
                uf1.union(trans(row, col), trans(row, col + 1));
                uf2.union(trans(row, col), trans(row, col + 1));
            }
        }
    }

    private void validate(int row, int col) {
        if (row <= 0 || row > n || col <= 0 || col > n) {
            throw new IllegalArgumentException("illegal value of row or column");
        }
    }

    public boolean isOpen(int row, int col) {
        validate(row, col);
        return id[row - 1][col - 1];
    }

    public boolean isFull(int row, int col) {
        validate(row, col);
        return uf2.find(virtualTop) == uf2.find(trans(row, col));
    }

    public int numberOfOpenSites() {
        return count;
    }

    public boolean percolates() {
        return uf1.find(virtualTop) == uf1.find(virtualBot);
    }

    private int trans(int row, int col) {
        return (row - 1) * n + col;
    }

    public static void main(String[] args) {
        Percolation test = new Percolation(5);
        test.open(1, 1);
        test.open(2, 1);
        test.open(3, 1);
        test.open(4, 1);
        test.open(5, 1);
        test.open(5, 3);
        test.open(4, 3);
/*
        int n=test.id[1][0];
        boolean a = uf1.connected(test.trans(3,3), test.trans(5,2) );
        System.out.println(n);
*/
        System.out.println(test.id[1][0]);
        System.out.println(test.numberOfOpenSites());
        System.out.println("Is this point full?" + test.isFull(4, 3));
        System.out.println("Is this point open?" + test.isOpen(4, 3));
        System.out.println("Is this model percolate?"+test.percolates());
    }
}