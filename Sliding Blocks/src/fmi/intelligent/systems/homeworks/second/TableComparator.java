package fmi.intelligent.systems.homeworks.second;

import java.util.Comparator;

public class TableComparator implements Comparator<Table> {

    @Override
    public int compare(Table x, Table y) {
        return x.getSummingFunction() - y.getSummingFunction();
    }
}
