package fmi.intelligent.systems.homeworks.second;

import java.util.*;

import static fmi.intelligent.systems.homeworks.second.Direction.*;

class Game {
	private static int size;
	private static int[][] table;
	private static int zeroPositionX;
	private static PriorityQueue<Table> queue;
	private static final TableComparator comparator = new TableComparator();
	private static Queue<String> path;


	public static void main(String[] args) {
		queue = new PriorityQueue<>(comparator);
//		visitedStates = new HashSet<>();
		path = new LinkedList<>();

		initTable();

		while (!hasSolution(table)) {
			System.out.println("This puzzle does not have solution, enter new puzzle");
			initTable();
		}

		Table parent = new Table(table, null, null);
		queue.add(parent);
//		visitedStates.add(parent);
		aStarSearch();

		System.out.println(path.size());
		path.forEach(System.out::println);
	}


	private static void aStarSearch() {
		Table finalChild = queue.peek();
		finalChild.setMove("");
		while (queue.peek().getManhattanDistance() != 0) {
			Table t = queue.poll();

			if (t.getParent() != null) {
				path.add(t.getMove());
			}

			if (t.canGenerateLeftChild()) {
				Table child = t.generateChild(t, LEFT);

//				if (!visitedStates.contains(child)) {
				queue.add(child);
//				}
			}

			if (t.canGenerateRightChild()) {
				Table child = t.generateChild(t, RIGHT);

//				if (!visitedStates.contains(child)) {
				queue.add(child);
//				}
			}

			if (t.canGenerateDownChild()) {
				Table child = t.generateChild(t, DOWN);

//				if (!visitedStates.contains(child)) {
				queue.add(child);
//				}
			}

			if (t.canGenerateUpChild()) {
				Table child = t.generateChild(t, UP);

//				if (!visitedStates.contains(child)) {
				queue.add(child);
//				}
			}

			finalChild = queue.peek();
		}

		path.add(finalChild.getMove());
	}

	private static void initTable() {
		Scanner input = new Scanner(System.in);
		size = input.nextInt();

		table = new int[size][size];

		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				System.out.print("[" + i + "]" + "[" + j + "] = ");
				table[i][j] = input.nextInt();

				if (table[i][j] == 0) {
					zeroPositionX = i;
				}
			}
		}
	}

	private static boolean hasSolution(int[][] table) {
		int inversions = calculateInversions(table);

		return (isOdd(size) && isEven(inversions)) ||
				(isEven(size) && (isEven(zeroPositionX) == isEven(inversions)));
	}

	private static int calculateInversions(int[][] table) {
		int[] temp = new int[size * size - 1];
		int k = 0;
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (table[i][j] == 0) {
					continue;
				}

				temp[k] = table[i][j];
				++k;
			}
		}

		int inversions = 0;

		for (int i = 0; i < size * size - 2; i++) {
			for (int j = i + 1; j < size * size - 1; j++) {
				if (temp[i] > temp[j]) {
					++inversions;
				}
			}
		}

		return inversions;
	}

	private static boolean isEven(int number) {
		return number % 2 == 0;
	}

	private static boolean isOdd(int number) {
		return number % 2 == 1;
	}
//
//	private static void printTable(int[][] table) {
//		for (int[] aTable : table) {
//			for (int j = 0; j < table.length; j++) {
//				System.out.print(aTable[j] + " ");
//			}
//			System.out.println();
//		}
//	}
}
