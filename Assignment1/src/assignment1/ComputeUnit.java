package assignment1;

import Jama.Matrix;
import Jama.SingularValueDecomposition;

public class ComputeUnit {
    /**
     * Returns the SVD approximation for a matrix.
     * @param SVD the singular value decomposition
     * @param rho the number of singular values to use
     * @return approximated matrix
     * @throws IllegalArgumentException when rho greater than rank of SVD
     */
    public static Matrix computeAHat(final SingularValueDecomposition SVD, final int rho) {
        if (rho > SVD.rank())
            throw new IllegalArgumentException("Rho " + rho + " greater than rank of SVD " + SVD.rank() + ".");
        final Matrix U = SVD.getU();
        final double[] s = SVD.getSingularValues();
        final Matrix V = SVD.getV();
        final Matrix AHat = new Matrix(U.getRowDimension(), U.getColumnDimension());
        for (int i = 0; i < rho; ++i) {
            final Matrix u_i = U.getMatrix(0, U.getRowDimension() - 1, i, i);
            final Matrix v_i_t = V.getMatrix(0, V.getRowDimension() - 1, i, i).transpose();
            AHat.plusEquals(u_i.timesEquals(s[i]).times(v_i_t));
        }
        return AHat;
    }

    /**
     * Computes the compression achieved by the SVD decomposition of a matrix.
     * @param A the matrix
     * @param rho the number of singular values used
     * @return the compression
     */
    public static double computeCompression(final Matrix A, final int rho) {
        final int rows = A.getRowDimension();
        final int cols = A.getColumnDimension();
        final double k_rho = rho * (1 + rows + cols);
        return 1 - k_rho / (rows * cols);
    }
}
