package org.tr.edu.yildiz.ce.openareas.test;

import org.tr.edu.yildiz.ce.openareas.utils.DistributionFinder;

public class BarbarosHayrettinPasa {
	public static void main(String[] args) throws Exception {
		String path = "/home/puya/Documents/FinalProject/Maps/BarbarosHayrettinPasa.geojson";
		DistributionFinder finder = new DistributionFinder();
		finder.execute(path);
	}
}
