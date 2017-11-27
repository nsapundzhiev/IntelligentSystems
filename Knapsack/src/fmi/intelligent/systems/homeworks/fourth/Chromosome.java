package fmi.intelligent.systems.homeworks.fourth;

class Chromosome {
	/**
	 * The sum of the values of all items.
	 */
	private int value;

	/**
	 * The sum of the weights of all items.
	 */
	private int weight;

	/**
	 * Binary representation of which item is in chromosome and which not.
	 */
	private boolean[] binaryChromosome;

	/**
	 * Constructor.
	 * @param binaryChromosome binary representation of which item is in chromosome and which not.
	 */
	Chromosome(boolean[] binaryChromosome) {
		this.binaryChromosome = binaryChromosome;
	}


	int getValue() {
		return value;
	}

	int getWeight() {
		return weight;
	}

	void setValue(int value) {
		this.value = value;
	}

	void setWeight(int weight) {
		this.weight = weight;
	}

	boolean[] getBinaryChromosome() {
		return binaryChromosome;
	}
}