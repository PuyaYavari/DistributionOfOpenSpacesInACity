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
		JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
		JsonArray boundariesBox = jsonObject.getAsJsonArray("bbox");
		List<Double> boundaries = Arrays.asList(boundariesBox.get(0).getAsDouble(), boundariesBox.get(1).getAsDouble(),
				boundariesBox.get(2).getAsDouble(), boundariesBox.get(3).getAsDouble());
		return boundaries;
	}

	public List<List<List<Double>>> findObjectCorners(JsonObject object) {
		List<List<List<Double>>> objectCorners = new ArrayList<List<List<Double>>>();
		try {
			object.get("geometry").getAsJsonObject().get("coordinates").getAsJsonArray().forEach(piece -> {
				JsonArray buildingPiece = piece.getAsJsonArray();
				buildingPiece.forEach(corner -> {
					JsonArray buildingCorner = corner.getAsJsonArray();
					Double x = buildingCorner.get(0).getAsDouble();
					Double y = buildingCorner.get(1).getAsDouble();
					objectCorners.add(Arrays.asList(Arrays.asList(x, y)));
				});
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return objectCorners;
	}
}
