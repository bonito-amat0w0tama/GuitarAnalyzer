import jp.crestmuse.cmx.math.DoubleMatrix;
import jp.crestmuse.cmx.math.DoubleMatrixFactory;

import org.apache.commons.math3.linear.*;

public class PilotMat {
	public void printMat(int[][] mat) {
        for (int i=0; i < mat.length; i++) {
        	for(int j=0; j < mat[i].length; j++) {
        		System.out.print(mat[i][j] + "\t");
        	}
        	System.out.println();
        }
	}
	public DoubleMatrix toDoubleMatrix(RealMatrix argMat) {
		int nrows = argMat.getRowDimension();
		int ncols = argMat.getColumnDimension();
        DoubleMatrixFactory dfac = DoubleMatrixFactory.getFactory();
		DoubleMatrix destMat = dfac.createMatrix(nrows, ncols);
		for (int i = 0; i < nrows; i++) {
			for (int j = 0; j < ncols; j++) {
				destMat.set(i, j, argMat.getEntry(i, j)); 
			}
		}
		return destMat;
	}
	public RealMatrix toRealMatrix(DoubleMatrix mat) {
		int nrows = mat.nrows();
		int ncols = mat.ncols();
		RealMatrix dest = MatrixUtils.createRealMatrix(nrows, ncols); 
		for (int i = 0; i < nrows; i++) {
			for (int j = 0; j < ncols; j++) {
				dest.setEntry(i, j, mat.get(i, j));
			}
		}
		return dest;
	}
	public static void main(String[] args) {
        int[][] matA = new int[3][2];
        int[][] matB = new int[2][3];
	
        int count = 0;
        for (int i=0; i < matA.length; i++) {
        	for(int j=0; j < matA[i].length; j++) {
        		matA[i][j] = 3;
        	}
        }
        for (int i=0; i < matB.length; i++) {
        	for(int j=0; j < matB[i].length; j++) {
        		matB[i][j] = 1;
        	}
        }
        
        int[][] dest = new int [3][3];
        for (int i=0; i < dest.length; i++) {
        	for(int j=0; j < dest[i].length; j++) {
        		for (int k=0; k< matA[i].length; k++) {
        			dest[i][j] += matA[i][k] * matB[k][j];
        		}
        	}
        }
        
        
        double[][] data = {{1, 2, 3,}, {4, 5, 6}};
        RealMatrix mat = MatrixUtils.createRealMatrix(data);
        double[][] arr = mat.getData();
        double a = mat.getEntry(0,0);
        DoubleMatrixFactory dfac = DoubleMatrixFactory.getFactory();
        
        DoubleMatrix dmat = dfac.createMatrix(3, 2);
        for (int i = 0; i < dmat.nrows(); i++) {
        	for (int j = 0; j < dmat.ncols(); j++) {
        		dmat.set(i, j, 3);
        	}
        }
        
        PilotMat pm = new PilotMat();
        DoubleMatrix aa = pm.toDoubleMatrix(mat);
        RealMatrix bb = pm.toRealMatrix(aa);
        System.out.println(bb.toString());
	}
}
