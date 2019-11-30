package org.tr.edu.yildiz.ce.openareas.utils;

import java.util.List;

import org.locationtech.jts.geom.Coordinate;

public class Structure {
	private List<CartesianCoordinate> corners;

	public Structure(List<CartesianCoordinate> corners) {
		this.corners = corners;
	}

	public List<CartesianCoordinate> getCorners() {
		return this.corners;
	}

	public Coordinate[] getCornersCoordinatesArray() {
		if (corners.size() > 2) {
			Coordinate[] coordinates = new Coordinate[corners.size() + 1];
			for (int i = 0; i < corners.size(); i++) {
				coordinates[i] = corners.get(i).getCoordinate();
			}
			coordinates[corners.size()] = corners.get(0).getCoordinate();
			return coordinates;
		} else {
			return new Coordinate[0];
		}
	}
}
