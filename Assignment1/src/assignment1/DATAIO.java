package assignment1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import Jama.Matrix;

public class DATAIO {
	/**
	 * Reads a matrix from a whitespace-delimited plain text file.
	 * 
	 * @param file
	 *            the file to read from
	 * @param rows
	 *            the number of rows
	 * @param cols
	 *            the number of columns
	 * @return the matrix represented in the file
	 * @throws IOException
	 */
	public static Matrix readMatrix(final File file, final int rows, final int cols) throws IOException {
		final Matrix data = new Matrix(rows, cols);
		try (final Scanner scanner = new Scanner(file)) {
			for (int i = 0; i < rows; ++i) {
				for (int j = 0; j < cols; ++j)
					data.set(i, j, scanner.nextInt());
			}
		}
		return data;
	}

	/**
	 * Writes a matrix to a whitespace-delimited plain text file.
	 * 
	 * @param A
	 *            the matrix to write
	 * @param file
	 *            the file to write to
	 * @throws FileNotFoundException
	 */
	public static void writeMatrix(final Matrix A, final File file) throws FileNotFoundException {
		try (final PrintWriter pw = new PrintWriter(file)) {
			for (int i = 0; i < A.getRowDimension(); ++i) {
				for (int j = 0; j < A.getColumnDimension(); ++j) {
					pw.print(A.get(i, j));
					if (j + 1 < A.getColumnDimension())
						pw.print(" ");
				}
				pw.println();
			}
		}
	}
}
