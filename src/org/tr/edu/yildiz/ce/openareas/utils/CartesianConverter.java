package org.tr.edu.yildiz.ce.openareas.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CartesianConverter {
	double northLat, southLath, westLong, eastLong;
	
	public CartesianConverter(Double west, Double south, Double east, Double north) {
		this.northLat = north;
		this.southLath = south;
		this.westLong = west;
		this.eastLong = east;
	}

	/**
	 * @param longitude
	 * @param latitude
	 * @return a list of x,y coordinates in meters
	 */
	public CartesianCoordinate convertToMeters(Double longitude, Double latitude) {
		Double x = convertX(longitude);
		Double y = convertY(latitude);
		return (new CartesianCoordinate(x, y));
	}

	/**
	 * @param lat1 latitude of first point
	 * @param lon1 longitude of first point
	 * @param lat2 latitude of second point
	 * @param lon2 longitude of second point
	 * @return distance between given two points in meters
	 */
	public Double findCartesianDistance(Double lat1, Double lon1, Double lat2, Double lon2) {
		Double R = 6378.137; // Radius of earth in KM
		Double dLat = lat2 * Math.PI / 180 - lat1 * Math.PI / 180;
		Double dLon = lon2 * Math.PI / 180 - lon1 * Math.PI / 180;
		Double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(lat1 * Math.PI / 180)
				* Math.cos(lat2 * Math.PI / 180) * Math.sin(dLon / 2) * Math.sin(dLon / 2);
		Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		Double d = R * c;
		return d * 1000; // meters
	}

	private Double convertX(Double longitude) {
		return findCartesianDistance(northLat, longitude, northLat, westLong);
	}

	private Double convertY(Double latitude) {
		return findCartesianDistance(latitude, westLong, northLat, westLong);
	}

	/**
	 * @param building: A List of different structures which form a building which
	 *                  is a list of corners that is a list of longitude and
	 *                  latitude.
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
		return Arrays.asList(0.0, convertY(borders.get(1)), convertX(borders.get(2)), 0.0);
	}
}
