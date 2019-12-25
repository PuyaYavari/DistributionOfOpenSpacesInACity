package org.tr.edu.yildiz.ce.openareas.utils;

import java.io.File;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import com.google.gson.JsonObject;

public class DistributionHistogramDrawer {
	public static void evaluate(String path, double minCircleRadius, double maxCircleRadius, double delta,
			String dirName, String extention) {
		try {
			OSMParser parser = new OSMParser();

			String data = parser.readFileAsString(path);
			System.out.println("Data ready.");

			List<JsonObject> buildingsObjectList = parser.findBuildings(data);
			System.out.println("Buildings ready.");
			List<List<List<List<Double>>>> buildingDataList = parser.findAllBuildingsCorners(buildingsObjectList);
			System.out.println("All building corners found.");

			List<Double> borders = parser.getBoundaries(data);
			System.out.println("Map boundaries ready.");

			CartesianConverter converter = new CartesianConverter(borders.get(0), borders.get(1), borders.get(2),
					borders.get(3));

			List<Building> buildings = converter.convertAllBuildingsToMeters(buildingDataList);
			System.out.println("All buildings converted to meters.");
			borders = converter.convertBorders(borders);
			System.out.println("Borders converted to meters.");

			OverlapCalculator calculator = new OverlapCalculator(buildings);

			DistributionFinder finder = new DistributionFinder();
			Map<Double, List<Double>> outputs = finder.executeFibo(borders, calculator);
			System.out.println("Intersection distributions calculated.");

			MatlabSession matlab = new MatlabSession("/home/puya/Documents/FinalProject/Output/Matlab/", true);
			matlab.saveAllHistograms(outputs, dirName, extention);
			matlab.close();
			System.out.println("Histograms saved successfuly.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void evaluate(String path, String dirName, String extention) {
		try {
			OSMParser parser = new OSMParser();

			String data = parser.readFileAsString(path);
			System.out.println("Data ready.");

			List<JsonObject> buildingsObjectList = parser.findBuildings(data);
			System.out.println("Buildings ready.");
			List<List<List<List<Double>>>> buildingDataList = parser.findAllBuildingsCorners(buildingsObjectList);
			System.out.println("All building corners found.");

			List<Double> borders = parser.getBoundaries(data);
			System.out.println("Map boundaries ready.");

			CartesianConverter converter = new CartesianConverter(borders.get(0), borders.get(1), borders.get(2),
					borders.get(3));

			List<Building> buildings = converter.convertAllBuildingsToMeters(buildingDataList);
			System.out.println("All buildings converted to meters.");
			borders = converter.convertBorders(borders);
			System.out.println("Borders converted to meters.");

			OverlapCalculator calculator = new OverlapCalculator(buildings);

			DistributionFinder finder = new DistributionFinder();
			Map<Double, List<Double>> outputs = finder.executeFibo(borders, calculator);
			System.out.println("Intersection distributions calculated.");

			MatlabSession matlab = new MatlabSession("/home/puya/Documents/FinalProject/Output/Matlab/", true);
			matlab.saveAllHistograms(outputs, dirName, extention);
			matlab.close();
			System.out.println("Histograms saved successfuly.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
