package assignment1;

import java.io.File;
import java.io.IOException;

import Jama.Matrix;

public class Problem3 {
	
	private static void printMatrix(Matrix m) {
		System.out.println("Size: " + m.getRowDimension() + "x" + m.getColumnDimension());
		double[][] d = m.getArray();
		for (int row = 0; row < d.length; row++) {
			for (int col = 0; col < d[row].length; col++) {
				System.out.printf("%6.4f\t", m.get(row, col));
			}
			System.out.println();
		}
		System.out.println();
	}

	public static void main(String[] args) throws NumberFormatException, IOException {
		final File dataFile = new File(args[0]);
		final Matrix A = DATAIO.readMatrix(dataFile, Integer.parseInt(args[1]), Integer.parseInt(args[2]));
		final PrincipalComponentsAnalysis PCA = new PrincipalComponentsAnalysis(A);

		final File pcaDir = new File("PCA");
		pcaDir.mkdir();

		for (int i = 0; i < PCA.getPrincipalComponentCount();i++){
			System.out.println("SV " + i + ": " + PCA.getSingularValue(i));
		}
		
		for (int i = 0; i < 5;i++){
			Matrix M = PCA.getPrincipalComponent(i);
			printMatrix(M.getMatrix(0, 4, 0, 0));
		}
		
		final Matrix L = PCA.getProjectedData(2);
		DATAIO.writeMatrix(L, new File(pcaDir, "locations.txt"));
		printMatrix(L);
		
		for (int i = 0; i < 100;i++){
			if(L.get(i, 0)>0)
				System.out.println(i);
		}
	}
}
