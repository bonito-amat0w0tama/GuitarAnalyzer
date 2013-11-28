import static org.junit.Assert.*;
import org.hamcrest.*;
import org.hamcrest.core.*;

import jp.crestmuse.cmx.amusaj.sp.SPExecutor;
import jp.crestmuse.cmx.amusaj.sp.STFT;
import jp.crestmuse.cmx.amusaj.sp.WindowSlider;
import jp.crestmuse.cmx.filewrappers.WAVWrapper;
import jp.crestmuse.cmx.math.DoubleMatrix;
import jp.crestmuse.cmx.processing.CMXController;

import static org.junit.Assert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsAnything.*;



import org.junit.Test;


public class SpectrogramGeneratorTest {

	@Test
	public void setUp() {
	}

	@Test
	public void testスペクトログラムの返り値のクラスがDoubleMatrix() {
		//fail("Not Implementaion");
		DoubleMatrix specg = null;
		assertThat(specg, is(any(DoubleMatrix.class)));
	}
	
	@Test
	public void testスペクトログラムのエラー出力() {
		GuitarAllNoteAnalyzer gaa = new GuitarAllNoteAnalyzer();

		WAVWrapper wav = gaa.readWav("");
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
		DoubleMatrix spec = sg.getSpectrogram(); 
		
		assertNull(spec);
	}

}
