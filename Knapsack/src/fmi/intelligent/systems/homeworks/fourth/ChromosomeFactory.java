package fmi.intelligent.systems.homeworks.fourth;

import java.util.List;
import java.util.Random;

class ChromosomeFactory {
	/**
	 * Amount weight that can be collected in the knapsack
	 */
	private int M;

	private static Random rand = new Random();

	ChromosomeFactory(int M) {
		this.M = M;
	}

	/**
	 * Generate initial population.
	 *
	 * @param populationSize count of chromosomes in population.
	 * @param itemsCount     all items count.
	 * @return array of chromosomes.
	 */
	Chromosome[] generatePopulation(int populationSize, int itemsCount) {
		Chromosome[] population = new Chromosome[populationSize];

		for (int i = 0; i < populationSize; i++) {
			population[i] = generateInitialChromosome(itemsCount);
			fitnessFunction(population[i]);
		}

		return population;
	}

	/**
	 * Generate children with crossover.
	 *
	 * @param parentChromosomeOne chromosome of first parent.
	 * @param parentChromosomeTwo chromosome of second parent.
	 * @return array with two children of parents after crossover.
	 */
	Chromosome[] generateChildrenChromosome(Chromosome parentChromosomeOne, Chromosome parentChromosomeTwo) {
		int itemsCount = DataLoader.getItemsCount();
		int locus = rand.nextInt(itemsCount - 5) + 5;

		boolean[] binaryChromosomeChildOne = new boolean[itemsCount];
		boolean[] binaryChromosomeChildTwo = new boolean[itemsCount];

		boolean[] parentOne = parentChromosomeOne.getBinaryChromosome();
		boolean[] parentTwo = parentChromosomeTwo.getBinaryChromosome();

		System.arraycopy(parentOne, 0, binaryChromosomeChildOne, 0, locus);
		System.arraycopy(parentTwo, locus, binaryChromosomeChildOne, locus, itemsCount - locus);

		System.arraycopy(parentTwo, 0, binaryChromosomeChildTwo, 0, locus);
		System.arraycopy(parentOne, locus, binaryChromosomeChildTwo, locus, itemsCount - locus);

		Chromosome[] children = new Chromosome[2];

		children[0] = new Chromosome(binaryChromosomeChildOne);
		fitnessFunction(children[0]);

		children[1] = new Chromosome(binaryChromosomeChildTwo);
		fitnessFunction(children[1]);

		return children;
	}

	/**
	 * Mutate with 1 bit selected chromosome.
	 *
	 * @param binaryChromosome binary chromosome.
	 */
	void mutation(boolean[] binaryChromosome) {
		int itemsCount = binaryChromosome.length;
		int mutationBit = rand.nextInt(itemsCount);

		binaryChromosome[mutationBit] = !binaryChromosome[mutationBit];
	}

	/**
	 * Generate chromosome.
	 *
	 * @param itemsCount all items count.
	 * @return generated chromosome instance.
	 */
	private Chromosome generateInitialChromosome(int itemsCount) {
		boolean[] binaryChromosome = new boolean[itemsCount];
		Chromosome chromosome = new Chromosome(binaryChromosome);

		for (int i = 0; i < itemsCount; i++) {
			binaryChromosome[i] = rand.nextBoolean();
		}

		return chromosome;
	}

	/**
	 * Calculate weight of current chromosome.
	 *
	 * @param chromosome chromosome.
	 */
	private void fitnessFunction(Chromosome chromosome) {
		boolean[] binaryChromosome = chromosome.getBinaryChromosome();
		int weight = chromosome.getWeight();
		int value = chromosome.getValue();

		List<Integer> weights = DataLoader.getWeights();
		List<Integer> values = DataLoader.getValues();

		for (int i = 0; i < binaryChromosome.length; i++) {
			if (weight + weights.get(i) > M) {
				break;
			}

			value += binaryChromosome[i] ? values.get(i) : 0;
			weight += binaryChromosome[i] ? weights.get(i) : 0;
		}

		chromosome.setValue(value);
		chromosome.setWeight(weight);
	}
}