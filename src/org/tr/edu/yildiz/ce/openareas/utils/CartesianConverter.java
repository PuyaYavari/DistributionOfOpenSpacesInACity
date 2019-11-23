package org.tr.edu.yildiz.ce.openareas.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class CartesianConverter {
	double earthRadius = 6371000;
	
	/**
	 * @param longitude
	 * @param latitude
	 * @return a list of x,y coordinates in meters
	 */
	public List<Double> convertToMeters(Double longitude, Double latitude) {
		Double x = earthRadius * Math.cos(latitude) * Math.cos(longitude);
		Double y = earthRadius * Math.cos(latitude) * Math.sin(longitude);
		return (Arrays.asList(x, y));
	}
	
	public List<List<List<Double>>> convertBuildingCornersToMeters(List<List<List<Double>>> building) {
		List<List<List<Double>>> buildingInMeters = new ArrayList<List<List<Double>>>();
		building.forEach(piece -> {
			List<List<Double>> pieceInMeters = new ArrayList<List<Double>>();
			piece.forEach(corner -> {
				pieceInMeters.add(convertToMeters(corner.get(0), corner.get(1)));
			});
			buildingInMeters.add(pieceInMeters);
		});
		return buildingInMeters;
	}
	
	/**
	 * @param borders
	 * @return [left,bottom,right,top] in meters
	 */
	public List<Double> convertBorders(List<Double> borders) {
		List<Double> upperLeft = convertToMeters(borders.get(0), borders.get(3));
		List<Double> lowerRight = convertToMeters(borders.get(2), borders.get(1));
		return Arrays.asList(upperLeft.get(0), lowerRight.get(1), lowerRight.get(0), upperLeft.get(1));
	}
}
