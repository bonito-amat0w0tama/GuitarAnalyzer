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

public class GuitarAllNoteAnalyzer {
	WAVWrapper wav;
	private SPExecutor ex = new SPExecutor();
	// wavを読み込むためにメンバ変数である必要あり
	WindowSlider winSldr;
	SpectrogramGenerator sg;
	STFT stft;
	CMXController cmx;
	DoubleMatrix allNoteWav;

	GuitarAllNoteAnalyzer() {
		cmx = CMXController.getInstance();
	}
	private void readWav(String wavPath) {
		try {
            this.wav =  WAVWrapper.readfile(wavPath);
		} catch(IOException e) {
			System.out.println("Wavファイルの読み込みミス");
			e.printStackTrace();
			wav = null;
		}
	}
	private void setSPModuleToSPExecutor(ProducerConsumerCompatible mdl) {
		this.ex.addSPModule(mdl);
	}

	private void setWindowSlider() {
		this.winSldr = new WindowSlider(false);
		this.setSPModuleToSPExecutor(winSldr);
	}
	private void setInputDataToWindowSlider() {
		this.winSldr.setInputData(this.wav);
	}
	private void setSTFT() {
		// STFTのための設定の読み込み
       	this.cmx.readConfig("./data/config.xml");	
       	this.stft = new STFT(false);	
		this.setSPModuleToSPExecutor(this.stft);
	}
	private void setSpectrogramGenerator() {
		this.sg = new SpectrogramGenerator();
		this.setSPModuleToSPExecutor(this.sg);
	}
	private void connectModule() {
		this.ex.connect(winSldr, 0, stft, 0);
		this.ex.connect(stft, 0, sg, 0);
	}

	public void setupAllModules() {
		this.setWindowSlider();
		this.setSTFT();
		this.setSpectrogramGenerator();

		this.connectModule();
	}
	
	public DoubleMatrix analyzeAllNote(String wavPath) {
		this.readWav(wavPath);
		this.setupAllModules();
		this.setInputDataToWindowSlider();
		this.ex.start();
		
	    // 無理矢理な方法
		while (!ex.finished()) {
			System.out.println("hello");
		    try {
                Thread.currentThread().sleep(100);
		    } catch (InterruptedException e) {

		    }
		}
		this.allNoteWav = sg.getSpectrogram();
		
		return allNoteWav;
	}
}
