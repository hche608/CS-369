package assignment1;

/**
 * @author hche608
 *
 */
public class MatrixUtils {


	public static double[][] MatrixInt2Double(int[][] intMatrix) {
		final double[][] result = new double[intMatrix.length][intMatrix[0].length];
		for (int i = 0; i < result.length; i++) {
			for (int j = 0; j < result[0].length; j++) {
				result[i][j] = (double) intMatrix[i][j];
			}
		}
		return result;

	}

	public static int[][] MatrixDouble2Int(int[][] doubleMatrix) {
		final int[][] result = new int[doubleMatrix.length][doubleMatrix[0].length];
		for (int i = 0; i < result.length; i++) {
			for (int j = 0; j < result[0].length; j++) {
				result[i][j] = (int) doubleMatrix[i][j];
			}
		}
		return result;

	}

}
