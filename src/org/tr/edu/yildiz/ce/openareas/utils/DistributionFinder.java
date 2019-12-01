package org.tr.edu.yildiz.ce.openareas.utils;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonObject;

public class DistributionFinder {

	public List<Double> execute(List<Double> borders, OverlapCalculator calculator, SlidingCircularPolygon polygon) {
		Double slideHStart = 0.0;
		Double slideHEnd = 0.0;
		Double slideVStart = 0.0;
		Double slideVEnd = 0.0;
		Double radius = polygon.getRadius();

		if (borders.get(0) > borders.get(2)) {
			slideHStart = borders.get(2) - radius;
			slideHEnd = borders.get(0) + radius;
		} else {
			slideHStart = borders.get(0) - radius;
			slideHEnd = borders.get(2) + radius;
		}
		if (borders.get(1) > borders.get(3)) {
			slideVStart = borders.get(3) - radius;
			slideVEnd = borders.get(1) + radius;
		} else {
			slideVStart = borders.get(1) - radius;
			slideVEnd = borders.get(3) + radius;
		}

		List<Double> intersections = new ArrayList<Double>();
		Double HPosition = slideHStart;
		Double VPosition = slideVStart;
		while (VPosition < slideVEnd) {
			while (HPosition < slideHEnd) {
				Double intersectionPercentage = calculator
						.findFilledAreaPercentage(new CartesianCoordinate(HPosition, VPosition), polygon);
				intersections.add(intersectionPercentage);
				HPosition += 1;
			}
			VPosition += 1;
			HPosition = slideHStart;
		}
		return intersections;
	}
}
