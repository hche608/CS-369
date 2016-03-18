package assignment1;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import Jama.Matrix;
import Jama.SingularValueDecomposition;

public class Problem1 {


	public static void main(String[] args) throws IOException {


		final int[][] image = PGMIO.read(new File(args[0]));

		final Matrix A = new Matrix(MatrixUtils.MatrixInt2Double(image));
		final SingularValueDecomposition SVD = A.svd();

		final File svdDir = new File("SVD");
		svdDir.mkdir();

		final Matrix U = SVD.getU();

		
		//PGMIO.write(imageU, new File(svdDir, "U.pgm"));


	}

}
