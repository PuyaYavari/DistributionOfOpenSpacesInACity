package org.tr.edu.yildiz.ce.openareas.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class OSMParser {
	public static String readFileAsString(String fileName) throws Exception {
		String data = "";
		data = new String(Files.readAllBytes(Paths.get(fileName)));
		return data;
	}

	public static List<JsonObject> findBuildings(String json) throws IOException {
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

	public static void main(String[] args) throws Exception {
		String data = readFileAsString("/home/puya/Documents/FinalProject/Data/BarbarosHayrettinPasa.geojson");
		List<JsonObject> buildingsList = findBuildings(data);
		System.out.println(buildingsList.get(0).get("geometry").getAsJsonObject().get("coordinates").toString());
	}
}
