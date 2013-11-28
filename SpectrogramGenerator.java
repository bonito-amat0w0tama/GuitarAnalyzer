import jp.crestmuse.cmx.amusaj.sp.*;
import jp.crestmuse.cmx.math.*;
import java.util.*; 
import static java.lang.Math.*;

public class SpectrogramGenerator extends SPModule{
	List<ComplexArray> list = new ArrayList<ComplexArray>();
    int count = 0;

	public void execute(Object[] src, TimeSeriesCompatible[] dest) {
	    ///System.out.println(count);
        count++;
        list.add((ComplexArray)src[0]);
	}

    // メインで走らせる
    DoubleMatrix getSpectrogram() {
    	try {
	        DoubleMatrix matrix = MathUtils.createDoubleMatrix(list.get(0).length(), list.size());
	        for (int j = 0; j < list.size(); j++) {
	            ComplexArray array = list.get(j);
	            for (int i = 0; i < array.length(); i++) {
	                matrix.set(i, j, sqrt(array.getReal(i) * array.getReal(i) + array.getImag(i) * array.getImag(i)));
	            }
	        }
	        return matrix;
    	} catch(Exception e) {
    		return null;
    	}
    }
	public Class[] getInputClasses() {
		return new Class[] { ComplexArray.class };
	}
	
	public Class[] getOutputClasses() {
		// ここに貯めるので何も出力しない
			return new Class[] { };
	}
}
