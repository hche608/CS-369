package assignment1;

import java.util.Arrays;
import java.util.List;
import java.util.function.DoubleUnaryOperator;

import Jama.Matrix;
import Jama.SingularValueDecomposition;

public class ComputeUnit {
	/**
	 * Returns the SVD approximation for a matrix.
	 * 
	 * @param SVD
	 *            the singular value decomposition
	 * @param rho
	 *            the number of singular values to use
	 * @return approximated matrix
	 * @throws IllegalArgumentException
	 *             when rho greater than rank of SVD
	 */

	public static Matrix computeAHat(final SingularValueDecomposition SVD, final int rho) {
		if (rho > SVD.rank())
			throw new IllegalArgumentException("Rho " + rho + " greater than rank of SVD " + SVD.rank() + ".");
		final Matrix U = SVD.getU();
		final double[] S = SVD.getSingularValues();
		final Matrix V = SVD.getV();
		final Matrix AHat = new Matrix(U.getRowDimension(), U.getColumnDimension());
		for (int i = 0; i < rho; ++i) {
			final Matrix u_i = U.getMatrix(0, U.getRowDimension() - 1, i, i);
			final Matrix v_i_t = V.getMatrix(0, V.getRowDimension() - 1, i, i).transpose();
			AHat.plusEquals(u_i.timesEquals(S[i]).times(v_i_t));
		}
		return AHat;
	}

	/**
	 * Computes the compression achieved by the SVD decomposition of a matrix.
	 * 
	 * @param A
	 *            the matrix
	 * @param rho
	 *            the number of singular values used
	 * @return the compression
	 */
	public static double computeCompression(final Matrix A, final int rho) {
		final int rows = A.getRowDimension();
		final int cols = A.getColumnDimension();
		final double k_rho = rho * (1 + rows + cols);
		return 1 - k_rho / (rows * cols);
	}

	/**
	 * Computes the maximum error between a matrix and its approximation.
	 * 
	 * @param A
	 *            the matrix
	 * @param AHat
	 *            the approximation
	 * @return the maximum error
	 */
	public static double computeMaxError(final Matrix A, final Matrix AHat) {
		return Arrays.stream(A.minus(AHat).getArray()).flatMapToDouble(Arrays::stream).map(Math::abs).max()
				.getAsDouble();
	}

	/**
	 * Computes the mean error between a matrix and its approximation.
	 * 
	 * @param A
	 *            the matrix
	 * @param AHat
	 *            the approximation
	 * @return the mean error
	 */
	public static double computeMeanError(final Matrix A, final Matrix AHat) {
		return Arrays.stream(A.minus(AHat).getArray()).flatMapToDouble(Arrays::stream).map(Math::abs).sum()
				/ (A.getColumnDimension() * A.getRowDimension());
	}
	
    /**
     * Computes the pseudoinverse of a matrix.
     * @param A the matrix to invert
     * @return the pseudoinverse of A
     */
    public static Matrix computePseudoInverse(final Matrix A) {
        final SingularValueDecomposition SVD = A.svd();
        final Matrix U = SVD.getU();
        final Matrix D = SVD.getS();
        final Matrix D_plus = computePseudoInverseDiagonal(D);
        final Matrix V = SVD.getV();
        return V.times(D_plus).times(U.transpose());
    }

    /**
     * Computes the pseudoinverse of a diagonal matrix.
     * @param D the diagonal matrix to invert
     * @return the pseudoinverse of D
     */
    public static Matrix computePseudoInverseDiagonal(final Matrix D) {
        final Matrix D_plus = D.copy();
        for (int i = 0; i < D_plus.getRowDimension(); ++i) {
            final double d_i = D.get(i, i);
            D_plus.set(i, i, d_i != 0 ? 1 / d_i : 0);
        }
        return D_plus;
    }
    
	/**
	 * Uses Newton's method to find a root of a function.
	 * 
	 * @param x_0
	 *            the initial value
	 * @param f
	 *            the function
	 * @param dfdx
	 *            the derivative of the function with respect to x
	 * @param xs
	 *            a log of x_i values
	 * @return a root of the function
	 */
	public static double newtonsMethod(final double x_0, final DoubleUnaryOperator f, final DoubleUnaryOperator dfdx,
			final List<Double> xs) {
		xs.add(x_0);
		double x_im1 = x_0;
		double x_i;
		while (true) {
			x_i = iterateNewtonsMethod(x_im1, f, dfdx);
			xs.add(x_i);
			if (Math.abs(x_i - x_im1) <= 0.0001)
				break;
			x_im1 = x_i;
		}
		return x_i;
	}
	
	/**
	 * Computes an iteration of Newton's method.
	 * 
	 * @param x_i
	 *            the current value of x
	 * @param f
	 *            the function
	 * @param dfdx
	 *            the derivative of the function with respect to x
	 * @return the next value of x
	 */
	private static double iterateNewtonsMethod(final double x_i, final DoubleUnaryOperator f,
			final DoubleUnaryOperator dfdx) {
		return x_i - f.applyAsDouble(x_i) / dfdx.applyAsDouble(x_i);
	}
}
