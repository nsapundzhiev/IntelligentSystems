package fmi.intelligent.systems.homeworks.sixth;

import java.util.List;
import java.util.Random;
import java.util.ArrayList;

import java.io.IOException;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;


class DataProcessing {

	/**
	 * Test data list.
	 */
	static List<Iris> testData = new ArrayList<>();       // 20

	/**
	 * Training data list.
	 */
	static List<Iris> trainingData = new ArrayList<>();  // 130

	/**
	 * Load data from file in trainingData list.
	 */
	static void loadData() {
		Path path = Paths.get("/home/nikolai/IdeaProjects/IntelligenSystems/" +
				"src/fmi/intelligent/systems/homeworks/sixth/resources/irisData.txt");

		try {
			Files.lines(path).forEach(
					line -> {
						String[] elements = line.split(",");
						trainingData.add(
								new Iris(Double.parseDouble(elements[0]),
										Double.parseDouble(elements[1]),
										Double.parseDouble(elements[2]),
										Double.parseDouble(elements[3]),
										elements[4])
						);
					}
			);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Separate data randomly in testData and trainingData.
	 */
	static void chooseTestAndTrainData() {
		Random rand = new Random();

		for (int i = 0; i < 20; i++) {
			Iris iris = trainingData.get(rand.nextInt(150 - i));
			testData.add(iris);
			trainingData.remove(iris);
		}
	}
}
