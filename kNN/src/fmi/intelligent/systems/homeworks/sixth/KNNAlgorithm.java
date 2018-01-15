package fmi.intelligent.systems.homeworks.sixth;

import java.util.Map;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.stream.Collectors;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

import static fmi.intelligent.systems.homeworks.sixth.DataProcessing.*;

public class KNNAlgorithm {

	/**
	 * K.
	 */
	private static int k = 40;

	/**
	 * Correct predictions.
	 */
	private static double numberOfCorrectPredictions = 0;


	public static void main(String[] args) {
		loadData();
		chooseTestAndTrainData();

		for (Iris iris : testData) {

			String predictedClassName = predictIrisClassName(iris);

			if (predictedClassName != null && predictedClassName.equals(iris.getClassName())) {
				++numberOfCorrectPredictions;
			} else {
				System.out.println(predictedClassName + "  " + iris.getClassName());
			}
		}

		System.out.println(percentCorrectPrediction() + "% correct predictions");
	}

	/**
	 * Predict class name of iris.
	 *
	 * @param iris which class name should predict.
	 * @return prediction for iris class name.
	 */
	private static String predictIrisClassName(Iris iris) {
		Map<Iris, Double> nearestIrises = new HashMap<>();
		String minDistanceClass = "";
		double minDistanceIris = Double.MAX_VALUE;

		for (Iris i : trainingData) {
			double distance = getDistance(iris, i);

			if (minDistanceIris > distance) {
				minDistanceIris = distance;
				minDistanceClass = i.getClassName();
			}

			nearestIrises.put(i, distance);
		}

		nearestIrises = getKNearestIrises(nearestIrises);

		return decideIrisClassName(nearestIrises, minDistanceClass);
	}

	/**
	 * Get K nearest neighbor irises by distance.
	 *
	 * @param irisWithDistances all irises and their distances to concrete iris.
	 * @return all K nearest neighbours.
	 */
	private static Map<Iris, Double> getKNearestIrises(Map<Iris, Double> irisWithDistances) {
		return irisWithDistances.entrySet()
				.stream()
				.sorted(Map.Entry.comparingByValue())
				.limit(k)
				.collect(Collectors.toMap(
						Map.Entry::getKey,
						Map.Entry::getValue,
						(e1, e2) -> e1,
						LinkedHashMap::new
				));
	}

	/**
	 * Get Euclidean distance between two Irises.
	 *
	 * @param i1 current Iris which class should be predicted.
	 * @param i2 Iris from training data set.
	 * @return distance between two Irises.
	 */
	private static double getDistance(Iris i1, Iris i2) {
		return sqrt(
				pow((i1.getPetalLength() - i2.getPetalLength()), 2)
						+ pow((i1.getPetalWidth() - i2.getPetalWidth()), 2)
						+ pow((i1.getSepalLength() - i2.getSepalLength()), 2)
						+ pow((i1.getSepalWidth() - i2.getSepalWidth()), 2)
		);
	}

	/**
	 * Decide Iris class name from k nearest neighbours.
	 *
	 * @param nearestIrises k nearest neighbour irises.
	 * @param minDistanceClass class name of iris which is closest to concrete iris.
	 * @return predicted class name for iris.
	 */
	private static String decideIrisClassName(Map<Iris, Double> nearestIrises, String minDistanceClass) {
		Map<String, Integer> counts = new HashMap<>();
		nearestIrises.forEach((key, value) -> {
			if (counts.containsKey(key.getClassName())) {
				counts.put(key.getClassName(), counts.get(key.getClassName()) + 1);
			} else {
				counts.put(key.getClassName(), 1);
			}
		});

		int max = 0;
		String className = "";

		for (Map.Entry<String, Integer> entry : counts.entrySet()) {
			if (entry.getValue() == max) {
				className = minDistanceClass;
				System.out.println("equal max value");
			}

			if (entry.getValue() > max) {
				max = entry.getValue();
				className = entry.getKey();
			}
		}

		return className;
	}

	/**
	 * Percentage of correct forecasting.
	 *
	 * @return correct predictions in percentage.
	 */
	private static double percentCorrectPrediction() {
		return numberOfCorrectPredictions / testData.size() * 100;
	}
}