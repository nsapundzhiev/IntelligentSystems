package fmi.intelligent.systems.homeworks.fourth;

import java.util.Arrays;
import java.util.Random;

public class Main {

	private static final int POPULATION_SIZE = 100;
	private static final int NEXT_GENERATION_SIZE = 20;
	private static final int SURVIVOR_POPULATION_SIZE = 80;
	private static final int MUTATION_INDIVIDUALS = 5;
	private static final int POPULATIONS = 200;
	private static ChromosomeFactory chromosomeFactory;
	private static Random rand;


	public static void main(String[] args) {
		chromosomeFactory = new ChromosomeFactory(40000);
		rand = new Random();

		DataLoader.loadData();

		int itemsCount = DataLoader.getItemsCount();

		Chromosome[] population = chromosomeFactory.generatePopulation(POPULATION_SIZE, itemsCount);
		Arrays.sort(population, new ChromosomeComparator());

		System.out.println("First population best chromosome:");
		printBestChromosome(population);

		for (int k = 0; k < POPULATIONS; k++) {
			Chromosome[] nextGeneration = generateNextGeneration(population);

			mutationOfNextGeneration(nextGeneration);

			System.arraycopy(nextGeneration, 0, population, SURVIVOR_POPULATION_SIZE, NEXT_GENERATION_SIZE);

			Arrays.sort(population, new ChromosomeComparator());

			if (k == 10 || k == 20 || k == 50 || k == 100) {
				System.out.println(k + "th population best chromosome:");
				printBestChromosome(population);
			}
		}

		System.out.println("Last generation best chromosome:");
		printBestChromosome(population);
	}

	/**
	 * Mutation of part of new generation.
	 *
	 * @param nextGeneration new generation.
	 */
	private static void mutationOfNextGeneration(Chromosome[] nextGeneration) {
		for (int i = 0; i < MUTATION_INDIVIDUALS; i++) {
			int randomMutation = rand.nextInt(NEXT_GENERATION_SIZE);

			chromosomeFactory.mutation(nextGeneration[randomMutation].getBinaryChromosome());
		}
	}

	/**
	 * Generates new generation of the fittest individuals of the old generation.
	 *
	 * @param population old generation.
	 * @return array with only new generation.
	 */
	private static Chromosome[] generateNextGeneration(Chromosome[] population) {
		Chromosome[] nextGeneration = new Chromosome[NEXT_GENERATION_SIZE];

		for (int i = 0; i < NEXT_GENERATION_SIZE; i = i + 2) {
			int parentOne = rand.nextInt(SURVIVOR_POPULATION_SIZE);
			int parentTwo = rand.nextInt(SURVIVOR_POPULATION_SIZE);

			Chromosome[] children = chromosomeFactory.generateChildrenChromosome(population[parentOne], population[parentTwo]);

			nextGeneration[i] = children[0];
			nextGeneration[i + 1] = children[1];
		}

		return nextGeneration;
	}

	private static void printBestChromosome(Chromosome[] population) {
		System.out.println("Value: " + population[0].getValue());
		System.out.println("Weight: " + population[0].getWeight());
		System.out.println();
	}
}
