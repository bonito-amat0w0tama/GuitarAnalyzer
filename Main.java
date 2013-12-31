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

        String nmfCode = "V = self.pop()\n" +
        		"W,H = self.nmfMatrix(V, 'nmf', 10, 1000)\n" +
        		"self.push(W)\n" +
        		"self.push(H)\n" +
        		"Wp = self.getPseudoInverseMatrix(W)\n" +
        		"self.push(Wp)\n" +
                "self.pushMatrix(self.pop())\n" +
        		"self.pushMatrix(self.pop())\n" +
        		"self.pushMatrix(self.pop())\n" +
                "self.writeDataToJson(name='zenon', data={'V': V.tolist(), 'W': W.tolist(), 'H': H.tolist(), 'Wp': Wp.tolist()}, dateFlag=True)";

        String junkNmf = "V = server.pop()\n" +
        		"W,H = server.nmfMatrix(V, 'nmf', 10, 500)\n" +
        		"print W.shape\n" +
        		"print H.shape\n" +
//        		"server.push(W)\n" +
//        		"server.push(H)\n" +
//        		"Wp = server.getPseudoInverseMatrix(W)\n" +
//        		"server.push(Wp)\n" +
//                "server.pushMatrix(server.pop())\n" +
//        		"server.pushMatrix(server.pop())\n" +
//        		"server.pushMatrix(server.pop())\n" +
//              "server.writeDataToJson(name='zenon', data={'V': V.tolist(), 'W': W.tolist(), 'H': H.tolist(), 'Wp': Wp.tolist()}, dateFlag=True)\n" +
//				"X = np.arange(25).reshape(5, 5)\n" +
//				"path = '../../jsonData/zenon_2013-12-26-20:1.json'\n" +
//				"data = Utils.NMFUtils.readJson(path=path)\n" +
//				"data = Utils.NMFUtils.json2NpArray(data)\n" +
//				"Wd = data['W']\n" +
//        		"print Wd.shape\n" +
//				"x = np.arange(100)\n" +
//        		"print type(W)\n" +
//        		"W = np.asarray(W)\n" +
//        		"print type(W)\n" +
				"SW, SH = Utils.NMFUtils.sortBasisAndCoef(W, H)\n" +
        		"Wp = server.getPseudoInverseMatrix(SW)\n" +
        		"print Wp.shape\n" +
        		"print V.shape\n" +
        		"h = np.dot(Wp, V[:,1])\n" +
        		"plt.plot(h)\n" +
//        		"Utils.NMFUtils.createActivationGraph(h, 1)\n" +
        		"plt.plot(h)\n" +
//        		"Utils.NMFUtils.createCoefGraph(SH, 2)\n" +
//        		"Utils.NMFUtils.createBasisGraph(SW, 1)\n" +
//        		"Utils.NMFUtils.createCoefGraph(SH, 2)\n" +
//        		"Utils.NMFUtils.plotTest(x=x)\n" +
        		"plt.show()\n";
//        		"Utils.NMFUtils.showPlot()";
        
        String junkCode = 
        		"Utils.NMFUtils.";

        // Tcp/ipで飛ばう
        try {
            ExternalCodeAdapter eca = 
                new ExternalCodeAdapter("localhost", 2222);
            eca.pushDoubleMatrix(allNote);
            // メモリの解放
            allNote = null;
            eca.pushCode(junkNmf);

//            DoubleMatrix H = (DoubleMatrix)eca.pop();
//            System.out.println("H: " + MathUtils.toString1(H));
//            DoubleMatrix W = (DoubleMatrix)eca.pop();
//            System.out.println("W: " + MathUtils.toString1(W));
//            DoubleMatrix Wp = (DoubleMatrix)eca.pop();
//            System.out.println("Wp: " + MathUtils.toString1(Wp));

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
