package org.tr.edu.yildiz.ce.openareas.utils;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonObject;

public class DistributionFinder {
	
	public void execute(String path) throws Exception {
		OSMParser parser = new OSMParser();
		CartesianConverter converter = new CartesianConverter();
		
		String data = parser.readFileAsString(path);
		
		List<JsonObject> buildingsObjectList = parser.findBuildings(data);
		List<List<List<List<Double>>>> buildingDataList = parser.findAllBuildingsCorners(buildingsObjectList);
		List<Building> buildings = converter.convertAllBuildingsToMeters(buildingDataList);
		
		List<Double> borders = parser.getBoundaries(data);
		borders = converter.convertBorders(borders);
		
		OverlapCalculator calculator = new OverlapCalculator(buildings);
		
		slidePoligon(borders, calculator, new SlidingCircularPolygon(1.0));
	}
	
	private List<Double> slidePoligon(List<Double> borders, OverlapCalculator calculator, SlidingCircularPolygon polygon) {
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
				intersections.add(calculator.findOverlappingArea(new CartesianCoordinate(HPosition, VPosition), polygon));
				HPosition += 1;
			}
			VPosition += 1;
			HPosition = slideHStart;
		}
		return intersections;
	}
}
