package org.tr.edu.yildiz.ce.openareas.utils;

import java.util.ArrayList;
import java.util.List;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Polygon;
import org.locationtech.jts.util.GeometricShapeFactory;

public class OverlapCalculator {
	private List<Building> buildings;

	public OverlapCalculator(List<Building> buildings) {
		this.buildings = buildings;
	}

	/**
	 * @param position position The current position of the circular polygon.
	 * @param polygon  Current circular polygon.
	 * @return A list of all the intersecting structures with the polygon.
	 */
	private List<Structure> findIntersectingStructures(CartesianCoordinate position, SlidingCircularPolygon polygon) {
		GeometricShapeFactory sf = new GeometricShapeFactory();
		sf.setSize(polygon.getRadius() * 2);
		sf.setCentre(position.getCoordinate());
		Polygon circle = sf.createCircle();

		List<Structure> intersectingStructures = new ArrayList<Structure>();
		buildings.forEach(building -> {
			building.getStructures().forEach(structure -> {
				if (structure.getAsPolygon().intersects(circle))
					intersectingStructures.add(structure);
			});
		});
		return intersectingStructures;
	}

	/**
	 * @param position The current position of the circular polygon.
	 * @param polygon  Current circular polygon.
	 * @return Percentage of the are of the circular polygon that is filled with
	 *         structures.
	 */
	public Double findEmptyAreaPercentage(CartesianCoordinate position, SlidingCircularPolygon polygon) {
		try {
			Double totalIntersectionArea = 0.0;
			List<Structure> intersectingStructures = findIntersectingStructures(position, polygon);

			GeometricShapeFactory sf = new GeometricShapeFactory();
			sf.setSize(polygon.getRadius() * 2);
			sf.setCentre(position.getCoordinate());
			Polygon circle = sf.createCircle();

			for (int i = 0; i < intersectingStructures.size(); i++) {
				Polygon structurePolygon = intersectingStructures.get(i).getAsPolygon();
				Geometry intersectionGeometry = structurePolygon.intersection(circle);
				Double intersectionArea = intersectionGeometry.getArea();
				totalIntersectionArea += intersectionArea;
			}

			Double totalIntersectionPercentage = totalIntersectionArea / circle.getArea();
			
			if (totalIntersectionPercentage <= 1.0) {
				return 1.0 - totalIntersectionPercentage;
			} else {
				return 0.0;
			}
		} catch (Exception e) {
			return 0.0;
		}
	}
}
