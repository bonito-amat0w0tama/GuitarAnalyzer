import static org.junit.Assert.*;

import java.io.IOException;
import java.net.ConnectException;

import jp.crestmuse.cmx.math.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.core.Is.is;


public class MainTest {
	private GuitarAudioAnalyzer gaa = new GuitarAllNoteAnalyzer();
	private GuitarAudioAnalyzer gsa = new GuitarSpectrumAnalyzer();
	private DoubleMatrix H, W;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {


	}

	@Before
	public void setUp() throws Exception {
		DoubleMatrix allNote = gaa.analyzeGuitarAudio("./data/zenon.wav");
        String code = "W,H = self.nmfMatrix(self.pop())\nself.push(W)\nself.push(H)";

        // Tcp/ipで飛ばう
        try {
            ExternalCodeAdapter eca = 
                new ExternalCodeAdapter("localhost", 1111);
            eca.pushDoubleMatrix(allNote);
            eca.pushCode(code);
            H = (DoubleMatrix)eca.pop();
            System.out.println("H: " + MathUtils.toString1(H));
            W = (DoubleMatrix)eca.pop();
            System.out.println("W: " + MathUtils.toString1(W));
            //eca.pushEnd();
            eca.close();
        } catch(ConnectException e) {
        	System.out.println("Pythonサーバーとのコネクションエラー");
            //e.printStackTrace();
        } catch(IOException e) {
        	e.printStackTrace();
        }
	}


}
