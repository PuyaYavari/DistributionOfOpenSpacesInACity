package org.tr.edu.yildiz.ce.openareas.utils;

import java.util.ArrayList;
import java.util.List;

import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Polygon;
import org.locationtech.jts.util.GeometricShapeFactory;

public class OverlapCalculator {
	private List<Building> buildings;
	private Boolean overlap;

	public OverlapCalculator(List<Building> buildings) {
		this.buildings = buildings;
	}

	private List<Structure> findOverlappingStructures(CartesianCoordinate position, SlidingCircularPolygon poligon) {
		Double radius = poligon.getRadius();
		List<Structure> overlappingStructures = new ArrayList<Structure>();
		buildings.forEach(building -> {
			building.getStructures().forEach(structure -> {
				overlap = false;
				structure.getCorners().forEach(corner -> {
					Boolean horizontalOverlap = false;
					Boolean verticalOverlap = false;
					System.out.println(position.getX() + " , " + position.getY());
					if (corner.getX() < (position.getX() + radius) && corner.getX() > (position.getX() - radius)) 
						horizontalOverlap = true;
					if (corner.getY() < (position.getY() + radius) && corner.getY() > (position.getY() - radius)) 
						verticalOverlap = true;
					if (horizontalOverlap && verticalOverlap) {
						System.out.println("OVERLAP!!");
						overlap = true;
					}
				});
				if (overlap)
					overlappingStructures.add(structure);
			});
		});
		return overlappingStructures;
	} //FIXME: WHAT IF CIRCLE INSIDE A STRUCTURE?, DO WITH JTS, TAKES A YEAR TO FINISH
	
	public Double findOverlappingArea(CartesianCoordinate position, SlidingCircularPolygon polygon) {
		Double overlapArea = 0.0;
		List<Structure> overlappingStructures = findOverlappingStructures(position, polygon);
//		System.out.println("overlapping structures count: " + overlappingStructures.size());
		
		GeometricShapeFactory sf = new GeometricShapeFactory();
		sf.setSize(polygon.getRadius() * 2);
		sf.setCentre(position.getCoordinate());
		Polygon circle = sf.createCircle();
		
		GeometryFactory gf = new GeometryFactory();
		overlappingStructures.forEach(structure -> {
			Polygon structurePolygon = gf.createPolygon(structure.getCornersCoordinates());
			System.out.println("Building: " + structurePolygon.toString());
		});
		
		return overlapArea;
	}
}
