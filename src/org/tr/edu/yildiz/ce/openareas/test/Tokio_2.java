package org.tr.edu.yildiz.ce.openareas.test;

import org.tr.edu.yildiz.ce.openareas.utils.DistributionHistogramDrawer;

public class Tokio_2 {

	public static void main(String[] args) {
		String path = "/home/puya/Documents/FinalProject/Maps/Tokio_2.geojson";
		DistributionHistogramDrawer.evaluate(path, 10.0, 10.0, 5.0, "Tokio_2", ".jpg");
	}

}
