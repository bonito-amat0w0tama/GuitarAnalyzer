import java.io.*;
import java.net.*;

import jp.crestmuse.cmx.amusaj.sp.*;
import jp.crestmuse.cmx.filewrappers.*;
import jp.crestmuse.cmx.math.*;
import jp.crestmuse.cmx.processing.*;


public class Main {

    public static void main(String[] args) {
        GuitarAllNoteAnalyzer gaa = new GuitarAllNoteAnalyzer();

        // DoubleMatrix allNote = gaa.analyzeGuitarAudio("./data/guitar.wav");
        DoubleMatrix allNote = gaa.analyzeGuitarAudio("./data/guitar.wav");

        String code = "V = self.pop()\n" +
        		"W,H = self.nmfMatrix(V, 'nmf', 10, 1000)\n" +
        		"self.push(W)\n" +
        		"self.push(H)\n" +
        		"Wp = self.getPseudoInverseMatrix(W)\n" +
        		"self.push(Wp)\n" +
                "self.pushMatrix(self.pop())\n" +
        		"self.pushMatrix(self.pop())\n" +
        		"self.pushMatrix(self.pop())\n" +
                "self.writeDataToJson(name='zenon', data={'V': V.tolist(), 'W': W.tolist(), 'H': H.tolist(), 'Wp': Wp.tolist()}, dateFlag=True)";

        // Tcp/ipで飛ばう
        try {
            ExternalCodeAdapter eca = 
                new ExternalCodeAdapter("localhost", 2222);
            eca.pushDoubleMatrix(allNote);
            allNote = null;
            eca.pushCode(code);

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
