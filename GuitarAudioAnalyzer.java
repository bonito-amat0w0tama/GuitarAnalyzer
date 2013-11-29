import jp.crestmuse.cmx.amusaj.sp.*;
import jp.crestmuse.cmx.processing.*;

public class GuitarAudioAnalyzer {
	private SPExecutor ex;
	private WindowSlider winsl;
	private STFT stft;

	GuitarAudioAnalyzer() {
		ex = new SPExecutor();
	}

	private void setSPModuleToSPExecutor(ProducerConsumerCompatible mdl) {
		this.ex.addSPModule(mdl);
	}

	void setWindowSlider() {
		winsl = new WindowSlider(false);
		this.setSPModuleToSPExecutor(winsl);
	}
	
	void setSTFT() {
		stft = new STFT(false);
		this.setSPModuleToSPExecutor(stft);
	}


	private void setAllModules() {
		this.setWindowSlider();
		this.setSTFT();
		this.connectAllModules();
	}
	
	private void connectAllModules() {
		this.ex.connect(winsl, 0, stft, 0);
	}
	
	public static void main(String args[]) {
		GuitarAudioAnalyzer gaa = new GuitarAudioAnalyzer();
		gaa.setAllModules();
		gaa.connectAllModules();
	}
}
