package org.tr.edu.yildiz.ce.openareas.test;

import org.tr.edu.yildiz.ce.openareas.utils.DistributionHistogramDrawer;

public class YTUDavutpasa {

	public static void main(String[] args) throws Exception {
		String path = "/home/puya/Documents/FinalProject/Maps/YTUDavutpasa.geojson";
		String dirName = "YTUDavutpasa";
		DistributionHistogramDrawer.evaluate(path, dirName, ".jpg");
	}
}
