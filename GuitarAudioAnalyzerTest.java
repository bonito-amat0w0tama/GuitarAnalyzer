import static org.junit.Assert.*;
import jp.crestmuse.cmx.filewrappers.WAVWrapper;

import org.junit.Test;


public class GuitarAudioAnalyzerTest {
	private GuitarAudioAnalyzer gaa  = new GuitarAudioAnalyzer();

	@Test
	public void setUp() {
		//gaa = new GuitarAudioAnalyzer();
	}
	
	@Test
	public void テストwavの読み込みに成功したらNull以外を返す() {
		 WAVWrapper wav = gaa.readWav("data/zenon.wav");
		//System.out.println(wav);
		assertNotNull(wav);
	}
	@Test
	public void テストWavの読み込みに失敗したらNullを返す() {
		WAVWrapper wav = gaa.readWav("hoge");
		assertNull(wav);
	}
	
	@Test
	public void テストTcp通信通信() {
		/*
        try {
            ExternalCodeAdapter eca = 
                new ExternalCodeAdapter("localhost", 1111);
            eca.pushDoubleMatrix(specgram);
            eca.pushCode(code);
            DoubleMatrix H = (DoubleMatrix)eca.pop();
            System.out.println("H: " + MathUtils.toString1(H));
            DoubleMatrix W = (DoubleMatrix)eca.pop();
            System.out.println("W: " + MathUtils.toString1(W));
            //eca.pushEnd();
            eca.close();
        } catch(ConnectException e) {
        	System.out.println("Pythonサーバーとのコネクションエラー");
            e.printStackTrace();
        } catch(IOException e) {
        	e.printStackTrace();
        }
        */
		
	}

}
