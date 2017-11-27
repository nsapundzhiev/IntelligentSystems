package fmi.intelligent.systems.homeworks.second;

import java.util.HashMap;
import java.util.Map;

class Table {
	private Table parent;
	private Map<Integer, Pair> map;
	private static Map<Integer, Pair> solutionMap;

	private String move;
	private int size;
	private int distance;
	private int[][] table;
	private int zeroPositionX;
	private int zeroPositionY;
	private int manhattanDistance;


	Table(int[][] table, Table parent, String move) {
		this.table = table;
		this.parent = parent;
		this.move = move;
		map = new HashMap<>();
		size = this.table.length;

		buildMap(this.table);
		solutionMap = buildSolution();

		this.zeroPositionX = map.get(0).getX();
		this.zeroPositionY = map.get(0).getY();

		if (parent == null) {
			this.distance = 0;
		} else {
			this.distance = this.parent.getDistance() + 1;
		}

		calculateManhattanDistance();
	}

	private void buildMap(int[][] table) {
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				map.put(table[i][j], new Pair(i, j));
			}
		}
	}

	private Map<Integer, Pair> buildSolution() {
		if (solutionMap != null) {
			return solutionMap;
		}

		solutionMap = new HashMap<>();
		int n = 1;

		for (int i = 0; i < size * size; i++) {
			if (i != size * size - 1) {
				solutionMap.put(n, new Pair(i / size, i % size));
				++n;
			} else {
				solutionMap.put(0, new Pair(i / size, i % size));
			}
		}

		return solutionMap;
	}

	private void calculateManhattanDistance() {
		int number = 1;
		for (int i = 1; i < size * size; i++) {
			Pair mapPair = map.get(number);
			Pair solutionMapPair = solutionMap.get(number);
			manhattanDistance += Math.abs(mapPair.getX() - solutionMapPair.getX())
					+ Math.abs(mapPair.getY() - solutionMapPair.getY());

			++number;
		}
	}

	int getSummingFunction() {
		return distance + manhattanDistance;
	}

	int getManhattanDistance() {
		return manhattanDistance;
	}

	private int getDistance() {
		return distance;
	}

	public Table getParent() {
		return parent;
	}

	private void swapZeroPosition(int[][] table, int x, int y) {
		table[zeroPositionX][zeroPositionY] = table[x][y];
		table[x][y] = 0;
	}

	boolean canGenerateLeftChild() {
		return zeroPositionY - 1 >= 0;
	}

	boolean canGenerateRightChild() {
		return zeroPositionY + 1 <= size - 1;
	}

	boolean canGenerateDownChild() {
		return zeroPositionX + 1 <= size - 1;
	}

	boolean canGenerateUpChild() {
		return zeroPositionX - 1 >= 0;
	}

	Table generateChild(Table parent, Direction direction) {
		int[][] childTable = new int[size][size];

		for (int i = 0; i < size; i++) {
			System.arraycopy(table[i], 0, childTable[i], 0, size);
		}

		switch (direction) {
			case LEFT:
				swapZeroPosition(childTable, zeroPositionX, zeroPositionY - 1);
				move = "right";
				break;
			case RIGHT:
				swapZeroPosition(childTable, zeroPositionX, zeroPositionY + 1);
				move = "left";
				break;
			case UP:
				swapZeroPosition(childTable, zeroPositionX - 1, zeroPositionY);
				move = "down";
				break;
			case DOWN:
				swapZeroPosition(childTable, zeroPositionX + 1, zeroPositionY);
				move = "up";
				break;
		}

		return new Table(childTable, parent, move);
	}

	@Override
	public String toString() {
		String result = "";

		for (int[] aTable : table) {
			for (int j = 0; j < table.length; j++) {
				result += aTable[j] + " ";
			}
			result += "\n";
		}
		return result;
	}

	public String getMove() {
		return move;
	}

	public void setMove(String move) {
		this.move = move;
	}
}
