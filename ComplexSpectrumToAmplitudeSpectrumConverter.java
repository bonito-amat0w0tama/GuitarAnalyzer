import java.util.*;

import jp.crestmuse.cmx.amusaj.sp.*;
import jp.crestmuse.cmx.math.*;


public class ComplexSpectrumToAmplitudeSpectrumConverter extends SPModule {
	DoubleMatrix fftResult;
	int len;
	List<Integer> list = new ArrayList<Integer>();
	int count = 0;
	
	public ComplexSpectrumToAmplitudeSpectrumConverter() {
	}

	public void execute(Object[] src, TimeSeriesCompatible[] dest) {
		System.out.println(count);
		count++;
		//len = ((ComplexArray) src[0]).length();
		//list.add(len);
	}
	public Class[] getInputClasses() {
		return new Class[] { ComplexArray.class };
	}
	public Class[] getOutputClasses() {
		return new Class[] { };
	}
	
	public DoubleMatrix createDoubleMatrix() {
		
		return this.fftResult; 
	}
	public void printLength() {
		System.out.println(len);
	}
}