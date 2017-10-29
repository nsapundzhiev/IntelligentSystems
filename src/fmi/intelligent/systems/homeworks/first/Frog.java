package fmi.intelligent.systems.homeworks.first;

public abstract class Frog {

	/** Step length. */
	static final int STEP = 1;

	/** Jump length. */
	static final int JUMP = 2;

	/**
	 * Swap positions of the frog on current position.
	 * @param position frog current position.
	 * @param map representation of map.
	 * @param moveLength kind of move (step or jump).
	 * @return new representation of map with changed positions.
	 */
	protected abstract char[] swapPosition(int position, char[] map, int moveLength);

	/**
	 * Check if it is possible frog from current position to move with moveLength.
	 * @param position frog current position.
	 * @param map representation of map.
	 * @param moveLength kind of move (step or jump).
	 * @return true if it is possible to move, else return false.
	 */
	protected abstract boolean canStepOrJump(int position, char[] map, int moveLength);

	/**
	 * Check if there is a possible move on this map.
	 * @param map representation of map.
	 * @return true if move can be made, else return false.
	 */
	boolean isThereMoves(char[] map) {
		for (int i = 0; i < map.length; i++) {
			if (new LeftFrog().canStepOrJump(i, map, STEP)
					|| new LeftFrog().canStepOrJump(i, map, JUMP)
					|| new RightFrog().canStepOrJump(i, map, STEP)
					|| new RightFrog().canStepOrJump(i, map, JUMP)) {
				return true;
			}
		}

		return false;
	}
}
