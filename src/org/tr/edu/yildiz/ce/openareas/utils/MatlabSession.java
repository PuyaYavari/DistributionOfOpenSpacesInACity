package org.tr.edu.yildiz.ce.openareas.utils;

import java.io.File;
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
		System.out.println("MAtlab OK");
	}
	
	//region DEFINITIONS
	
	public boolean define(String name, int value) {
		try {
			_proxy.setVariable(name, value);
			return true;
		} catch (MatlabInvocationException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean define(String name, float value) {
		try {
			_proxy.setVariable(name, value);
			return true;
		} catch (MatlabInvocationException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean define(String name, double value) {
		try {
			_proxy.setVariable(name, value);
			return true;
		} catch (MatlabInvocationException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean define(String name, List<Double> value) {
		try {
			_proxy.setVariable(name, value);
			return true;
		} catch (MatlabInvocationException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean define(String name, Map<Double, List<Double>> value) {
		try {
			_proxy.setVariable(name, value);
			return true;
		} catch (MatlabInvocationException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean define(String name, int[] value) {
		try {
			_proxy.setVariable(name, value);
			return true;
		} catch (MatlabInvocationException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	//end region DEFINITIONS
	//region HISTOGRAM
	
	public boolean histogram(String fieldName) {
		try {
			_proxy.eval(String.format("hist(%s)", fieldName));
			return true;
		} catch (MatlabInvocationException e) {
			e.printStackTrace();
			return false;
		}
		
	}
	
	public <T> boolean histogram(List<T> value) {
		try {
			_proxy.eval(String.format("hist([%s])", listToString(value)));
			return true;
		} catch (MatlabInvocationException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	//end region HISTOGRAM

	
	private <T> String listToString(List<T> value) {
		String result = "";
		for(int i = 0; i < value.size(); i++) {
			if (i == 0) {
				result = String.format("%s", value.get(i).toString());
			} else {
				result = String.format("%s %s", result, value.get(i).toString());
			}
		}
		return result;
	}
}
