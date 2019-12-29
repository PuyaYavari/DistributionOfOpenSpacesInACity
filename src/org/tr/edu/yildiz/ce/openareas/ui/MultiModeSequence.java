package org.tr.edu.yildiz.ce.openareas.ui;

public class MultiModeSequence {
	String name;
	boolean isInputEnabled;
	
	public MultiModeSequence(String name, boolean isInputEnabled) {
		this.name = name;
		this.isInputEnabled = isInputEnabled;
	}
	
	@Override
	public String toString() {
		return this.name;
	}
}
