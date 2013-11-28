import java.io.*;
import java.net.*;

import jp.crestmuse.cmx.amusaj.sp.*;
import jp.crestmuse.cmx.filewrappers.*;
import jp.crestmuse.cmx.math.*;
import jp.crestmuse.cmx.processing.*;


public class Main {

	public static void main(String[] args) {
		GuitarAudioAnalyzer gaa = new GuitarAudioAnalyzer();

		WAVWrapper wav = gaa.readWav("./data/zenon.wav");
		WindowSlider winSldr = new WindowSlider(false);
		STFT stft = new STFT(false);	
		SpectrogramGenerator SG = new SpectrogramGenerator();
        InputChecker foo = new InputChecker();
		CMXController cmx = CMXController.getInstance();

       	cmx.readConfig("./data/config.xml");
		winSldr.setInputData(wav);
		SPExecutor ex = new SPExecutor();
		ex.addSPModule(winSldr);
		ex.addSPModule(stft);
		ex.addSPModule(SG);
        //cmx.addSPModule(foo);
		ex.connect(winSldr, 0, stft, 0);
		ex.connect(stft, 0, SG, 0);
		ex.start();
        
        // 無理矢理な方法
		while (!ex.finished()) {
		    try {
                Thread.currentThread().sleep(100);
		    } catch (InterruptedException e) {
            }
		}

		DoubleMatrix specgram = SG.getSpectrogram();
        String code = "W,H = self.nmfMatrix(self.pop())\nself.push(W)\nself.push(H)";


        // Tcp/ipで飛ばう
        try {
            ExternalCodeAdapter eca = 
                new ExternalCodeAdapter("localhost", 1111);
            eca.pushDoubleMatrix(specgram);
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
