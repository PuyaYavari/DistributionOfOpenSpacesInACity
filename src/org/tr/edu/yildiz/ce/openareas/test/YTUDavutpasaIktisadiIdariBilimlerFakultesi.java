package org.tr.edu.yildiz.ce.openareas.test;

import org.tr.edu.yildiz.ce.openareas.utils.DistributionHistogramDrawer;

public class YTUDavutpasaIktisadiIdariBilimlerFakultesi {

	public static void main(String[] args) throws Exception {
		String path = "/home/puya/Documents/FinalProject/Maps/YTUDavutpasaIktisadiIdariBilimlerFakultesi.geojson";
		DistributionHistogramDrawer.evaluate(path, 89.0, 89.0, 1.0, "YTUDavutpasaIktisadiIdariBilimlerFakultesi", ".jpg");
	}
}
