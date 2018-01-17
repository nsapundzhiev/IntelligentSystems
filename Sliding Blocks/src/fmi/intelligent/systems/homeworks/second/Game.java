package fmi.intelligent.systems.homeworks.second;

import java.util.Set;
import java.util.HashSet;
import java.util.Scanner;
import java.util.PriorityQueue;

import static fmi.intelligent.systems.homeworks.second.Direction.*;

class Game {
    private static final TableComparator comparator = new TableComparator();
    private static int size;
    private static int zeroPositionX;
    private static int[][] table;
    private static Table parent;
    private static Set<Table> visitedStates;
    private static PriorityQueue<Table> queue;

    public static void main(String[] args) {
        queue = new PriorityQueue<>(comparator);
        visitedStates = new HashSet<>();

        initTable();

        while (!hasSolution(table)) {
            System.out.println("This puzzle does not have solution, enter new puzzle");
            initTable();
        }

        parent = new Table(table);
        queue.add(parent);

        aStarSearch();

        parent.printPathAndDistance();
    }

    /**
     * A* search algorithm.
     */
    private static void aStarSearch() {
        while (queue.peek().getManhattanDistance() != 0) {
            Table currentTable = queue.poll();
            visitedStates.add(currentTable);

            if (currentTable.canGenerateLeftChild()) {
                addChildInQueueIfPossible(currentTable, LEFT);
            }

            if (currentTable.canGenerateRightChild()) {
                addChildInQueueIfPossible(currentTable, RIGHT);
            }

            if (currentTable.canGenerateDownChild()) {
                addChildInQueueIfPossible(currentTable, DOWN);
            }

            if (currentTable.canGenerateUpChild()) {
                addChildInQueueIfPossible(currentTable, UP);
            }
        }

        parent = queue.peek();
    }

    /**
     * Generate child from current table based in direction and add it to queue if it is not visited.
     *
     * @param currentTable current table,
     * @param direction    the direction we move zero.
     */
    private static void addChildInQueueIfPossible(Table currentTable, Direction direction) {
        Table child = currentTable.generateChild(currentTable, direction);

        if (!visitedStates.contains(child)) {
            queue.add(child);
        }
    }

    /**
     * Generate initial state of table based on user input.
     */
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

    /**
     * Check if initial state has solution.
     *
     * @param table initial state.
     * @return true if table has solution, otherwise return false.
     */
    private static boolean hasSolution(int[][] table) {
        int inversions = calculateInversions(table);

        return (isOdd(size) && isEven(inversions)) ||
                (isEven(size) && (isEven(zeroPositionX) == isEven(inversions)));
    }

    /**
     * Calculate inversions in table.
     *
     * @param table current table state.
     * @return number of inversions.
     */
    private static int calculateInversions(int[][] table) {
        int[] temp = fillTableInArray(table);
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

    /**
     * Fill data from table in array, with same order of elements.
     *
     * @param table current table.
     * @return array with data from table, without zero.
     */
    private static int[] fillTableInArray(int[][] table) {
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

        return temp;
    }

    private static boolean isEven(int number) {
        return number % 2 == 0;
    }

    private static boolean isOdd(int number) {
        return number % 2 == 1;
    }
}
