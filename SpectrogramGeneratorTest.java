import static org.junit.Assert.*;
import org.hamcrest.*;
import jp.crestmuse.cmx.math.DoubleMatrix;
import static org.junit.Assert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsAnything.*;



import org.junit.Test;


public class SpectrogramGeneratorTest {

	@Test
	public void setUp() {
	}

	@Test
	public void testスペクトログラム出力() {
		//fail("Not Implementaion");
		
		
		DoubleMatrix specg = null;
		
		assertThat(specg, is(any(DoubleMatrix.class)));

		
	}

}
