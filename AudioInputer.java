import java.io.IOException;

import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math.linear.RealVector;

import jp.crestmuse.cmx.amusaj.sp.SPExecutor;
import jp.crestmuse.cmx.amusaj.sp.SPModule;
import jp.crestmuse.cmx.amusaj.sp.STFT;
import jp.crestmuse.cmx.amusaj.sp.TimeSeriesCompatible;
import jp.crestmuse.cmx.amusaj.sp.WindowSlider;
import jp.crestmuse.cmx.filewrappers.WAVWrapper;
import jp.crestmuse.cmx.math.ComplexArray;
import jp.crestmuse.cmx.math.MathUtils;
import jp.crestmuse.cmx.processing.CMXController;


public class AudioInputer implements ModuleUser {
//    private CMXController cmx = null; 
    private WindowSlider mic = null;
    private WindowSlider wavWinsl = new WindowSlider(false);
//    private STFT stft = new STFT(false);
//	private AudioTranscriptioner at = new AudioTranscriptioner();

	public AudioInputer(CMXController cmx) {
		this.cmx = cmx;
	}
	
	public void setupModules() {
        this.cmx.readConfig("data/config.xml");
		this.mic= cmx.createMic(44100);

//		this.cmx.addSPModule(winsl);
//		this.cmx.addSPModule(stft);
//		this.cmx.addSPModule(at);

//		this.cmx.connect(winsl,  0, stft, 0);
//		this.cmx.connect(stft,  0, at, 0);
	}
	public void inputMic() {
		this.cmx.showAudioMixerChooser(null);
		mic = cmx.createMic();
	}
	public void inputWav(String path) throws IOException{
		try {
			WAVWrapper wav = WAVWrapper.readfile(path);
			wavWinsl.setInputData(wav);
		} catch (IOException e) {
			throw new IOException("wavファイルが読み込めません");
		}
	}
	
	public setupModule
}