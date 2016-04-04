package assignment1;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import Jama.Matrix;

public class Problem2 {



    /**
     * Computes the mean of the values in a matrix.
     * @param A the matrix
     * @return the mean of the matrix elements
     */
    private static double mean(final Matrix A) {
        return Arrays.stream(A.getArray()).flatMapToDouble(Arrays::stream).sum() /
                (A.getRowDimension() * A.getColumnDimension());
    }

    /**
     * Computes the standard deviation of the values in a matrix.
     * @param A the matrix
     * @return the standard deviation of the matrix elements
     */
    private static double stdev(final Matrix A) {
        final double Ex2 = Arrays.stream(A.getArray()).flatMapToDouble(Arrays::stream).map(x -> x*x).sum() /
                (A.getRowDimension() * A.getColumnDimension());
        final double Ex = mean(A);
        return Math.sqrt(Ex2 - Ex * Ex);
    }
	
	public static void main(String[] args) throws IOException {
		final int[][] image = PGMIO.read(new File(args[0]));

        final Matrix A = new Matrix(MatrixUtils.MatrixInt2Double(image));
        final Matrix P_inv = ComputeUnit.computePseudoInverse(A);
        final Matrix I_hat = P_inv.times(A);

        final File inverseDir = new File("inverse");
        inverseDir.mkdir();

        final int[][] imagePinv = MatrixUtils.MatrixDouble2Int(P_inv.getArray(),
                new MatrixUtils.LinearMap(MatrixUtils.min(P_inv.getArray()), MatrixUtils.max(P_inv.getArray())));
        PGMIO.write(imagePinv, new File(inverseDir, "Pinv.pgm"));

        final int[][] imageIhat = MatrixUtils.MatrixDouble2Int(I_hat.getArray(),
                new MatrixUtils.LinearMap(MatrixUtils.min(I_hat.getArray()), MatrixUtils.max(I_hat.getArray())));
        PGMIO.write(imageIhat, new File(inverseDir, "Ihat.pgm"));

        final Matrix I = Matrix.identity(I_hat.getRowDimension(), I_hat.getColumnDimension());
        final Matrix E = I.minus(I_hat);

        /*
        final Variables var = new Variables();
        var.put("range", (MatrixUtils.max(E.getArray()) - MatrixUtils.min(E.getArray())));
        var.put("mean", mean(E));
        var.put("stdev", stdev(E));
        var.write(new File(inverseDir, "error.tex"));
*/
        System.out.print("Range: " + (MatrixUtils.max(E.getArray()) - MatrixUtils.min(E.getArray())));
        System.out.print(", Mean: " + mean(E));
        System.out.print(", Stdev: " + stdev(E) + "\n");
        
        final Matrix B = A.times(P_inv);
        final int[][] imageB = MatrixUtils.MatrixDouble2Int(B.getArray(),
                new MatrixUtils.LinearMap(MatrixUtils.min(B.getArray()), MatrixUtils.max(B.getArray())));
        PGMIO.write(imageB, new File(inverseDir, "B.pgm"));


	}

}
