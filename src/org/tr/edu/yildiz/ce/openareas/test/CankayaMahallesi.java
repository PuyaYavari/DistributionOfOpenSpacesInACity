package org.tr.edu.yildiz.ce.openareas.test;

import org.tr.edu.yildiz.ce.openareas.utils.DistributionHistogramDrawer;

public class CankayaMahallesi {

	public static void main(String[] args) {
		String path = "/home/puya/Documents/FinalProject/Maps/CankayaMahallesi.geojson";
		String dirName = "CankayaMahallesi";
		DistributionHistogramDrawer.evaluate(path, dirName, ".jpg");
	}

}
