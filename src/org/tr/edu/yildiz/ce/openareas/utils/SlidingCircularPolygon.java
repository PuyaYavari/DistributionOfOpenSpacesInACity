package org.tr.edu.yildiz.ce.openareas.utils;

import org.locationtech.jts.util.GeometricShapeFactory;

public class SlidingCircularPolygon {
	private Double radius;
	private Double area;
	
	public SlidingCircularPolygon(Double radius) {
		this.radius = radius;
		GeometricShapeFactory sh = new GeometricShapeFactory();
		sh.setSize(radius*2);
		this.area = sh.createCircle().getArea();
	}
	
	public Double getRadius() {
		return this.radius;
	}
	
	public Double getArea() {
		return this.area;
	}
}
