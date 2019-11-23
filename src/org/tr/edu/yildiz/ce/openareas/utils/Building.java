package org.tr.edu.yildiz.ce.openareas.utils;

import java.util.List;

public class Building {
	private List<Structure> structures;
	
	public Building(List<Structure> structures) {
		this.structures = structures;
	}
	
	public List<Structure> getStructures() { return this.structures; }
}
