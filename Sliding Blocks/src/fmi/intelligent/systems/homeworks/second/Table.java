package fmi.intelligent.systems.homeworks.second;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

class Table {
    private static Map<Integer, Pair> solutionMap = new HashMap<>();
    private int size;
    private int distance;
    private int zeroPositionX;
    private int zeroPositionY;
    private int manhattanDistance;
    private int[][] table;
    private String[] path;
    private String move;
    private Table parent;
    private Map<Integer, Pair> map;

    /**
     * Constructor for initial - parent state.
     *
     * @param table initial state.
     */
    Table(int[][] table) {
        this.table = table;
        this.parent = null;
        this.move = "";
        this.map = new HashMap<>();
        this.size = this.table.length;
        this.path = new String[0];
        this.distance = 0;

        fillMapFromTable(this.table);

        this.zeroPositionX = map.get(0).getX();
        this.zeroPositionY = map.get(0).getY();

        buildSolution();
        calculateManhattanDistance();
    }

    /**
     * Constructor for children states.
     *
     * @param table  current table state.
     * @param parent parent state.
     * @param move   the way we get the current state from the parent.
     */
    Table(int[][] table, Table parent, String move) {
        this.table = table;
        this.parent = parent;
        this.move = move;
        this.map = new HashMap<>();
        this.size = this.table.length;

        int parentPathSize = parent.getPath().length;
        this.path = new String[parentPathSize + 1];
        System.arraycopy(parent.getPath(), 0, this.path, 0, parentPathSize);
        this.path[parentPathSize] = move;

        fillMapFromTable(this.table);

        this.zeroPositionX = map.get(0).getX();
        this.zeroPositionY = map.get(0).getY();
        this.distance = this.parent.getDistance() + 1;

        calculateManhattanDistance();
    }

    /**
     * Fill table with data from matrix table.
     *
     * @param table current table state.
     */
    private void fillMapFromTable(int[][] table) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                map.put(table[i][j], new Pair(i, j));
            }
        }
    }

    /**
     * Build solution of the table.
     */
    private void buildSolution() {
        int n = 1;

        for (int i = 0; i < size * size; i++) {
            if (i != size * size - 1) {
                solutionMap.put(n, new Pair(i / size, i % size));
                ++n;
            } else {
                solutionMap.put(0, new Pair(i / size, i % size));
            }
        }
    }

    /**
     * Calculate Manhattan distance between current and goal states.
     */
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

    /**
     * Generate child from parent.
     *
     * @param parent    parent instance.
     * @param direction the direction we move zero.
     * @return new table with changed position of zero.
     */
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

    /**
     * Swap position of zero.
     *
     * @param table current table.
     * @param x     new x coordinate of zero.
     * @param y     new y coordinate of zero.
     */
    private void swapZeroPosition(int[][] table, int x, int y) {
        table[zeroPositionX][zeroPositionY] = table[x][y];
        table[x][y] = 0;
    }


    boolean canGenerateRightChild() {
        return zeroPositionY + 1 <= size - 1;
    }

    boolean canGenerateLeftChild() {
        return zeroPositionY - 1 >= 0;
    }

    boolean canGenerateDownChild() {
        return zeroPositionX + 1 <= size - 1;
    }

    boolean canGenerateUpChild() {
        return zeroPositionX - 1 >= 0;
    }

    int getSummingFunction() {
        return distance + manhattanDistance;
    }

    int getManhattanDistance() {
        return manhattanDistance;
    }

    private String[] getPath() {
        return path;
    }

    private int getDistance() {
        return distance;
    }

    void printPathAndDistance() {
        System.out.println("Distance: " + distance);
        System.out.println("Moves: ");

        Arrays.stream(path).forEach(System.out::println);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Table)) return false;

        Table table1 = (Table) o;

        return zeroPositionX == table1.zeroPositionX
                && zeroPositionY == table1.zeroPositionY
                && Arrays.deepEquals(table, table1.table);
    }

    @Override
    public int hashCode() {
        int result = Arrays.deepHashCode(table);

        result = 31 * result + zeroPositionX;
        result = 31 * result + zeroPositionY;

        return result;
    }
}
