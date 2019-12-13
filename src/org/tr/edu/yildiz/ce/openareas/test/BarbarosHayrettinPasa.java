package org.tr.edu.yildiz.ce.openareas.test;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import org.tr.edu.yildiz.ce.openareas.utils.Building;
import org.tr.edu.yildiz.ce.openareas.utils.CartesianConverter;
import org.tr.edu.yildiz.ce.openareas.utils.DistributionFinder;
import org.tr.edu.yildiz.ce.openareas.utils.OSMParser;
import org.tr.edu.yildiz.ce.openareas.utils.OverlapCalculator;
import org.tr.edu.yildiz.ce.openareas.utils.SlidingCircularPolygon;

import com.google.gson.JsonObject;

public class BarbarosHayrettinPasa {
	public static void main(String[] args) throws Exception {
		String path = "/home/puya/Documents/FinalProject/Maps/BarbarosHayrettinPasa.geojson";;

		OSMParser parser = new OSMParser();

		String data = parser.readFileAsString(path);

		List<JsonObject> buildingsObjectList = parser.findBuildings(data);
		List<List<List<List<Double>>>> buildingDataList = parser.findAllBuildingsCorners(buildingsObjectList);

		List<Double> borders = parser.getBoundaries(data);

		CartesianConverter converter = new CartesianConverter(borders.get(0), borders.get(1), borders.get(2),
				borders.get(3));

		List<Building> buildings = converter.convertAllBuildingsToMeters(buildingDataList);
		borders = converter.convertBorders(borders);

		OverlapCalculator calculator = new OverlapCalculator(buildings);
		System.out.println(borders);

		DistributionFinder finder = new DistributionFinder();
		Map<Double,List<Double>> outputs = finder.execute(borders, calculator, 1.0, 1.0, 1.0);
		
		
		PrintWriter writer = new PrintWriter("/home/puya/Documents/FinalProject/Output/output", "UTF-8");
		writer.println(outputs.toString());
		writer.close();
	}
}
