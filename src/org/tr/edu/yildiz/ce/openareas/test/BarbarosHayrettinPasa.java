package org.tr.edu.yildiz.ce.openareas.test;

import org.tr.edu.yildiz.ce.openareas.utils.DistributionHistogramDrawer;

public class BarbarosHayrettinPasa {
	public static void main(String[] args) throws Exception {
		String path = "/home/puya/Documents/FinalProject/Maps/BarbarosHayrettinPasa.geojson";
		DistributionHistogramDrawer.evaluate(path, 50.0, 50.0, 1.0, "BarbarosHayrettinPasa", ".jpg");
	}
}
