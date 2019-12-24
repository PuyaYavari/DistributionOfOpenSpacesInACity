package org.tr.edu.yildiz.ce.openareas.test;

import org.tr.edu.yildiz.ce.openareas.utils.DistributionHistogramDrawer;

public class YTUDavutpasaOrtaBahce {

	public static void main(String[] args) throws Exception {
		String path = "/home/puya/Documents/FinalProject/Maps/YTUDavutpasaOrtaBahce.geojson";
		DistributionHistogramDrawer.evaluate(path, 1.0, 1.0, 1.0, "YTUDavutpasaOrtaBahce", ".jpg");
	}

}
