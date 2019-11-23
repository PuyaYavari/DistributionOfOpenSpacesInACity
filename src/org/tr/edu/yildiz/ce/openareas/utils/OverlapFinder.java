package org.tr.edu.yildiz.ce.openareas.utils;

import java.util.ArrayList;
import java.util.List;

public class OverlapFinder {
	private List<List<List<List<Double>>>> buildings = new ArrayList<List<List<List<Double>>>>();
	private Boolean overlap;

	public OverlapFinder(List<List<List<List<Double>>>> buildings) {
		this.buildings = buildings;
	}

	private List<List<List<Double>>> findOverlappingStructures(List<Double> position, Double radius) {
		List<List<List<Double>>> overlappingStructures = new ArrayList<List<List<Double>>>();
		buildings.forEach(building -> {
			building.forEach(piece -> {
				overlap = false;
				piece.forEach(corner -> {
					Boolean horizontalOverlap = false;
					Boolean verticalOverlap = false;
					if (corner.get(0) < position.get(0) + radius && corner.get(0) > position.get(0) - radius)
						horizontalOverlap = true;
					if (corner.get(1) < position.get(1) + radius && corner.get(1) > position.get(1) - radius)
						verticalOverlap = true;
					if (horizontalOverlap && verticalOverlap)
						overlap = true;
				});
				if (overlap)
					overlappingStructures.add(piece);
			});
		});
		return overlappingStructures;
	}
	
	//TODO: FIND OVERLAPPING AREA AND PERCENTAGE
}
