package assignment1;

import java.util.Arrays;

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
}
