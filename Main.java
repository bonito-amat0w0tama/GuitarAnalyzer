import java.io.*;
import java.net.*;


import jp.crestmuse.cmx.amusaj.sp.*;
import jp.crestmuse.cmx.filewrappers.*;
import jp.crestmuse.cmx.math.*;
import jp.crestmuse.cmx.processing.*;

import org.apache.commons.math3.linear.*;


public class Main {
    public static void main(String[] args) {
        GuitarAllNoteAnalyzer gaa = new GuitarAllNoteAnalyzer();
        SendingCodeGenerator scg = new SendingCodeGenerator();

        // DoubleMatrix allNote = gaa.analyzeGuitarAudio("./data/guitar.wav");
        DoubleMatrix allNote = gaa.analyzeGuitarAudio("./data/guitar.wav");

        DoubleMatrix V = null, SH = null, SW =null, Wp = null;
        // Tcp/ipで飛ばう
        try {
            ExternalCodeAdapter eca = 
                new ExternalCodeAdapter("localhost", 2222);
            eca.pushDoubleMatrix(allNote);
            // メモリの解放
            allNote = null;
            eca.pushCode(scg.pilot);

            V = (DoubleMatrix)eca.pop();
//            System.out.println("H: " + MathUtils.toString1(SH));
            SW = (DoubleMatrix)eca.pop();
            SH = (DoubleMatrix)eca.pop();
//            System.out.println("W: " + MathUtils.toString1(SW));
            Wp = (DoubleMatrix)eca.pop();
//            System.out.println("Wp: " + MathUtils.toString1(Wp));

            eca.pushEnd();
            eca.close();
        } catch(ConnectException e) {
            System.out.println("Pythonサーバーとのコネクションエラー");
            e.printStackTrace();
        } catch(IOException e) {
        	e.printStackTrace();
        }
        
        
        MatrixManager mm = new MatrixManager();
        RealMatrix RV = mm.toRealMatrix(V);
        RealMatrix RH = mm.toRealMatrix(SH);
        RealMatrix RW = mm.toRealMatrix(SW);
        RealMatrix RWp = mm.toRealMatrix(Wp);
        
        System.out.println(RV.getRowDimension() + " " + RV.getColumnDimension());
        System.out.println(RWp.getRowDimension() + " " + RWp.getColumnDimension());
        RealMatrix mat = RWp.multiply(RV);
        
        char[] note = {'c', 'd', 'e', 'f', 'g', 'a', 'b', 'c'};
        for (int i = 0; i < mat.getColumnDimension(); i++) {
            RealVector vec = mat.getColumnVector(i);
            int a = vec.getMaxIndex();
            double b = vec.getMaxValue();
            try {
                System.out.println(i + ":" + note[a] + ":" + b);
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println(i + ":" + a + ":" + b);
            }
        }
	}
}
