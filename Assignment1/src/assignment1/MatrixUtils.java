package assignment1;

import java.util.Arrays;
import java.util.function.DoubleToIntFunction;
import java.util.function.DoubleUnaryOperator;

import Jama.Matrix;

/**
 * @author hche608
 *
 */
public class MatrixUtils {

	/**
	 * Maps doubles to bytes by truncating values outside the byte range.
	 */
	public static final DoubleToIntFunction TRUNCATING_MAP = x -> {
		final int i = (int) Math.round(x);
		if (i < 0)
			return 0;
		else if (i > 255)
			return 255;
		else
			return i;
	};

	/**
	 * Represents a linear map from a given range to the byte range.
	 */
	public static class LinearMap implements DoubleToIntFunction {
		private final double a;
		private final double b;

		public LinearMap(final double min, final double max) {
			a = min;
			b = max - min;
		}

		@Override
		public int applyAsInt(final double x) {
			return (int) Math.round(255 * (x - a) / b);
		}
	}

	/**
	 * Represents a logarithmic map from a given range to the byte range.
	 */
	public static class LogarithmicMap implements DoubleToIntFunction {
		private final double a;
		private final double b;
		private final DoubleUnaryOperator T;

		public LogarithmicMap(final double min, final double max) {
			T = x -> x - min + 1;
			a = 255 / Math.log(T.applyAsDouble(max));
			b = -a * Math.log(T.applyAsDouble(min));
		}

		@Override
		public int applyAsInt(final double x) {
			return (int) Math.round(a * Math.log(T.applyAsDouble(x)) + b);
		}
	}

	/**
	 * Finds the maximum value in a two-dimensional double array.
	 * 
	 * @param d
	 *            2D array of doubles
	 * @return maximum value
	 */
	public static double max(final double[][] d) {
		return Arrays.stream(d).flatMapToDouble(Arrays::stream).max().getAsDouble();
	}

	/**
	 * Finds the minimum value in a two-dimensional double array.
	 * 
	 * @param d
	 *            2D array of doubles
	 * @return minimum value
	 */
	public static double min(final double[][] d) {
		return Arrays.stream(d).flatMapToDouble(Arrays::stream).min().getAsDouble();
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

	public static double[][] MatrixInt2Double(int[][] intMatrix) {
		final double[][] result = new double[intMatrix.length][intMatrix[0].length];
		for (int i = 0; i < result.length; i++) {
			for (int j = 0; j < result[0].length; j++) {
				result[i][j] = (double) intMatrix[i][j];
			}
		}
		return result;

	}

	public static int[][] MatrixDouble2Int(double[][] doubleMatrix, DoubleToIntFunction f) {
		final int[][] result = new int[doubleMatrix.length][doubleMatrix[0].length];
		for (int i = 0; i < result.length; i++) {
			for (int j = 0; j < result[0].length; j++) {
				result[i][j] = f.applyAsInt(doubleMatrix[i][j]);
			}
		}
		return result;

	}

}
