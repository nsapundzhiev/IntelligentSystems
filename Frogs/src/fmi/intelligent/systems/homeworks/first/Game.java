package fmi.intelligent.systems.homeworks.first;

import java.util.LinkedList;

import static fmi.intelligent.systems.homeworks.first.Frog.JUMP;
import static fmi.intelligent.systems.homeworks.first.Frog.STEP;

class Game {
    /**
     * Holds solution.
     */
    private static char[] solution;

    /**
     * Map length.
     */
    private static int MAP_LENGTH;

    /**
     * Holds path to the solution.
     */
    private static LinkedList<char[]> path = new LinkedList<>();

    /**
     * Instance of Left Frog.
     */
    private static LeftFrog leftFrog = new LeftFrog();

    /**
     * Instance of Right Frog.
     */
    private static RightFrog rightFrog = new RightFrog();

    /**
     * Main function of the program, which is running the game.
     */
    public static void main(String[] args) {
        // number of frogs
        int n = 20;

        // build map with frogs
        char[] map = MapBuilder.buildFrogsMap(n);

        //build solution with frogs
        solution = MapBuilder.buildSolution(map);

        if (map == null) {
            System.out.println("Wrong frogs number!");
            return;
        }

        MAP_LENGTH = map.length;

        path.addLast(map);

        dfs(0, map);

        for (char[] arr : path) {
            printCharArray(arr);
        }
    }

    /**
     * Depth-first search algorithm to find how to swap the positions of the frogs on both sides.
     *
     * @param currentPosition frog current position.
     * @param map             representation of frogs on map.
     * @return true if found correct solution, else return false.
     */
    private static boolean dfs(int currentPosition, char[] map) {
        if (MapBuilder.isSolution(map, solution)) {
            return true;
        }

        if (currentPosition >= MAP_LENGTH) {
            return false;
        }

        if (path.isEmpty()) {
            return false;
        }

        while (currentPosition < MAP_LENGTH) {
            char[] newMap;

            if (leftFrog.canStep(map, currentPosition)) {
                newMap = makeMoveAndGetNewMap(leftFrog, currentPosition, map, STEP);
                if (newMap != null) {
                    if (dfs(0, newMap)) {
                        return true;
                    } else {
                        path.removeLast();
                    }
                }
            }

            if (leftFrog.canJump(map, currentPosition)) {
                newMap = makeMoveAndGetNewMap(leftFrog, currentPosition, map, JUMP);
                if (newMap != null) {
                    if (dfs(0, newMap)) {
                        return true;
                    } else {
                        path.removeLast();
                    }
                }
            }

            if (rightFrog.canStep(map, currentPosition)) {
                newMap = makeMoveAndGetNewMap(rightFrog, currentPosition, map, STEP);
                if (newMap != null) {
                    if (dfs(0, newMap)) {
                        return true;
                    } else {
                        path.removeLast();
                    }
                }
            }

            if (rightFrog.canJump(map, currentPosition)) {
                newMap = makeMoveAndGetNewMap(rightFrog, currentPosition, map, JUMP);
                if (newMap != null) {
                    if (dfs(0, newMap)) {
                        return true;
                    } else {
                        path.removeLast();
                    }
                }
            }

            ++currentPosition;
        }

        return false;
    }

    /**
     * Check if is possible to move frog from this position with move (step or jump)
     * and swap positions if it is possible.
     *
     * @param frog     what kind of frog is on the position.
     * @param position frog position.
     * @param map      representation of frogs on map.
     * @param move     kind of move - step or jump.
     * @return new map with swapped positions.
     */
    private static char[] makeMoveAndGetNewMap(Frog frog, int position, char[] map, int move) {
        char[] newMap = null;
        if (frog.canStepOrJump(position, map, move)) {
            newMap = new char[MAP_LENGTH];
            System.arraycopy(map, 0, newMap, 0, MAP_LENGTH);
            newMap = frog.swapPosition(position, newMap, move);
            path.addLast(newMap);
        }

        return newMap;
    }

    /**
     * Print char array in a good way.
     *
     * @param charArray array with chars.
     */
    private static void printCharArray(char[] charArray) {
        System.out.println(new String(charArray));
    }
}
