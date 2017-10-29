package fmi.intelligent.systems.homeworks.first;

import java.util.Arrays;

final class MapBuilder {

	/**
	 * Check if frogs are ordered in correct way.
	 * @param frogsMap frogs map.
	 * @param solution expected solution of the game.
	 * @return true if frogs are ordered like in solution, else return false.
	 */
	static boolean isSolution(char[] frogsMap, char[] solution) {
		return Arrays.equals(solution, frogsMap);
	}

	/**
	 * Build frogs map in the start of the game.
	 * @param n number of frogs on each side.
	 * @return starter frogs map.
	 */
	static char[] buildFrogsMap(int n) {
		if (n < 1) {
			return null;
		}

		char[] frogsMap = new char[2 * n + 1];

		for (int i = 0; i < n; i++) {
			frogsMap[i] = new LeftFrog().leftFrogSymbol();
		}

		frogsMap[n] = '_';

		for (int i = 0; i < n; i++) {
			frogsMap[n + 1 + i] = new RightFrog().rightFrogSymbol();
		}

		return frogsMap;
	}

	/**
	 * Build expected solution of the game.
	 * @param frogsMap frogs map in the start of the game.
	 * @return solution of the game.
	 */
	static char[] buildSolution(char[] frogsMap) {
		int arraySize = frogsMap.length;

		char[] tempFrogsMap = Arrays.copyOf(frogsMap, arraySize);

		for (int i = 0; i < arraySize / 2; i++) {
			char temp = tempFrogsMap[i];
			tempFrogsMap[i] = tempFrogsMap[arraySize - i - 1];
			tempFrogsMap[arraySize - i - 1] = temp;
		}

		return tempFrogsMap;
	}
}
