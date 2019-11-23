package org.tr.edu.yildiz.ce.openareas.utils;

import org.locationtech.jts.geom.Coordinate;

public class CartesianCoordinate {
	private Double x, y;
	
	public CartesianCoordinate(Double x, Double y) {
		this.x = x;
		this.y = y;
	}
	
	public Double getX() { return x; }
	public Double getY() { return y; }
	public Coordinate getCoordinate() { return new Coordinate(x, y); }
}
