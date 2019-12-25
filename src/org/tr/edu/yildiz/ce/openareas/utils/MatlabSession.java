package org.tr.edu.yildiz.ce.openareas.utils;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.n52.matlab.control.MatlabConnectionException;
import org.n52.matlab.control.MatlabInvocationException;
import org.n52.matlab.control.MatlabProxy;
import org.n52.matlab.control.MatlabProxyFactory;
import org.n52.matlab.control.MatlabProxyFactoryOptions;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

public class MatlabSession {
	private MatlabProxy _proxy;
	
	public MatlabSession(String outputDir, Boolean isHidden) throws MatlabConnectionException {
		MatlabProxyFactoryOptions.Builder builder = new MatlabProxyFactoryOptions.Builder();
		builder.setHidden(isHidden);
		File fileDir = new File(outputDir);
		builder.setMatlabStartingDirectory(fileDir);
		MatlabProxyFactoryOptions options = builder.build();
		MatlabProxyFactory factory = new MatlabProxyFactory(options);
		this._proxy = factory.getProxy();
		System.out.println("MAtlab session created successfuly.");
	}
	
	public void close() throws MatlabInvocationException {
		_proxy.exit();
	}

	//region HISTOGRAM
	
	public <T> boolean saveHistogram(Double key, List<T> value, String directoryName, String fileName, String extention) {
		try {
			_proxy.setVariable("x", listToArray((List<Double>) value));
			if (makeDir(String.format("Histogram/%s", directoryName))) {
				_proxy.eval(String.format("save('Histogram/%s/%smRadius.mat','x')", directoryName, key.toString()));
				_proxy.eval("[PDF, CDF, Interval, Delta] = Histogram(x, 100);");
				_proxy.eval("f = figure('visible','off');");
				_proxy.eval(String.format("saveas(plot(Interval,PDF),'Histogram/%s/%s')", directoryName, String.format("%s_PDF%s", fileName, extention)));
				_proxy.eval(String.format("saveas(plot(Interval,CDF),'Histogram/%s/%s')", directoryName, String.format("%s_CDF%s", fileName, extention)));
				_proxy.eval(String.format("saveas(semilogy(Interval,PDF),'Histogram/%s/%s')", directoryName, String.format("%s_PDF_semilogy%s", fileName, extention)));
				_proxy.eval(String.format("saveas(semilogy(Interval,CDF),'Histogram/%s/%s')", directoryName, String.format("%s_CDF_semilogy%s", fileName, extention)));
				_proxy.eval(String.format("saveas(semilogx(Interval,PDF),'Histogram/%s/%s')", directoryName, String.format("%s_PDF_semilogx%s", fileName, extention)));
				_proxy.eval(String.format("saveas(semilogx(Interval,CDF),'Histogram/%s/%s')", directoryName, String.format("%s_CDF_semilogx%s", fileName, extention)));
				_proxy.eval(String.format("saveas(loglog(Interval,PDF),'Histogram/%s/%s')", directoryName, String.format("%s_PDF_loglog%s", fileName, extention)));
				_proxy.eval(String.format("saveas(loglog(Interval,CDF),'Histogram/%s/%s')", directoryName, String.format("%s_CDF_loglog%s", fileName, extention)));
				_proxy.eval(String.format("saveas(plot(Interval,PDF),'Histogram/%s/%s')", directoryName, String.format("%s_PDF.fig", fileName, extention)));
				_proxy.eval(String.format("saveas(plot(Interval,CDF),'Histogram/%s/%s')", directoryName, String.format("%s_CDF.fig", fileName, extention)));
				
				return true;
			} else {
				return false;
			}
		} catch (MatlabInvocationException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public void saveAllHistograms(Map<Double,List<Double>> values, String directoryName, String extention) {
		values.forEach((k,v) -> {
			saveHistogram(k,v, directoryName, String.format("%smRadius", k.toString()), extention);
		});
	}
	
	//end region HISTOGRAM
	
	public static double[] listToArray(List<Double> value) {
		return Arrays.stream(value.toArray(new Double[value.size()])).mapToDouble(Double::doubleValue).toArray();
	}
	
	private boolean makeDir(String directoryName) {
		try {
			_proxy.eval(String.format("mkdir('%s')", directoryName));
			return true;
		} catch (MatlabInvocationException e) {
			e.printStackTrace();
			return false;
		}
	}
}
