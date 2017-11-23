package fmi.intelligent.systems.homeworks.third;

import java.util.Random;
import java.util.Scanner;

class NQueensSolver {
	private static int[] conflicts;
	private static int[] queensTable;
	private static Random rand;

	private static void randInitTable(int queensCount) {
		queensTable = new int[queensCount];

		for (int i = 0; i < queensCount; i++) {
			rand = new Random();
			int randNumber = rand.nextInt(queensCount);

			queensTable[i] = randNumber;
		}

		calculateConflicts(queensCount);
	}

	private static void calculateConflicts(int queensCount) {
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

	private static int getColumnWithMaxConflicts() {
		int column = 0;
		int maxConflicts = conflicts[0];

		for (int i = 1; i < conflicts.length; i++) {
			if (maxConflicts < conflicts[i]) {
				maxConflicts = conflicts[i];
				column = i;
			}

			if (maxConflicts == conflicts[i]) {
				column = rand.nextBoolean() ? column : i;
			}
		}

		return column;
	}

	private static int getRowWithMinConflicts(int column, int queensCount) {
		int[] confl = new int[queensCount];
		int min = -1;
		int row = 0;

		for (int j = 0; j < queensCount; j++) {
			if (queensTable[column] == queensTable[j]) {
				++confl[j];
			}

			if (queensTable[column] - column == queensTable[j] - j) {
				++conflicts[j];
			}

			if (queensTable[column] + column == queensTable[j] + j) {
				++conflicts[j];
			}
		}

		for (int i = 0; i < queensCount; i++) {
			if (min > queensTable[i]) {
				min = queensTable[i];
				row = i;
			}
		}

		return row;
	}

	private static boolean hasConflicts() {
		for (int conflict : conflicts) {
			if (conflict > 1) {
				return true;
			}
		}

		return false;
	}

	private static void solve(int queensCount) {
		randInitTable(queensCount);

		int k = 0;
		while (k < Integer.MAX_VALUE) {
			int colMaxConfl = getColumnWithMaxConflicts();
			int rowMinConfl = getRowWithMinConflicts(colMaxConfl, queensCount);

			queensTable[colMaxConfl] = rowMinConfl;

			calculateConflicts(queensCount);

			if (!hasConflicts()) {
				break;
			}

			++k;
		}

		if (hasConflicts()) {
			solve(queensCount);
		}
	}


	private static void printQueensTable(int[] table) {
		for (int i = 0; i < table.length; i++) {
			for (int queen : table) {
				if (i == queen) {
					System.out.print("* ");
				} else {
					System.out.print("_ ");
				}
			}
			System.out.println();
		}
	}

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		System.out.println("Enter the number of queensTable: ");
		int queensCount = input.nextInt();

		solve(queensCount);
		printQueensTable(queensTable);
		System.out.println("----------------");
	}
}

