import java.io.*;
import java.net.*;

import jp.crestmuse.cmx.amusaj.sp.*;
import jp.crestmuse.cmx.filewrappers.*;
import jp.crestmuse.cmx.math.*;
import jp.crestmuse.cmx.processing.*;


public class Main {
    public static void main(String[] args) {
        GuitarAllNoteAnalyzer gaa = new GuitarAllNoteAnalyzer();
        SendingCodeGenerator scg = new SendingCodeGenerator();

        // DoubleMatrix allNote = gaa.analyzeGuitarAudio("./data/guitar.wav");
        DoubleMatrix allNote = gaa.analyzeGuitarAudio("./data/guitar.wav");

        // Tcp/ipで飛ばう
        try {
            ExternalCodeAdapter eca = 
                new ExternalCodeAdapter("localhost", 2222);
            eca.pushDoubleMatrix(allNote);
            // メモリの解放
            allNote = null;
            eca.pushCode(scg.pilot);

            DoubleMatrix H = (DoubleMatrix)eca.pop();
            System.out.println("H: " + MathUtils.toString1(H));
            DoubleMatrix W = (DoubleMatrix)eca.pop();
            System.out.println("W: " + MathUtils.toString1(W));
            DoubleMatrix Wp = (DoubleMatrix)eca.pop();
            System.out.println("Wp: " + MathUtils.toString1(Wp));

            eca.pushEnd();
            eca.close();
        } catch(ConnectException e) {
            System.out.println("Pythonサーバーとのコネクションエラー");
            e.printStackTrace();
        } catch(IOException e) {
        	e.printStackTrace();
        }
	}
}
