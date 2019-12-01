package org.tr.edu.yildiz.ce.openareas.utils;

import org.locationtech.jts.geom.Polygon;
import org.locationtech.jts.util.GeometricShapeFactory;

public class SlidingCircularPolygon {
	private Double radius;
	private Double area;
	private Polygon polygon;
	
	public SlidingCircularPolygon(Double radius) {
		this.radius = radius;
		GeometricShapeFactory sh = new GeometricShapeFactory();
		sh.setSize(radius*2);
		this.polygon = sh.createCircle();
		this.area = polygon.getArea();
	}
	
	public Double getRadius() {
		return this.radius;
	}
	
	public Double getArea() {
		return this.area;
	}
	
	public Polygon getPolygon() {
		return this.polygon;
	}
}
