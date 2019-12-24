package org.tr.edu.yildiz.ce.openareas.test;

import org.tr.edu.yildiz.ce.openareas.utils.DistributionHistogramDrawer;

public class Rio_1 {

	public static void main(String[] args) {
		String path = "/home/puya/Documents/FinalProject/Maps/Rio_1.geojson";
		DistributionHistogramDrawer.evaluate(path, 10.0, 10.0, 5.0, "Rio_1", ".jpg");
	}

}
