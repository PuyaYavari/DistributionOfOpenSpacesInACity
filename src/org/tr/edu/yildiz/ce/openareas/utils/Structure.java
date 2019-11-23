package org.tr.edu.yildiz.ce.openareas.utils;

import java.util.List;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.CoordinateSequence;
import org.locationtech.jts.geom.Envelope;

public class Structure {
	private List<CartesianCoordinate> corners;
	
	public Structure(List<CartesianCoordinate> corners) {
		this.corners = corners;
	}
	
	public List<CartesianCoordinate> getCorners() {
		return this.corners;
	}
	
	public CoordinateSequence getCornersCoordinates() {
		CoordinateSequence coordinates = new CoordinateSequence() {
			
			@Override
			public Coordinate[] toCoordinateArray() {
				Coordinate[] coordinates = new Coordinate[corners.size()];
				for (int i = 0; i < corners.size(); i++) { 
					coordinates[i] = corners.get(i).getCoordinate();
				}
				return coordinates;
			}
			
			@Override
			public int size() {
				return corners.size();
			}
			
			@Override
			public void setOrdinate(int arg0, int arg1, double arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public double getY(int arg0) {
				return 0;
			}
			
			@Override
			public double getX(int arg0) {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public double getOrdinate(int arg0, int arg1) {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public int getDimension() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public Coordinate getCoordinateCopy(int arg0) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public void getCoordinate(int arg0, Coordinate arg1) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public Coordinate getCoordinate(int arg0) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Envelope expandEnvelope(Envelope arg0) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public CoordinateSequence copy() {
				// TODO Auto-generated method stub
				return null;
			}
			
			public Object clone() { return null; }
		};
		return coordinates;
	}
}
