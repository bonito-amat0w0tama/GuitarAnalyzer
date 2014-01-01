import jp.crestmuse.cmx.amusaj.sp.SPModule;
import jp.crestmuse.cmx.amusaj.sp.TimeSeriesCompatible;
import jp.crestmuse.cmx.math.ComplexArray;

public class AudioTranscriptioner extends SPModule{
	public void execute(Object[] src, TimeSeriesCompatible[] dest) {
	}

	public Class[] getInputClasses() {
		return new Class[] { ComplexArray.class };
	}
	
	public Class[] getOutputClasses() {
        return new Class[] { String.class};
	}

}
