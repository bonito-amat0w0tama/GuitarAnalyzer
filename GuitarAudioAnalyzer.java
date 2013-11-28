import java.io.*;
import java.nio.*;
import java.net.*;
import jp.crestmuse.cmx.processing.*;
import jp.crestmuse.cmx.filewrappers.*;
import jp.crestmuse.cmx.sound.*;
import jp.crestmuse.cmx.amusaj.sp.*;
import javax.sound.sampled.*;
import jp.crestmuse.cmx.processing.*;
import jp.crestmuse.cmx.math.*;

public class GuitarAudioAnalyzer {
	WAVWrapper wav;


	GuitarAudioAnalyzer() {

	}

	public WAVWrapper readWav(String wavPath) {
		try {
            WAVWrapper wavWrpr = WAVWrapper.readfile(wavPath);
            return wavWrpr;
		} catch(IOException e) {
			System.out.println("Wavファイルの読み込みミス");
			e.printStackTrace();
			return null;
		}
	}

	public static void main(String[] args) {
		GuitarAudioAnalyzer gaa = new GuitarAudioAnalyzer();

		WAVWrapper wav = gaa.readWav("./data/zenon.wav");
		WindowSlider winSldr = new WindowSlider(false);
		STFT stft = new STFT(false);	
		SpectrogramGenerator sg = new SpectrogramGenerator();
        InputChecker foo = new InputChecker();
		CMXController cmx = CMXController.getInstance();

		// STFTのための設定の読み込み
       	cmx.readConfig("./data/config.xml");
		winSldr.setInputData(wav);

		SPExecutor ex = new SPExecutor();
		ex.addSPModule(winSldr);
		ex.addSPModule(stft);
		ex.addSPModule(sg);
        //cmx.addSPModule(foo);
		ex.connect(winSldr, 0, stft, 0);
		ex.connect(stft, 0, sg, 0);
		ex.start();
        
        // 無理矢理な方法
		while (!ex.finished()) {
		    try {
                Thread.currentThread().sleep(100);
		    } catch (InterruptedException e) {
            }
		}

		DoubleMatrix specgram = sg.getSpectrogram();
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
            e.printStackTrace();
        } catch(IOException e) {
        	e.printStackTrace();
        }

	}


}
