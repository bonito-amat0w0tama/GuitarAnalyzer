
import java.io.*;
import java.net.*;

import jp.crestmuse.cmx.amusaj.sp.*;
import jp.crestmuse.cmx.filewrappers.*;
import jp.crestmuse.cmx.math.*;
import jp.crestmuse.cmx.processing.*;

import org.apache.commons.math3.linear.*;


public class PilotMain {
    public static void main(String[] args) {
        GuitarAllNoteAnalyzer gaa = new GuitarAllNoteAnalyzer();
        SendingCodeGenerator scg = new SendingCodeGenerator();

        String allPath = "./data/doremi_dou.wav";
        DoubleMatrix allNote = gaa.analyzeGuitarAudio(allPath);

        DoubleMatrix V = null, SH = null, SW =null, Wp = null;

        try {
            ExternalCodeAdapter eca = 
                new ExternalCodeAdapter("localhost", 3333);
            eca.pushDoubleMatrix(allNote);
            // メモリの解放
            allNote = null;
            eca.pushCode(scg.nmfDoremi);

            V = (DoubleMatrix)eca.pop();
            SW = (DoubleMatrix)eca.pop();
            SH = (DoubleMatrix)eca.pop();
            Wp = (DoubleMatrix)eca.pop();

            eca.pushEnd();
            eca.close();
        } catch(ConnectException e) {
            System.out.println("Pythonサーバーとのコネクションエラー");
            e.printStackTrace();
            System.exit(-1);
        } catch(IOException e) {
        	e.printStackTrace();
        	System.exit(-1);
        }
        
        RealMatrix RV = MyUtils.toRealMatrix(V);
        RealMatrix RH = MyUtils.toRealMatrix(SH);
        RealMatrix RW = MyUtils.toRealMatrix(SW);
        RealMatrix RWp = MyUtils.toRealMatrix(Wp);
        
        CMXController cmx = CMXController.getInstance();
        cmx.readConfig("data/config.xml");
//        cmx.showAudioMixerChooser(null);
        WindowSlider winsl = new WindowSlider(false); 
        WindowSlider mic = cmx.createMic();
        
        try {
        	String wavPath = "./data/waon.wav";
            WAVWrapper wav =  WAVWrapper.readfile(wavPath);
            winsl.setInputData(wav);
//            cmx.wavread(wav);
        } catch(Exception e) {
        	e.printStackTrace();
        	System.exit(-1);
        }

        STFT stft = new STFT(false);
        AudioTranscriptioner at = new AudioTranscriptioner();
        at.setWp(RWp);
//		
		cmx.addSPModule(winsl);
//		cmx.addSPModule(mic);
		cmx.addSPModule(stft);
		cmx.addSPModule(at);
//
		cmx.connect(winsl,  0, stft, 0);
//		cmx.connect(mic,  0, stft, 0);
		cmx.connect(stft,  0, at, 0);
//
		cmx.startSP();
        
//        CMXController cmx = CMXController.getInstance();
//        AudioInputer ai = new AudioInputer(cmx);
//        String path = "data/guitar.wav";
//
//        try {
//            ai.inputWav(path);
//        } catch (IOException e) {
//        	e.printStackTrace();
//        }
	}
}
