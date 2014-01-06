import jp.crestmuse.cmx.amusaj.sp.*;
import jp.crestmuse.cmx.filewrappers.WAVWrapper;
import jp.crestmuse.cmx.processing.CMXController;


public class PilotInput {
	public static void main(String[] args) {
        CMXController cmx = CMXController.getInstance();
        cmx.readConfig("data/config.xml");
//        WindowSlider winsl = new WindowSlider(false); 
        cmx.showAudioMixerChooser(null);
        WindowSlider mic = cmx.createMic(44100);
        
//        try {
//        	String wavPath = "./data/doremi_0106.wav";
//            WAVWrapper wav =  WAVWrapper.readfile(wavPath);
//            winsl.setInputData(wav);
////            cmx.wavread(wav);
//        } catch(Exception e) {
//        	e.printStackTrace();
//        	System.exit(-1);
//        }

        STFT stft = new STFT(false);
        AudioTranscriptioner at = new AudioTranscriptioner();
//		
//		cmx.addSPModule(winsl);
		cmx.addSPModule(mic);
		cmx.addSPModule(stft);
		cmx.addSPModule(at);
//
//		cmx.connect(winsl,  0, stft, 0);
		cmx.connect(mic,  0, stft, 0);
		cmx.connect(stft,  0, at, 0);
//
		cmx.startSP();
	}
}
