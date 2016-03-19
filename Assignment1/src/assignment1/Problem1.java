package assignment1;

import java.io.File;
import java.io.IOException;
import Jama.Matrix;
import Jama.SingularValueDecomposition;

public class Problem1 {

	private static void printMatrix(Matrix m) {
		System.out.println("Size: " + m.getRowDimension() + "x" + m.getColumnDimension());
		double[][] d = m.getArray();
		for (int row = 0; row < d.length; row++) {
			for (int col = 0; col < d[row].length; col++) {
				System.out.printf("%6.1f\t", m.get(row, col));
			}
			System.out.println();
		}
		System.out.println();
	}

	private static void printPGM(int[][] img) {
		System.out.println("Size: " + img.length + "x" + img[0].length);
		for (int row = 0; row < img.length; row++) {
			for (int col = 0; col < img[row].length; col++) {
				System.out.printf("%d\t", img[row][col]);
			}
			System.out.println();
		}
		System.out.println();
	}

	public static void main(String[] args) throws IOException {

		final int[][] image = PGMIO.read(new File(args[0]));

		final Matrix A = new Matrix(MatrixUtils.MatrixInt2Double(image));
		final SingularValueDecomposition SVD = A.svd();

		final File svdDir = new File("SVD");
		svdDir.mkdir();

		final Matrix U = SVD.getU();
		final int[][] imageU = MatrixUtils.MatrixDouble2Int(U.getArray(),
				new MatrixUtils.LinearMap(MatrixUtils.min(U.getArray()), MatrixUtils.max(U.getArray())));
		PGMIO.write(imageU, new File(svdDir, "U.pgm"));

		// printMatrix(U);

		final Matrix D = SVD.getS();
		final int[][] imageD = MatrixUtils.MatrixDouble2Int(D.getArray(),
				new MatrixUtils.LinearMap(MatrixUtils.min(D.getArray()), MatrixUtils.max(D.getArray())));
		PGMIO.write(imageD, new File(svdDir, "D.pgm"));

		printMatrix(D);
		printPGM(imageD);

		final Matrix V = SVD.getV();
		final int[][] imageV = MatrixUtils.MatrixDouble2Int(V.getArray(),
				new MatrixUtils.LinearMap(MatrixUtils.min(V.getArray()), MatrixUtils.max(V.getArray())));
		PGMIO.write(imageV, new File(svdDir, "V.pgm"));

		// printMatrix(V);

		final File aHatDir = new File("Ahat");
		aHatDir.mkdir();

		final int[] rhos = { 1, 2, 3, 4, 5, 10, 20, 30, 40, 90 };

		for (int rho : rhos) {
			final Matrix A_hat = ComputeUnit.computeAHat(SVD, rho);
			final int[][] imageAHat = MatrixUtils.MatrixDouble2Int(A_hat.getArray(), MatrixUtils.TRUNCATING_MAP);
			PGMIO.write(imageAHat, new File(aHatDir, rho + ".pgm"));

			System.out.print("MaxError: " + MatrixUtils.computeMaxError(A, A_hat));
			System.out.print(", MeanError: " + MatrixUtils.computeMeanError(A, A_hat));
			final double compression = ComputeUnit.computeCompression(A, rho);
			System.out.print(", Compression: " + compression + "\n");
		}

	}

}
