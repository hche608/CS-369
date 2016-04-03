package assignment1;

import Jama.Matrix;
import Jama.SingularValueDecomposition;
import java.util.Arrays;

public class PrincipalComponentsAnalysis {

    private final Matrix A;
    private final Matrix U;
    private final Matrix D;

    /**
     * Constructs the principal components analysis for the given matrix.
     * Assumes that rows of the matrix are independent samples and that columns represent variables.
     * @param A the matrix
     */
    public PrincipalComponentsAnalysis(final Matrix A) {

        this.A = A;
        final Matrix B = centerColumns(A);
        //final Matrix S;

        final int rows = A.getRowDimension();
        final int cols = A.getColumnDimension();

        if (rows > cols) {
            final SingularValueDecomposition SVD = B.svd();
            U = SVD.getV();
            D = SVD.getS();
        } else {
            final SingularValueDecomposition SVD = B.transpose().svd();
            U = SVD.getU();
            D = SVD.getS();
        }

    }

    /**
     * Centers the columns of a matrix.
     * @param A the matrix
     * @return the centered matrix
     */
    private Matrix centerColumns(final Matrix A) {
        final Matrix B = A.copy();
        final int rows = B.getRowDimension();
        final int cols = B.getColumnDimension();
        for (int i = 0; i < cols; ++i) {
            final Matrix column = B.getMatrix(0, rows - 1, i, i);
            final double[][] mean = new double[rows][];
            Arrays.fill(mean, new double[]{Arrays.stream(column.getColumnPackedCopy()).sum() / rows});
            column.minusEquals(new Matrix(mean));
            B.setMatrix(0, rows - 1, i, i, column);
        }
        return B;
    }

    /**
     * Get the principal component associated with the ith largest singular value.
     * @param i i
     * @return the principal component
     */
    public Matrix getPrincipalComponent(final int i) {
        return U.getMatrix(0, U.getRowDimension() - 1, i, i);
    }

    /**
     * Get the number of principal components.
     * @return the number of principal components
     */
    public int getPrincipalComponentCount() {
        return U.getColumnDimension();
    }

    /**
     * Project the data into the k-dimensional space defined by the first k principal components.
     * @param k k
     * @return the projected data
     */
    public Matrix getProjectedData(final int k) {
        final Matrix P_k = getProjectionMatrix(k);
        return A.times(P_k);
    }

    /**
     * Get the projection matrix whose columns are the first k principal components.
     * @param k k
     * @return the projection matrix
     */
    private Matrix getProjectionMatrix(final int k) {
        return U.getMatrix(0, U.getRowDimension() - 1, 0, k - 1);
    }

    /**
     * Get the ith largest singular value.
     * @param i i
     * @return the singular value
     */
    public double getSingularValue(final int i) {
        return D.get(i, i);
    }

}