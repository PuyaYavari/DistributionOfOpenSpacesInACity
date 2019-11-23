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
	public CartesianCoordinate convertToMeters(Double longitude, Double latitude) {
		Double x = earthRadius * Math.cos(latitude) * Math.cos(longitude);
		Double y = earthRadius * Math.cos(latitude) * Math.sin(longitude);
		return (new CartesianCoordinate(x, y));
	}
	
	/**
	 * @param building: A List of different structures which form a building which is a list of corners that is
	 * a list of longitude and latitude.
	 * @return The Building object corresponding to the input.
	 */
	public Building convertBuildingCornersToMeters(List<List<List<Double>>> building) {
		List<Structure> buildingInMeters = new ArrayList<Structure>();
		building.forEach(structure -> {
			List<CartesianCoordinate> structureCornersInMeters = new ArrayList<CartesianCoordinate>();
			structure.forEach(corner -> {
				structureCornersInMeters.add(convertToMeters(corner.get(0), corner.get(1)));
			});
			buildingInMeters.add(new Structure(structureCornersInMeters));
		});
		return (new Building(buildingInMeters));
	}
	
	/**
	 * @param buildings: A list of buildings in longitude and latitude.
	 * @return A list of building objects corresponding to input.
	 */
	public List<Building> convertAllBuildingsToMeters(List<List<List<List<Double>>>> buildings) {
		List<Building> buildingsInMeter = new ArrayList<Building>();
		buildings.forEach(building -> {
			buildingsInMeter.add(convertBuildingCornersToMeters(building));
		});
		return buildingsInMeter;
	}
	
	/**
	 * @param borders
	 * @return [left,bottom,right,top] in meters
	 */
	public List<Double> convertBorders(List<Double> borders) {
		CartesianCoordinate upperLeft = convertToMeters(borders.get(0), borders.get(3));
		CartesianCoordinate lowerRight = convertToMeters(borders.get(2), borders.get(1));
		return Arrays.asList(upperLeft.getX(), lowerRight.getY(), lowerRight.getX(), upperLeft.getY());
	}
}
