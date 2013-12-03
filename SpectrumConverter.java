import java.util.*;

import jp.crestmuse.cmx.amusaj.sp.*;
import jp.crestmuse.cmx.math.*;

import static java.lang.Math.*;

public class SpectrumConverter extends SPModule {
	DoubleMatrix fftResult;
	int len;
	int count = 0;
	
	public SpectrumConverter() {
	}

	private DoubleArray convertComplexArrayToDoubleArray(ComplexArray compArray) {
		System.out.println("Call");
        DoubleArray dblArray = MathUtils.createDoubleArray(compArray.length());
        for (int i = 0; i < compArray.length(); i++) {
        	dblArray.set(i, sqrt(compArray.getReal(i) * compArray.getReal(i)
        			+ compArray.getImag(i) * compArray.getImag(i)));
        }
        return dblArray;
	}
	public void execute(Object[] src, TimeSeriesCompatible[] dest) throws InterruptedException {
		ComplexArray cmpArray = (ComplexArray)src[0];
		DoubleArray dblArray = this.convertComplexArrayToDoubleArray(cmpArray);

		dest[0].add(dblArray);
		
	}
	public Class[] getInputClasses() {
		return new Class[] { ComplexArray.class };
	}
	public Class[] getOutputClasses() {
		return new Class[] { };
	}
	
}