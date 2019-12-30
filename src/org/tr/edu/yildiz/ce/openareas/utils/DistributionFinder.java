package org.tr.edu.yildiz.ce.openareas.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.google.gson.JsonObject;

public class DistributionFinder {

	double maxRadius = 0.0;

	public List<Double> execute(List<Double> borders, OverlapCalculator calculator, SlidingCircularPolygon polygon,
			boolean isRandom) {
		Double slideHStart = 0.0;
		Double slideHEnd = 0.0;
		Double slideVStart = 0.0;
		Double slideVEnd = 0.0;
		Double radius = polygon.getRadius();

		if (maxRadius != 0.0) {
			if (borders.get(0) > borders.get(2)) {
				slideHStart = borders.get(2) + maxRadius;
				slideHEnd = borders.get(0) - maxRadius;
			} else {
				slideHStart = borders.get(0) + maxRadius;
				slideHEnd = borders.get(2) - maxRadius;
			}
			if (borders.get(1) > borders.get(3)) {
				slideVStart = borders.get(3) + maxRadius;
				slideVEnd = borders.get(1) - maxRadius;
			} else {
				slideVStart = borders.get(1) + maxRadius;
				slideVEnd = borders.get(3) - maxRadius;
			}
		} else {
			if (borders.get(0) > borders.get(2)) {
				slideHStart = borders.get(2) + radius;
				slideHEnd = borders.get(0) - radius;
			} else {
				slideHStart = borders.get(0) + radius;
				slideHEnd = borders.get(2) - radius;
			}
			if (borders.get(1) > borders.get(3)) {
				slideVStart = borders.get(3) + radius;
				slideVEnd = borders.get(1) - radius;
			} else {
				slideVStart = borders.get(1) + radius;
				slideVEnd = borders.get(3) - radius;
			}
		}

		List<Double> intersections = new ArrayList<Double>();
		int progressEnd = (int) (Math.abs((slideHEnd - slideHStart)) * Math.abs((slideVEnd - slideVStart)));
		int progress = 0;
		if (isRandom) {
			int count = 0;
			while (count < 1000000) {
				Random r = new Random();
				double HPosition = slideHStart + (slideHEnd - slideHStart) * r.nextDouble();
				double VPosition = slideVStart + (slideVEnd - slideVStart) * r.nextDouble();
				Double intersectionPercentage = calculator
						.findEmptyAreaPercentage(new CartesianCoordinate(HPosition, VPosition), polygon);
				intersections.add(intersectionPercentage);
				progress += 1;
				System.out.print(String.format("Current radius: %1$,.2f   ", radius));
				System.out.println(String.format("Progress: %d/%d", progress, 1000000));
				count++;
			}
		} else {
			Double HPosition = slideHStart;
			Double VPosition = slideVStart;
			while (VPosition < slideVEnd) {
				while (HPosition < slideHEnd) {
					Double intersectionPercentage = calculator
							.findEmptyAreaPercentage(new CartesianCoordinate(HPosition, VPosition), polygon);
					intersections.add(intersectionPercentage);
					HPosition += 1;
					progress += 1;
					System.out.print(String.format("Current radius: %1$,.2f   ", radius));
					System.out.println(String.format("Progress: %d/%d", progress, progressEnd));
				}
				VPosition += 1;
				HPosition = slideHStart;
			}
		}
		return intersections;
	}

	/**
	 * @param borders    A list of map borders in meters
	 * @param calculator An OverlapCalculator object
	 * @param start      Minimum radius of the circular polygon
	 * @param end        Maximum radius of the circular polygon
	 * @param delta      Step size of the radius of the circular polygon
	 * @param isRandom	 Calculate outputs for random points
	 * 
	 * @return A map of Radius to List of Fullness
	 */
	public Map<Double, List<Double>> execute(List<Double> borders, OverlapCalculator calculator, Double start,
			Double end, Double delta, boolean isRandom) {
		Map<Double, List<Double>> outputs = new HashMap<Double, List<Double>>();
		Double radius = start;
		maxRadius = end;
		while (radius <= maxRadius) {
			System.out.println(String.format("Calculating for radius %1$,.2f...", radius));
			List<Double> output = execute(borders, calculator, new SlidingCircularPolygon(radius), isRandom);
			outputs.put(radius, output);
			radius += delta;
		}
		maxRadius = 0.0;
		return outputs;
	}

	/**
	 * @param borders    A list of map borders in meters
	 * @param calculator An OverlapCalculator object
	 * @param isRandom	 Calculate outputs for random points
	 * 
	 * @return A map of Radius to List of Fullness
	 */
	public Map<Double, List<Double>> executeFibo(List<Double> borders, OverlapCalculator calculator, boolean isRandom) {
		Map<Double, List<Double>> outputs = new HashMap<Double, List<Double>>();
		double smallerBorder;
		if (borders.get(1) > borders.get(2)) {
			smallerBorder = borders.get(2);
		} else {
			smallerBorder = borders.get(1);
		}
		double[] prevRadius = { 0.0, 1.0 };
		double radius = 1.0;
		if (smallerBorder > 1288) {
			int tmp = 1;
			while (smallerBorder > (tmp * 1000) + 288)
				tmp++;
			tmp--;
			while (radius < (smallerBorder - (tmp * 1000)) / 2) {
				prevRadius[0] = prevRadius[1];
				prevRadius[1] = radius;
				radius = prevRadius[0] + prevRadius[1];
			}
			maxRadius = radius;
		} else {
			while (isFitting(borders, radius)) {
				prevRadius[0] = prevRadius[1];
				prevRadius[1] = radius;
				radius = prevRadius[0] + prevRadius[1];
			}
			maxRadius = prevRadius[1];
		}
		prevRadius[0] = 0.0;
		prevRadius[1] = 1.0;
		radius = 1.0;
		while (radius <= maxRadius) {
			System.out.println(String.format("Calculating for radius %1$,.2f...", radius));
			List<Double> output = execute(borders, calculator, new SlidingCircularPolygon(radius), isRandom);
			outputs.put(radius, output);
			prevRadius[0] = prevRadius[1];
			prevRadius[1] = radius;
			radius = prevRadius[0] + prevRadius[1];
		}
		maxRadius = 0.0;
		return outputs;
	}

	private boolean isFitting(List<Double> borders, double radius) {
		if (borders.get(0) > borders.get(2) && borders.get(0) - borders.get(2) > radius * 2) {
			if (borders.get(1) > borders.get(3) && borders.get(1) - borders.get(3) > radius * 2) {
				return true;
			} else if (borders.get(1) < borders.get(3) && borders.get(3) - borders.get(1) > radius * 2) {
				return true;
			}
		} else if (borders.get(0) < borders.get(2) && borders.get(2) - borders.get(0) > radius * 2) {
			if (borders.get(1) > borders.get(3) && borders.get(1) - borders.get(3) > radius * 2) {
				return true;
			} else if (borders.get(1) < borders.get(3) && borders.get(3) - borders.get(1) > radius * 2) {
				return true;
			}
		}
		return false;
	}
}
