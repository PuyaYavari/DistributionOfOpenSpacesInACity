package org.tr.edu.yildiz.ce.openareas.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class OSMParser {

	public String readFileAsString(String fileName) throws Exception {
		String data = "";
		data = new String(Files.readAllBytes(Paths.get(fileName)));
		return data;
	}

	/**
	 * @param json
	 * @return List of buildings jsonObjects
	 * @throws IOException
	 */
	public List<JsonObject> findBuildings(String json) throws IOException {
		List<JsonObject> buildingsList = new ArrayList<JsonObject>();
		JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
		JsonArray features = jsonObject.getAsJsonArray("features");
		features.forEach(feature -> {
			JsonObject featureObject = new JsonParser().parse(feature.toString()).getAsJsonObject();
			try {
				if (featureObject.getAsJsonObject("properties").has("building")) {
					JsonObject building = featureObject.getAsJsonObject("properties");
					if (building.get("building").getAsString() != "no") {
						buildingsList.add(featureObject);
					}
				}
			} catch (ClassCastException e) {
				// e.printStackTrace();
			}
		});
		return buildingsList;
	}

	/**
	 * @param json
	 * @return [left,bottom,right,top]
	 */
	public List<Double> getBoundaries(String json) {
		try {
			JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
			JsonArray boundariesBox = jsonObject.getAsJsonArray("bbox");
			List<Double> boundaries = Arrays.asList(boundariesBox.get(0).getAsDouble(),
					boundariesBox.get(1).getAsDouble(), boundariesBox.get(2).getAsDouble(),
					boundariesBox.get(3).getAsDouble());
			return boundaries;
		} catch (Exception e) {
			try {
				List<Double> boundaries = Arrays.asList(null, null, null, null);
				List<List<List<List<Double>>>> buildingDataList = findAllBuildingsCorners(findBuildings(json));
				for(List<List<List<Double>>> building: buildingDataList) {
					for(List<List<Double>> structure: building) {
						for(List<Double> corner: structure) {
							if(corner.get(0) > boundaries.get(0) || boundaries.get(0) == null) {
								boundaries.set(0, corner.get(0));
							}
							if(corner.get(2) < boundaries.get(0) || boundaries.get(0) == null) {
								boundaries.set(2, corner.get(0));
							}
							if(corner.get(1) > boundaries.get(1) || boundaries.get(1) == null) {
								boundaries.set(1, corner.get(1));
							}
							if(corner.get(3) < boundaries.get(1) || boundaries.get(1) == null) {
								boundaries.set(3, corner.get(1));
							}
						}
					}
				}
				return boundaries;
			} catch (Exception ex) {
				ex.printStackTrace();
				return null;
			}
		}
	}

	/**
	 * @param object: a JsonObject of the building
	 * @return building data based on longitude and latitude.
	 */
	public List<List<List<Double>>> findBuildingCorners(JsonObject object) {
		List<List<List<Double>>> building = new ArrayList<List<List<Double>>>();
		try {
			JsonObject geometry = object.get("geometry").getAsJsonObject();
			String buildingType = geometry.get("type").getAsString();
			geometry.get("coordinates").getAsJsonArray().forEach(build -> {
				if (buildingType.equals("MultiPolygon")) {
					build.getAsJsonArray().forEach(piece -> {
						List<List<Double>> structure = new ArrayList<List<Double>>();
						JsonArray buildingPiece = piece.getAsJsonArray();
						buildingPiece.forEach(corner -> {
							JsonArray buildingCorner = corner.getAsJsonArray();
							Double x = buildingCorner.get(0).getAsDouble();
							Double y = buildingCorner.get(1).getAsDouble();
							structure.add(Arrays.asList(x, y));
						});
						building.add(structure);
					});
				} else if (buildingType.equals("Polygon")) {
					List<List<Double>> structure = new ArrayList<List<Double>>();
					build.getAsJsonArray().forEach(corner -> {
						JsonArray buildingCorner = corner.getAsJsonArray();
						Double x = buildingCorner.get(0).getAsDouble();
						Double y = buildingCorner.get(1).getAsDouble();
						structure.add(Arrays.asList(x, y));
					});
					building.add(structure);
				} else {
					System.out.println("Unknown type:" + geometry.get("type").getAsString());
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return building;
	}

	/**
	 * @param objects: A list of JsonObjects of buildings.
	 * @return List of all buildings based on longitude and latitude.
	 */
	public List<List<List<List<Double>>>> findAllBuildingsCorners(List<JsonObject> objects) {
		List<List<List<List<Double>>>> buildings = new ArrayList<List<List<List<Double>>>>();
		objects.forEach(object -> {
			buildings.add(findBuildingCorners(object));
		});
		return buildings;
	}
}
