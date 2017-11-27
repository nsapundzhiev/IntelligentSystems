package fmi.intelligent.systems.homeworks.fourth;

import java.util.Comparator;

class ChromosomeComparator implements Comparator<Chromosome> {

	@Override
	public int compare(Chromosome x, Chromosome y) {
		return y.getValue() - x.getValue();
	}
}