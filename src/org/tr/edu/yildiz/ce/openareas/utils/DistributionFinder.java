package org.tr.edu.yildiz.ce.openareas.utils;

import java.util.List;

import com.google.gson.JsonObject;

public class DistributionFinder {
	
	public void execute(String path) throws Exception {
		OSMParser parser = new OSMParser();
		String data = parser.readFileAsString(path);
		List<JsonObject> buildingsList = parser.findBuildings(data);
		CartesianConverter converter = new CartesianConverter();
		System.out.println(converter.convertBuildingCornersToMeters(parser.findObjectCorners(buildingsList.get(0))));
		System.out.println("Map boundaries in lat. and long. "+parser.getBoundaries(data));
		System.out.println("Map boundaries in meters. "+converter.convertBorders(parser.getBoundaries(data)));
	}
}
