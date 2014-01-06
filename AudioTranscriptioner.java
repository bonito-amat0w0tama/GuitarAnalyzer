import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

import jp.crestmuse.cmx.amusaj.sp.SPModule;
import jp.crestmuse.cmx.amusaj.sp.TimeSeriesCompatible;
import jp.crestmuse.cmx.math.ComplexArray;
import static java.lang.Math.*;

public class AudioTranscriptioner extends SPModule{
	private RealMatrix wp;
	public void execute(Object[] src, TimeSeriesCompatible[] dest) 
			throws InterruptedException {

	    ComplexArray spec = (ComplexArray)src[0];
	    // パワースペクトルグラムに変換
	    // RealMatrix型に変換するために、double[][]型に
	    double[][] powsec = new double[spec.length()][1];
	    for (int i = 0; i < spec.length(); i++) {
            powsec[i][0] = (double)sqrt(spec.getReal(i) * spec.getReal(i) +
                        spec.getImag(i) * spec.getImag(i));
	    }
	    RealMatrix in = MatrixUtils.createRealMatrix(powsec);
	    RealVector actVec = this.wp.multiply(in).getColumnVector(0);
	    String note = this.transcription(actVec);
	    dest[0].add(note);
	}

	private String transcription(RealVector vec) {
        String[] noteList = {"c", "d", "e", "f", "g", "a", "b", "c"};
        String dest = "None";
        int index = vec.getMaxIndex(); 
        double val = vec.getMaxValue();
        if (val > 0.2) {
            if (index < noteList.length) {
                dest = noteList[index];
                System.out.println(dest + ":" + val);
            } else {
                dest = String.valueOf(index);
                System.out.println(dest + ":" + val);
            }
        }
        return dest;
	}
	public Class[] getInputClasses() {
		return new Class[] { ComplexArray.class };
	}
	
	public Class[] getOutputClasses() {
        return new Class[] { String.class};
	}
	
	public void setWp(RealMatrix Wp) {
		this.wp = Wp;
	}
}
