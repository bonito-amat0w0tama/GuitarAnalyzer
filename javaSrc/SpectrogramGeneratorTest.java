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
}
