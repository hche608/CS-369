package assignment1;

import java.io.File;
import java.io.IOException;
import Jama.Matrix;

public class Problem3 {



	public static void main(String[] args) throws NumberFormatException, IOException {
		final File dataFile = new File(args[0]);
		final Matrix A = DATAIO.readMatrix(dataFile, Integer.parseInt(args[1]), Integer.parseInt(args[2]));
		final PrincipalComponentsAnalysis PCA = new PrincipalComponentsAnalysis(A);

		final File pcaDir = new File("PCA");
		pcaDir.mkdir();

		final Matrix L = PCA.getProjectedData(2);
		DATAIO.writeMatrix(L, new File(pcaDir, "locations.txt"));
	}

}
