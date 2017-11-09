package fmi.intelligent.systems.homeworks.first;

final class RightFrog extends Frog {

    @Override
    public char[] swapPosition(int position, char[] map, int moveLength) {
        map[position - moveLength] = map[position];
        map[position] = '_';

        return map;
    }

    @Override
    public boolean canStepOrJump(int position, char[] map, int moveLength) {
        return map != null
                && map[position] == rightFrogSymbol()
                && position - moveLength >= 0
                && position <= map.length - 1
                && map[position - moveLength] == '_';
    }

    /**
     * Return representation of right frog symbol on the map.
     */
    char rightFrogSymbol() {
        return 'R';
    }
}
