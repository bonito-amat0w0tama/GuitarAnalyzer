import static org.junit.Assert.*;
import jp.crestmuse.cmx.filewrappers.WAVWrapper;
import jp.crestmuse.cmx.math.DoubleMatrix;

import org.junit.Test;


import org.hamcrest.*;
import org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsAnything.*;


public class GuitarAllNoteAnalyzerTest {
	private GuitarAllNoteAnalyzer gaa  = new GuitarAllNoteAnalyzer();

	@Test
	public void setUp() {
		//gaa = new GuitarAudioAnalyzer();
	}
	
	@Test
	public void test全音をNMFで解析をしDoubleMatrix型のベクトルを得る() {
		DoubleMatrix base = gaa.analyzeAllNote("./data/zenon.wav");
		assertThat(base, is(any(DoubleMatrix.class)));
	}
	@Test
	public void test全音をNMFで解析に失敗した時Nullを返す() {
		DoubleMatrix base = gaa.analyzeAllNote("");
		assertNull(base);
	}
	
}
