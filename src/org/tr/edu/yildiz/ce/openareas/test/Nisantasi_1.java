package org.tr.edu.yildiz.ce.openareas.test;

import org.tr.edu.yildiz.ce.openareas.utils.DistributionHistogramDrawer;

public class Nisantasi_1 {

	public static void main(String[] args) {
		String path = "/home/puya/Documents/FinalProject/Maps/Nisantasi_1.geojson";
		String dirName = "Nisantasi_1";
		DistributionHistogramDrawer.evaluate(path, 1.0, 1.0, 1.0, dirName, ".jpg");
		DistributionHistogramDrawer.evaluate(path, 2.0, 2.0, 1.0, dirName, ".jpg");
		DistributionHistogramDrawer.evaluate(path, 3.0, 3.0, 1.0, dirName, ".jpg");
		DistributionHistogramDrawer.evaluate(path, 5.0, 5.0, 1.0, dirName, ".jpg");
		DistributionHistogramDrawer.evaluate(path, 8.0, 8.0, 1.0, dirName, ".jpg");
		DistributionHistogramDrawer.evaluate(path, 13.0, 13.0, 1.0, dirName, ".jpg");
		DistributionHistogramDrawer.evaluate(path, 21.0, 21.0, 1.0, dirName, ".jpg");
		DistributionHistogramDrawer.evaluate(path, 34.0, 34.0, 1.0, dirName, ".jpg");
		DistributionHistogramDrawer.evaluate(path, 55.0, 55.0, 1.0, dirName, ".jpg");
		DistributionHistogramDrawer.evaluate(path, 89.0, 89.0, 1.0, dirName, ".jpg");
	}

}
