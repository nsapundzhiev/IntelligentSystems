package fmi.intelligent.systems.homeworks.third;

import java.util.Random;

class NQueensSolver {

	/**
	 * Coefficient of which depends how many times queues should be shuffled
	 * before starting algorithm again.
	 */
	private static final int COEFFICIENT = 3;

	/**
	 * Queens Count.
	 */
	private int queensCount;

	/**
	 * Array with queens conflicts.
	 */
	private int[] conflicts;

	/**
	 * Table with queens positions - array index is column and value on index is row.
	 */
	private int[] queensTable;

	/**
	 * Random instance.
	 */
	private Random randomInstance;

	/**
	 * Constructor.
	 *
	 * @param queensCount queens count.
	 */
	NQueensSolver(int queensCount) {
		this.queensCount = queensCount;
	}

	/**
	 * Solve nqueens with min conflicts algorithm.
	 */
	void solve() {
		randInitTable();

		for (int i = 0; i < queensCount * COEFFICIENT; i++) {
			int columnWithMaxConflicts = getColumnWithMaxConflicts();

			queensTable[columnWithMaxConflicts] = getRowWithMinConflicts(columnWithMaxConflicts);

			calculateConflicts();

			if (!hasConflicts()) {
				break;
			}
		}

		if (hasConflicts()) {
			solve();
		}
	}

	/**
	 * Print board with solution.
	 * ('*' - queen, 'o' - empty field)
	 */
	void printQueensTable() {
		for (int i = 0; i < queensCount; i++) {
			for (int queen : queensTable) {
				if (i == queen) {
					System.out.print("* ");
				} else {
					System.out.print("o ");
				}
			}
			System.out.println();
		}
	}

	/**
	 * Generate initial table with queens.
	 */
	private void randInitTable() {
		randomInstance = new Random();
		queensTable = new int[queensCount];

		for (int i = 0; i < queensCount; i++) {
			queensTable[i] = randomInstance.nextInt(queensCount);
		}

		calculateConflicts();
	}

	/**
	 * Calculates every queen conflicts.
	 */
	private void calculateConflicts() {
		conflicts = new int[queensCount];

		for (int i = 0; i < queensCount - 1; i++) {
			for (int j = i + 1; j < queensCount; j++) {
				if (queensTable[i] == queensTable[j]) {
					++conflicts[i];
					++conflicts[j];
				}

				if (queensTable[i] - i == queensTable[j] - j) {
					++conflicts[i];
					++conflicts[j];
				}

				if (queensTable[i] + i == queensTable[j] + j) {
					++conflicts[i];
					++conflicts[j];
				}
			}
		}
	}

	/**
	 * Calculate on which column have max number of conflicts.
	 *
	 * @return column with max number of conflicts, if there are more than one
	 * columns with equal number of conflicts return randomly one of them.
	 */
	private int getColumnWithMaxConflicts() {
		int column = 0;
		int maxConflicts = conflicts[0];

		for (int i = 1; i < conflicts.length; i++) {
			if (maxConflicts < conflicts[i]) {
				maxConflicts = conflicts[i];
				column = i;
			}

			if (maxConflicts == conflicts[i]) {
				column = randomInstance.nextBoolean() ? column : i;
			}
		}

		return column;
	}

	/**
	 * Calculate on which row of a column there are min number of conflicts.
	 *
	 * @param column column with max conflicts.
	 * @return row of a column with min number of conflicts, if there are
	 * more than one rows with equal number of conflicts return randomly one of them.
	 */
	private int getRowWithMinConflicts(int column) {
		int[] tempTable = new int[queensCount];
		int[] columnConflicts = new int[queensCount];

		for (int i = 0; i < queensCount; i++) {
			System.arraycopy(queensTable, 0, tempTable, 0, queensCount);
			tempTable[column] = i;

			for (int j = 0; j < queensCount; j++) {
				if (column == j) {
					continue;
				}

				if (tempTable[column] == tempTable[j]) {
					++columnConflicts[i];
				}

				if (tempTable[column] - column == tempTable[j] - j) {
					++columnConflicts[i];
				}

				if (tempTable[column] + column == tempTable[j] + j) {
					++columnConflicts[i];
				}
			}
		}

		int row = getMinConflicts(columnConflicts);

		conflicts[column] = columnConflicts[row];

		return row;
	}

	/**
	 * Check if there are conflicts.
	 * @return true if there are conflicts, false otherwise.
	 */
	private boolean hasConflicts() {
		for (int conflict : conflicts) {
			if (conflict >= 1) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Get row of a column on which there are min number of conflicts.
	 *
	 * @param conflicts table with conflicts on column.
	 * @return row with min number of conflicts.
	 */
	private int getMinConflicts(int[] conflicts) {
		int min = Integer.MAX_VALUE;
		int row = 0;

		for (int k = 0; k < queensCount; k++) {
			if (min == conflicts[k]) {
				row = randomInstance.nextBoolean() ? row : k;
			}

			if (min > conflicts[k]) {
				min = conflicts[k];
				row = k;
			}
		}

		return row;
	}
}

