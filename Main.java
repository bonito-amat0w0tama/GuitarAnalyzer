import java.io.*;
import java.net.*;

import jp.crestmuse.cmx.amusaj.sp.*;
import jp.crestmuse.cmx.filewrappers.*;
import jp.crestmuse.cmx.math.*;
import jp.crestmuse.cmx.processing.*;


public class Main {

	public static void main(String[] args) {
		GuitarAllNoteAnalyzer gaa = new GuitarAllNoteAnalyzer();

		DoubleMatrix allNote = gaa.analyzeAllNote("./data/zenon.wav");

        String code = "W,H = self.nmfMatrix(self.pop())\nself.push(W)\nself.push(H)";

        // Tcp/ipで飛ばう
        try {
            ExternalCodeAdapter eca = 
                new ExternalCodeAdapter("localhost", 1111);
            eca.pushDoubleMatrix(allNote);
            eca.pushCode(code);
            DoubleMatrix H = (DoubleMatrix)eca.pop();
            System.out.println("H: " + MathUtils.toString1(H));
            DoubleMatrix W = (DoubleMatrix)eca.pop();
            System.out.println("W: " + MathUtils.toString1(W));
            //eca.pushEnd();
            eca.close();
        } catch(ConnectException e) {
        	System.out.println("Pythonサーバーとのコネクションエラー");
            //e.printStackTrace();
        } catch(IOException e) {
        	e.printStackTrace();
        }

	}


}
