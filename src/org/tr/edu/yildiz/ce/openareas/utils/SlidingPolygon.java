package org.tr.edu.yildiz.ce.openareas.utils;

import java.util.ArrayList;
import java.util.List;

public class SlidingPolygon {
	private Double radius;
	
	public SlidingPolygon(Double radius) {
		this.radius = radius;
	}
	
	public List<Double> slide(List<Double> borders) {
		Double slideHStart = 0.0;
		Double slideHEnd = 0.0;
		Double slideVStart = 0.0;
		Double slideVEnd = 0.0;
		if (borders.get(0) > borders.get(2)) {
			slideHStart = borders.get(2) - radius;
			slideHEnd = borders.get(0) + radius;
		} else {
			slideHStart = borders.get(0) - radius;
			slideHEnd = borders.get(2) + radius;
		}
		if (borders.get(1) > borders.get(3)) {
			slideHStart = borders.get(3) - radius;
			slideHEnd = borders.get(1) + radius;
		} else {
			slideHStart = borders.get(1) - radius;
			slideHEnd = borders.get(3) + radius;
		}
		
		List<Double> intersections = new ArrayList<Double>();
		Double HPosition = slideHStart;
		Double VPosition = slideVStart;
		while (VPosition < slideVEnd) {
			while (HPosition < slideHEnd) {
				//TODO: FIND OVERLAPPING BUILDINGS
				//TODO: FIND OVERLAPPING AREA AND ADD TO INTERSECTIONS LIST
				HPosition += 1;
			}
			VPosition += 1;
		}
		return intersections;
	}
}
