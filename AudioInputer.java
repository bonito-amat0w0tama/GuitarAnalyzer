import jp.crestmuse.cmx.amusaj.sp.SPExecutor;
import jp.crestmuse.cmx.amusaj.sp.SPModule;
import jp.crestmuse.cmx.amusaj.sp.STFT;
import jp.crestmuse.cmx.amusaj.sp.TimeSeriesCompatible;
import jp.crestmuse.cmx.amusaj.sp.WindowSlider;
import jp.crestmuse.cmx.math.ComplexArray;
import jp.crestmuse.cmx.math.MathUtils;
import jp.crestmuse.cmx.processing.CMXController;


public class AudioInputer {
    private CMXController _cmx = CMXController.getInstance();
    private WindowSlider _winsl = null;
    private STFT _stft = new STFT(false);
    private SpectrogramGenerator _sg = new SpectrogramGenerator();
    private Junk _junk = new Junk(); 
	private SPExecutor _ex = new SPExecutor();

	public AudioInputer() {
		_cmx.showAudioMixerChooser(null);
	}
	
	private void inputAudio() {
        _cmx.readConfig("data/config.xml");
		_winsl = _cmx.createMic(44100);
		
		_cmx.addSPModule(_winsl);
		_cmx.addSPModule(_stft);
//		_cmx.addSPModule(_sg);
		_cmx.addSPModule(_junk);

		_cmx.connect(_winsl,  0, _stft, 0);
		_cmx.connect(_stft,  0, _junk, 0);
//		_cmx.connect(_stft,  0, _junk, 0);
		
		_cmx.startSP();
	}
	
	
	private class Junk extends SPModule {
		public void execute(Object[] src, TimeSeriesCompatible[] dest)
				throws InterruptedException {
			System.out.println(MathUtils.toString((ComplexArray)src[0]));
		}
        public Class[] getInputClasses() {
            return new Class[] { ComplexArray.class };
        }
        public Class[] getOutputClasses() {
            return new Class[] { };
        }
	}
	public static void main(String args[]) {
		AudioInputer ai = new AudioInputer();
		ai.inputAudio();
	}
}
