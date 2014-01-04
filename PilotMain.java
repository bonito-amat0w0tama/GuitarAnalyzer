
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
            eca.pushCode(scg.pilotTrans);

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
        

        CMXController cmx = CMXController.getInstance();
//        cmx.showAudioMixerChooser(null);
        cmx.readConfig("data/config.xml");
//        WindowSlider winsl = cmx.createMic();
        WindowSlider winsl = new WindowSlider(false); 
        
        WAVWrapper wav;
        try {
        	String wavPath = "./data/guitar.wav";
            wav =  WAVWrapper.readfile(wavPath);
        } catch(Exception e) {
        	wav = null;
        	e.printStackTrace();
        }
        winsl.setInputData(wav);
        STFT stft = new STFT(false);
        AudioTranscriptioner at = new AudioTranscriptioner();
        at.setWp(RWp);
		
		cmx.addSPModule(winsl);
		cmx.addSPModule(stft);
		cmx.addSPModule(at);

		cmx.connect(winsl,  0, stft, 0);
		cmx.connect(stft,  0, at, 0);
		
		cmx.startSP();
	}
}
