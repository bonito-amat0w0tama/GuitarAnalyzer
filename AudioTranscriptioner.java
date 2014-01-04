import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

import jp.crestmuse.cmx.amusaj.sp.SPModule;
import jp.crestmuse.cmx.amusaj.sp.TimeSeriesCompatible;
import jp.crestmuse.cmx.math.ComplexArray;
import static java.lang.Math.*;

public class AudioTranscriptioner extends SPModule{
	private RealMatrix m_wp;
	private char[] noteList = {'c', 'd', 'e', 'f', 'g', 'a', 'b', 'c'};
	public void execute(Object[] src, TimeSeriesCompatible[] dest) 
			throws InterruptedException {
	    ComplexArray spec = (ComplexArray)src[0];
	    double[][] powsec = new double[spec.length()][1];
	    for (int i = 0; i < spec.length(); i++) {
            powsec[i][0] = (double)sqrt(spec.getReal(i) * spec.getReal(i) +
                        spec.getImag(i) * spec.getImag(i));
	    }
	    RealMatrix in = MatrixUtils.createRealMatrix(powsec);
//	    System.out.println("powsec:" + powsec.length);
//	    System.out.println("in:" + in.getRowDimension() + ":" + in.getColumnDimension());
//	    System.out.println("wp:" + m_wp.getRowDimension() + ":" + m_wp.getColumnDimension());

	    RealMatrix mat = m_wp.multiply(in);
//	    System.out.println("note:" + note.getRowDimension() + ":" + note.getColumnDimension());

	    
	    String note = this.transcription(mat);
	    dest[0].add(note);
//	    System.out.println((ComplexArray)src[0]);
	}

	private String transcription(RealMatrix mat) {
        String[] note = {"c", "d", "e", "f", "g", "a", "b", "c"};
        String dest;
        RealVector vec = mat.getColumnVector(0);
        System.out.println(vec.getDimension());
        int index = vec.getMaxIndex(); 
        double val = vec.getMaxValue();
        try {
        	dest = note[index];
            System.out.println(dest + ":" + val);
        } catch (ArrayIndexOutOfBoundsException e) {
            dest = "err";
            System.out.println(dest + ":" + val);
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
		m_wp = Wp;
	}
}
