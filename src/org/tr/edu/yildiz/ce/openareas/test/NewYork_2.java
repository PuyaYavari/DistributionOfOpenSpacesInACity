package org.tr.edu.yildiz.ce.openareas.test;

import org.tr.edu.yildiz.ce.openareas.utils.DistributionHistogramDrawer;

public class NewYork_2 {

	public static void main(String[] args) {
		String path = "/home/puya/Documents/FinalProject/Maps/NewYork_2.geojson";
		DistributionHistogramDrawer.evaluate(path, 10.0, 10.0, 5.0, "NewYork_2", ".jpg");
	}

}
